package rw.ac.rca.termOneExam.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.List;


@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CityUtilTest {
    @Autowired
    private CityService cityService;

    @Autowired
    private TestRestTemplate restTemplate;

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
    public void citiesContainKigaliAndMusanze() throws JSONException {
        String response = this.restTemplate.getForObject("/api/cities/all", String.class);
        System.out.println(response);

        assertTrue(response.contains("101"));
        assertTrue(response.contains("Kigali"));

        assertTrue(response.contains("102"));
        assertTrue(response.contains("Musanze"));
    }

}
