package com.allenisalai.op;

import javax.persistence.*;

@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "ideal_billable_hours")
    private Integer idealBillableHours = 25;

    @Column(name = "max_weekly_hours")
    private Integer maxWeeklyHours = 30;

    public Staff() {
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getIdealBillableHours() {
        return this.idealBillableHours;
    }

    public Integer getMaxWeeklyHours() {
        return this.maxWeeklyHours;
    }
}
