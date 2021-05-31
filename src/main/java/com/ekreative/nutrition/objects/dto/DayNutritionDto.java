package com.ekreative.nutrition.objects.dto;

import java.util.List;

public class DayNutritionDto {

    private List<MealNutritionDto> mealNutritionDtoList;
    private Long nutritionScore;

    public List<MealNutritionDto> getMealNutritionDtoList() {
        return mealNutritionDtoList;
    }

    public void setMealNutritionDtoList(List<MealNutritionDto> mealNutritionDtoList) {
        this.mealNutritionDtoList = mealNutritionDtoList;
    }

    public Long getNutritionScore() {
        return nutritionScore;
    }

    public void setNutritionScore(Long nutritionScore) {
        this.nutritionScore = nutritionScore;
    }
}
