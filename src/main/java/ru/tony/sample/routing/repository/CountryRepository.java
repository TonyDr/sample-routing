package ru.tony.sample.routing.repository;

import ru.tony.sample.routing.entiry.Country;

import java.util.List;

public interface CountryRepository {

    /**
     * Get list of all countries
     *
     * @return list of countries
     */
    List<Country> getAll();

    /**
     * Get count of countries
     *
     * @return count
     */
    int getCount();

    /**
     * Get specified page of countries
     *
     * @param page number of page
     * @param pageSize size of page
     * @return countries on page
     */
    List<Country> getPage(int page, int pageSize);
}
