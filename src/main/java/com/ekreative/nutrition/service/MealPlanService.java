package com.ekreative.nutrition.service;

import com.ekreative.nutrition.objects.*;
import com.ekreative.nutrition.objects.dto.MealDto;
import com.ekreative.nutrition.objects.dto.MealPlanDto;
import com.ekreative.nutrition.objects.dto.NutritionsDto;
import com.ekreative.nutrition.objects.enums.MEALS;
import com.ekreative.nutrition.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final DaysRepository daysRepository;
    private final MealRepository mealRepository;
    private final NutritionsRepository nutritionsRepository;
    private final ScheduleRepository scheduleRepository;
    private final NutritionsConsumedRepository nutritionsConsumedRepository;

    @Autowired
    public MealPlanService(MealPlanRepository mealPlanRepository, DaysRepository daysRepository,
                           MealRepository mealRepository, NutritionsRepository nutritionsRepository,
                           ScheduleRepository scheduleRepository, NutritionsConsumedRepository nutritionsConsumedRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.daysRepository = daysRepository;
        this.mealRepository = mealRepository;
        this.nutritionsRepository = nutritionsRepository;
        this.scheduleRepository = scheduleRepository;
        this.nutritionsConsumedRepository = nutritionsConsumedRepository;
    }

    public int createMealPlan(MealPlanDto mealPlanDto) {
        MealPlan mealPlan = new MealPlan();

        mealPlan.setName(mealPlanDto.getName());
        mealPlanRepository.saveMealPlan(mealPlan);
        List<Days> days = daysRepository.getDays();

        for (MealDto mealDto : mealPlanDto.getMeals()) {
            Meal meal = new Meal();
            meal.setName(mealDto.getName().toUpperCase());
            meal.setMealPlan(mealPlan);
            mealRepository.saveMeal(meal);

            for (Days day : days) {
                Nutritions nutritions = new Nutritions();
                nutritions.setMeal(meal);
                nutritions.setDay(day);
                nutritions.setCarbohydrates(mealDto.getNutritionsDto().getCarbohydrates());
                nutritions.setFats(mealDto.getNutritionsDto().getFats());
                nutritions.setProteins(mealDto.getNutritionsDto().getProteins());
                nutritionsRepository.saveNutritions(nutritions);

                NutritionsConsumed nutritionsConsumed = new NutritionsConsumed();
                nutritionsConsumed.setMeal(meal);
                nutritionsConsumed.setDay(day);
                nutritionsConsumed.setCarbohydrates(0);
                nutritionsConsumed.setFats(0);
                nutritionsConsumed.setProteins(0);
                nutritionsConsumedRepository.saveNutritionsConsumed(nutritionsConsumed);
            }
        }
        return mealPlan.getId();
    }

    public void editMealPlan(int mealPlanId, MealPlanDto mealPlanDto, boolean isUpdateFromToday) {
        if (mealPlanRepository.getMealPlanById(mealPlanId) == null) {
            throw new IllegalArgumentException();
        }
        LocalDate updateStartDate;

        if (isUpdateFromToday) {
            updateStartDate = LocalDate.now();
        } else {
            updateStartDate = LocalDate.now().plusDays(1);
        }

        MealPlan mealPlan = mealPlanRepository.getMealPlanById(mealPlanId);
        mealPlan.setName(mealPlanDto.getName());

        List<Nutritions> nutritionsList = nutritionsRepository.getNutritionsByMealPlanIdStartingFromDate(mealPlanId,
                Date.valueOf(updateStartDate));

        for (Nutritions nutritions : nutritionsList) {
            for (MealDto mealDto : mealPlanDto.getMeals()) {
                if (nutritions.getMeal().getName().equalsIgnoreCase(mealDto.getName())) {
                    nutritions.setCarbohydrates(mealDto.getNutritionsDto().getCarbohydrates());
                    nutritions.setFats(mealDto.getNutritionsDto().getFats());
                    nutritions.setProteins(mealDto.getNutritionsDto().getProteins());
                    nutritionsRepository.updateNutritions(nutritions);
                }
            }
        }
        mealPlanRepository.updateMealPlan(mealPlan);
    }

    public MealPlanDto getMealPlan(int dayId) {
        if (mealPlanRepository.getMealPlanByDayId(dayId) == null || scheduleRepository.getScheduleByDayId(dayId) == null) {
            throw new IllegalArgumentException();
        }
        MealPlan mealPlan = mealPlanRepository.getMealPlanByDayId(dayId);
        Schedule schedule = scheduleRepository.getScheduleByDayId(dayId);

        MealPlanDto mealPlanDto = new MealPlanDto();
        mealPlanDto.setName(mealPlan.getName());
        List<MealDto> mealDtoList = new ArrayList<>();

        for (Meal meal : mealPlan.getMeals()) {
            if (!meal.getName().equals(MEALS.WORKOUT_SNACK.name()) || schedule.isWorkout()) {
                Nutritions nutritions = nutritionsRepository.getNutritionsByMealIdAndDate(meal.getId(),
                        schedule.getDay().getDate());

                NutritionsDto nutritionsDto = new NutritionsDto();
                nutritionsDto.setCarbohydrates(nutritions.getCarbohydrates());
                nutritionsDto.setFats(nutritions.getFats());
                nutritionsDto.setProteins(nutritions.getProteins());

                MealDto mealDto = new MealDto();
                mealDto.setName(meal.getName());
                mealDto.setNutritionsDto(nutritionsDto);
                mealDtoList.add(mealDto);
            }
        }
        mealPlanDto.setMeals(mealDtoList);

        return mealPlanDto;
    }

    public void deleteMealPlan(int mealPlanId) {
        if (mealPlanRepository.getMealPlanById(mealPlanId) == null) {
            throw new IllegalArgumentException();
        }
        mealPlanRepository.deleteMealPlan(mealPlanRepository.getMealPlanById(mealPlanId));
    }
}
