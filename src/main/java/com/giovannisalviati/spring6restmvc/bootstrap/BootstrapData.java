package com.giovannisalviati.spring6restmvc.bootstrap;

import com.giovannisalviati.spring6restmvc.entities.Beer;
import com.giovannisalviati.spring6restmvc.entities.Customer;
import com.giovannisalviati.spring6restmvc.models.BeerDTO;
import com.giovannisalviati.spring6restmvc.models.BeerStyle;
import com.giovannisalviati.spring6restmvc.repositories.BeerRepository;
import com.giovannisalviati.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {

        if (beerRepository.count() == 0) {

            Beer beer1 = Beer.builder()
                    .beerName("Heineken")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("9876")
                    .quantityOnHand(30)
                    .price(BigDecimal.valueOf(3.20))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Guinness")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("8523")
                    .quantityOnHand(12)
                    .price(BigDecimal.valueOf(3.90))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sierra Nevada Pale Ale")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("7675")
                    .quantityOnHand(50)
                    .price(BigDecimal.valueOf(2.90))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.saveAll(List.of(beer1, beer2, beer3));
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("Jack")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Andrea")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Anita")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(List.of(customer1, customer2, customer3));

        }

    }
}
