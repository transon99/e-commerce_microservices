package com.sondev.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.constants.ResponseStatus;
import com.sondev.common.response.ResponseMessage;
import com.sondev.productservice.dto.request.ProductRequest;
import com.sondev.productservice.event.ReduceQtyEvent;
import com.sondev.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping("/products")
@RestController
@Slf4j
@RequiredArgsConstructor
//@Api("product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage> createProduct(@ModelAttribute ProductRequest productRequest) throws JsonProcessingException  {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Insert product successful !!",
                productService.createProduct(productRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        log.info("ProductController | getProductById is called");

        log.info("ProductController | getProductById | productId : " + id);
        return  ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
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
                ResponseStatus.OK,
                "get product successful !!",
                productService.getProducts(searchText, offset, pageSize, sortStr)));
    }

    @GetMapping("/filter-by-cat")
    public ResponseEntity<ResponseMessage> getProductByCategory(@RequestParam String category) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "get product successful !!",
                productService.getProductByCategory(category)));
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseMessage> filterProduct(@RequestParam String categoryId,
                                                       @RequestParam Integer offset,
                                                       @RequestParam Integer pageSize,
                                                       @RequestParam String brandId) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "get product successful !!",
                productService.findProductsByCategoryAndBrand( offset, pageSize,categoryId, brandId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Delete product successful !!",
                productService.deleteProductById(id)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestParam(value = "image", required = false)  List<MultipartFile> files,
                                                  @RequestParam("data") String data,
                                                  @PathVariable(name = "id") String id) throws JsonProcessingException, IllegalAccessException  {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update product successful !!",
                productService.updateProduct(files, data, id)));
    }

    @PutMapping("/reduce-quantity")
    public ResponseEntity<ResponseMessage> reduceQuantity(
            @RequestBody ReduceQtyEvent reduceQtyEvent
    ) {

        log.info("ProductController | reduceQuantity is called");



        productService.reduceQuantity(reduceQtyEvent);
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update product successful !!",
                null));
    }

}
