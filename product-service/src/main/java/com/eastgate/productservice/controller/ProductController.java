package com.eastgate.productservice.controller;

import com.eastgate.productservice.dto.request.ProductRequest;
import com.eastgate.productservice.dto.response.ProductDto;
import com.eastgate.productservice.dto.response.ResponseMessage;
import com.eastgate.productservice.service.Impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import java.util.Optional;

@RequestMapping("/products")
@RestController
@Slf4j
@RequiredArgsConstructor
//@Api("product")
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createProduct(@RequestBody @Validated ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "create a product successfully !!",
                        productService.createProduct(productRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "find product successfully !!",
                        productService.findProductById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getProducts(@RequestParam String searchText,
                                                       @RequestParam Integer offset,
                                                       @RequestParam Integer pageSize,
                                                       @RequestParam String sortStr) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "find product successfully !!",
                        productService.getProducts(searchText, offset,pageSize, sortStr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "delete product successfully !!",
                        productService.deleteProductById(id)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestBody @Validated Map<String, Object> fields,
                                                  @PathVariable(name = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "find product successfully !!",
                        productService.updateProduct(fields, id)));
    }

}
