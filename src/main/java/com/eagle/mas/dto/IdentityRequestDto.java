package com.eagle.mas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdentityRequestDto {
    private boolean bypassCache;
    private List<String> fields;
    private String id;
    private String process;
    private String source;
}
