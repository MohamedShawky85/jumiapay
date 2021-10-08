package com.jumia.mapper;


public interface CustomerResponseMapper <T, D> {
	T dtoToEntity(D dto);

	D entityToDto(T entity);
 
}
