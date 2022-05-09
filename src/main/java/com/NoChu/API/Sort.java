package com.NoChu.API;

import com.NoChu.dto.*;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class Sort {

    //긴 문자열의 재료들을 하나의 리스트형태로 넣어줌
    public List<QueryDto> MaterialSort(List<QueryDto> QueryResult){
        System.out.println("====재료정렬_시작====");

        //쿼리 결과만큼 반복
        for (int i = 0; i < QueryResult.size(); i++) {
            String[] TempIngre = QueryResult.get(i).getIngredientsNm().split(",");
            List<String> Ingredients = new ArrayList<String>(Arrays.asList(TempIngre));

            //음식 이름과 재료이름이 같은 경우 공백을 추가하여 구분
            String FoodName = QueryResult.get(i).getFoodNm();
            for(int j=0; j<Ingredients.size(); j++){
                if(FoodName.equals(Ingredients.get(j))){
                    Ingredients.set(j,Ingredients.get(j)+"ㅤ");
                }
            }

            //음식이름을 가장 첫번째에 추가
            Ingredients.add(0, FoodName);
            QueryResult.get(i).setIngredientsList(Ingredients);
        }
        return QueryResult;
    }

    //요일별정렬
    public TempWeekDto DaySort(List<QueryDto> QueryResult){
        System.out.println("====요일별정렬_시작====");

        Sell mon = new Sell();
        Sell tue = new Sell();
        Sell wed = new Sell();
        Sell thu = new Sell();
        Sell fri = new Sell();
        Sell sat = new Sell();
        Sell sun = new Sell();


        //처음 불러온 데이터만큼 반복하여 구별하여 각 리스트에 추가
        for (int i = 0; i < QueryResult.size(); i++) {
            LocalDate ParseDate = LocalDate.parse(QueryResult.get(i).getYmd());
            DayOfWeek Dayweek = ParseDate.getDayOfWeek();
            String Day = Dayweek.getDisplayName(TextStyle.SHORT, Locale.US);
            QueryDto Temp = QueryResult.get(i);

            if (Day.equals("Mon")) {
                for (int j = 0; j < QueryResult.get(i).getIngredientsList().size(); j++) {
                    MenuDto one = new MenuDto(Temp,j);
                    mon.getSell().add(one);
                }
            } else if (Day.equals("Tue")) {
                for (int j = 0; j < QueryResult.get(i).getIngredientsList().size(); j++) {
                    MenuDto one = new MenuDto(Temp,j);
                    tue.getSell().add(one);

                }
            } else if (Day.equals("Wed")) {
                for (int j = 0; j < QueryResult.get(i).getIngredientsList().size(); j++) {
                    MenuDto one = new MenuDto(Temp,j);
                    wed.getSell().add(one);

                }
            } else if (Day.equals("Thu")) {
                for (int j = 0; j < QueryResult.get(i).getIngredientsList().size(); j++) {
                    MenuDto one = new MenuDto(Temp,j);
                    thu.getSell().add(one);

                }
            } else if (Day.equals("Fri")) {
                for (int j = 0; j < QueryResult.get(i).getIngredientsList().size(); j++) {
                    MenuDto one = new MenuDto(Temp,j);
                    fri.getSell().add(one);
                }
            } else if (Day.equals("Sat")) {
                for (int j = 0; j < QueryResult.get(i).getIngredientsList().size(); j++) {
                    MenuDto one = new MenuDto(Temp,j);
                    sat.getSell().add(one);

                }

            } else if (Day.equals("Sun")) {
                for (int j = 0; j < QueryResult.get(i).getIngredientsList().size(); j++) {
                    MenuDto one = new MenuDto(Temp,j);
                    sun.getSell().add(one);

                }
            }
        }

        TempWeekDto tempWeekDto = new TempWeekDto();
        tempWeekDto.getTempWeekDto().add(mon);
        tempWeekDto.getTempWeekDto().add(tue);
        tempWeekDto.getTempWeekDto().add(wed);
        tempWeekDto.getTempWeekDto().add(thu);
        tempWeekDto.getTempWeekDto().add(fri);
        tempWeekDto.getTempWeekDto().add(sat);
        tempWeekDto.getTempWeekDto().add(sun);

        return tempWeekDto;
    }

    //식사구분별 정렬
    public Page MealSort(TempWeekDto tempWeekDto){
        System.out.println("====식사구분별_시작====");
        Week M01 = new Week();
        Week M02 = new Week();
        Week M03 = new Week();
        Week M04 = new Week();

        MenuDto Temp;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < tempWeekDto.getTempWeekDto().get(i).getSell().size(); j++) {
                Temp = tempWeekDto.getTempWeekDto().get(i).getSell().get(j);
                switch (Temp.getMealId()) {
                    case "M01":
                        M01.getWeek().get(i).getSell().add(Temp);
                        break;

                    case "M02":
                        M02.getWeek().get(i).getSell().add(Temp);
                        break;

                    case "M03":
                        M03.getWeek().get(i).getSell().add(Temp);
                        break;

                    case "M04":
                        M04.getWeek().get(i).getSell().add(Temp);
                        break;
                }
            }
        }

        Page days = new Page();
        days.getWeeks().add(M01);
        days.getWeeks().add(M02);
        days.getWeeks().add(M03);
        days.getWeeks().add(M04);

        return days;
    }
}
