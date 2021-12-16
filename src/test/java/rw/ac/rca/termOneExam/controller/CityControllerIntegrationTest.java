package rw.ac.rca.termOneExam.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.utils.APICustomResponse;


@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_success() throws JSONException {
        String response = this.restTemplate.getForObject("/api/cities/all", String.class);
        System.out.println(response);
        JSONAssert.assertEquals("[{\"id\":101,\"name\":\"Kigali\",\"weather\":24.0,\"fahrenheit\":75.2},{\"id\":102,\"name\":\"Musanze\",\"weather\":18.0,\"fahrenheit\":64.4},{\"id\":103,\"name\":\"Rubavu\",\"weather\":20.0,\"fahrenheit\":68.0},{\"id\":104,\"name\":\"Nyagatare\",\"weather\":28.0,\"fahrenheit\":82.4}]\n", response, false);
    }

    @Test
    public void getById_success() throws JSONException {
        ResponseEntity<City> response = this.restTemplate.getForEntity("/api/cities/id/101", City.class);

        System.out.println(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(101, response.getBody().getId());
        assertEquals(24, response.getBody().getWeather());
        assertEquals("Kigali", response.getBody().getName());

    }
    @Test
    public void getById_404() {
        ResponseEntity<APICustomResponse> response = this.restTemplate.getForEntity("/api/cities/id/890", APICustomResponse.class);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("City not found with id 890", response.getBody().getMessage());
    }


    @Test
    public void createCity_Success() {

        CreateCityDTO city=new CreateCityDTO();
        city.setName("Nyanza");
        city.setWeather(25.0);

        ResponseEntity<City> response = this.restTemplate.postForEntity("/api/cities/add", city, City.class);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Nyanza",response.getBody().getName());
        assertEquals(25.0, response.getBody().getWeather());
    }

    @Test
    public void createCity_Failure() {

        CreateCityDTO city=new CreateCityDTO();
        city.setName("Kigali");
        city.setWeather(28.0);

        ResponseEntity<APICustomResponse> response = this.restTemplate.postForEntity("/api/cities/add", city, APICustomResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("City name " + city.getName() + " is registered already",response.getBody().getMessage());

    }




}
