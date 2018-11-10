package com.allenisalai.op;

import org.hibernate.annotations.Cascade;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.*;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "session")
@PlanningEntity
public class Session {

    private static int planning_id = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idgen")
    @SequenceGenerator(initialValue = 1, name = "idgen", sequenceName = "session_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private Staff staff;

    @Column(name = "start_time")
    private Integer startTime = 0;

    @Column(name = "duration")
    private Integer duration = 0;

    @Column(name = "day")
    private Integer day;

    public Session() {
    }

    public Session(Staff staff, Client client, Integer day) {
        this.staff = staff;
        this.client = client;
        this.day = day;


        // set a negative id so that during planning we can compare sessions
        planning_id--;
        this.id = planning_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

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
                this.day.equals(s.getDay()) &&
                        (
                                (s.startTime >= start && s.startTime < end)
                                        || (sess2End >= start && sess2End < end)
                        )
        );
    }

    public int getEndTime() {
        return this.startTime + this.duration;
    }

}
