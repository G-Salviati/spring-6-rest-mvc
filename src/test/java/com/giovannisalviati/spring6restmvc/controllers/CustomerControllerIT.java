package com.giovannisalviati.spring6restmvc.controllers;

import com.giovannisalviati.spring6restmvc.entities.Customer;
import com.giovannisalviati.spring6restmvc.models.CustomerDTO;
import com.giovannisalviati.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;


    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().getFirst();

        assertThat(customerController.getCustomerById(customer.getId())).isNotNull();
    }

    @Test
    void testGetCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () ->
                customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> customers = customerController.listCustomer();

        assertThat(customers.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyListCustomers() {
        customerRepository.deleteAll();

        List<CustomerDTO> customers = customerController.listCustomer();

        assertThat(customers.size()).isEqualTo(0);
    }
}