package com.giovannisalviati.spring6restmvc.controllers;

import com.giovannisalviati.spring6restmvc.models.BeerDTO;
import com.giovannisalviati.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
    public static final String BEER_PATH = "/api/v1/beers";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeer() {
        return beerService.listBeer();
    }

    @GetMapping(BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID id) {

        log.debug("getBeerById called - in controller.");

        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> saveNewBeer(@Validated @RequestBody BeerDTO beer) {
        BeerDTO createdBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beers/" + createdBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<Void> updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
        if (beerService.updateBeerById(beerId, beer).isEmpty()) {
            throw new NotFoundException();
        }
        ;

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<Void> deleteBeerById(@PathVariable("beerId") UUID beerId) {
        if (!beerService.deleteBeerById(beerId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<Void> patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
        if (beerService.patchBeerById(beerId, beer).isEmpty()) {
            throw new NotFoundException();
        }
        ;

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
