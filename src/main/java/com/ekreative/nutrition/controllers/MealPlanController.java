package com.ekreative.nutrition.controllers;

import com.ekreative.nutrition.objects.dto.MealPlanDto;
import com.ekreative.nutrition.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @Autowired
    public MealPlanController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
    }

    @GetMapping("schedule/{dayId}/meal-plan")
    public ResponseEntity<MealPlanDto> getMealPlan(@PathVariable int dayId) {
        try {
            return new ResponseEntity<>(mealPlanService.getMealPlan(dayId), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MealPlanDto(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/meal-plans")
    public ResponseEntity<Integer> createMealPlan(@RequestBody MealPlanDto mealPlanDto) {
        return new ResponseEntity<>(mealPlanService.createMealPlan(mealPlanDto), HttpStatus.CREATED);
    }

    @PutMapping("/meal-plans/{mealPlanId}")
    public HttpStatus editMealPlan(@RequestBody MealPlanDto mealPlanDto, @PathVariable int mealPlanId,
                                   @RequestParam boolean startingFromToday) {
        try {
            mealPlanService.editMealPlan(mealPlanId, mealPlanDto, startingFromToday);
            return HttpStatus.NO_CONTENT;
        } catch (IllegalArgumentException ex) {
            return HttpStatus.NOT_FOUND;
        }
    }

    @DeleteMapping("/meal-plans/{mealPlanId}")
    public HttpStatus deleteMealPlan(@PathVariable int mealPlanId) {
        try {
            mealPlanService.deleteMealPlan(mealPlanId);
            return HttpStatus.NO_CONTENT;
        } catch (IllegalArgumentException ex) {
            return HttpStatus.NOT_FOUND;
        }
    }
}
