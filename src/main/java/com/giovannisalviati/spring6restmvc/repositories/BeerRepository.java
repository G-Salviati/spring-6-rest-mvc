package com.giovannisalviati.spring6restmvc.repositories;

import com.giovannisalviati.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
