package com.giovannisalviati.spring6restmvc.controllers;

import com.giovannisalviati.spring6restmvc.entities.Beer;
import com.giovannisalviati.spring6restmvc.models.BeerDTO;
import com.giovannisalviati.spring6restmvc.repositories.BeerRepository;
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
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerById() {
        Beer beer = beerRepository.findAll().getFirst();

        assertThat(beerController.getBeerById(beer.getId())).isNotNull();
    }

    @Test
    void testGetBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () ->
                beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    void testListBeers() {
        List<BeerDTO> beers = beerController.listBeer();

        assertThat(beers.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyListBeers() {
        beerRepository.deleteAll();

        List<BeerDTO> beers = beerController.listBeer();

        assertThat(beers.size()).isEqualTo(0);
    }
}