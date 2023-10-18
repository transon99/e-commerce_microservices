package com.sondev.common.response;

import com.sondev.common.constants.ResponseStatusCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {

    private T data;
    private PagingData pagingData;
    private String message;

    @JsonIgnore
    private ResponseStatusCode responseStatusCode;
    public ResponseDTO(final T data) {
        super();
        this.data = data;
    }
}
