package com.giovannisalviati.spring6restmvc.services;

import com.giovannisalviati.spring6restmvc.models.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface BeerCSVService {
    List<BeerCSVRecord> convertCSV(File csvFile) throws FileNotFoundException;
}
