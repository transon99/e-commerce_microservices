package com.sondev.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.common.exceptions.MissingInputException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.PagingData;
import com.sondev.common.response.ResponseMessage;
import com.sondev.common.utils.PaginationUtils;
import com.sondev.config.VNPayConfig;
import com.sondev.dto.request.PaymentRequest;
import com.sondev.dto.request.StripeItemRequest;
import com.sondev.dto.response.CartDto;
import com.sondev.dto.response.CartItemDto;
import com.sondev.dto.response.PaymentDto;
import com.sondev.dto.response.ProductDto;
import com.sondev.entity.Payment;
import com.sondev.entity.PaymentStatus;
import com.sondev.feignclient.CartClient;
import com.sondev.feignclient.OrderClient;
import com.sondev.feignclient.ProductClient;
import com.sondev.mapper.PaymentMapper;
import com.sondev.repository.PaymentRepository;
import com.sondev.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final CartClient cartClient;
    private final ProductClient productClient;
    private final ObjectMapper objectMapper;

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @Value("${CLIENT_BASE_URL}")
    private String clientBaseURL;

    private static final String SUCCESS_VNPAY_CODE = "00";

    public String createPayment(PaymentRequest paymentRequest, String token)
            throws UnsupportedEncodingException, StripeException {

        CartDto cartDto = new CartDto();

        ResponseMessage cartResponse = cartClient.getCartByUser(token).getBody();
        assert cartResponse != null;
        if (cartResponse.getData() != null) {
            cartDto = objectMapper.convertValue(cartResponse.getData(), CartDto.class);
        }
        List<StripeItemRequest> stripeItemRequestList = new ArrayList<>();
        List<CartItemDto> cartItemDtoList = cartDto.getCartItemDtoList();
        for(CartItemDto cartItemDto: cartItemDtoList){
            ProductDto productDto = new ProductDto();
            ResponseMessage productResponse = productClient.findById(cartItemDto.getProductId(),token).getBody();
            assert productResponse != null;
            if (productResponse.getData() != null) {
                productDto = objectMapper.convertValue(productResponse.getData(), ProductDto.class);
            }

            stripeItemRequestList.add(StripeItemRequest.builder()
                            .quantity(cartItemDto.getQuantity())
                            .productName(productDto.getName())
                    .build());
        }

        switch (paymentRequest.getPaymentMethod()) {
            case VN_PAY:
                String paymentUrl = payWithVN_Pay(paymentRequest.getTotalPrice());

                if (StringUtils.equals(extractResponseCodeFromUrl(paymentUrl), SUCCESS_VNPAY_CODE)) {
                    Payment payment = paymentMapper.reqToEntity(paymentRequest);
                    payment.setPaymentStatus(PaymentStatus.COMPLETED);
                    return paymentMapper.toDto(paymentRepository.save(payment)).getId();
                }else {

                }
                break;
            case STRIPE:
                Session session = payWithStripe(stripeItemRequestList);
                break;
        }
        return null;
    }

    public PaymentDto findById(String paymentId) {
        if (paymentId == null) {
            throw new MissingInputException("Missing input paymentId");
        }
        PaymentDto paymentDto = paymentMapper.toDto(paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Can't find product with id " + paymentId)));

        log.info("PaymentServiceImpl | findById | PaymentDto :" + paymentDto.toString());

        return paymentDto;
    }

    public PagingData getPayments(Integer offset, Integer pageSize, String sortStr) {

        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        Page<Payment> paymentPage = paymentRepository.findAll(pageable);

        return PagingData.builder()
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(paymentPage.getTotalElements())
                .build();
    }

    public PaymentDto updatePayment(Map<String, Object> fields, String paymentId) {

        Payment currentPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Can't find payment with id" + paymentId));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Payment.class, key);
            if (field == null) {
                throw new NullPointerException("Can't find any field");
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field, currentPayment, value);
        });

        return paymentMapper.toDto(paymentRepository.save(currentPayment));
    }

    public String deleteById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        if (paymentRepository.findById(id).isEmpty()) {
            log.error("Unable to delete non-existent data!");
            throw new NotFoundException("Unable to delete non-existent data!");
        }
        paymentRepository.deleteById(id);
        return id;
    }

    private String extractResponseCodeFromUrl(String url) {
        return null;
    }

    private String payWithVN_Pay(double totalPrice) throws UnsupportedEncodingException {
        double amount = totalPrice * 100;
        //        String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        //        String vnp_IpAddr = VNPayConfig.getIpAddress(req);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "VCB");

        //        if (bankCode != null && !bankCode.isEmpty()) {
        //            vnp_Params.put("vnp_BankCode", bankCode);
        //        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", VNPayConfig.orderType);
        vnp_Params.put("vnp_Locale", "vn");

        //        String locate = req.getParameter("language");
        //        if (locate != null && !locate.isEmpty()) {
        //            vnp_Params.put("vnp_Locale", locate);
        //        } else {
        //            vnp_Params.put("vnp_Locale", "vn");
        //        }

        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        //        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return VNPayConfig.vnp_PayUrl + "?" + queryUrl;
    }


    private Session payWithStripe(List<StripeItemRequest> checkoutItemDtoList) throws StripeException {
        String successURL = clientBaseURL + "payment/success";

        String failureURL = clientBaseURL + "payment/failed";

        Stripe.apiKey = secretKey;

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();


        for (StripeItemRequest checkoutItemDto: checkoutItemDtoList) {
            sessionItemList.add(createSessionLineItem(checkoutItemDto));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureURL)
                .addAllLineItem(sessionItemList)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(params);
    }

    private SessionCreateParams.LineItem createSessionLineItem(StripeItemRequest checkoutItemDto) {

        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();

    }

    private SessionCreateParams.LineItem.PriceData createPriceData(StripeItemRequest checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long)(checkoutItemDto.getPrice()*100))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build()
                ).build();
    }
}
