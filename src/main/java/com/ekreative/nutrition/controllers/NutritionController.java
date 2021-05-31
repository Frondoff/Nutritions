package com.ekreative.nutrition.controllers;

import com.ekreative.nutrition.objects.dto.DayNutritionDto;
import com.ekreative.nutrition.objects.dto.NutritionsDto;
import com.ekreative.nutrition.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NutritionController {

    private final NutritionService nutritionService;

    @Autowired
    public NutritionController(NutritionService nutritionService) {
        this.nutritionService = nutritionService;
    }

    @GetMapping("schedule/{dayId}/meal-plan/nutrition")
    public ResponseEntity<DayNutritionDto> getDayNutrition(@PathVariable int dayId) {
        try {
            return new ResponseEntity<>(nutritionService.getDayNutrition(dayId), HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException ex) {
            return new ResponseEntity<>(new DayNutritionDto(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("schedule/{dayId}/meal-plan/{mealId}")
    public HttpStatus updateNutritionsConsumed(@PathVariable int dayId, @PathVariable int mealId,
                                               @RequestBody NutritionsDto nutritionsDto) {
        try {
            nutritionService.updateNutritionsConsumed(dayId, mealId, nutritionsDto);
            return HttpStatus.NO_CONTENT;
        } catch (IllegalArgumentException ex) {
            return HttpStatus.NOT_FOUND;
        }
    }
}
