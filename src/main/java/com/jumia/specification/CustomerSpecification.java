package com.jumia.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.jumia.entity.Customer;


public class CustomerSpecification implements Specification<Customer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient List<SearchCriteria> list;
    
	 public CustomerSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }
   @Override
   public Predicate  toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		// create a new predicate list
		List<Predicate> predicates = new ArrayList<>();
		
		// add add criteria to predicates

				for (SearchCriteria criteria : list) {
					if (criteria.getOperation().equals(SearchOperation.LIKE)) {
						predicates.add(builder.like(
		                        builder.lower(root.get(criteria.getKey())),
		                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
					
				}else if (criteria.getOperation().equals(SearchOperation.IN)) {
	                predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
					}
				else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
					predicates.add(builder.equal(builder.lower(root.get(criteria.getKey())), criteria.getValue()));
					
				}
				else if (criteria.getOperation().equals(SearchOperation.BEFORE)) {
					predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
					
				}
				else if (criteria.getOperation().equals(SearchOperation.AFTER)) {
					predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
					
				}
					}
				return builder.and(predicates.toArray(new Predicate[0]));
		}

}
