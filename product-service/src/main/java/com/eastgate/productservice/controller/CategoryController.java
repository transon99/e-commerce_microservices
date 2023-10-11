package com.eastgate.productservice.controller;

import com.eastgate.productservice.dto.request.CategoryRequest;
import com.eastgate.productservice.dto.response.ResponseMessage;
import com.eastgate.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/v1/category")
@RestController
@Slf4j
@RequiredArgsConstructor
//@Api("category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createCategory(@RequestBody @Validated CategoryRequest categoryRequest) {
        categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "create a category successfully !!",
                        categoryService.createCategory(categoryRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "get category successfully !!",
                        categoryService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "delete category successfully !!",
                        categoryService.deleteById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestBody Map<String, Object> fields , @PathVariable(name = "id") String id) {
        categoryService.updateCategory(fields, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "update category successfully !!",
                        categoryService.updateCategory(fields,id)));
    }

}
