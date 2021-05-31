package com.ekreative.nutrition.repository;

import com.ekreative.nutrition.objects.Meal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public class MealRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public MealRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveMeal(Meal meal) {
        Session session = sessionFactory.getCurrentSession();
        session.save(meal);
    }

    public List<Meal> getMealsByMealPlanIdAndDate(int id, Date date) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select meal from Meal meal " +
                        "left JOIN Nutritions nutritions ON meal.id = nutritions.meal.id " +
                        "left join MealPlan mealPlan on meal.mealPlan.id = mealPlan.id " +
                        "where mealPlan.id = :id and nutritions.day.date = :date", Meal.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .list();
    }
}
