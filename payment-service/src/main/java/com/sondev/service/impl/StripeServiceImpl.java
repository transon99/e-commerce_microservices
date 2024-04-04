package com.sondev.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;
import com.sondev.dto.MetaData;
import com.sondev.entity.Payment;
import com.sondev.entity.PaymentMethod;
import com.sondev.entity.PaymentStatus;
import com.sondev.event.ReduceQtyData;
import com.sondev.event.ReduceQtyEvent;
import com.sondev.event.UpdateOrderRequest;
import com.sondev.feignclient.ProductClient;
import com.sondev.repository.PaymentRepository;
import com.sondev.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.LineItem;
import com.stripe.model.LineItemCollection;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionListLineItemsParams;
import com.stripe.param.checkout.SessionRetrieveParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StripeServiceImpl implements StripeService {

    private final ProductClient productClient;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PaymentRepository paymentRepository;

    @Value("${webhook.webhook}")
    private String endpointSecret;

    @Value("${stripe.secret.key}")
    private String secretKey;

    @Override
    public String handleStripeEvent(String payload, String sigHeader) throws StripeException {
        Event event = null;
        Stripe.apiKey = secretKey;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (JsonSyntaxException e) {
            // Invalid payload

            return "";
        } catch (SignatureVerificationException e) {
            // Invalid signature
            return "";
        }

        // Handle the checkout.session.completed event
        switch (event.getType()) {
            case "checkout.session.completed":
                Session sessionEvent = (Session) event.getDataObjectDeserializer().getObject().get();
                SessionRetrieveParams params =
                        SessionRetrieveParams.builder()
                                .addExpand("line_items")
                                .build();

                Session session = Session.retrieve(sessionEvent.getId(), params, null);

                SessionListLineItemsParams listLineItemsParams =
                        SessionListLineItemsParams.builder()
                                .build();

                LineItemCollection lineItems = session.listLineItems(listLineItemsParams);

                log.info("lineItems {}", lineItems.toString());
                log.info("metadata {}", session.getMetadata());
//                List<OrderItemDto> orderItems = lineItems.getData().stream().map(lineItem -> {
//                    try {
//                        return OrderItemDto.builder()
//                                .productId(getMetaData(getMetaDataString(lineItem)).getProductId())
//                                .quantity( lineItem.getQuantity().intValue())
//                                .userId(getMetaData(getMetaDataString(lineItem)).getUserId())
//                                .build();
//                    } catch (StripeException e) {
//                        throw new RuntimeException(e);
//                    }
//                }).toList();
//                ;

//                OrderRequest orderRequest = OrderRequest.builder()
//
//                        .orderItemRequest(orderItems).build();

                kafkaTemplate.send("update-orderStatus", UpdateOrderRequest.builder()
                        .orderId(session.getMetadata().get("orderId"))
                        .paymentId(session.getMetadata().get("paymentId"))
                        .deliveryStatus("DISPATCHED")
                        .build());

                List<ReduceQtyData> reduceQtyDataList = lineItems.getData().stream().map(lineItem -> {
                    try {
                        return ReduceQtyData.builder()
                                .productId(getMetaData(getMetaDataString(lineItem)).getProductId())
                                .quantity(Math.toIntExact(lineItem.getQuantity()))
                                .build();
                    } catch (StripeException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();

                ReduceQtyEvent reduceQtyEvent = ReduceQtyEvent.builder()
                        .reduceQtyDataList(reduceQtyDataList)
                        .build();

                productClient.reduceQuantity(reduceQtyEvent);

//                kafkaTemplate.send("update-productQty", reduceQtyEvent);

                return "";
            default:
                log.warn("Unhandled event type: " + event.getType());
        }

        return "";
    }

    private Map<String, String> getMetaDataString(LineItem lineItem) throws StripeException {
        Product product = Product.retrieve(lineItem.getPrice().getProduct());
        return product.getMetadata();
    }

    private MetaData getMetaData(Map<String, String> metaDataString) throws StripeException {
        return objectMapper.convertValue(metaDataString, MetaData.class);

    }


    @Override
    public String test() {
        kafkaTemplate.send("update-orderStatus", UpdateOrderRequest.builder()
                .orderId("hálkdhflakshd")
                .deliveryStatus("DELIVERING")
                .build());


        List<ReduceQtyData> reduceQtyDataList = List.of(ReduceQtyData.builder()
                .productId("jashdlkfjhk")
                .quantity(123)
                .build());

        ReduceQtyEvent reduceQtyEvent = ReduceQtyEvent.builder()
                .reduceQtyDataList(reduceQtyDataList)
                .build();

        kafkaTemplate.send("update-productQty", reduceQtyEvent);

        return "publish kafka";
    }

}
