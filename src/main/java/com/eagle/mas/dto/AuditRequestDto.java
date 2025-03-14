package com.eagle.mas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditRequestDto {
    private boolean bypassCache;
    private String id;
    private String process;
    private String source;
}
