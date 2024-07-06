package com.giovannisalviati.spring6restmvc.controllers;

import com.giovannisalviati.spring6restmvc.models.Beer;
import com.giovannisalviati.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;

    @RequestMapping("/api/v1/beers")
    public List<Beer> listBeer(){
        return beerService.listBeer();
    }
    public Beer getBeerById(UUID id){

        log.debug("getBeerById called - in controller.");

        return beerService.getBeerById(id);
    }
}
