package org.example.cardealership.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String description;

    @Column(name = "car_year")
    @Positive
    @Max(2026)
    private int year;

    @Positive
    private double price;

    @Column(name = "added_at")
    private LocalDate addedAt;

    public Car() {}

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDate.now();
    }

    public Car(String brand, String model, String description, int year, double price) {
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.year = year;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getAddedAt() {
        return addedAt;
    }
}