package com.sondev.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserZalo {
    private Boolean is_sensitive;
    private String name;
    private String id;
    private Integer error;
    private String message;
    private Picture picture;
    @Data
    public static class Picture {
        private ZaloData data;

        @Data
        public static class ZaloData {
            private String url;
        }
    }
}
