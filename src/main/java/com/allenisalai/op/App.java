package com.allenisalai.op;

import org.apache.log4j.BasicConfigurator;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.Solver;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        //BasicConfigurator.configure();
        System.out.println("Starting the process.");

        SolverFactory<Schedule> solverFactory = SolverFactory.createFromXmlResource("solverConfig.xml");
        Solver<Schedule> scheduleSolver = solverFactory.buildSolver();

        Schedule unsolvedSchedule = new Schedule();
        Schedule solvedSchedule = scheduleSolver.solve(unsolvedSchedule);

        System.out.println("Finished the process.");
        System.out.println(scheduleSolver.explainBestScore());
        solvedSchedule.print();
    }

}
