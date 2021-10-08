package com.jumia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumia.dto.request.CustomerRequestDto;
import com.jumia.dto.response.CustomerResponseDto;
import com.jumia.payload.PagedResponse;
import com.jumia.service.CustomerService;

@RestController
@RequestMapping("${api.prefix.url}")
public class CountryController {

@Autowired
CustomerService customerService;


@PostMapping("/filter")
public PagedResponse<CustomerResponseDto> retrieveCategorizedPhoneNumbers(@RequestParam(defaultValue = "1") Integer pageNo,
		@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortColumn,
		@RequestParam(defaultValue = "DESC") String sortType,@RequestBody CustomerRequestDto customerRequest)  {
	
	return customerService.retrieveCategorizedPhoneNumbers(pageNo - 1, pageSize, sortColumn, sortType, customerRequest);
}

@PostMapping("/categorized")
public PagedResponse<CustomerResponseDto> groupCustomersByCountry(@RequestParam(defaultValue = "1") Integer pageNo,
		@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortColumn,
		@RequestParam(defaultValue = "DESC") String sortType,@RequestBody CustomerRequestDto customerRequest)  {
	
	return customerService.groupCustomersByCountry(pageNo - 1, pageSize, sortColumn, sortType, customerRequest);
}
	
}
