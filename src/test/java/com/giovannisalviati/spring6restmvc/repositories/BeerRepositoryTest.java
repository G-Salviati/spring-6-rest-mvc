package com.giovannisalviati.spring6restmvc.repositories;

import com.giovannisalviati.spring6restmvc.entities.Beer;
import com.giovannisalviati.spring6restmvc.models.BeerStyle;
import jakarta.validation.ConstraintViolationException;
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
                .upc("512512")
                .beerStyle(BeerStyle.PALE_ALE)
                .price(BigDecimal.valueOf(11.5))
                .build());

        beerRepository.flush();

        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
        assertThat(beer.getBeerName()).isNotNull();
        assertThat(beer.getBeerName()).isNotBlank();
        assertThat(beer.getUpc()).isNotNull();
        assertThat(beer.getUpc()).isNotBlank();
        assertThat(beer.getPrice()).isNotNull();
        assertThat(beer.getPrice()).isGreaterThan(BigDecimal.valueOf(0));
        assertThat(beer.getBeerStyle()).isNotNull();
    }

    @Test
    void saveNewBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer beer = beerRepository.save(Beer.builder()
                    .beerName("new Beer safasfasfasfasafasfasfasfasafasfasfasfasafasfasfasfasafasfasfasfasafasfasfasfasafasfasfasfa")
                    .upc("512512")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .price(BigDecimal.valueOf(11.5))
                    .build());

            beerRepository.flush();
        });
    }
}