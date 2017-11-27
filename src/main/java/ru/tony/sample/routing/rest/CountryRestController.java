package ru.tony.sample.routing.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tony.sample.routing.entiry.Country;
import ru.tony.sample.routing.service.CountryService;

import java.util.List;


@RestController
public class CountryRestController {

    private CountryService service;

    /**
     * Return list of countries
     * paged or not
     *
     * @param page page need to be showed
     * @return list of countries
     */
    @GetMapping("api/countries")
    public List<Country> getCountries(@RequestParam(name = "page", required = false) Integer page) {
        return service.getCountryList(page);
    }

    @GetMapping("api/countries/count")
    public String getCount() {
        return String.valueOf(service.getCount());
    }

    @Autowired
    public void setService(CountryService service) {
        this.service = service;
    }
}
