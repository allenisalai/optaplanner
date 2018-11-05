package com.allenisalai.op;

import org.apache.commons.lang3.StringUtils;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.ArrayList;
import java.util.List;

@PlanningSolution
public class Schedule {
    ArrayList<Staff> staff;

    ArrayList<Client> clients;

    ArrayList<Integer> startTimes;

    ArrayList<Integer> durations;

    ArrayList<Integer> days;

    HardSoftScore score;


    ArrayList<Session> sessions;

    public Schedule() {

        this.sessions= new ArrayList<Session>();

        this.startTimes = new ArrayList<Integer>();
        for (int i = 0; i < 24; i++) {
            this.startTimes.add(i);
        }


        this.durations = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            this.durations.add(i);
        }

        this.days = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            this.days.add(i);
        }

        this.staff = new ArrayList<Staff>();
        this.staff.add(new Staff("Mariah"));

        this.clients = new ArrayList<Client>();
        this.clients.add(new Client("Tim", 1, 10));

        this.clients.add(new Client("Bob", 2, 3));
        this.clients.add(new Client("Sally", 2, 4));
        this.clients.add(new Client("Jimmy", 5, 2));

        this.clients.add(new Client("Ryan", 10, 4));
    }

    @ValueRangeProvider(id = "staff")
    @ProblemFactCollectionProperty
    public List<Staff> getStaff() {
        return this.staff;
    }

    @ValueRangeProvider(id = "clients")
    @ProblemFactCollectionProperty
    public List<Client> getClients() {
        return this.clients;
    }

    @ValueRangeProvider(id = "days")
    @ProblemFactCollectionProperty
    public List<Integer> getDays() {
        return this.days;
    }

    @ValueRangeProvider(id = "durations")
    @ProblemFactCollectionProperty
    public List<Integer> getDurations() {
        return this.durations;
    }

    @ValueRangeProvider(id = "startTimes")
    @ProblemFactCollectionProperty
    public List<Integer> getStartTimes() {
        return this.startTimes;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    @PlanningEntityCollectionProperty
    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }


    public void print() {
        for (Staff s: this.staff) {
            this.printStaffSchedule(s);
        }

    }

    private void printStaffSchedule(Staff s) {
        //String[] headers = {"Hour", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        System.out.println(this.sessions.size());
        for (Session sess : this.sessions) {
            if (sess.getStaff() == s) {
                sess.print();
            }
        }


    }

}
