package com.eastgate.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PagingData {
    private String searchText;
    private Integer offset;
    private Integer pageSize;
    private String sort;
    private Long totalRecord;
}
