package com.sondev.productservice.controller;

import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.service.CategoryService;
import com.sondev.common.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/v1/categories")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity createCategory(@RequestBody @Validated CategoryRequest categoryRequest) {
        return Utils.checkStatusCodeAndResponse(categoryService.createCategory(categoryRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(categoryService.findCategoryById(id));
    }

    @GetMapping()
    public ResponseEntity findAll() {
        return Utils.checkStatusCodeAndResponse(categoryService.findAllCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(categoryService.deleteCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Map<String, Object> fields, @PathVariable(name = "id") String id) {
        return Utils.checkStatusCodeAndResponse(categoryService.updateCategory(fields, id));
    }

}
