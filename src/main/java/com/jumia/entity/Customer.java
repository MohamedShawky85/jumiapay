package com.jumia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "CUSTOMER")
@Data
public class Customer {

	@Id
	private Long id;
	
	@Column(name = "NAME")
	private String name ;
	@Column(name = "PHONE")
	private String phone;
}
