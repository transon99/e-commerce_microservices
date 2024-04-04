package com.sondev.controller;

import com.google.gson.JsonSyntaxException;
import com.sondev.common.constants.ResponseStatus;
import com.sondev.common.response.ResponseMessage;
import com.sondev.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.ApiResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/stripe")
@RestController
@Slf4j
@RequiredArgsConstructor
public class StripeController {

    private final StripeService stripeService;

    @PostMapping("/event")
    public ResponseEntity<ResponseMessage> handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader)
            throws StripeException {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "successful !!",
                stripeService.handleStripeEvent(payload, sigHeader)));
    }

    @PostMapping("/test")
    public ResponseEntity<ResponseMessage> test(){
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "successful !!",
                stripeService.test()));
    }
}
