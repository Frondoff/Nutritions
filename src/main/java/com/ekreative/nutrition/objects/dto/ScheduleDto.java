package com.ekreative.nutrition.objects.dto;

import java.sql.Date;

public class ScheduleDto {

    private String dayName;
    private Date date;
    private MealPlanDto mealPlanDto;
    private boolean isWorkout;

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MealPlanDto getMealPlanDto() {
        return mealPlanDto;
    }

    public void setMealPlanDto(MealPlanDto mealPlanDto) {
        this.mealPlanDto = mealPlanDto;
    }

    public boolean isWorkout() {
        return isWorkout;
    }

    public void setWorkout(boolean workout) {
        isWorkout = workout;
    }
}
