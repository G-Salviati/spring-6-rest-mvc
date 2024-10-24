package com.giovannisalviati.spring6restmvc.services;

import com.giovannisalviati.spring6restmvc.mappers.BeerMapper;
import com.giovannisalviati.spring6restmvc.models.BeerDTO;
import com.giovannisalviati.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor

public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeer() {

        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        return Optional.ofNullable(beerMapper.beerToBeerDTO(beerRepository.findById(id)
                .orElse(null)));

    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOtoBeer(beer)));
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

    }

    @Override
    public void deleteBeerById(UUID beerId) {

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
