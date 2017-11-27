package ru.tony.sample.routing.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import ru.tony.sample.routing.entiry.Country;
import ru.tony.sample.routing.entiry.Currency;
import ru.tony.sample.routing.service.CountryService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CountryRestController.class, secure = false)
public class CountryRestControllerTest {

    private static final String GREAT_BRITAIN = "Great Britain";
    private static final String POUND = "Pound";
    private static final String RUSSIA = "Russia";
    private static final String ROUBLE = "Rouble";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;
    private static final String EXPECTED =
            "[{\"name\":\"" + RUSSIA + "\",\"currencies\":[{\"name\":\"" + ROUBLE + "\",\"code\":null,\"symbol\":null}]}," +
             "{\"name\":\"" + GREAT_BRITAIN + "\",\"currencies\":[{\"name\":\"" + POUND + "\",\"code\":null,\"symbol\":null}]}]";
    private List<Country> mockCountryList = asList(getMockCountry(RUSSIA, ROUBLE),
            getMockCountry(GREAT_BRITAIN, POUND));

    @Test
    public void getCountriesShouldReturnAllCountryList() throws Exception {
        when(countryService.getCountryList(null)).thenReturn(mockCountryList);

        RequestBuilder requestBuilder = get("/api/countries").accept(APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(OK.value(), result.getResponse().getStatus());
        String resultString = result.getResponse().getContentAsString();
        assertEquals(EXPECTED, resultString);
    }

    @Test
    public void getCountriesShouldReturnPagedResultWhenParamPresent() throws Exception {
        int page = 2;
        when(countryService.getCountryList(eq(page))).thenReturn(mockCountryList);

        MvcResult result = mockMvc.perform(getPagedRequest(page)).andReturn();
        assertEquals(OK.value(), result.getResponse().getStatus());
        assertEquals(EXPECTED, result.getResponse().getContentAsString());


    }

    @Test
    public void shouldReturnCorrectEmptyList() throws Exception {
        int page = 0;
        when(countryService.getCountryList(eq(page))).thenReturn(new ArrayList<Country>());

        MvcResult result = mockMvc.perform(getPagedRequest(page)).andReturn();
        assertEquals(OK.value(), result.getResponse().getStatus());
        assertEquals("[]", result.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnCountOfCountries() throws Exception {
        when(countryService.getCount()).thenReturn(2);
        RequestBuilder requestBuilder = get("/api/countries/count").accept(MediaType.TEXT_PLAIN);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(OK.value(), result.getResponse().getStatus());
        String resultString = result.getResponse().getContentAsString();
        assertEquals("2", resultString);
    }


    private RequestBuilder getPagedRequest(int page) {
        return get("/api/countries?page=" + page).accept(APPLICATION_JSON);
    }


    private Country getMockCountry(String name, String currencyName) {
        Country country = new Country();
        country.setName(name);
        Currency currency = new Currency();
        currency.setName(currencyName);
        country.setCurrencies(singletonList(currency));
        return country;
    }
}
