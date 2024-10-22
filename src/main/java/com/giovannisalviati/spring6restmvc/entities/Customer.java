package com.giovannisalviati.spring6restmvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    private UUID id;

    @Version
    private Integer version;

    String customerName;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
