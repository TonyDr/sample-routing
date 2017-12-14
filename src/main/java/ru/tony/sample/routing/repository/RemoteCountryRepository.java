package ru.tony.sample.routing.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.tony.sample.routing.entiry.Country;
import ru.tony.sample.routing.rest.SSLRestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Current implementation of country repository
 */
@Service
public class RemoteCountryRepository implements CountryRepository {

    private static final String URL = "https://restcountries.eu/rest/v2/all";

    private List<Country> countryList;

    private SSLRestTemplate restTemplate;

    @Autowired
    public RemoteCountryRepository(SSLRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * load data from remote service
     */
    @PostConstruct
    public void init() {
        ResponseEntity<Country[]> rateResponse =
                restTemplate.getTemplate().getForEntity(URL, Country[].class);
        countryList = Arrays.asList(rateResponse.getBody());
    }

    @Override
    public List<Country> getAll() {
        return countryList;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public List<Country> getPage(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int end = start + pageSize;
        if (page <= 0 || start > getCount()) {
            return new ArrayList<>();
        }
        if (end > getCount()) {
            end = getCount();
        }
        return new ArrayList<>(countryList.subList(start , end ));
    }
}
