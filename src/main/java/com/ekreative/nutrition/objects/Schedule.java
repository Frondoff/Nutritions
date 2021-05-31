package com.ekreative.nutrition.objects;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "day_id")
    private Days day;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "meal_plan_id")
    private MealPlan mealPlan;

    @Column(name = "is_workout")
    private boolean isWorkout;

    @Column(name = "nutrition_score")
    private long nutritionScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Days getDay() {
        return day;
    }

    public void setDay(Days days) {
        this.day = days;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    public boolean isWorkout() {
        return isWorkout;
    }

    public void setWorkout(boolean workout) {
        isWorkout = workout;
    }

    public long getNutritionScore() {
        return nutritionScore;
    }

    public void setNutritionScore(long nutritionsScore) {
        this.nutritionScore = nutritionsScore;
    }
}
