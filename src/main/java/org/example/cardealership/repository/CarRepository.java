package org.example.cardealership.repository;

import org.example.cardealership.entity.Car;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends ListCrudRepository<Car, Long> {

    // Find cars by brand
    List<Car> findByBrand(String brand);

    // Find cars with price less than a certain value
    List<Car> findByPriceLessThan(double price);

    // Find cars with year greater than a certain value
    List<Car> findByYearGreaterThan(int year);
}