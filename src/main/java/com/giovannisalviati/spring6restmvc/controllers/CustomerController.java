package com.giovannisalviati.spring6restmvc.controllers;


import com.giovannisalviati.spring6restmvc.models.Customer;
import com.giovannisalviati.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
