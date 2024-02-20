package com.sondev.service.impl;

import com.google.gson.JsonSyntaxException;
import com.sondev.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.LineItemCollection;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionListLineItemsParams;
import com.stripe.param.checkout.SessionRetrieveParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Override
    public String handleStripeEvent(String payload, String sigHeader) throws StripeException {
        Event event = null;
        Stripe.apiKey = "sk_test_51OgOCdLOmLphZ7kVM5nykoqiQWrHmGNr1l9sze8TKIEtNJnxogNdWR4gd3aIVPL4yLAODf0HKJK3SINaRC4t86C800K08LLmEO";

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
        if ("checkout.session.completed".equals(event.getType())) {
            Session sessionEvent = (Session) event.getDataObjectDeserializer().getObject().get();
            SessionRetrieveParams params =
                    SessionRetrieveParams.builder()
                            .addExpand("line_items")
                            .build();

            Session session = Session.retrieve(sessionEvent.getId(), params, null);

            SessionListLineItemsParams listLineItemsParams =
                    SessionListLineItemsParams.builder()
                            .build();

            // Retrieve the session. If you require line items in the response, you may include them by expanding line_items.
            LineItemCollection lineItems = session.listLineItems(listLineItemsParams);
            return "";
        }
        return "";
    }

}
