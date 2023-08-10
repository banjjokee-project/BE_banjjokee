package com.project.BE_banjjokee.dto;

import com.project.BE_banjjokee.model.WalkRecord;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class WalkRecordAllDTO {
    Map<Integer, Months> monthMap = new HashMap();

    public WalkRecordAllDTO(List<WalkRecord> walkRecords) {
        walkRecords.stream()
                .forEach(walkRecord -> {
                    LocalDate date = walkRecord.getDate();
                    int month = date.getMonthValue();
                    int day = date.getDayOfMonth();
                    if(monthMap.containsKey(month)) {
                        Months months = monthMap.get(month);
                        Map<Integer, Days> dayMap = months.getDayMap();
                        if(dayMap.containsKey(day)) {
                            Days days = dayMap.get(day);
                            days.getWalkRecordDTOS().add(new WalkRecordDTO(walkRecord.getId(), walkRecord.getContent(), walkRecord.getAchievement()));
                            dayMap.put(day, days);
                        } else {
                            Days days = new Days();
                            days.getWalkRecordDTOS().add(new WalkRecordDTO(walkRecord.getId(), walkRecord.getContent(), walkRecord.getAchievement()));
                            dayMap.put(day, days);
                        }
                        monthMap.put(month, months);
                    } else {
                        Months months = new Months();
                        Map<Integer, Days> dayMap = months.getDayMap();
                        Days days = new Days();
                        days.getWalkRecordDTOS().add(new WalkRecordDTO(walkRecord.getId(), walkRecord.getContent(), walkRecord.getAchievement()));
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
    List<WalkRecordDTO> walkRecordDTOS = new ArrayList<>();
}

@Getter
class WalkRecordDTO {
    Long id;
    String content;
    Integer achievement;

    public WalkRecordDTO(Long id, String content, Integer achievement) {
        this.id = id;
        this.content = content;
        this.achievement = achievement;
    }
}