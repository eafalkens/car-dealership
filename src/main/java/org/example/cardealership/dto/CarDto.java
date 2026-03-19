package org.example.cardealership.dto;

public class CarDto {

    private Long id;
    private String brand;
    private String model;
    private String description;
    private int year;
    private double price;

    public CarDto() {
    }

    public CarDto(Long id, String brand, String model, String description, int year, double price) {
        this.id = id;
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
}