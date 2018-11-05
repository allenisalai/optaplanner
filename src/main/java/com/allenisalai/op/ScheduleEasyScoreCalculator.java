package com.allenisalai.op;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class ScheduleEasyScoreCalculator implements EasyScoreCalculator<Schedule> {
    public Score calculateScore(Schedule schedule) {
        Integer hardScore = 0;

        int maxHours = 0;
        for (Client c : schedule.clients) {
            maxHours += c. getWeeklyContractHours();
        }

        int plannedHours = 0;
        for (Session s : schedule.sessions) {
            plannedHours += s.getDuration();
        }

        return HardSoftScore.valueOf(plannedHours, 0);
    }
}
