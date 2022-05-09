package com.NoChu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto{

    private String foodNm;
    private String ingreNm;
    private String ymd;
    private String mealId;

    public MenuDto(MenuDto menuDto) {
        this.foodNm = menuDto.getFoodNm();
        this.ingreNm = menuDto.getIngreNm();
        this.ymd = menuDto.getYmd();
        this.mealId = menuDto.getMealId();
    }

    public MenuDto(QueryDto queryDto, int Count){
        this.foodNm = queryDto.getFoodNm();
        this.ingreNm = queryDto.getIngredientsList().get(Count);
        this.ymd = queryDto.getYmd();
        this.mealId = queryDto.getMealId();
    }



}
