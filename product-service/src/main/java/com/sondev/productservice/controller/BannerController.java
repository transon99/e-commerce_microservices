package com.sondev.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sondev.common.response.ResponseMessage;
import com.sondev.productservice.service.BannerService;
import com.sondev.productservice.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/banners")
@RestController
@Slf4j
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    public ResponseEntity<ResponseMessage> create(@RequestParam("image") MultipartFile file,
                                                  @RequestParam("data") String data) throws
            JsonProcessingException {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Insert banner successful !!",
                bannerService.create(file, data)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Get banner successful !!",
                bannerService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> findByCondition(@RequestParam String searchText,
                                                           @RequestParam Integer offset,
                                                           @RequestParam Integer pageSize,
                                                           @RequestParam String sortStr) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "get banner successful !!",
                bannerService.findByCondition(searchText, offset, pageSize, sortStr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Delete banner successful !!",
                bannerService.deleteById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestParam(value = "image",
            required = false) MultipartFile file,
                                                  @RequestParam("data") String data,
                                                  @PathVariable(name = "id") String id)
            throws JsonProcessingException, IllegalAccessException {
        return ResponseEntity.ok().body(new ResponseMessage(
                "OK",
                "Update banner successful !!",
                bannerService.update(file, data, id)));
    }

}
