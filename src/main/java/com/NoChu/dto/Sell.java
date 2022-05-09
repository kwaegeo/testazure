package com.NoChu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Sell{


    private List<MenuDto> sell = new ArrayList<>();
    private int sellSize;
    private String dayName;


    public Sell(com.NoChu.dto.Sell sc){
        this.sellSize = sc.getSellSize();
        this.sell = new ArrayList<>();
        for(int i=0; i<sc.getSell().size(); i++){
            this.sell.add(new MenuDto(sc.getSell().get(i)));
        }
    }


    public Sell(String DayName){
        this.dayName = DayName;
        this.sell = new ArrayList<>();
    }



}
