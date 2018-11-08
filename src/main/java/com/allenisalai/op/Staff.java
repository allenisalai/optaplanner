package com.allenisalai.op;

public class Staff {

    String name;

    private Integer idealBillableHours = 25;

    private Integer maxWeeklyHours = 30;

    public Staff(String name) {
        this.name = name;
    }

    public Integer getIdealBillableHours() {
        return this.idealBillableHours;
    }

    public Integer getMaxWeeklyHours() {
        return this.maxWeeklyHours;
    }
}
