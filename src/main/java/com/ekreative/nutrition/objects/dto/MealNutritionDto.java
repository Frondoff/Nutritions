package com.ekreative.nutrition.objects.dto;

public class MealNutritionDto {

    private String mealName;
    private NutritionsDto nutritionsDto;
    private NutritionsDto nutritionsConsumedDto;

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public NutritionsDto getNutritionsDto() {
        return nutritionsDto;
    }

    public void setNutritionsDto(NutritionsDto nutritionsDto) {
        this.nutritionsDto = nutritionsDto;
    }

    public NutritionsDto getNutritionsConsumedDto() {
        return nutritionsConsumedDto;
    }

    public void setNutritionsConsumedDto(NutritionsDto nutritionsConsumedDto) {
        this.nutritionsConsumedDto = nutritionsConsumedDto;
    }
}
