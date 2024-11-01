package com.giovannisalviati.spring6restmvc.services;

import com.giovannisalviati.spring6restmvc.models.BeerCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BeerCSVServiceImpl implements BeerCSVService {
    @Override
    public List<BeerCSVRecord> convertCSV(File csvFile) throws FileNotFoundException {

        return new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                .withType(BeerCSVRecord.class)
                .build()
                .parse();
    }
}
