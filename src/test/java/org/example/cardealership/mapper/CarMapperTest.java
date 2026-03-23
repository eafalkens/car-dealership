package org.example.cardealership.mapper;

import org.example.cardealership.dto.CarDto;
import org.example.cardealership.dto.CreateCarDto;
import org.example.cardealership.dto.UpdateCarDto;
import org.example.cardealership.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarMapperTest {

    private CarMapper carMapper;

    @BeforeEach
    void setUp() {
        carMapper = new CarMapper();
    }

    @Test
    void toDtoShouldReturnCarDtoWhenCarIsNotNull() {
        Car car = new Car("Volvo", "V60", "SUV", 2020, 200000);
        car.setId(1L);

        CarDto carDto = carMapper.toDto(car);

        assertNotNull(carDto);
        assertEquals(1, carDto.getId());
        assertEquals("Volvo", carDto.getBrand());
        assertEquals("V60", carDto.getModel());
        assertEquals("SUV", carDto.getDescription());
        assertEquals(2020, carDto.getYear());
        assertEquals(200000, carDto.getPrice());
    }

    @Test
    void toEntityShouldReturnNewCarWhenDtoIsNotNull() {
        CreateCarDto dto = new CreateCarDto();
        dto.setBrand("Volvo");
        dto.setModel("XC60");
        dto.setDescription("SUV");
        dto.setYear(2020);
        dto.setPrice(200000.0);

        Car car = carMapper.toEntity(dto);

        assertNotNull(car);
        assertEquals("Volvo", car.getBrand());
        assertEquals("XC60", car.getModel());
        assertEquals("SUV", car.getDescription());
        assertEquals(2020, car.getYear());
        assertEquals(200000, car.getPrice());
    }

    @Test
    void updateEntityShouldUpdateCarWhenCarAndDtoIsNotNull() {
        Car car = new Car();
        car.setBrand("Volvo");
        car.setModel("V60");
        car.setDescription("SUV");
        car.setYear(2020);
        car.setPrice(200000.0);

        UpdateCarDto dto = new UpdateCarDto();
        dto.setBrand("Audi");
        dto.setModel("A1");
        dto.setDescription("Sedan");
        dto.setYear(2018);
        dto.setPrice(78000.0);

        carMapper.updateEntity(dto, car);

        assertEquals(dto.getBrand(), car.getBrand());
        assertEquals(dto.getModel(), car.getModel());
        assertEquals(dto.getDescription(), car.getDescription());
        assertEquals(dto.getYear(), car.getYear());
        assertEquals(dto.getPrice(), car.getPrice());
    }
}