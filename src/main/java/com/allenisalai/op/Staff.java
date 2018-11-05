package com.allenisalai.op;

import org.apache.commons.lang3.StringUtils;
import org.optaplanner.core.api.domain.solution.PlanningSolution;

import java.util.ArrayList;

//@PlanningSolution
public class Staff {

    String name;



    private ArrayList<ArrayList<String>> schedule;

    public Staff(String name) {
        this.name = name;


        this.schedule = new ArrayList<ArrayList<String>>(7);

        for (int i = 0; i < 7; i++) {
            ArrayList<String> dayArray = new ArrayList<String>(24);
            for (int j = 0; j < 24; j++) {
                dayArray.add("");
            }

            this.schedule.add(dayArray);
        };
    }


    public void Print()  {
        String[] headers = {"Hour", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        System.out.println(StringUtils.join(headers, ","));

        for (int i = 0; i < 24; i++) {
            ArrayList<String> hourLine = new ArrayList<String>(8);

            hourLine.add(String.valueOf(i));

            for (ArrayList<String> day: this.schedule) {
                hourLine.add(day.get(i));
            }
            System.out.println(String.join(",", hourLine));
        }
    }

    public void setClient(String clientId, int day, int hour) {
        this.schedule.get(day).set(hour, clientId);
    }
}
