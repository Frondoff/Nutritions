package com.ekreative.nutrition.repository;

import com.ekreative.nutrition.objects.Days;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DaysRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public DaysRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Days> getDays() {
        return sessionFactory.getCurrentSession()
                .createQuery("Select days from Days days order by days.id", Days.class)
                .list();
    }
}
