package com.ekreative.nutrition.objects.dto;

public class MealDto {

    private String name;
    private NutritionsDto nutritionsDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NutritionsDto getNutritionsDto() {
        return nutritionsDto;
    }

    public void setNutritionsDto(NutritionsDto nutritionsDto) {
        this.nutritionsDto = nutritionsDto;
    }
}
