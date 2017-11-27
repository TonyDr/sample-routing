package ru.tony.sample.routing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tony.sample.routing.entiry.Country;
import ru.tony.sample.routing.repository.CountryRepository;

import java.util.List;

@Service
public class CountryService {

    private static  final int PAGE_SIZE = 5;

    private final CountryRepository repository;

    @Autowired
    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    /**
     * return country list
     *
     * @param page page to return
     * @return country list
     */
    public List<Country> getCountryList(Integer page) {
        if (page == null) {
            return repository.getAll();
        } else {
            return repository.getPage(page, PAGE_SIZE);
        }
    }


    public int getCount() {
        return repository.getCount();
    }
}
