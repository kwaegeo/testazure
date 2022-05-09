package com.NoChu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Week {

    private List<Sell> week = Arrays.asList(new Sell(),new Sell(),new Sell(),new Sell(),new Sell(),new Sell(),new Sell());
    private String mealName;
    private List<String> dayNameList = new ArrayList<>();

    public Week(Week week){
        this.week = new ArrayList<>();
        for(int i = 0; i<week.getWeek().size(); i++){
            this.week.add(new Sell(week.getWeek().get(i)));
        }
    }

}
