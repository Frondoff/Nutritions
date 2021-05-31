package com.ekreative.nutrition.service;

import com.ekreative.nutrition.objects.Meal;
import com.ekreative.nutrition.objects.Nutritions;
import com.ekreative.nutrition.objects.NutritionsConsumed;
import com.ekreative.nutrition.objects.Schedule;
import com.ekreative.nutrition.objects.dto.DayNutritionDto;
import com.ekreative.nutrition.objects.dto.MealNutritionDto;
import com.ekreative.nutrition.objects.dto.NutritionsDto;
import com.ekreative.nutrition.objects.enums.MEALS;
import com.ekreative.nutrition.repository.MealRepository;
import com.ekreative.nutrition.repository.NutritionsConsumedRepository;
import com.ekreative.nutrition.repository.NutritionsRepository;
import com.ekreative.nutrition.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NutritionService {

    private final ScheduleRepository scheduleRepository;
    private final MealRepository mealRepository;
    private final NutritionsRepository nutritionsRepository;
    private final NutritionsConsumedRepository nutritionsConsumedRepository;

    @Autowired
    public NutritionService(ScheduleRepository scheduleRepository, MealRepository mealRepository,
                            NutritionsRepository nutritionsRepository, NutritionsConsumedRepository nutritionsConsumedRepository) {
        this.scheduleRepository = scheduleRepository;
        this.mealRepository = mealRepository;
        this.nutritionsRepository = nutritionsRepository;
        this.nutritionsConsumedRepository = nutritionsConsumedRepository;
    }

    public void updateNutritionsConsumed(int dayId, int mealId, NutritionsDto nutritionsDto) {
        if (scheduleRepository.getScheduleByDayId(dayId) == null) {
            throw new IllegalArgumentException();
        }
        Schedule schedule = scheduleRepository.getScheduleByDayId(dayId);

        if (nutritionsConsumedRepository.getNutritionsConsumedByMealIdAndDate(mealId, schedule.getDay().getDate()) == null) {
            throw new IllegalArgumentException();
        }
        NutritionsConsumed nutritionsConsumed = nutritionsConsumedRepository.getNutritionsConsumedByMealIdAndDate(
                mealId, schedule.getDay().getDate());
        nutritionsConsumed.setCarbohydrates(nutritionsDto.getCarbohydrates());
        nutritionsConsumed.setFats(nutritionsDto.getFats());
        nutritionsConsumed.setProteins(nutritionsDto.getProteins());

        nutritionsConsumedRepository.updateNutritionsConsumed(nutritionsConsumed);
    }

    public DayNutritionDto getDayNutrition(int dayId) {
        if (scheduleRepository.getScheduleByDayId(dayId) == null) {
            throw new IllegalArgumentException();
        }
        DayNutritionDto dayNutritionDto = new DayNutritionDto();
        List<MealNutritionDto> mealNutritionDtoList = new ArrayList<>();
        Schedule schedule = scheduleRepository.getScheduleByDayId(dayId);
        List<Meal> meals = mealRepository.getMealsByMealPlanIdAndDate(schedule.getMealPlan().getId(), schedule.getDay().getDate());

        for (Meal meal : meals) {
            if (!meal.getName().equals(MEALS.WORKOUT_SNACK.name()) || schedule.isWorkout()) {
                MealNutritionDto mealNutritionDto = new MealNutritionDto();
                mealNutritionDto.setMealName(meal.getName());

                mealNutritionDto.setNutritionsDto(createNutritionsDto(
                        nutritionsRepository.getNutritionsByMealIdAndDate(meal.getId(), schedule.getDay().getDate())));

                mealNutritionDto.setNutritionsConsumedDto(createNutritionsConsumedDto(
                        nutritionsConsumedRepository.getNutritionsConsumedByMealIdAndDate(meal.getId(),
                                schedule.getDay().getDate())));

                mealNutritionDtoList.add(mealNutritionDto);
            }
        }
        long nutritionScore = calculateNutritionScore(schedule);
        dayNutritionDto.setMealNutritionDtoList(mealNutritionDtoList);
        dayNutritionDto.setNutritionScore(nutritionScore);

        schedule.setNutritionScore(nutritionScore);
        scheduleRepository.updateSchedule(schedule);

        return dayNutritionDto;
    }

    private long calculateNutritionScore(Schedule schedule) {
        List<Meal> meals = schedule.getMealPlan().getMeals();
        double t = 0.0;
        double c = 0.0;
        for (Meal meal : meals) {
            if (!meal.getName().equals(MEALS.WORKOUT_SNACK.name()) || schedule.isWorkout()) {
                t += nutritionsRepository.getNutritionsSumByMealIdAndDate(meal.getId(), schedule.getDay().getDate());
                c += nutritionsConsumedRepository.getNutritionsConsumedSumByMealIdAndDate(meal.getId(), schedule.getDay().getDate());
            }
        }
        double d = Math.abs(t - c) / t;
        return Math.round(5 * Math.exp(-2.3 * d));
    }

    private NutritionsDto createNutritionsDto(Nutritions nutritions) {
        NutritionsDto nutritionsDto = new NutritionsDto();
        nutritionsDto.setCarbohydrates(nutritions.getCarbohydrates());
        nutritionsDto.setFats(nutritions.getFats());
        nutritionsDto.setProteins(nutritions.getProteins());

        return nutritionsDto;
    }

    private NutritionsDto createNutritionsConsumedDto(NutritionsConsumed nutritionsConsumed) {
        NutritionsDto nutritionsDto = new NutritionsDto();
        nutritionsDto.setCarbohydrates(nutritionsConsumed.getCarbohydrates());
        nutritionsDto.setFats(nutritionsConsumed.getFats());
        nutritionsDto.setProteins(nutritionsConsumed.getProteins());

        return nutritionsDto;
    }
}
