package reports;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

public class Main {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoints(Config.nodes).build();
        Session session = cluster.connect();
        MappingManager mappingManager = new MappingManager(session);
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
}
