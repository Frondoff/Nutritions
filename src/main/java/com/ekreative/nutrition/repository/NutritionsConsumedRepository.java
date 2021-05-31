package com.ekreative.nutrition.repository;

import com.ekreative.nutrition.objects.NutritionsConsumed;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Repository
@Transactional
public class NutritionsConsumedRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public NutritionsConsumedRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveNutritionsConsumed(NutritionsConsumed nutritionsConsumed) {
        Session session = sessionFactory.getCurrentSession();
        session.save(nutritionsConsumed);
    }

    public void updateNutritionsConsumed(NutritionsConsumed nutritionsConsumed) {
        Session session = sessionFactory.getCurrentSession();
        session.update(nutritionsConsumed);
    }

    public NutritionsConsumed getNutritionsConsumedByMealIdAndDate(int id, Date date) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select nutritionsConsumed From NutritionsConsumed nutritionsConsumed " +
                        "where nutritionsConsumed.meal.id = :id and nutritionsConsumed.day.date = :date", NutritionsConsumed.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .uniqueResult();
    }

    public int getNutritionsConsumedSumByMealIdAndDate(int id, Date date) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select (nutritionsConsumed.carbohydrates " +
                        "+ nutritionsConsumed.fats + nutritionsConsumed.proteins) as Sum " +
                        "From NutritionsConsumed nutritionsConsumed " +
                        "where nutritionsConsumed.meal.id = :id and nutritionsConsumed.day.date = :date", Integer.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .uniqueResult();
    }
}
