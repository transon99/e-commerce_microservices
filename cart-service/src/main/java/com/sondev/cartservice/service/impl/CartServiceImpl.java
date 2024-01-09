package com.sondev.cartservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondev.cartservice.dto.request.AddToCartRequest;
import com.sondev.cartservice.dto.request.CartRequest;
import com.sondev.cartservice.dto.response.CartDto;
import com.sondev.cartservice.dto.response.CartItemDto;
import com.sondev.cartservice.dto.response.ProductDto;
import com.sondev.cartservice.entity.Cart;
import com.sondev.cartservice.entity.CartItem;
import com.sondev.cartservice.feignclient.ProductClient;
import com.sondev.cartservice.mapper.CartItemMapper;
import com.sondev.cartservice.mapper.CartMapper;
import com.sondev.cartservice.repository.CartItemRepository;
import com.sondev.cartservice.repository.CartRepository;
import com.sondev.cartservice.service.CartService;
import com.sondev.common.exceptions.MissingInputException;
import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.ResponseMessage;
import com.sondev.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ObjectMapper objectMapper;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final ProductClient productClient;

    @Override
    public String createCart(CartRequest cartRequest) {
        Cart cartEntity = cartMapper.reqToEntity(cartRequest);
        return cartMapper.toDto(cartRepository.save(cartEntity)).getId();
    }

    @Override
    public List<CartDto> findAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if (CollectionUtils.isEmpty(carts)) {
            throw new NotFoundException("Can't find any cart");
        }
        return cartMapper.toDto(cartRepository.findAll());
    }

    @Override
    public CartDto findCartById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        return cartMapper.toDto(cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find cart with id " + id)));
    }

    @Override
    public CartDto getUserCart(String token) {
        if (token == null) {
            throw new MissingInputException("Missing input id");
        }
        CartDto cartDto;

        Claims claims = JwtUtils.parseClaims(token);
        String userId = (String) claims.get("userId");
        Optional<Cart> currentCart = cartRepository.findByUserId(userId);

        if (currentCart.isPresent()) {
            cartDto = cartMapper.toDto(currentCart.get());
        } else {
            Cart newCart = Cart.builder()
                    .userId(userId)
                    .build();
            cartDto = cartMapper.toDto(newCart);
        }

        return cartDto;
    }

    @Override
    public String deleteCartById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        cartRepository.deleteById(id);
        return id;
    }

    @Override
    public String addToCart(AddToCartRequest addToCartRequest, String token) {
        Claims claims = JwtUtils.parseClaims(token);
        String userId = (String) claims.get("userId");
        Optional<Cart> currentCart = cartRepository.findByUserId(userId);
        CartItem cartItem = CartItem.builder()
                .userId(userId)
                .quantity(addToCartRequest.getQuantity())
                .productId(addToCartRequest.getProductId())
                .build();
        CartItem cartItemSave = cartItemRepository.save(cartItem);

        currentCart.ifPresentOrElse(
                cart -> {
                    cart.getCartItems().add(cartItemSave.getId());
                    cartRepository.save(cart);
                    log.info("add product to cart");
                },
                () -> {
                    Cart newCart = Cart.builder()
                            .cartItems(Set.of(cartItemSave.getId()))
                            .userId(userId)
                            .build();
                    cartRepository.save(newCart);
                });

        return "Add product with id" + addToCartRequest.getProductId() + "to cart successful !!!";
    }

    @Override
    public String updateCart(CartRequest cartRequest, String token) {
        Cart currentCart = getCurrentCart(token);
        Set<String> listCartItem = currentCart.getCartItems();
        String cartItemId = find(listCartItem, cartRequest.getProductId());
        if (StringUtils.isNotEmpty(cartItemId)) {
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new NotFoundException("Can't find cartItem with id" + cartItemId));
            cartItem.setQuantity(cartRequest.getQuantity());
            cartItemRepository.save(cartItem);
        }

        return "Update cart successful !!!";
    }

    @Override
    public String removeCartItem(String productId, String token) {
        Cart currentCart = getCurrentCart(token);
        Set<String> cartItemIds = currentCart.getCartItems();
        String cartItemId = find(cartItemIds, productId);
        cartItemIds.remove(cartItemId);
        cartItemRepository.deleteById(cartItemId);
        return null;
    }

    @Override
    public CartItemDto findCartItemById(String id) {
        return cartItemMapper.toDto(cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find cart item with id" + id)));
    }

    private Cart getCurrentCart(String token) {
        Claims claims = JwtUtils.parseClaims(token);
        String userId = (String) claims.get("userId");
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Can't find cart with userId" + userId));
    }

    private String find(Set<String> cartItems, String productId) {
        if (cartItems == null) {
            return null;
        }
        String cartItem = null;
        for (String item : cartItems) {
            if (StringUtils.equals(item, productId)) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private double totalPrice(Set<String> cartItemIdList, String userId, String token) {

        double totalPrice = 0.0;
        for (String item : cartItemIdList) {
            CartItem cartItem = cartItemRepository.findByIdAndUserId(item, userId);
            ResponseMessage productResponse = productClient.findById(token, cartItem.getProductId()).getBody();
            assert productResponse != null;
            if (productResponse.getData() != null) {
                ProductDto productDto = objectMapper.convertValue(productResponse.getData(), ProductDto.class);

                totalPrice += productDto.getPriceUnit() * cartItem.getQuantity();
            }

        }
        return totalPrice;
    }

}
