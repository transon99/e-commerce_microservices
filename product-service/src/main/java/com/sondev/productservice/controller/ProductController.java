package com.sondev.productservice.controller;

import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.dto.response.ProductDto;
import com.sondev.productservice.dto.response.ResponseMessage;
import com.sondev.productservice.service.impl.ProductServiceImpl;
import com.sondev.common.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/v1/products")
@RestController
@Slf4j
@RequiredArgsConstructor
//@Api("product")
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity createProduct(@RequestBody @Validated ProductRequest productRequest) {
        log.info("*** ProductDto, controller; create product *");
        return Utils.checkStatusCodeAndResponse(productService.createProduct(productRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return  ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "insert team successfully !!",
                productService.findProductById(id)));
    }


    @GetMapping()
    public ResponseEntity getProducts(@RequestParam String searchText,
                                      @RequestParam Integer offset,
                                      @RequestParam Integer pageSize,
                                      @RequestParam String sortStr) {
        return Utils.checkStatusCodeAndResponse(productService.getProducts(searchText, offset, pageSize, sortStr));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(productService.deleteProductById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody @Validated Map<String, Object> fields,
                                 @PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(productService.updateProduct(fields, id));
    }

}
