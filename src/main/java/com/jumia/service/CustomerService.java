package com.jumia.service;




import com.jumia.dto.request.CustomerRequestDto;
import com.jumia.dto.response.CustomerResponseDto;

import com.jumia.payload.PagedResponse;

public interface CustomerService {

	PagedResponse<CustomerResponseDto> retrieveCategorizedPhoneNumbers(Integer pageNo, Integer pageSize,String sortColumn,String sortType, CustomerRequestDto request);

	PagedResponse<CustomerResponseDto> groupCustomersByCountry(Integer pageNo, Integer pageSize, String sortColumn,
			String sortType, CustomerRequestDto request);
}
