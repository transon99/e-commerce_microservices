package com.sondev.productservice.controller;

import com.sondev.common.response.ResponseMessage;
import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RequestMapping("/api/v1/products")
@RestController
@Slf4j
@RequiredArgsConstructor
//@Api("product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseMessage> createProduct(@RequestBody @Validated ProductRequest productRequest) {
        log.info("ProductController | addProduct is called");

        log.info("ProductController | addProduct | productRequest : " + productRequest.toString());
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Insert product successful !!",
                productService.createProduct(productRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        log.info("ProductController | getProductById is called");

        log.info("ProductController | getProductById | productId : " + id);
        return  ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Get product successful !!",
                productService.findProductById(id)));
    }


    @GetMapping()
    public ResponseEntity<ResponseMessage> getProducts(@RequestParam String searchText,
                                      @RequestParam Integer offset,
                                      @RequestParam Integer pageSize,
                                      @RequestParam String sortStr) {
        log.info("ProductController | getProducts is called");

        log.info("ProductController | getProducts | offset {}, pageSize {}, sortStr {}   : ", offset, pageSize, sortStr);
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "get product successful !!",
                productService.getProducts(searchText, offset, pageSize, sortStr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Delete product successful !!",
                productService.deleteProductById(id)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestBody @Validated Map<String, Object> fields,
                                 @PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Update product successful !!",
                productService.updateProduct(fields,id)));
    }

    @PutMapping("/reduce-quantity/{id}")
    public ResponseEntity<ResponseMessage> reduceQuantity(
            @PathVariable("id") String productId,
            @RequestParam Integer quantity
    ) {

        log.info("ProductController | reduceQuantity is called");

        log.info("ProductController | reduceQuantity | productId : " + productId);
        log.info("ProductController | reduceQuantity | quantity : " + quantity);

        productService.reduceQuantity(productId,quantity);
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Update product successful !!",
                null));
    }

}
