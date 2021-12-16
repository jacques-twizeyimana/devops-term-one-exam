package rw.ac.rca.termOneExam.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityUtilTest {
    @Autowired
    private CityService cityService;

    @Mock
    private ICityRepository mockCityRepository;

    @Test
    public void MoreThan40Weather() {
        List<City> cities = cityService.getAll();
        for(City city:cities){
            assertTrue(city.getWeather() < Double.parseDouble("40"));
        }
    }

    @Test
    public void LesThan10Weather() {
        List<City> cities = cityService.getAll();
        for(City city:cities){
            assertTrue(city.getWeather() > Double.parseDouble("10"));
        }

    }

    @Test
    public void citiesContainKigaliAndMusanze() {
        List<City> cities = cityService.getAll();

        assertTrue(cities.stream()
                .filter(city -> "Musanze".equals(city.getName()))
                .findAny().isPresent());

        assertTrue(cities.stream()
                .filter(city -> "Kigali".equals(city.getName()))
                .findAny().isPresent());

    }
    @Test
    public void testSpying() {
        List<City> spyList = Mockito.spy(ArrayList.class);
        City city = new City("Kigali", 24);
        spyList.add(city);
        Mockito.verify(spyList).add(city);

        assertEquals(1, spyList.size());
    }

    @Test
    public void testMocking() {

        Mockito.when(mockCityRepository.count()).thenReturn(11L);
        long numberOfCities = mockCityRepository.count();

        assertEquals(11l,numberOfCities);
        Mockito.verify(mockCityRepository).count();
    }

}
