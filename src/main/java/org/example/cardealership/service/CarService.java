package org.example.cardealership.service;

import org.example.cardealership.entity.Car;
import org.example.cardealership.repository.CarRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Get all cars
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Get car by ID
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    // Create a new car
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    // Update car
    public Car updateCar(Long id, Car car) {
        if (carRepository.existsById(id)) {
            car.setId(id);
            return carRepository.save(car);
        }
        return null;
    }

    // Delete Car
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}