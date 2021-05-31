package com.ekreative.nutrition.repository;

import com.ekreative.nutrition.objects.Schedule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ScheduleRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ScheduleRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Schedule createSchedule(Schedule schedules) {
        Session session = sessionFactory.getCurrentSession();
        session.save(schedules);

        return schedules;
    }

    public void updateSchedule(Schedule schedules) {
        Session session = sessionFactory.getCurrentSession();
        session.update(schedules);
    }

    public Schedule getScheduleById(int id) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select schedule from Schedule schedule where schedule.id = :id", Schedule.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    public Schedule getScheduleByDayId(int id) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select schedule from Schedule schedule " +
                        "where schedule.day.id = :id", Schedule.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    public List<Schedule> getSchedulesStartingFromDayId(int id) {
        return sessionFactory.getCurrentSession()
                .createQuery("Select schedule from Schedule schedule where schedule.day.id >= :id " +
                        "order by schedule.id", Schedule.class)
                .setParameter("id", id)
                .list();
    }
}
