package com.sondev.service;

import com.stripe.exception.StripeException;

public interface StripeService {

    String handleStripeEvent(String payload, String sigHeader) throws StripeException;

    String test();

}
