package com.ekreative.nutrition.objects.dto;

import java.util.List;

public class MealPlanDto {

    private String name;
    private List<MealDto> meals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MealDto> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDto> meals) {
        this.meals = meals;
    }
}
