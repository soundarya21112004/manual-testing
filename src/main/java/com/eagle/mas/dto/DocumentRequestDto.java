package com.eagle.mas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequestDto {
    private String documentName;
    private String id;
    private String process;
    private String source;
}
