package com.sondev.common.utils;

import com.sondev.common.constants.ResponseStatusCode;
import com.sondev.common.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utils {
    public static ResponseEntity checkStatusCodeAndResponse(ResponseDTO ResponseDTO) {
        if (ResponseDTO != null && ResponseDTO.getResponseStatusCode() != null) {
            switch (ResponseDTO.getResponseStatusCode()) {
                case INTERNAL_SERVER_ERROR:
                    return new ResponseEntity(ResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
                case UNAUTHORIZED:
                    return new ResponseEntity(ResponseDTO, HttpStatus.UNAUTHORIZED);
                case NOT_FOUND:
                    return new ResponseEntity(ResponseDTO, HttpStatus.NOT_FOUND);
                case BAD_REQUEST:
                    return new ResponseEntity(ResponseDTO, HttpStatus.BAD_REQUEST);
                case UNPROCESSABLE_ENTITY:
                    return new ResponseEntity(ResponseDTO, HttpStatus.UNPROCESSABLE_ENTITY);
                case LOCKED:
                    return new ResponseEntity(ResponseDTO, HttpStatus.LOCKED);
                default:
                    return new ResponseEntity(ResponseDTO, HttpStatus.OK);
            }
        }

        return ResponseEntity.noContent().build();
    }

    public static ResponseDTO getResponseSuccess(Object data, String content) {
        return ResponseDTO.builder()
                .data(data)
                .message(content)
                .responseStatusCode(ResponseStatusCode.OK)
                .build();
    }
}
