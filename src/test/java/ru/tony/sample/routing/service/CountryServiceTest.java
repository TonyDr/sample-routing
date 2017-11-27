package ru.tony.sample.routing.service;

import org.junit.Before;
import org.junit.Test;
import ru.tony.sample.routing.entiry.Country;
import ru.tony.sample.routing.repository.CountryRepository;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CountryServiceTest {

    private CountryService sut;
    private CountryRepository repo;

    @Before
    public void beforeTest() {
        repo = mock(CountryRepository.class);
        sut = new CountryService(repo);
    }

    @Test
    public void whenCallGetAllShouldCallGetAllInRepository() {
        when(repo.getAll()).thenReturn(singletonList(new Country()));

        assertEquals(1, sut.getCountryList(null).size());

        verify(repo).getAll();
    }

    @Test
    public void whenCallGetPageShouldCallGetPageInRepository() {
        when(repo.getPage(eq(1), eq(5))).thenReturn(singletonList(new Country()));

        assertEquals(1, sut.getCountryList(1).size());

        verify(repo).getPage(eq(1), eq(5));
    }
}