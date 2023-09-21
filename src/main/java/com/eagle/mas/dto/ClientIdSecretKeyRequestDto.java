package com.eagle.mas.dto;

import lombok.Data;

@Data
public class ClientIdSecretKeyRequestDto {
	public String clientId;
	public String secretKey;
	public String appId;
}
