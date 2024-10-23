package com.giovannisalviati.spring6restmvc.mappers;

import com.giovannisalviati.spring6restmvc.entities.Customer;
import com.giovannisalviati.spring6restmvc.models.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
