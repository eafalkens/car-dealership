package org.example.cardealership.controller;

import org.example.cardealership.dto.CarDto;
import org.example.cardealership.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    private CarDto carDto;

    @BeforeEach
    void setUp() {
        carDto = new CarDto(1L, "Volvo", "XC60", "SUV", 2020, 200000.0);
    }

    @Test
    void testListCars() throws Exception {
        when(carService.getAllCars()).thenReturn(List.of(carDto));

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("cars"));

        verify(carService).getAllCars();
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/cars/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCar() throws Exception {
        mockMvc.perform(post("/cars/create")
                        .param("brand", "BMW")
                        .param("model", "X5")
                        .param("description", "SUV")
                        .param("year", "2021")
                        .param("price", "300000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cars"));

        verify(carService).createCar(any()); // <-- Fixat med import
    }

    @Test
    void testShowUpdateForm() throws Exception {
        when(carService.getCarById(1L)).thenReturn(Optional.of(carDto));

        mockMvc.perform(get("/cars/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("update"))
                .andExpect(model().attributeExists("car"));

        verify(carService).getCarById(1L);
    }

    @Test
    void testUpdateCar() throws Exception {
        mockMvc.perform(post("/cars/edit/1")
                        .param("brand", "Audi")
                        .param("model", "A4")
                        .param("description", "Sedan")
                        .param("year", "2022")
                        .param("price", "280000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cars"));

        verify(carService).updateCar(eq(1L), any()); // <-- Fixat med import
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(get("/cars/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cars"));

        verify(carService).deleteCar(1L);
    }

    @Test
    void testSearchByBrand() throws Exception {
        when(carService.findCarsByBrand("Volvo")).thenReturn(List.of(carDto));

        mockMvc.perform(get("/cars/search/brand").param("brand", "Volvo"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("cars"));

        verify(carService).findCarsByBrand("Volvo");
    }

    @Test
    void testSearchByPrice() throws Exception {
        when(carService.findCarsCheaperThan(300000)).thenReturn(List.of(carDto));

        mockMvc.perform(get("/cars/search/price").param("price", "300000"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("cars"));

        verify(carService).findCarsCheaperThan(300000);
    }

    @Test
    void testSearchByYear() throws Exception {
        when(carService.findCarsNewerThan(2019)).thenReturn(List.of(carDto));

        mockMvc.perform(get("/cars/search/year").param("year", "2019"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("cars"));

        verify(carService).findCarsNewerThan(2019);
    }
}