package com.eagle.mas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MvJsonResponseDto {
    private String biometrics;

    private Map<String, String> identity;

    private Map<String, String> documents;

    private String metaInfo;

    private String audits;

}
