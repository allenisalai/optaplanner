package com.allenisalai.op;

import org.apache.commons.lang3.StringUtils;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
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
    }

    public Schedule( EntityManager em ) {
        this.startTimes = new ArrayList<Integer>();
        // 8am -> 5pm
        for (int i = 8; i < 18; i++) {
            this.startTimes.add(i);
        }


        this.durations = new ArrayList<Integer>();
        for (int i = 0; i <= 3; i++) {
            this.durations.add(i);
        }

        this.days = new ArrayList<Integer>();
        // monday - friday
        for (int i = 1; i < 6; i++) {
            this.days.add(i);
        }

        // load staff from db
        this.staff = new ArrayList<Staff>();
        List<Staff> staffList = em.createQuery(
                "select s from Staff s ", Staff.class)
                .getResultList();
        this.staff.addAll(staffList);

        // load clients from db
        this.clients = new ArrayList<Client>();
        List<Client> clientList = em.createQuery(
                "select c from Client c ", Client.class)
                .getResultList();
        this.clients.addAll(clientList);

//
//        // 65 total hours
//        this.clients.add(new Client("Jimmy", 5, 12));
//        this.clients.add(new Client("Tim", 10, 10));
//        this.clients.add(new Client("Robert", 15, 10));
//        this.clients.add(new Client("Jenny", 10, 8));
//
//        this.clients.add(new Client("Kary", 5, 6));
//        this.clients.add(new Client("Matt", 10, 5));
//        this.clients.add(new Client("Sally", 20, 5));
//        this.clients.add(new Client("Ryan", 10, 4));
//        this.clients.add(new Client("Bob", 15, 3));
//        this.clients.add(new Client("Mark", 10, 2));

        this.sessions = new ArrayList<Session>();
        for (Staff s : this.staff) {
            for (Client c : this.clients) {
                for (Integer d : this.days) {
                    this.sessions.add(new Session(s, c, d));
                }
            }
        }
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
        for (Staff s : this.staff) {
            this.printStaffSchedule(s);
        }

    }

    public void save( EntityManager em ) {
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM session").executeUpdate();

        for (Session sess : this.sessions) {
            if (sess.getDuration() != 0 && sess.getStartTime() != 0) {
                em.persist(sess);
            }
        }

        em.flush();
        em.close();
    }

    private void printStaffSchedule(Staff s) {
        //String[] headers = {"Hour", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        this.sessions.sort(new Comparator<Session>() {
            public int compare(Session o1, Session o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });

        this.sessions.sort(new Comparator<Session>() {
            public int compare(Session o1, Session o2) {
                return o1.getDay().compareTo(o2.getDay());
            }
        });


        for (Session sess : this.sessions) {
            if (sess.getStaff() == s && sess.getDuration() != 0) {
                sess.print();
            }
        }

        for (Client c : this.clients) {
            int totalHoursPlanned = 0;
            for (Session sess : this.sessions) {
                if (sess.getClient().equals(c)) {
                    totalHoursPlanned += sess.getDuration();
                }
            }
            System.out.printf("%s - %d/%d\n", c.getId(), totalHoursPlanned, c.getWeeklyContractHours());
        }

        float totalPossibleRevenue = 0;
        for (Client c : this.clients) {
            totalPossibleRevenue += (c.getWeeklyContractHours() * c.getContractPricePerHour());
        }
        float plannedRevenue = 0;

        for (Session sess : this.sessions) {
            plannedRevenue += (sess.getDuration() * sess.getClient().getContractPricePerHour());
        }

        for (Staff st : this.staff) {
            int billableHours = 0;
            for (Session sess : this.sessions) {
                if (sess.getStaff().equals(st)) {
                    billableHours += sess.getDuration();
                }
            }
            System.out.printf("%s Billable hours - %d\n", st.getFirstName(), billableHours);
        }


        float revenuePercentage = (plannedRevenue / totalPossibleRevenue) * 100;
        System.out.printf("Percentage revenue planned:  $%.0f / $%.0f  (%.2f%%)\n", plannedRevenue, totalPossibleRevenue, revenuePercentage);

    }

}
