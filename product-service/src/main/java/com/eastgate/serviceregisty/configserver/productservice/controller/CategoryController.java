package com.eastgate.serviceregisty.configserver.productservice.controller;

import com.eastgate.serviceregisty.configserver.productservice.dto.request.CategoryRequest;
import com.eastgate.serviceregisty.configserver.productservice.dto.response.CategoryResponse;
import com.eastgate.serviceregisty.configserver.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/category")
@RestController
@Slf4j
@RequiredArgsConstructor
//@Api("category")
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated CategoryRequest categoryRequest) {
        categoryService.save(categoryRequest);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryResponse> findById(@PathVariable("id") Long id) {
//        CategoryResponse category = categoryService.findById(id);
//        return ResponseEntity.ok(category);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
//        Optional.ofNullable(categoryService.findById(id)).orElseThrow(() -> {
//            log.error("Unable to delete non-existent data!");
//            return null;
//        });
//        categoryService.deleteById(id);
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/page-query")
//    public ResponseEntity<Page<CategoryResponse>> pageQuery(CategoryResponse categoryResponse,
//                                                            @PageableDefault(sort = "createAt",
//                                                               direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<CategoryResponse> categoryPage = categoryService.findByCondition(categoryResponse, pageable);
//        return ResponseEntity.ok(categoryPage);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Void> update(@RequestBody @Validated CategoryResponse categoryResponse, @PathVariable("id") Long id) {
//        categoryService.update(categoryResponse, id);
//        return ResponseEntity.ok().build();
//    }

}
