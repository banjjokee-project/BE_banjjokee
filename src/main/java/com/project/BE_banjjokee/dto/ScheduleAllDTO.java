package com.project.BE_banjjokee.dto;

import com.project.BE_banjjokee.model.Schedule;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ScheduleAllDTO {
    Map<Integer, Months> monthMap = new HashMap();

    public ScheduleAllDTO(List<Schedule> schedules) {
        schedules.stream()
                .forEach(schedule -> {
                    LocalDate date = schedule.getDate();
                    int month = date.getMonthValue();
                    int day = date.getDayOfMonth();
                    if(monthMap.containsKey(month)) {
                        Months months = monthMap.get(month);
                        Map<Integer, Days> dayMap = months.getDayMap();
                        if(dayMap.containsKey(day)) {
                            Days days = dayMap.get(day);
                            days.getScheduleDTOS().add(new ScheduleDTO(schedule.getType(), schedule.getContent()));
                            dayMap.put(day, days);
                        } else {
                            Days days = new Days();
                            days.getScheduleDTOS().add(new ScheduleDTO(schedule.getType(), schedule.getContent()));
                            dayMap.put(day, days);
                        }
                        monthMap.put(month, months);
                    } else {
                        Months months = new Months();
                        Map<Integer, Days> dayMap = months.getDayMap();
                        Days days = new Days();
                        days.getScheduleDTOS().add(new ScheduleDTO(schedule.getType(), schedule.getContent()));
                        dayMap.put(day, days);
                        monthMap.put(month, months);
                    }
                });
    }
}

@Getter
class Months {
    Map<Integer, Days> dayMap = new HashMap();
}

@Getter
class Days {
    List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
}

@Getter
class ScheduleDTO {
    String type;
    String content;

    public ScheduleDTO(String type, String content) {
        this.type = type;
        this.content = content;
    }
}
