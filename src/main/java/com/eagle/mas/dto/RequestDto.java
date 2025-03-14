package com.eagle.mas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto<T> {
    private String id;
    private Map<String, Object> metadata;
    private T request;
    private LocalDateTime requesttime;
    private String version;
}
