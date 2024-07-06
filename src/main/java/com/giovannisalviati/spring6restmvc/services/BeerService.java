package com.giovannisalviati.spring6restmvc.services;

import com.giovannisalviati.spring6restmvc.models.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}
