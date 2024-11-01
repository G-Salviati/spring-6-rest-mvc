package com.giovannisalviati.spring6restmvc.services;

import com.giovannisalviati.spring6restmvc.models.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeerCSVServiceImplTest {
    BeerCSVServiceImpl beerCSVServiceImpl = new BeerCSVServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> csvRecords = beerCSVServiceImpl.convertCSV(file);

        assertNotNull(csvRecords);

        assertFalse(csvRecords.isEmpty());
    }
}