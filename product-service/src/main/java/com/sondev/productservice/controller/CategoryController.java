package com.sondev.productservice.controller;

import com.sondev.common.response.ResponseMessage;
import com.sondev.productservice.dto.request.CategoryRequest;
import com.sondev.productservice.service.CategoryService;
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

@RequestMapping("/api/v1/categories")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createCategory(@RequestBody @Validated CategoryRequest categoryRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Create category successful !!",
                categoryService.createCategory(categoryRequest)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Find category successful !!",
                categoryService.findCategoryById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getCategories(@RequestParam String searchText,
                                                         @RequestParam Integer offset,
                                                         @RequestParam Integer pageSize,
                                                         @RequestParam String sortStr) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Find category successful !!",
                categoryService.getCategories(searchText, offset, pageSize, sortStr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Delete category successful !!",
                categoryService.deleteCategoryById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestBody Map<String, Object> fields, @PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Update category successful !!",
                categoryService.updateCategory(fields, id)));
    }

}
