package org.example.cardealership.service;

import org.example.cardealership.dto.CarDto;
import org.example.cardealership.dto.CreateCarDto;
import org.example.cardealership.dto.UpdateCarDto;
import org.example.cardealership.entity.Car;
import org.example.cardealership.mapper.CarMapper;
import org.example.cardealership.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    CarService carService;
    Car car;
    CarDto carDto;
    CreateCarDto createCarDto;
    UpdateCarDto updateCarDto;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId(1L);
        car.setBrand("Volvo");
        car.setModel("XC60");
        car.setYear(2020);
        car.setPrice(200000.0);
        car.setDescription("SUV");

        carDto = new CarDto(1L, "Volvo", "XC60", "SUV", 2020, 200000.0);

        createCarDto = new CreateCarDto();
        createCarDto.setBrand("Volvo");
        createCarDto.setModel("S60");
        createCarDto.setYear(2025);
        createCarDto.setPrice(300000.0);
        createCarDto.setDescription("Sedan");

        updateCarDto = new UpdateCarDto();
        updateCarDto.setBrand("BMW");
        updateCarDto.setModel("X5");
        updateCarDto.setYear(2022);
        updateCarDto.setPrice(450000.0);
        updateCarDto.setDescription("SUV");
    }

    @Test
    void getAllCarsShouldReturnListOfCarDtos() {
        when(carRepository.findAll()).thenReturn(List.of(car));
        when(carMapper.toDto(car)).thenReturn(carDto);

        List<CarDto> result = carService.getAllCars();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(carDto, result.getFirst());

        verify(carRepository).findAll();
        verify(carMapper).toDto(car);
    }

    @Test
    void getCarByIdShouldReturnCarDtoWhenCarExists() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carMapper.toDto(car)).thenReturn(carDto);

        Optional<CarDto> result = carService.getCarById(1L);

        assertTrue(result.isPresent());
        assertEquals(carDto, result.get());

        verify(carRepository).findById(1L);
        verify(carMapper).toDto(car);
    }

    @Test
    void getCarByIdShouldReturnEmptyOptionalWhenCarDoesNotExist() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<CarDto> result = carService.getCarById(1L);

        assertTrue(result.isEmpty());

        verify(carRepository).findById(1L);
        verify(carMapper, never()).toDto(any());
    }

    @Test
    void createCarShouldReturnSavedCarDtoWhenCreateCarDtoIsValid() {
        Car mappedCar = new Car();
        mappedCar.setBrand("Volvo");
        mappedCar.setModel("S60");
        mappedCar.setYear(2025);
        mappedCar.setPrice(300000.0);
        mappedCar.setDescription("Sedan");

        Car savedCar = new Car();
        savedCar.setId(2L);
        savedCar.setBrand("Volvo");
        savedCar.setModel("S60");
        savedCar.setYear(2025);
        savedCar.setPrice(300000.0);
        savedCar.setDescription("Sedan");

        CarDto savedCarDto = new CarDto(2L, "Volvo", "S60", "Sedan", 2025, 300000.0);

        when(carMapper.toEntity(createCarDto)).thenReturn(mappedCar);
        when(carRepository.save(mappedCar)).thenReturn(savedCar);
        when(carMapper.toDto(savedCar)).thenReturn(savedCarDto);

        CarDto result = carService.createCar(createCarDto);

        assertNotNull(result);
        assertEquals(savedCarDto, result);

        verify(carMapper).toEntity(createCarDto);
        verify(carRepository).save(mappedCar);
        verify(carMapper).toDto(savedCar);
    }

    @Test
    void createCarShouldThrowExceptionWhenYearIsOlderThan1950() {
        CreateCarDto oldCar = new CreateCarDto();
        oldCar.setBrand("Ford");
        oldCar.setModel("Old");
        oldCar.setYear(1940);
        oldCar.setPrice(50000.0);
        oldCar.setDescription("Old car");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> carService.createCar(oldCar)
        );

        assertEquals("We do not accept cars older than 1950.", exception.getMessage());

        verify(carMapper, never()).toEntity(any());
        verify(carRepository, never()).save(any());
        verify(carMapper, never()).toDto(any());
    }

    @Test
    void updateCarShouldReturnUpdatedCarDtoWhenCarExists() {
        CarDto updatedCarDto = new CarDto(1L, "BMW", "X5", "SUV", 2022, 450000.0);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);
        when(carMapper.toDto(car)).thenReturn(updatedCarDto);

        CarDto result = carService.updateCar(1L, updateCarDto);

        assertNotNull(result);
        assertEquals(updatedCarDto, result);

        verify(carRepository).findById(1L);
        verify(carMapper).updateEntity(updateCarDto, car);
        verify(carRepository).save(car);
        verify(carMapper).toDto(car);
    }

    @Test
    void updateCarShouldReturnNullWhenCarDoesNotExist() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        CarDto result = carService.updateCar(1L, updateCarDto);

        assertNull(result);

        verify(carRepository).findById(1L);
        verify(carMapper, never()).updateEntity(any(), any());
        verify(carRepository, never()).save(any());
        verify(carMapper, never()).toDto(any());
    }

    @Test
    void deleteCarShouldCallDeleteById() {
        carService.deleteCar(1L);

        verify(carRepository).deleteById(1L);
    }

    @Test
    void findCarsByBrandShouldReturnListOfCarDtos() {
        when(carRepository.findByBrand("Volvo")).thenReturn(List.of(car));
        when(carMapper.toDto(car)).thenReturn(carDto);

        List<CarDto> result = carService.findCarsByBrand("Volvo");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(carDto, result.getFirst());

        verify(carRepository).findByBrand("Volvo");
        verify(carMapper).toDto(car);
    }

    @Test
    void findCarsCheaperThanShouldReturnListOfCarDtos() {
        when(carRepository.findByPriceLessThan(300000.0)).thenReturn(List.of(car));
        when(carMapper.toDto(car)).thenReturn(carDto);

        List<CarDto> result = carService.findCarsCheaperThan(300000.0);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(carDto, result.getFirst());

        verify(carRepository).findByPriceLessThan(300000.0);
        verify(carMapper).toDto(car);
    }

    @Test
    void findCarsNewerThanShouldReturnListOfCarDtos() {
        when(carRepository.findByYearGreaterThan(2019)).thenReturn(List.of(car));
        when(carMapper.toDto(car)).thenReturn(carDto);

        List<CarDto> result = carService.findCarsNewerThan(2019);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(carDto, result.getFirst());

        verify(carRepository).findByYearGreaterThan(2019);
        verify(carMapper).toDto(car);
    }
}