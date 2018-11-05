package com.allenisalai.op;

public class Client {
    String id;

    int contractPricePerHour;

    int weeklyContractHours;

    public Client(String id, int contractPricePerHour, int weeklyContractHours) {
        this.id = id;
        this.contractPricePerHour = contractPricePerHour;
        this.weeklyContractHours = weeklyContractHours;
    }

    public String getId() {
        return id;
    }

    /*
    public int getContractPricePerHour() {
        return contractPricePerHour;
    }
    */

    public int getWeeklyContractHours() {
        return weeklyContractHours;
    }
}
