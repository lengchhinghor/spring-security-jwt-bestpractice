package com.chhinghor.hrd.chhinghorjwt.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class ResponseData<T> {
    @JsonProperty("response")
    private T data;
}
