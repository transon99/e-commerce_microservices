package com.sondev.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.ResponseMessage;
import com.sondev.productservice.service.CategoryService;
import com.sondev.productservice.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping("/galleries")
@RestController
@Slf4j
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    public ResponseEntity<ResponseMessage> create(@RequestParam("image") MultipartFile file,
                                                  @RequestParam("data") String data) throws
            JsonProcessingException {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Insert gallery successful !!",
                galleryService.create(file, data)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Get gallery successful !!",
                galleryService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> findByCondition(@RequestParam String searchText,
                                                           @RequestParam Integer offset,
                                                           @RequestParam Integer pageSize,
                                                           @RequestParam String sortStr) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "get galleries successful !!",
                galleryService.findByCondition(searchText, offset, pageSize, sortStr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Delete gallery successful !!",
                galleryService.deleteById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestParam(value = "image",
            required = false) MultipartFile file,
                                                  @RequestParam("data") String data,
                                                  @PathVariable(name = "id") String id)
            throws JsonProcessingException, IllegalAccessException {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Update gallery successful !!",
                galleryService.update(file, data, id)));
    }

}
