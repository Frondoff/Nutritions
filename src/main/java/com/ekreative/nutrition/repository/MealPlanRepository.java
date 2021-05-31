package com.ekreative.nutrition.repository;

import com.ekreative.nutrition.objects.MealPlan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MealPlanRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public MealPlanRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveMealPlan(MealPlan mealPlan) {
        Session session = sessionFactory.getCurrentSession();
        session.save(mealPlan);
    }

    public void updateMealPlan(MealPlan mealPlan) {
        Session session = sessionFactory.getCurrentSession();
        session.update(mealPlan);
    }

    public void deleteMealPlan(MealPlan mealPlan) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(mealPlan);
    }

    public MealPlan getMealPlanById(int id) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select mealPlan from MealPlan mealPlan where mealPlan.id = :id", MealPlan.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    public MealPlan getMealPlanByDayId(int id) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select mealPlan from MealPlan mealPlan " +
                        "left join Schedule schedule on mealPlan.id = schedule.mealPlan.id " +
                        " where schedule.day.id = :id", MealPlan.class)
                .setParameter("id", id)
                .uniqueResult();
    }
}
