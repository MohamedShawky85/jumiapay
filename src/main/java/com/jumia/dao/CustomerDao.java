package com.jumia.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jumia.entity.Customer;


public interface CustomerDao  extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer>{
	
	@Query(value = "select * from customer where phone like substr(phone,0,6) || '%' ", 
			  nativeQuery = true)
	Page<Customer> groupCustomersByCountry(PageRequest pageable);	
	
}