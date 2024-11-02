package com.giovannisalviati.spring6restmvc.bootstrap;

import com.giovannisalviati.spring6restmvc.entities.Beer;
import com.giovannisalviati.spring6restmvc.entities.Customer;
import com.giovannisalviati.spring6restmvc.models.BeerCSVRecord;
import com.giovannisalviati.spring6restmvc.models.BeerDTO;
import com.giovannisalviati.spring6restmvc.models.BeerStyle;
import com.giovannisalviati.spring6restmvc.repositories.BeerRepository;
import com.giovannisalviati.spring6restmvc.repositories.CustomerRepository;
import com.giovannisalviati.spring6restmvc.services.BeerCSVService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCSVService beerCSVService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadBeerCsvData();
        loadCustomerData();
    }

    private void loadBeerCsvData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {

            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            List<BeerCSVRecord> beerCSVRecords = beerCSVService.convertCSV(file);

            beerCSVRecords.forEach((beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                String upc;
                if (beerCSVRecord.getRow() != null) {
                    upc = beerCSVRecord.getRow().toString();
                } else {

                    Random rand = new Random();

                    int n = rand.nextInt(99999999) + 1;

                    upc = Integer.toString(n);
                }

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(upc)
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());
            }
            ));
        }
    }

    private void loadBeerData() {

        if (beerRepository.count() == 0) {

            Beer beer1 = Beer.builder()
                    .beerName("Heineken")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("9876")
                    .quantityOnHand(30)
                    .price(BigDecimal.valueOf(3.20))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Guinness")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("8523")
                    .quantityOnHand(12)
                    .price(BigDecimal.valueOf(3.90))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sierra Nevada Pale Ale")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("7675")
                    .quantityOnHand(50)
                    .price(BigDecimal.valueOf(2.90))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.saveAll(List.of(beer1, beer2, beer3));
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("Jack")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Andrea")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Anita")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(List.of(customer1, customer2, customer3));

        }

    }
}
