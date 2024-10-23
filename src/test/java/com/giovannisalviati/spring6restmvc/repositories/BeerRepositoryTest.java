package com.giovannisalviati.spring6restmvc.repositories;

import com.giovannisalviati.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveNewBeer() {
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("new Beer")
                .price(BigDecimal.valueOf(11.5))
                .build());

        assertThat(beer).isNotNull();
        assertThat(beer.getBeerName()).isNotNull();
        assertThat(beer.getPrice()).isNotNull();
    }
}