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
    public void saveCitySuccess () {

        City city = new City("California",20);

        CreateCityDTO createCity = new CreateCityDTO();
        createCity.setName("California");
        createCity.setWeather(20);

        when(cityRepositoryMock.findById(1l)).thenReturn(Optional.empty());
        when(cityRepositoryMock.save(city)).thenReturn(city);

        City response = cityService.save(createCity);
        System.out.println(response);

        assertEquals("California",response.getName());
    }
}
