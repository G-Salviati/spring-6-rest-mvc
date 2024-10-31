package com.giovannisalviati.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giovannisalviati.spring6restmvc.entities.Beer;
import com.giovannisalviati.spring6restmvc.mappers.BeerMapper;
import com.giovannisalviati.spring6restmvc.models.BeerDTO;
import com.giovannisalviati.spring6restmvc.repositories.BeerRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

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

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

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

    @Rollback
    @Transactional
    @Test
    void testDeleteBeerById() {
        Beer beer = beerRepository.findAll().getFirst();

        ResponseEntity<Void> responseEntity = beerController.deleteBeerById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        Optional<Beer> removedBeerOpt = beerRepository.findById(beer.getId());
        assertTrue(removedBeerOpt.isEmpty());
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteBeerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPatchBeerById() {
        Beer beer = beerRepository.findAll().getFirst();
        Integer version = beer.getVersion();
        BeerDTO beerDTO = BeerDTO.builder().price(BigDecimal.valueOf(5.00)).build();

        ResponseEntity<Void> responseEntity = beerController.patchBeerById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        entityManager.flush();
        Optional<Beer> updatedBeerOpt = beerRepository.findById(beer.getId());
        assertTrue(updatedBeerOpt.isPresent());
        assertThat(updatedBeerOpt.get().getPrice()).isEqualTo(beerDTO.getPrice());
        assertThat(updatedBeerOpt.get().getBeerName()).isEqualTo(beer.getBeerName());
        assertThat(updatedBeerOpt.get().getVersion()).isEqualTo(version + 1);
    }

    @Rollback
    @Transactional
    @Test
    void testPatchBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.patchBeerById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Test
    void patchBeerByIdNameTooLong() throws Exception {
        Beer beer = beerRepository.findAll().getFirst();

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "new name 12321412451123214124511232141245112321412451123214124511232141245112321412451");

        MvcResult result = mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}