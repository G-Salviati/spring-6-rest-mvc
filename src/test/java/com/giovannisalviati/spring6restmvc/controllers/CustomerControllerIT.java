package com.giovannisalviati.spring6restmvc.controllers;

import com.giovannisalviati.spring6restmvc.entities.Customer;
import com.giovannisalviati.spring6restmvc.mappers.CustomerMapper;
import com.giovannisalviati.spring6restmvc.models.CustomerDTO;
import com.giovannisalviati.spring6restmvc.repositories.CustomerRepository;
import jakarta.persistence.EntityManager;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    EntityManager entityManager;


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

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("NewCustomer")
                .build();

        ResponseEntity<CustomerDTO> responseEntity = customerController.saveNewCustomer(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUri = responseEntity.getHeaders().getLocation().getPath().split("/");

        UUID id = UUID.fromString(locationUri[4]);

        assertThat(customerRepository.findById(id)).isNotNull();
    }
}