package com.NoChu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryDto {

    private String ymd;

    private String codeNm;

    private String mealId;

    private String foodNm;

    private String ingredientsNm;

    private List<String> ingredientsList;

}
