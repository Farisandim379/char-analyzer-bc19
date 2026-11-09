package com.bootcamp19.helloworld.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>("success", "OK", data);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>("failed", message, null);
    }
}
