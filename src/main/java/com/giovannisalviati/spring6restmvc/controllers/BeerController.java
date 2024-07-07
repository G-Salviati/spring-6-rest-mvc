package com.giovannisalviati.spring6restmvc.controllers;

import com.giovannisalviati.spring6restmvc.models.Beer;
import com.giovannisalviati.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {
    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeer(){
        return beerService.listBeer();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{beerId}")
    public Beer getBeerById(@PathVariable("beerId") UUID id){

        log.debug("getBeerById called - in controller.");

        return beerService.getBeerById(id);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody Beer beer){
        beerService.saveNewBeer(beer);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
