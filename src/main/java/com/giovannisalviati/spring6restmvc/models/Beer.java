package com.giovannisalviati.spring6restmvc.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Beer {
    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStyle beerstyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
