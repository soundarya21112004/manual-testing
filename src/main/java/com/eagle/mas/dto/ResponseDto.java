package com.eagle.mas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private String id;
    private String version;
    private LocalDateTime responsetime;
    private Map<String, Object> metadata;
    private T response;
    private List<ErrorDto> errors;
}
