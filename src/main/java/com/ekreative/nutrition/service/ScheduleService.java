package com.ekreative.nutrition.service;

import com.ekreative.nutrition.objects.Days;
import com.ekreative.nutrition.objects.MealPlan;
import com.ekreative.nutrition.objects.Schedule;
import com.ekreative.nutrition.objects.dto.MealPlanDto;
import com.ekreative.nutrition.objects.dto.ScheduleDto;
import com.ekreative.nutrition.objects.dto.ScheduleForWeekDto;
import com.ekreative.nutrition.objects.enums.DAYS_OF_THE_WEEK;
import com.ekreative.nutrition.repository.DaysRepository;
import com.ekreative.nutrition.repository.MealPlanRepository;
import com.ekreative.nutrition.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MealPlanRepository mealPlanRepository;
    private final DaysRepository daysRepository;
    private final MealPlanService mealPlanService;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, MealPlanRepository mealPlanRepository,
                           DaysRepository daysRepository, MealPlanService mealPlanService) {
        this.scheduleRepository = scheduleRepository;
        this.mealPlanRepository = mealPlanRepository;
        this.daysRepository = daysRepository;
        this.mealPlanService = mealPlanService;
    }

    public List<Integer> createSchedule(List<ScheduleForWeekDto> scheduleForWeekDtoList) {
        List<Integer> scheduleIdList = new ArrayList<>();
        Map<DAYS_OF_THE_WEEK, ScheduleForWeekDto> daySchedule = new HashMap<>();
        Map<DAYS_OF_THE_WEEK, MealPlan> mealPlanForTheDay = new HashMap<>();

        for (ScheduleForWeekDto scheduleForWeekDto : scheduleForWeekDtoList) {
            daySchedule.put(DAYS_OF_THE_WEEK.valueOf(scheduleForWeekDto.getDayOfTheWeekName()), scheduleForWeekDto);
            mealPlanForTheDay.put(DAYS_OF_THE_WEEK.valueOf(scheduleForWeekDto.getDayOfTheWeekName()),
                    mealPlanRepository.getMealPlanById(scheduleForWeekDto.getMealPlanId()));
        }
        List<Days> days = daysRepository.getDays();

        for (Days day : days) {
            Schedule schedule = new Schedule();
            schedule.setDay(day);
            schedule.setMealPlan(mealPlanForTheDay.get(DAYS_OF_THE_WEEK.valueOf(day.getName())));
            schedule.setWorkout(daySchedule.get(DAYS_OF_THE_WEEK.valueOf(day.getName())).isWorkout());
            schedule.setNutritionScore(0);

            scheduleIdList.add(scheduleRepository.createSchedule(schedule).getId());
        }
        return scheduleIdList;
    }

    public void editSchedule(ScheduleForWeekDto scheduleForWeekDto, int dayId, boolean isUpdateFromToday) {
        if (!isUpdateFromToday) {
            dayId += 7;
        }
        List<Schedule> schedules = scheduleRepository.getSchedulesStartingFromDayId(dayId);
        MealPlan mealPlan = mealPlanRepository.getMealPlanById(scheduleForWeekDto.getMealPlanId());

        for (int i = 0; i < schedules.size(); i += 7) {
            schedules.get(i).setMealPlan(mealPlan);
            schedules.get(i).setWorkout(scheduleForWeekDto.isWorkout());

            scheduleRepository.updateSchedule(schedules.get(i));
        }
    }

    public ScheduleDto getSchedule(int dayId) {
        if (scheduleRepository.getScheduleByDayId(dayId) == null) {
            throw new IllegalArgumentException();
        }
        Schedule schedule = scheduleRepository.getScheduleByDayId(dayId);
        ScheduleDto scheduleDto = new ScheduleDto();

        scheduleDto.setDayName(schedule.getDay().getName());
        scheduleDto.setDate(schedule.getDay().getDate());
        scheduleDto.setWorkout(schedule.isWorkout());

        MealPlanDto mealPlanDto = mealPlanService.getMealPlan(dayId);
        scheduleDto.setMealPlanDto(mealPlanDto);

        return scheduleDto;
    }
}
