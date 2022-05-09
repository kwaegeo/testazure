package com.NoChu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    private List<Week> weeks = new ArrayList<>();
    private int pageNum;

    public Page(Page page){
        this.weeks = new ArrayList<>();
        for(int i = 0; i<page.getWeeks().size(); i++){
            this.weeks.add(new Week(page.getWeeks().get(i)));
        }
    }

}

