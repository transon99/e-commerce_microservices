package com.sondev.authservice.adapter.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sondev.authservice.adapter.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public Map uploadFile(MultipartFile multipartFile) {
        try {
            Map data = this.cloudinary.uploader().upload(multipartFile.getBytes(), Map.of());
            return data;
        } catch (IOException io) {
            throw new RuntimeException("Image upload fail");
        }
    }

    @Override
    public void deleteFile(String publicId) {
        try {
            Map result = this.cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
            log.info("delete file: {} successful ", result);
        } catch (IOException io) {
            throw new RuntimeException("Image upload fail");
        }
    }
}
