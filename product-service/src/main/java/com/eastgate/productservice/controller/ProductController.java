package com.eastgate.productservice.controller;

import com.eastgate.productservice.dto.request.ProductRequest;
import com.eastgate.productservice.service.Impl.ProductServiceImpl;
import com.eastgate.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createProduct(@RequestBody @Validated ProductRequest productRequest) {
        return Utils.checkStatusCodeAndResponse(productService.createProduct(productRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(productService.findProductById(id));
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
