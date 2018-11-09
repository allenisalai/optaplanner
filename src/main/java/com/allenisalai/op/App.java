package com.allenisalai.op;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.Solver;

import javax.persistence.EntityManager;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args)  {
        //BasicConfigurator.configure();


        SessionFactory sf = getSessionFactory();
        Session session = sf.openSession();
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        //session.beginTransaction();



        System.out.println("Starting the process.");

        SolverFactory<Schedule> solverFactory = SolverFactory.createFromXmlResource("solverConfig.xml");
        Solver<Schedule> scheduleSolver = solverFactory.buildSolver();

        Schedule unsolvedSchedule = new Schedule(em);
        Schedule solvedSchedule = scheduleSolver.solve(unsolvedSchedule);

        System.out.println("Finished the process.");
        System.out.println(scheduleSolver.explainBestScore());
        solvedSchedule.print();
        System.out.println("Saving");
        solvedSchedule.save(em);

        //session.getTransaction().commit();
        session.close();
        System.out.println("Should Exit");
    }

    public static SessionFactory getSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }

        return null;
    }
}
