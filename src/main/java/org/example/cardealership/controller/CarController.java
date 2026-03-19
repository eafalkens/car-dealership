package org.example.cardealership.controller;

import org.example.cardealership.dto.CreateCarDto;
import org.example.cardealership.dto.UpdateCarDto;
import org.example.cardealership.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // 1. List all cars
    @GetMapping
    public String listCars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "list"; // Motsvarar list.html
    }

    // 2. Show form to create a new car
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("car", new CreateCarDto());
        return "create"; // Motsvarar create.html
    }

    // 3. Handle the submission of the new car form
    @PostMapping("/create")
    public String createCar(@ModelAttribute("car") CreateCarDto carDto) {
        carService.createCar(carDto);
        return "redirect:/cars";
    }

    // 4. Show form to update an existing car
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        var car = carService.getCarById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        model.addAttribute("car", car);
        return "update";
    }

    // 5. Handle the submission of the update form
    @PostMapping("/edit/{id}")
    public String updateCar(@PathVariable Long id, @ModelAttribute("car") UpdateCarDto carDto) {
        carService.updateCar(id, carDto);
        return "redirect:/cars";
    }

    // 6. Delete a car
    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/cars";
    }
}