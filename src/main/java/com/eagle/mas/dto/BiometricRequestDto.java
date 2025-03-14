package com.eagle.mas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiometricRequestDto {
    private boolean bypassCache;
    private String id;
    private List<String> modalities;
    private String person;
    private String process;
    private String source;

}
