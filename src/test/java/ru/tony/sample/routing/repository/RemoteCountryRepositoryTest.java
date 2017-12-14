package ru.tony.sample.routing.repository;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.tony.sample.routing.rest.SSLRestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RemoteCountryRepositoryTest {


    private static final int PAGE_SIZE = 3;
    private static RemoteCountryRepository sut;

    @BeforeClass
    public static void beforeClass() {
        sut = new RemoteCountryRepository(new SSLRestTemplate());
        sut.init();
    }

    @Test
    public void getAllShouldReturnRecords() {
        assertTrue(sut.getAll().size() > 0);
    }

    @Test
    public void recordContainsCurrency() {
        assertTrue(sut.getAll().get(0).getCurrencies().size() > 0);
    }

    @Test
    public void getCountShouldReturnValue() {
        // accordingly information from internet exist 195 countries but service return more;
        assertTrue(sut.getCount() >= 195);
    }

    @Test
    public void shouldReturnFirstPage() {
        assertEquals(PAGE_SIZE, sut.getPage(1, PAGE_SIZE).size());
    }

    @Test
    public void shouldReturnEmptyListZeroPageNumber() {
        assertEquals(0, sut.getPage(0, PAGE_SIZE).size());
    }

    @Test
    public void shouldReturnEmptyListForNegativePageNumber() {
        assertEquals(0, sut.getPage(-1, PAGE_SIZE).size());
    }

    @Test
    public void shouldReturnEmptyListWhenPageGreaterThenMaxAvailable() {
        assertEquals(0, sut.getPage(sut.getCount()/ PAGE_SIZE + 2, PAGE_SIZE).size());
    }

    @Test
    public void shouldReturnLastPage() {
        int pages = (int) Math.ceil((double) sut.getCount() / PAGE_SIZE);
        int lastPageSize = sut.getCount() - (pages -1) * PAGE_SIZE;
        assertEquals(lastPageSize, sut.getPage(pages, PAGE_SIZE).size());
    }
}