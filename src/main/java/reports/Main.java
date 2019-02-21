package reports;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class Main {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoints(Config.nodes).build();
        Session session = cluster.connect();
        MappingManager mappingManager = new MappingManager(session);

        // creating instance
        createInstance(10, mappingManager);

        // stating worker threads
        final int numberOfOfficers = Config.numberOfOfficers;
        Officer[] officers = new Officer[numberOfOfficers];
        Thread[] threads = new Thread[numberOfOfficers];
        for (int i = 0; i < numberOfOfficers; i++) {
            officers[i] = new Officer(mappingManager);
            threads[i] = new Thread(officers[i]);
            threads[i].start();
        }

        for (int i = 0; i < numberOfOfficers; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        session.close();
        cluster.close();
    }

    private static void createInstance(int instanceSize, MappingManager mm) {
        Mapper<Report> m = mm.mapper(Report.class);

        for (int i = 0; i < instanceSize; i++) {
            Report r = Report.createRandom();
            m.save(r);
        }

        System.out.println(String.format("Instance of size %s created", instanceSize));
    }
}
