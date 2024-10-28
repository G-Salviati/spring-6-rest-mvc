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
import java.util.Optional;
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

    @Rollback
    @Transactional
    @Test
    void testUpdateCustomerById() {
        Customer customer = customerRepository.findAll().getFirst();

        UUID customerId = customer.getId();
        Integer version = customer.getVersion();
        String customerName = customer.getCustomerName();

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        customerDTO.setCustomerName("UPDATED name");

        ResponseEntity<Void> responseEntity = customerController.updateCustomerById(customerId, customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(responseEntity.getBody()).isNull();

        entityManager.flush();
        Optional<Customer> updatedCustomerOpt = customerRepository.findById(customerId);
        assertTrue(updatedCustomerOpt.isPresent());
        Customer updatedCustomer = updatedCustomerOpt.get();
        assertThat(updatedCustomer.getId()).isEqualTo(customerId);
        assertThat(updatedCustomer.getVersion()).isEqualTo(version + 1);
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteCustomerById() {
        Customer customer = customerRepository.findAll().getFirst();

        ResponseEntity<Void> responseEntity = customerController.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertFalse(customerRepository.findById(customer.getId()).isPresent());
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });

    }

    @Rollback
    @Transactional
    @Test
    void testPatchCustomerById() {
        Customer customer = customerRepository.findAll().getFirst();
        UUID customerId = customer.getId();
        Integer version = customer.getVersion();

        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("UPDATED")
                .build();

        ResponseEntity<Void> responseEntity = customerController.patchCustomerById(customerId, customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        entityManager.flush();
        Optional<Customer> updatedCustomerOpt = customerRepository.findById(customerId);
        assertTrue(updatedCustomerOpt.isPresent());
        Customer updatedCustomer = updatedCustomerOpt.get();
        assertThat(updatedCustomer.getId()).isEqualTo(customerId);
        assertThat(updatedCustomer.getVersion()).isEqualTo(version + 1);
    }

    @Rollback
    @Transactional
    @Test
    void testPatchCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.patchCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }
}