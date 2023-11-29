package com.sondev.controller;

import com.sondev.common.response.ResponseMessage;
import com.sondev.dto.request.PaymentRequest;
import com.sondev.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/v1/payment")
@RestController
@Slf4j
@RequiredArgsConstructor
//@Api("payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createPayment(@RequestBody @Validated PaymentRequest paymentRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Create payment successful !!",
                paymentService.createPayment(paymentRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Create payment successful !!",
                paymentService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable("id") String id) {

        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Create payment successful !!",
                paymentService.deleteById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getProducts(
            @RequestParam Integer offset,
            @RequestParam Integer pageSize,
            @RequestParam String sortStr) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "get payments successful !!",
                paymentService.getPayments(offset, pageSize, sortStr)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestBody @Validated Map<String, Object> fields,
                                                  @PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Update product successful !!",
                paymentService.updatePayment(fields, id)));
    }

}
