package rw.ac.rca.termOneExam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {


    @Mock
    private ICityRepository cityRepositoryMock;

    @InjectMocks
    private CityService cityService;

    @Test
    public void getAll_Test() {
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(new City(1,"Nyanza",17,34),
                new City(2,"Huye",24,52)));
        assertEquals(17,cityService.getAll().get(0).getWeather());
    }
    @Test
    public void getById_success() {
        when(cityRepositoryMock.findById(2l)).thenReturn(Optional.of(new City(2, "Huye", 24, 52)));
        assertEquals(true,cityService.getById(2).isPresent());
    }

    @Test
    public void getById_404() {
        when(cityRepositoryMock.findById(26l)).thenReturn(Optional.empty());
        System.out.println(cityService.getById(26));
        assertEquals (Optional.empty(),cityService.getById(26));
    }

    @Test
    public void createCityAlreadyExists () {
        CreateCityDTO city = new CreateCityDTO();
        city.setName("Kigali");
        city.setWeather(20);

        when(cityRepositoryMock.existsByName("Kigali"))
                .thenReturn(true);
        assertTrue(cityService.existsByName(city.getName()));

    }

    @Test
    public void existByName_success() {
        when(cityRepositoryMock.existsByName("Kigali")).thenReturn(true);

        assertEquals(true, cityService.existsByName("Kigali"));
    }

    @Test
    public void existByName_fail() {
        when(cityRepositoryMock.existsByName("Huye")).thenReturn(false);

        assertEquals(false, cityService.existsByName("Huye"));
    }

    @Test
    public void createCity_success() {
        CreateCityDTO cityDTO = new CreateCityDTO();
        cityDTO.setName("Texas");
        cityDTO.setWeather(32);

        when(cityRepositoryMock.save(any(City.class))).thenReturn(new City(cityDTO.getName(),cityDTO.getWeather()));

        assertEquals(cityDTO.getName(), cityService.save(cityDTO).getName());
    }
}
