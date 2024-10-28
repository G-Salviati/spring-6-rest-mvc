package com.giovannisalviati.spring6restmvc.controllers;

import com.giovannisalviati.spring6restmvc.entities.Beer;
import com.giovannisalviati.spring6restmvc.mappers.BeerMapper;
import com.giovannisalviati.spring6restmvc.models.BeerDTO;
import com.giovannisalviati.spring6restmvc.repositories.BeerRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    EntityManager entityManager;

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

    @Transactional
    @Rollback
    @Test
    void testSaveNewBeer() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity<BeerDTO> responseEntity = beerController.saveNewBeer(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationURI = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationURI[4]);

        assertThat(beerRepository.findById(savedUUID)).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateBeerById() {

        Beer beer = beerRepository.findAll().getFirst();
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);

        Integer version = beer.getVersion();
        BigDecimal price = beer.getPrice();

        beerDTO.setPrice(price == null ? BigDecimal.valueOf(2.0) : BigDecimal.valueOf(price.doubleValue() + 1));

        ResponseEntity<Void> responseEntity = beerController.updateBeerById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));
        assertThat(responseEntity.getBody()).isNull();

        entityManager.flush();
        Optional<Beer> updatedBeerOpt = beerRepository.findById(beer.getId());

        assertTrue(updatedBeerOpt.isPresent());
        Beer updatedBeer = updatedBeerOpt.get();
        System.out.println("In test: " + updatedBeer);
        assertThat(updatedBeer).isNotNull();
        assertThat(updatedBeer.getId()).isEqualTo(beer.getId());
        assertThat(updatedBeer.getPrice()).isNotEqualTo(price);
        assertThat(updatedBeer.getVersion()).isEqualTo(version + 1);
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.updateBeerById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }
}