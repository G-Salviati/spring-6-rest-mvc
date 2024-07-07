package com.giovannisalviati.spring6restmvc.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Customer {
    private UUID id;
    private Integer version;
    String customerName;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
