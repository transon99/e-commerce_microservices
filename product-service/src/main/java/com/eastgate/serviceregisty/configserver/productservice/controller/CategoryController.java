package com.eastgate.serviceregisty.configserver.productservice.controller;

import com.eastgate.serviceregisty.configserver.productservice.dto.request.CategoryRequest;
import com.eastgate.serviceregisty.configserver.productservice.dto.response.CategoryDTO;
import com.eastgate.serviceregisty.configserver.productservice.dto.response.ResponseMessage;
import com.eastgate.serviceregisty.configserver.productservice.service.CategoryService;
import com.eastgate.serviceregisty.configserver.productservice.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseMessage> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("OK",
                        "get category successfully !!",
                        categoryService.findById(id)));
    }

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
