package com.staybook.accounts.mapper;

import com.staybook.accounts.dto.CustomersDto;
import com.staybook.accounts.entity.Customer;

public class CustomersMapper {
	 public static CustomersDto mapToCustomerDto(Customer customer, CustomersDto customerDto) {
	        customerDto.setName(customer.getName());
	        customerDto.setEmail(customer.getEmail());
	        customerDto.setMobileNumber(customer.getMobileNumber());
	        return customerDto;
	    }

	    public static Customer mapToCustomer(CustomersDto customerDto, Customer customer) {
	        customer.setName(customerDto.getName());
	        customer.setEmail(customerDto.getEmail());
	        customer.setMobileNumber(customerDto.getMobileNumber());
	        return customer;
	    }
}
