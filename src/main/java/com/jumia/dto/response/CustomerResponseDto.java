package com.jumia.dto.response;

import lombok.Data;

@Data
public class CustomerResponseDto {
	private Long id;
	private String name ;
	private String phone;
	private String state;
}
