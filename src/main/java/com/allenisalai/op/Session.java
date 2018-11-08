package com.allenisalai.op;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.UUID;


@PlanningEntity
public class Session {

    private UUID id;

    private Client client;

    private Staff staff;

    private Integer startTime = 0;

    private Integer duration = 0;

    private Integer day;

    public Session() {

    }

    public Session(Staff staff, Client client, Integer day) {
        this.staff = staff;
        this.client = client;
        this.day = day;

        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    //@PlanningVariable(valueRangeProviderRefs = {"clients"})
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    //@PlanningVariable(valueRangeProviderRefs = {"staff"})
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

    //@PlanningVariable(valueRangeProviderRefs = {"days"})
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public boolean overlaps(Session s) {
        int start = this.startTime; // 8
        int end = this.startTime + this.duration; // 11

        int sess2End = s.startTime + s.duration;

        return (
                this.day == s.getDay() &&
                        (
                                (s.startTime >= start && s.startTime < end)
                                        || (sess2End >= start && sess2End < end)
                        )
        );
    }

    public int getEndTime() {
        return this.startTime + this.duration;
    }


    public void print() {
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        System.out.printf("%s: %s %s at %d for %d hours \n", this.staff.name, this.client.id, days[this.day], this.startTime, this.duration);
    }
}
