package com.giovannisalviati.spring6restmvc.services;

import com.giovannisalviati.spring6restmvc.models.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    Map<UUID, CustomerDTO> customersMap;

    public CustomerServiceImpl() {
        this.customersMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("The Mayflower Pub")
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("The Queens Head Pub")
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("The Victoria Pub")
                .build();

        customersMap.put(customer1.getId(), customer1);
        customersMap.put(customer2.getId(), customer2);
        customersMap.put(customer3.getId(), customer3);

    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customersMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        log.debug("getBeerById called - in service. ID: {}", id.toString());

        return Optional.of(customersMap.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO newCustomer) {
        CustomerDTO customer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName(newCustomer.getCustomerName())
                .version(1)
                .build();

        customersMap.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customersMap.get(customerId);

        existingCustomer.setCustomerName(customer.getCustomerName());

        customersMap.put(customerId, existingCustomer);

        return Optional.of(existingCustomer);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        customersMap.remove(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customersMap.get(customerId);

        if (StringUtils.hasText(customer.getCustomerName())) {
            existingCustomer.setCustomerName(customer.getCustomerName());
        }

        if (customer.getVersion() != null) {
            existingCustomer.setVersion(customer.getVersion());
        }

        return Optional.of(existingCustomer);
    }
}
