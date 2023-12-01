package com.sondev.productservice.controller;

import com.sondev.common.response.ResponseMessage;
import com.sondev.productservice.dto.request.BrandRequest;
import com.sondev.productservice.service.BrandService;
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

@RequestMapping("/api/v1/brands")
@RestController
@Slf4j
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ResponseMessage> create(@RequestBody @Validated BrandRequest brandRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Create brand successful !!",
                brandService.create(brandRequest)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Find brand successful !!",
                brandService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getBrand(@RequestParam String searchText,
                                                         @RequestParam Integer offset,
                                                         @RequestParam Integer pageSize,
                                                         @RequestParam String sortStr) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Find brand successful !!",
                brandService.getBrands(searchText, offset, pageSize, sortStr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Delete brand successful !!",
                brandService.deleteById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestBody Map<String, Object> fields, @PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Update brand successful !!",
                brandService.update(fields, id)));
    }

}
