package com.ekreative.nutrition.controllers;

import com.ekreative.nutrition.objects.dto.ScheduleDto;
import com.ekreative.nutrition.objects.dto.ScheduleForWeekDto;
import com.ekreative.nutrition.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule/{dayId}")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable int dayId) {
        try {
            return new ResponseEntity<>(scheduleService.getSchedule(dayId), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new ScheduleDto(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/schedule")
    public ResponseEntity<List<Integer>> createSchedule(@RequestBody List<ScheduleForWeekDto> scheduleForWeekDto) {
        return new ResponseEntity<>(scheduleService.createSchedule(scheduleForWeekDto), HttpStatus.CREATED);
    }

    @PutMapping("/schedule/{dayId}")
    public HttpStatus editSchedule(@RequestBody ScheduleForWeekDto scheduleForWeekDto, @PathVariable int dayId,
                                   @RequestParam(name = "isUpdateFromToday") boolean isUpdateFromToday) {
        try {
            scheduleService.editSchedule(scheduleForWeekDto, dayId, isUpdateFromToday);
            return HttpStatus.NO_CONTENT;
        } catch (IllegalArgumentException ex) {
            return HttpStatus.NOT_FOUND;
        }
    }
}
