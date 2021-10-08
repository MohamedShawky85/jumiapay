package com.jumia.serviceimpl;



import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jumia.dao.CustomerDao;
import com.jumia.dto.request.CustomerRequestDto;
import com.jumia.dto.response.CustomerResponseDto;
import com.jumia.entity.Customer;
import com.jumia.mapper.CustomerMapper;
import com.jumia.payload.PagedResponse;
import com.jumia.service.CustomerService;
import com.jumia.specification.CustomerSpecification;
import com.jumia.specification.SearchCriteria;
import com.jumia.specification.SearchOperation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

@Autowired	
CustomerDao customerDao;

@Autowired
CustomerMapper customerMapper;

protected static final Map<String, String> prefixesRegex;

/**
 * This block used for handing the countries codes with the constant Regex
 */
static {
	prefixesRegex = new HashMap<>();
	prefixesRegex.put("(237)", "\\(237\\)\\ ?[2368]\\d{7,8}$");
	prefixesRegex.put("(251)", "\\(251\\)\\ ?[1-59]\\d{8}$");
	prefixesRegex.put("(212)", "\\(212\\)\\ ?[5-9]\\d{8}$");
	prefixesRegex.put("(258)", "\\(258\\)\\ ?[28]\\d{7,8}$");
	prefixesRegex.put("(256)", "\\(256\\)\\ ?\\d{9}$");
}



/**
 * 
 */
@Override
public PagedResponse<CustomerResponseDto> retrieveCategorizedPhoneNumbers(Integer pageNo, Integer pageSize,String sortColumn,String sortType, CustomerRequestDto request)
{
	
	CustomerSpecification customerSpecification = new CustomerSpecification();
	
	
	try {
		customerSpecification = builtSearcheCritria(request, SearchOperation.EQUAL, customerSpecification);
	} catch (IllegalArgumentException e) {
		
		log.error(e.getMessage());
	} catch (IllegalAccessException e) {
		log.error(e.getMessage());
	}
	
	
	Page<Customer> pagedResult = customerDao.findAll(customerSpecification,PageRequest.of(pageNo, pageSize,
			Sort.by("ASC".equalsIgnoreCase(sortType) ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn)));

	return new PagedResponse<>(
			pagedResult.getContent().stream().map(this::mapCustomerEntityToResposeDto).collect(Collectors.toList()),
			pagedResult.getNumber(), pagedResult.getSize(), pagedResult.getTotalElements(), pagedResult.getTotalPages(),
			pagedResult.isLast());

}


@Override
public PagedResponse<CustomerResponseDto> groupCustomersByCountry(Integer pageNo, Integer pageSize,String sortColumn,String sortType, CustomerRequestDto request)
{
	
	
	Page<Customer> pagedResult = customerDao.groupCustomersByCountry(PageRequest.of(pageNo, pageSize,
			Sort.by("ASC".equalsIgnoreCase(sortType) ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn)));

	return new PagedResponse<>(
			pagedResult.getContent().stream().map(this::mapCustomerEntityToResposeDto).collect(Collectors.toList()),
			pagedResult.getNumber(), pagedResult.getSize(), pagedResult.getTotalElements(), pagedResult.getTotalPages(),
			pagedResult.isLast());

}

/**
 * 
 * @param customer
 * @return
 */
CustomerResponseDto mapCustomerEntityToResposeDto(Customer customer)
{
	
	

	CustomerResponseDto customerResponse = customerMapper.entityToDto(customer);
	
	   customerResponse.setState(checkCountryPattern(customerResponse.getPhone()) ? "valid" : "not valid");
	   return customerResponse;

}


/**
 * 
 * @param phoneNumber
 * @return
 */
public boolean checkCountryPattern(String phoneNumber)
{
    boolean numberInvaldState = false;
    if(null == phoneNumber)
    	return numberInvaldState;
    
	String phonePrefix = phoneNumber.split(" ")[0];
	
	Pattern p = Pattern.compile(prefixesRegex.get(phonePrefix)); 
    
	if(!p.matcher(phoneNumber).matches())
		return numberInvaldState;
	
return true;
}

/**
 * 
 * @param requestDtoObj
 * @param searchOperation
 * @param customerSpecification
 * @return
 * @throws IllegalArgumentException
 * @throws IllegalAccessException
 */
public CustomerSpecification builtSearcheCritria(Object requestDtoObj, SearchOperation searchOperation, CustomerSpecification customerSpecification)
		throws IllegalArgumentException, IllegalAccessException {



	Field[] fields = requestDtoObj.getClass().getDeclaredFields();

	for (Field field : fields) {
		field.setAccessible(true);

		if (field.get(requestDtoObj) != null) {
			 if (field.getType().toString().equals("class java.lang.String")) {
					searchOperation = SearchOperation.LIKE;
				} 
				else if (field.getType().toString().equals("class java.lang.Long")) {
					searchOperation = SearchOperation.IN;
				}

				
			 customerSpecification.add(new SearchCriteria(field.getName(), field.get(requestDtoObj), searchOperation));
			
		}

	}
	return customerSpecification;

}



}
