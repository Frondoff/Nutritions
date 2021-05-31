package com.ekreative.nutrition.objects.dto;

public class ScheduleForWeekDto {

    private String dayOfTheWeekName;
    private int mealPlanId;
    private boolean isWorkout;

    public String getDayOfTheWeekName() {
        return dayOfTheWeekName;
    }

    public void setDayOfTheWeekName(String dayOfTheWeekName) {
        this.dayOfTheWeekName = dayOfTheWeekName;
    }

    public int getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public boolean isWorkout() {
        return isWorkout;
    }

    public void setWorkout(boolean workout) {
        isWorkout = workout;
    }
}
