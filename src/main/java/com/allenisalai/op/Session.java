package com.allenisalai.op;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Session {

    private Client client;


    private Staff staff;


    private Integer startTime;


    private Integer duration ;

    private Integer day;

    @PlanningVariable(valueRangeProviderRefs = {"clients"})
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @PlanningVariable(valueRangeProviderRefs = {"staff"})
    public Staff getStaff() {
        return this.staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @PlanningVariable(valueRangeProviderRefs = {"startTimes"})
    public Integer getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    @PlanningVariable(valueRangeProviderRefs = {"durations"})
    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @PlanningVariable(valueRangeProviderRefs = {"days"})
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void print() {
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        System.out.printf("%s: %s for %d hours \n", this.staff.name, days[this.day], this.duration);
    }
}
