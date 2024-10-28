package com.giovannisalviati.spring6restmvc.services;

import com.giovannisalviati.spring6restmvc.models.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO newCustomer);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);

    void deleteCustomerById(UUID customerId);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
