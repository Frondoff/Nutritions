package com.ekreative.nutrition.objects;

import javax.persistence.*;

@Entity
@Table(name = "nutritions")
public class Nutritions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "day_id")
    private Days day;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @Column
    private int carbohydrates;
    @Column
    private int fats;
    @Column
    private int proteins;

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

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }
}
