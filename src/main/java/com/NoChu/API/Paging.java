package com.NoChu.API;

import com.NoChu.dto.*;
import com.NoChu.MenuService.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class Paging {

    @Autowired
    private MenuServiceImpl menuservice;

    private List<CommonCodeDto> ConditionResult = new ArrayList<>();

    //DtoSize 추출 (DtoSizeExtract)
    public int[][] DtoSizeExt(Page days){
        System.out.println("====Dto사이즈추출_시작====");

        int[][]DtoSize = new int[5][8];

        for (int i = 0; i < 4; i++) {
            System.out.println("\t[" + i + "식사구분" + "]");

            for (int j = 0; j < days.getWeeks().get(i).getWeek().size(); j++) {
                System.out.println("\t\t[" + j + "요일" + "]");
                int Count = 0;

                for (MenuDto k : days.getWeeks().get(i).getWeek().get(j).getSell()) {
                    Count += 1;
                    System.out.println("\t\t\t" + k + " " + i + "식사구분" + j + "요일" + Count + "번째  재료사이즈");

                }
                DtoSize[i][j] = Count;
                days.getWeeks().get(i).getWeek().get(j).setSellSize(DtoSize[i][j]);
            }
        }
        return DtoSize;
    }

    //최대사이즈 추출
    public int[] MaxSizeExt(int[][] DtoSize){
        System.out.println("====최대사이즈추출_시작====");

        int[] MaxSize = new int[5];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                if (DtoSize[i][j] > DtoSize[i][j + 1]) {DtoSize[i][j + 1] = DtoSize[i][j];}

                MaxSize[i] = DtoSize[i][j + 1];
            }
        }
        return MaxSize;
    }

    //페이지 수 추출
    public int PageSizeExt(int[] MaxSize){
        System.out.println("====페이지수추출_시작====");

        int PageNum = 0;

        for (int i = 0; i < 5; i++) {
            PageNum += MaxSize[i];
        }

        //20으로 최대사이즈를 나눴을 때 나머지가 없는 경우
        if (PageNum % 20 == 0)
            PageNum = PageNum / 20;
        //나머지가 있는 경우 +1
        else
            PageNum = PageNum / 20 + 1;

        //20일경우
        if(PageNum == 0){
            PageNum = PageNum+1;
        }

        return PageNum;
    }


    //공백넣기
    public Page InputNull(int[] MaxSize, Page days){
        System.out.println("====공백넣기_시작====");

        for(int i=0; i<4; i++){
            String Meal="";
            Week temptimes = days.getWeeks().get(i);
            for(int j = 0; j< temptimes.getWeek().size(); j++){
                switch(i) {
                    case 0:
                        Meal="M01";
                        break;
                    case 1:
                        Meal="M02";
                        break;
                    case 2:
                        Meal="M03";
                        break;
                    case 3:
                        Meal="M04";
                        break;
                }
                if(MaxSize[i]> temptimes.getWeek().get(j).getSellSize()){
                    int Remaining = MaxSize[i] - temptimes.getWeek().get(j).getSellSize();
                    for(int k=0; k<Remaining; k++){
                        days.getWeeks().get(i).getWeek().get(j).getSell().add(new MenuDto("  ","ㅤ", null, Meal));
                    }
                }
            }
        }
        return days;
    }

    //통합화
    public Page Integration(Page days){
        System.out.println("====통합화_시작====");
        for(int i=0, k=1; k<4; i++, k++) {
            for (int j = 0; j < days.getWeeks().get(i).getWeek().size(); j++) {
                days.getWeeks().get(0).getWeek().get(j).getSell().addAll(days.getWeeks().get(k).getWeek().get(j).getSell());

            }
        }
        return days;
    }

    //조건선택
    public Page ConditionSelect(Page hoo, Page days, int Count){

        switch (days.getWeeks().get(0).getWeek().get(Count).getSell().get(0).getMealId()) {
            case "M01":
                hoo.getWeeks().get(0).getWeek().get(Count).getSell().add(days.getWeeks().get(0).getWeek().get(Count).getSell().get(0));
                break;
            case "M02":
                hoo.getWeeks().get(1).getWeek().get(Count).getSell().add(days.getWeeks().get(0).getWeek().get(Count).getSell().get(0));
                break;
            case "M03":
                hoo.getWeeks().get(2).getWeek().get(Count).getSell().add(days.getWeeks().get(0).getWeek().get(Count).getSell().get(0));
                break;
            case "M04":
                hoo.getWeeks().get(3).getWeek().get(Count).getSell().add(days.getWeeks().get(0).getWeek().get(Count).getSell().get(0));
                break;
        }
        return hoo;
    }

    public Page MealInsert(Page hoo){

        List<String> Meal = Arrays.asList("조식","중식","석식","간식");
        for(int i=0; i<4; i++) {
            hoo.getWeeks().get(i).setMealName(Meal.get(i));
        }

        return hoo;
    }

    public PageCompilation Paging(Page days, List<String> ConditionDay){
        System.out.println("====페이징_시작====");

        Paging paging = new Paging();

        PageCompilation compilation = new PageCompilation();

        //복사
        Page nullPage = new Page(days);

        //비어있는 객체로 구성
        for(int k=0; k<4; k++){
            for(int i=0; i<7; i++){
                for(int j = 0; j<days.getWeeks().get(k).getWeek().get(i).getSell().size(); j++){
                    nullPage.getWeeks().get(k).getWeek().get(i).getSell().remove(0);
                }
            }
        }

        int quotient = days.getWeeks().get(0).getWeek().get(0).getSell().size()/20;   //20으로 나눈 몫
        int remainder = days.getWeeks().get(0).getWeek().get(0).getSell().size()%20;  //20으로 나눈 나머지
        int DtoSize = days.getWeeks().get(0).getWeek().get(0).getSell().size();       //초기 사이즈 고정

        //증가할 페이지 번호
        int PageNum =1;


        //만약 목록의 사이즈가 20보다 크다면
        if(DtoSize>20) {
            //몫 만큼 반복
            for (int l = 0; l < quotient; l++) {
                Page NewPage = new Page(nullPage);

                //요일별
                for (int i = 0; i < 7; i++) {

                    //구분별 날짜를 넣어준 뒤
                    for (int p = 0; p < 4; p++) {
                        NewPage.getWeeks().get(p).getWeek().get(i).setDayName(ConditionDay.get(i));
                    }

                    //20개씩 새로운 객체에 추가 후 기존 객체 삭제
                    for (int j = 0; j < 20; j++) {
                        NewPage = paging.ConditionSelect(NewPage,days,i);
                        days.getWeeks().get(0).getWeek().get(i).getSell().remove(0);
                    }
                }

                NewPage = paging.MealInsert(NewPage);
                NewPage.setPageNum(PageNum);
                PageNum++;
                compilation.getPageList().add(NewPage);
            }

            //반복후 남는 값이 있다면
            if (days.getWeeks().get(0).getWeek().get(0).getSell().size() != 0) {
                int PageNum2 = quotient;
                for (int l = 0; l < 1; l++) {
                    Page NewPage = new Page(nullPage);

                    for (int i = 0; i < 7; i++) {

                        for (int p = 0; p < 4; p++) {
                            NewPage.getWeeks().get(p).getWeek().get(i).setDayName(ConditionDay.get(i));
                        }

                        for (int j = 0; j < remainder; j++) {
                            NewPage = paging.ConditionSelect(NewPage,days,i);
                            days.getWeeks().get(0).getWeek().get(i).getSell().remove(0);
                        }
                    }

                    NewPage = paging.MealInsert(NewPage);
                    NewPage.setPageNum(PageNum2 + 1);
                    compilation.getPageList().add(NewPage);
                }
            }
        }

        //20보다 작다면
        else {
            for(int f=0; f<1; f++){
                Page NewPage = new Page(nullPage);

                for (int i = 0; i < 7; i++) {

                    for (int p = 0; p < 4; p++) {
                        NewPage.getWeeks().get(p).getWeek().get(i).setDayName(ConditionDay.get(i));
                    }

                    for (int h = 0; h < DtoSize; h++) {
                        NewPage = paging.ConditionSelect(NewPage,days,i);
                        days.getWeeks().get(0).getWeek().get(i).getSell().remove(0);
                    }
                }
                NewPage = paging.MealInsert(NewPage);
                NewPage.setPageNum(PageNum);
                compilation.getPageList().add(NewPage);
            }

        }

        //없는 식사구분 삭제
        int SizeCount;
        for (int i = 0; i < compilation.getPageList().size(); i++) {
            for (int j = 0; j < compilation.getPageList().get(i).getWeeks().size(); j++) {
                SizeCount = 0;
                for (int k = 0; k < 7; k++) {
                    SizeCount += compilation.getPageList().get(i).getWeeks().get(j).getWeek().get(k).getSell().size();
                }
                if (SizeCount == 0) {
                    compilation.getPageList().get(i).getWeeks().remove(j);
                    j = j - 1;
                }
            }
        }
        return compilation;
    }

}
