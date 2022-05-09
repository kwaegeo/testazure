package com.NoChu.mapper;

import com.NoChu.dto.CommonCodeDto;
import com.NoChu.dto.QueryDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("select to_char(cms_menu.ymd,'yyyy-mm-dd') as ymd, cms_common_code.code_nm, cms_menu.meal_id, cms_food.food_nm,\n" +
            "LISTAGG(cms_ingredients.ingredients_nm, ',') WITHIN GROUP(ORDER BY cms_recipe.food_id) as ingredients_nm\n"+
            "from cms_menu\n" +
            "inner\n" +
            "join cms_recipe\n" +
            "on cms_recipe.food_id = cms_menu.food_id\n" +
            "inner\n" +
            "join cms_food\n" +
            "on cms_menu.food_id = cms_food.food_id\n" +
            "inner\n" +
            "join cms_ingredients\n" +
            "on cms_recipe.ingredients_id = cms_ingredients.ingredients_id\n" +
            "inner\n" +
            "join cms_common_code\n" +
            "on cms_common_code.code_id = cms_menu.restaurant_id\n" +
            "inner\n" +
            "join cms_common_code\n" +
            "on cms_common_code.code_id = cms_menu.meal_id\n" +
            "where \n" +
            "ymd\n" +
            "between to_date('${date1}', 'yy-mm-dd')\n" +
            "and to_date('${date2}','yy-mm-dd')\n"+
            "and cms_common_code.code_nm = '${rest}'\n"+
            "and cms_menu.meal_id = '${time2}'\n"+
            "group by cms_recipe.food_id,cms_menu.ymd, cms_common_code.code_nm, cms_menu.meal_id, cms_food.food_nm\n"+
            "order by cms_menu.ymd, cms_common_code.code_nm, cms_menu.meal_id")
    List<QueryDto> findCondition(@Param("rest") String rest, @Param("time2") String time2, @Param("date1") String date1, @Param("date2") String date2);



    @Select("select to_char(cms_menu.ymd,'yyyy-mm-dd') as ymd, cms_common_code.code_nm, cms_menu.meal_id, cms_food.food_nm,\n" +
            "LISTAGG(cms_ingredients.ingredients_nm, ',') WITHIN GROUP(ORDER BY cms_recipe.food_id) as ingredients_nm\n"+
            "from cms_menu\n" +
            "inner\n" +
            "join cms_recipe\n" +
            "on cms_recipe.food_id = cms_menu.food_id\n" +
            "inner\n" +
            "join cms_food\n" +
            "on cms_menu.food_id = cms_food.food_id\n" +
            "inner\n" +
            "join cms_ingredients\n" +
            "on cms_rec" +
            "ipe.ingredients_id = cms_ingredients.ingredients_id\n" +
            "inner\n" +
            "join cms_common_code\n" +
            "on cms_common_code.code_id = cms_menu.restaurant_id\n" +
            "inner\n" +
            "join cms_common_code\n" +
            "on cms_common_code.code_id = cms_menu.meal_id\n" +
            "where \n" +
            "ymd\n" +
            "between to_date('${date1}', 'yy-mm-dd')\n" +
            "and to_date('${date2}','yy-mm-dd')\n"+
            "and cms_common_code.code_nm = '${rest}'\n"+
            "group by cms_recipe.food_id,cms_menu.ymd, cms_common_code.code_nm, cms_menu.meal_id, cms_food.food_nm\n"+
            "order by cms_menu.ymd, cms_common_code.code_nm, cms_menu.meal_id")
    List<QueryDto> findAll(@Param("rest") String rest, @Param("date1") String date1, @Param("date2") String date2);


    @Select("select code_nm, code_id\n" +
            "from cms_common_code\n" +
            "order by code_id")
    List<CommonCodeDto> findSearchCondition();
}
