package com.giovannisalviati.spring6restmvc.mappers;

import com.giovannisalviati.spring6restmvc.entities.Beer;
import com.giovannisalviati.spring6restmvc.models.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDTOtoBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDTO(Beer beer);
}
