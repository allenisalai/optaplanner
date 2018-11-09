package com.allenisalai.op;


import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contract_rate")
    private Integer contractPricePerHour;

    @Column(name = "weekly_contract_hours")
    private Integer weeklyContractHours;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getContractPricePerHour() {
        return contractPricePerHour;
    }

    public Integer getWeeklyContractHours() {
        return weeklyContractHours;
    }
}
