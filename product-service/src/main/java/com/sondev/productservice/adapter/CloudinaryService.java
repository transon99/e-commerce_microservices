package com.sondev.productservice.adapter;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    Map uploadFile(MultipartFile multipartFile);
    void deleteFile(String publicId);
}
