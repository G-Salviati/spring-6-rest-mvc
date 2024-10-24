package com.giovannisalviati.spring6restmvc.repositories;

import com.giovannisalviati.spring6restmvc.entities.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveNewCustomer() {
        Customer customer = customerRepository.save(Customer.builder()
                .customerName("new Customer")
                .build());

        assertThat(customer).isNotNull();
        Assertions.assertThat(customer.getId()).isNotNull();
        assertThat(customer.getCustomerName()).isNotNull();
    }
}