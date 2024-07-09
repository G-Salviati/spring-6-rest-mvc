package com.giovannisalviati.spring6restmvc.controllers;

import com.giovannisalviati.spring6restmvc.models.Beer;
import com.giovannisalviati.spring6restmvc.services.BeerService;
import com.giovannisalviati.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BeerController.class)
//@SpringBootTest
class BeerControllerTest {

    //@Autowired
    //BeerController beerController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getBeerById() throws Exception {
        //System.out.println(beerController.getBeerById(UUID.randomUUID()));
        Beer testBeer = beerServiceImpl.listBeer().getFirst();

        given(beerService.getBeerById(any(UUID.class))).willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beers/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}