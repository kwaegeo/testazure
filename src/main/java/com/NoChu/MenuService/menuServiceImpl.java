package com.NoChu.MenuService;

import com.NoChu.dto.CommonCodeDto;
import com.NoChu.dto.QueryDto;
import com.NoChu.mapper.MenuMapper;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    public MenuServiceImpl(MenuMapper MenuMapper) {
        this.menuMapper = MenuMapper;
    }

    //식사구분이 있는 쿼리
    public List<QueryDto> findCondition(String rest, String Meal, String date1, String date2) {

        List<QueryDto> result = menuMapper.findCondition(rest, Meal, date1, date2);
        return result;
    }

    //식사구분이 전체인 쿼리
    public List<QueryDto> findAll(String rest, String date1, String date2) {

        List<QueryDto> result = menuMapper.findAll(rest, date1, date2);
        return result;
    }

    //조회조건을 불러오는 쿼리
    public List<CommonCodeDto> findSearchCondition() {

        List<CommonCodeDto> result = menuMapper.findSearchCondition();
        return result;
    }


    //조회 조건을 분류 메서드
    public List<String> SearchConditionCf(List<CommonCodeDto> ConditionResult, String Key) {

        List<String> RestList = new ArrayList<>();
        List<String> MealList = new ArrayList<>();

        int ResultSize = ConditionResult.size();

        for (int i = 0; i < ResultSize; i++) {

            if (ConditionResult.get(i).getCodeId().substring(0, 1).equals("R")) {
                RestList.add(ConditionResult.get(i).getCodeNm());
            } else {
                MealList.add(ConditionResult.get(i).getCodeNm());
            }
        }
        if (Key == "Rest")
            return RestList;
        else
            return MealList;
    }

    //날짜 계산 메서드
    public List<String> DateCalc(String StartDate, String EndDate) {
        System.out.println("====날짜계산_시작====");
        LocalDate ParseStart = LocalDate.parse(StartDate);
        LocalDate ParseEnd = LocalDate.parse(EndDate);

        DayOfWeek StartWeek = ParseStart.getDayOfWeek();
        DayOfWeek EndWeek = ParseEnd.getDayOfWeek();

        int StartWeekNumber = StartWeek.getValue();
        int EndWeekNumber = EndWeek.getValue();

        //반복되는 횟수
        int LoopCount = EndWeekNumber - StartWeekNumber;


        String StartDate_KR_Name = StartWeek.getDisplayName(TextStyle.SHORT, Locale.KOREA) + " (" + StartDate.substring(5, 10) + ")";

        List<String> ConditionDay = new ArrayList<>(List.of(" ", " ", " ", " ", " ", " ", " "));

        ConditionDay.set(StartWeekNumber - 1, StartDate_KR_Name);

        for (int i = 1; i <= LoopCount; i++) {
            LocalDate DateTomorrow = ParseStart.plusDays(i);
            String ParseTomorrow = DateTomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            DayOfWeek TomorrowDay = DateTomorrow.getDayOfWeek();
            int TomorrowParse = TomorrowDay.getValue() - 1;

            String Tomorrow_KR_Name = TomorrowDay.getDisplayName(TextStyle.SHORT, Locale.KOREA) + " (" + ParseTomorrow.substring(5, 10) + ")";
            ConditionDay.set(TomorrowParse, Tomorrow_KR_Name);
        }

        return ConditionDay;
    }

    //총일자계산 메서드
    public long TotalDateCalc(String StartDate, String EndDate) {
        System.out.println("====총일자계산_시작====");

        long TotalDate;

        LocalDate ParseStart = LocalDate.parse(StartDate);
        LocalDate ParseEnd = LocalDate.parse(EndDate);

        LocalDateTime StartTime = ParseStart.atStartOfDay();
        LocalDateTime EndTime = ParseEnd.atStartOfDay();

        TotalDate = Duration.between(StartTime, EndTime).toDays() + 1;

        return TotalDate;
    }

    //변수에 저장된 (식사구분이름)을 (식사구분CODE)로 변환
    public String MealCodeCv(String Meal) {
        System.out.println("====식사코드변환_시작====");

        switch (Meal) {
            case "조식":
                Meal = "M01";
                break;
            case "중식":
                Meal = "M02";
                break;
            case "석식":
                Meal = "M03";
                break;
            case "간식":
                Meal = "M04";
                break;
            case "전체":
                Meal = " ";
                break;
        }
        return Meal;
    }

}
