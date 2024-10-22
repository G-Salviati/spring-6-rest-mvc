package com.giovannisalviati.spring6restmvc.services;

import com.giovannisalviati.spring6restmvc.models.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> listBeer();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    void updateBeerById(UUID beerId, BeerDTO beer);

    void deleteBeerById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
