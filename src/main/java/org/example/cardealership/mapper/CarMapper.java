package org.example.cardealership.mapper;

import org.example.cardealership.dto.CarDto;
import org.example.cardealership.dto.CreateCarDto;
import org.example.cardealership.dto.UpdateCarDto;
import org.example.cardealership.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    // Entity → EntityDTO
    public CarDto toDto(Car car) {
        if (car == null) {
            return null;
        }
        return new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getDescription(),
                car.getYear(),
                car.getPrice()
        );
    }

    //CreateEntity -> Entity
    public Car toEntity(CreateCarDto dto) {
        if (dto == null) {
            return null;
        }
        Car car = new Car();
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setDescription(dto.getDescription());
        car.setYear(dto.getYear());
        car.setPrice(dto.getPrice());
        return car;
    }

    // UpdateEntityDTO → update already existing Entity
    public void updateEntity(UpdateCarDto dto, Car car) {
        if (dto == null || car == null) {
            return;
        }
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setDescription(dto.getDescription());
        car.setYear(dto.getYear());
        car.setPrice(dto.getPrice());
    }
}
