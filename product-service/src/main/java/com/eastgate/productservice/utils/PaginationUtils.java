package com.eastgate.productservice.utils;


import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;

public class PaginationUtils {
    public static Sort buildSort(String sortStr) {
        Sort sort;
        if (StringUtils.isNotEmpty(sortStr)) {
            if (StringUtils.contains(sortStr, "-")) {
                sortStr = StringUtils.split(sortStr, "-")[0];
                sort = Sort.by(sortStr).descending();
            } else {
                sort = Sort.by(sortStr);
            }
        } else {
            sort = Sort.by("id").ascending();
        }

        return sort;
    }
}
