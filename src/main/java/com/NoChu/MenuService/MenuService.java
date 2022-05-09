package com.NoChu.MenuService;

import com.NoChu.dto.CommonCodeDto;
import com.NoChu.dto.QueryDto;

import java.util.List;

public interface MenuService {
    public List<QueryDto> findCondition(String rest, String time2, String date1, String date2);

    public List<QueryDto> findAll(String rest, String date1, String date2);

    public List<CommonCodeDto> findSearchCondition();

    //조회조건분류 (SearchConditionClassification)
    public List<String> SearchConditionCf(List<CommonCodeDto> ConditionResult, String Key);
    
    //날짜계산
    public List<String> DateCalc(String StartDate, String EndDate);

    //총일자계산
    public long TotalDateCalc(String StartDate, String EndDate);
    
    //식사코드변환 (MealCodeConversion)
    public String MealCodeCv(String Meal);


}
