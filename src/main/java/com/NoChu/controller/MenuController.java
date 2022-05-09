package com.NoChu.controller;

import com.NoChu.API.Paging;
import com.NoChu.dto.*;
import com.NoChu.API.Sort;
import com.NoChu.MenuService.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@Controller
public class MenuController {

    @Autowired
    private MenuServiceImpl menuservice;

    @Autowired
    private Sort sort;

    @Autowired
    private Paging paging;

    private List<QueryDto> QueryResult = new ArrayList<>();

    private List<CommonCodeDto> ConditionResult = new ArrayList<>();





    //조회 조건 페이지 출력 요청 시 처리하는 메서드
    @GetMapping("/SelectCondition")
    public String 조회조건출력(Model model) {

        //조회 조건 Query 요청
        ConditionResult = menuservice.findSearchCondition();

        //Code Id에 따라 분류
        List<String> MealList = menuservice.SearchConditionCf(ConditionResult,"Meal");
        List<String> RestList = menuservice.SearchConditionCf(ConditionResult,"Rest");


        model.addAttribute("RestList",RestList);
        model.addAttribute("MealList",MealList);

        return "Menu/SelectCondition";
    }
//
//    @GetMapping("SelectCondition")
//    public ModelAndView 조회조건출력(){
//
//        ModelAndView mv = new ModelAndView();
//
//        //조회 조건 Query 요청
//        ConditionResult = menuservice.findSearchCondition();
//
//        //Code Id에 따라 분류
//        List<String> MealList = menuservice.SearchConditionCf(ConditionResult,"Meal");
//        List<String> RestList = menuservice.SearchConditionCf(ConditionResult,"Rest");
//
//        mv.addObject("RestList", RestList);
//        mv.addObject("MealList", MealList);
//        mv.setViewName("Menu/SelectCondition");
//
//        return mv;
//    }


    @GetMapping("/MenuPrint/")
    public String 식단표출력(Model model, HttpServletRequest httpServletRequest) throws ParseException {

        //(식당명, 식사구분, 날짜 조건) 데이터 변수에 저장
        String rest = httpServletRequest.getParameter("rest");      //식당명
        String Meal = httpServletRequest.getParameter("Meal");      //식사구분
        String StartDate = httpServletRequest.getParameter("date1");    //시작일
        String EndDate = httpServletRequest.getParameter("date2");    //종료일

        //날짜를 계산하여 한 List에 저장
        List<String> ConditionDay = menuservice.DateCalc(StartDate,EndDate);

        //시작일부터 종료일까지 기간 저장
        long TotalDate = menuservice.TotalDateCalc(StartDate, EndDate);
        String Meals = menuservice.MealCodeCv(Meal);

        //만약 (식사구분)이 전체일 경우 전체 조회 쿼리 실행
        if (Meals == " ") {
            QueryResult = menuservice.findAll(rest, StartDate, EndDate);
        } else {

            //혹은 (식사구분)이 선택일 경우 조건에 맞는 쿼리 실행
            QueryResult = menuservice.findCondition(rest, Meals, StartDate, EndDate);
        }

        //데이터 정렬화
        QueryResult = sort.MaterialSort(QueryResult);
        TempWeekDto tempWeekDto = sort.DaySort(QueryResult);
        Page page = sort.MealSort(tempWeekDto);


        //페이징에 필요한 데이터 추출
        int [][]DtoSize = paging.DtoSizeExt(page);
        int[] MaxSize = paging.MaxSizeExt(DtoSize);
        int PageNum = paging.PageSizeExt(MaxSize);


        //페이징 시작
        page = paging.InputNull(MaxSize, page);
        page = paging.Integration(page);

        PageCompilation compilation = paging.Paging(page,ConditionDay);


        //현재날짜
        LocalDate now = LocalDate.now();


        //View에 뿌려줄 model에 요청값 저장
        model.addAttribute("Final", compilation);
        model.addAttribute("Place", rest);
        model.addAttribute("StartDate", StartDate);
        model.addAttribute("EndDate", EndDate);
        model.addAttribute("TotalPage",PageNum);
        model.addAttribute("Now",now);
        model.addAttribute("TotalDate", TotalDate);
        return "Menu/MenuPrint";


    }
}