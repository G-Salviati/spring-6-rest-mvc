package com.giovannisalviati.spring6restmvc.controllers;


import com.giovannisalviati.spring6restmvc.models.Customer;
import com.giovannisalviati.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    List<Customer> listCustomer() {
        return customerService.listCustomers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{customerId}")
    Customer getCustomerById(@PathVariable("customerId") UUID id) {
        log.debug("getCustomerById called - in controller.");

        return customerService.getCustomerById(id);
    }

    @PostMapping
    ResponseEntity<Customer> saveNewCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + createdCustomer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
