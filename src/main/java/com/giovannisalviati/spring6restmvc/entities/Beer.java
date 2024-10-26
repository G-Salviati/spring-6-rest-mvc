package com.giovannisalviati.spring6restmvc.entities;

import com.giovannisalviati.spring6restmvc.models.BeerStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.id.UUIDGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@ToString
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
