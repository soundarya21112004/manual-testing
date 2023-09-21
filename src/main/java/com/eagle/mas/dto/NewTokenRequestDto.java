package com.eagle.mas.dto;

import lombok.Data;

@Data
public class NewTokenRequestDto extends BaseRequestDTO {

    private ClientIdSecretKeyRequestDto request;
}
