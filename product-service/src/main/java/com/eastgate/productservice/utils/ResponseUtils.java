//package com.eastgate.serviceregisty.configserver.productservice.utils;
//
//import com.eastgate.serviceregisty.configserver.productservice.dto.response.CategoryDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//public class ResponseUtils {
//    public static ResponseEntity checkStatusCodeAndResponse(CategoryDTO citizenResponseDTO) {
//        if (citizenResponseDTO != null && citizenResponseDTO.getResponseStatusCode() != null) {
//            switch (citizenResponseDTO.getResponseStatusCode()) {
//                case INTERNAL_SERVER_ERROR:
//                    return new ResponseEntity(citizenResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//                case UNAUTHORIZED:
//                    return new ResponseEntity(citizenResponseDTO, HttpStatus.UNAUTHORIZED);
//                case NOT_FOUND:
//                    return new ResponseEntity(citizenResponseDTO, HttpStatus.NOT_FOUND);
//                case BAD_REQUEST:
//                    return new ResponseEntity(citizenResponseDTO, HttpStatus.BAD_REQUEST);
//                case UNPROCESSABLE_ENTITY:
//                    return new ResponseEntity(citizenResponseDTO, HttpStatus.UNPROCESSABLE_ENTITY);
//                case LOCKED:
//                    return new ResponseEntity(citizenResponseDTO, HttpStatus.LOCKED);
//                default:
//                    return new ResponseEntity(citizenResponseDTO, HttpStatus.OK);
//            }
//        }
//
//        return ResponseEntity.noContent().build();
//    }
//}
