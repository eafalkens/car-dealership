package org.example.cardealership.service;

import org.example.cardealership.dto.CarDto;
import org.example.cardealership.dto.CreateCarDto;
import org.example.cardealership.dto.UpdateCarDto;
import org.example.cardealership.entity.Car;
import org.example.cardealership.mapper.CarMapper;
import org.example.cardealership.repository.CarRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    // Get all cars
    public List<CarDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get car by ID
    public Optional<CarDto> getCarById(Long id) {
        return carRepository.findById(id)
                .map(carMapper::toDto);
    }

    // Create a new car
    public CarDto createCar(CreateCarDto createCarDto) {
        Car car = carMapper.toEntity(createCarDto);
        Car savedCar = carRepository.save(car);
        return carMapper.toDto(savedCar);
    }

    // Update car
    public CarDto updateCar(Long id, UpdateCarDto updateCarDto) {
        Optional<Car> existingCarOptional = carRepository.findById(id);
        if (existingCarOptional.isEmpty()) {
            return null;
        }
        Car existingCar = existingCarOptional.get();
        carMapper.updateEntity(updateCarDto, existingCar);
        Car updatedCar = carRepository.save(existingCar);
        return carMapper.toDto(updatedCar);
    }

    // Delete Car
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    // Find cars by brand
    public List<CarDto> findCarsByBrand(String brand) {
        return carRepository.findByBrand(brand)
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    // Find cars cheaper than a certain price
    public List<CarDto> findCarsCheaperThan(double price) {
        return carRepository.findByPriceLessThan(price)
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    // Find cars newer than a certain year
    public List<CarDto> findCarsNewerThan(int year) {
        return carRepository.findByYearGreaterThan(year)
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }
}