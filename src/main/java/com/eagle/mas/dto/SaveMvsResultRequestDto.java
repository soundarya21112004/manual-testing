package com.eagle.mas.dto;

import lombok.Data;

@Data
public class SaveMvsResultRequestDto {
    public String sno;
    public String verifyStatus;
    public String requestId;
    public String statusComment;
}
