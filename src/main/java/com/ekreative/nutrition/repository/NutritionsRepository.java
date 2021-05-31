package com.ekreative.nutrition.repository;

import com.ekreative.nutrition.objects.Nutritions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public class NutritionsRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public NutritionsRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNutritions(Nutritions nutritions) {
        Session session = sessionFactory.getCurrentSession();
        session.save(nutritions);
    }

    public void updateNutritions(Nutritions nutritions) {
        Session session = sessionFactory.getCurrentSession();
        session.update(nutritions);
    }

    public List<Nutritions> getNutritionsByMealPlanIdStartingFromDate(int id, Date date) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select nutritions From Nutritions nutritions " +
                        "where nutritions.day.date >= :date and nutritions.meal.mealPlan.id = :id " +
                        "order by nutritions.id", Nutritions.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .list();
    }

    public Nutritions getNutritionsByMealIdAndDate(int id, Date date) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select nutritions From Nutritions nutritions " +
                        "where nutritions.day.date = :date and nutritions.meal.id = :id " +
                        "order by nutritions.meal.id", Nutritions.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .uniqueResult();
    }

    public int getNutritionsSumByMealIdAndDate(int id, Date date) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select (nutritions.carbohydrates " +
                        "+ nutritions.fats + nutritions.proteins) as Sum From Nutritions nutritions " +
                        "where nutritions.meal.id = :id and nutritions.day.date = :date", Integer.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .uniqueResult();
    }
}
