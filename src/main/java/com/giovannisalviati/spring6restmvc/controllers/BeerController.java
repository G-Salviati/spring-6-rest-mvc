package com.giovannisalviati.spring6restmvc.controllers;

import com.giovannisalviati.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BeerController {
    private final BeerService beerService;
}
