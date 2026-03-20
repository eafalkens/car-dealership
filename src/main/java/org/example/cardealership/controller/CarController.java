package org.example.cardealership.controller;

import jakarta.validation.Valid;
import org.example.cardealership.dto.CreateCarDto;
import org.example.cardealership.dto.UpdateCarDto;
import org.example.cardealership.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.example.cardealership.exception.ResourceNotFoundException;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // List all cars
    @GetMapping
    public String listCars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "list";
    }

    // Show form to create a new car
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("car", new CreateCarDto());
        return "create";
    }

    // Handle the submission of the new car form
    @PostMapping("/create")
    public String createCar(@Valid @ModelAttribute("car") CreateCarDto carDto, BindingResult result) {
        if (result.hasErrors()) {
            return "create";
        }
        try {
            carService.createCar(carDto);
        } catch (IllegalArgumentException e) {
            result.rejectValue("year", "year.invalid", e.getMessage());
            return "create";
        }
        return "redirect:/cars";
    }

    // Show form to update an existing car
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        var car = carService.getCarById(id).orElseThrow(() -> new ResourceNotFoundException("Car not found with Id:" + id));
        model.addAttribute("car", car);
        return "update";
    }

    // Handle the submission of the update form
    @PostMapping("/edit/{id}")
    public String updateCar(@PathVariable Long id, @Valid @ModelAttribute("car") UpdateCarDto carDto, BindingResult result) {
        if (result.hasErrors()) {
            return "update";
        }
        try {
            carService.updateCar(id, carDto);
        } catch (IllegalArgumentException e) {
            result.rejectValue("year", "year.invalid", e.getMessage());
            return "update";
        }
        return "redirect:/cars";
    }

    // Delete a car
    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/cars";
    }

    // Search for cars by brand
    @GetMapping("/search/brand")
    public String searchByBrand(@RequestParam String brand, Model model) {
        model.addAttribute("cars", carService.findCarsByBrand(brand));
        return "list";
    }

    // Search for cars cheaper than a certain price
    @GetMapping("/search/price")
    public String searchByPrice(@RequestParam double price, Model model) {
        model.addAttribute("cars", carService.findCarsCheaperThan(price));
        return "list";
    }

    // Search for cars newer than a certain year
    @GetMapping("/search/year")
    public String searchByYear(@RequestParam int year, Model model) {
        model.addAttribute("cars", carService.findCarsNewerThan(year));
        return "list";
    }
}