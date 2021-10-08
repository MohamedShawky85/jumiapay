package com.jumia.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import com.jumia.dto.response.CustomerResponseDto;
import com.jumia.entity.Customer;


@Mapper(componentModel = "spring",nullValueCheckStrategy =   NullValueCheckStrategy.ALWAYS)
public interface CustomerMapper extends CustomerResponseMapper<Customer,CustomerResponseDto>{

	Customer dtoToEntity(CustomerResponseDto responseDto) ;

	CustomerResponseDto entityToDto(Customer customerEntity);


}
