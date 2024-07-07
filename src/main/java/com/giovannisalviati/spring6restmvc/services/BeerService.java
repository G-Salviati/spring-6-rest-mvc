package com.giovannisalviati.spring6restmvc.services;

import com.giovannisalviati.spring6restmvc.models.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeer();

    Beer getBeerById(UUID id);

    void saveNewBeer(Beer beer);
}
