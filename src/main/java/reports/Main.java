package reports;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

public class Main {
    static final String[] nodes = {"192.168.15.15", "192.168.16.16"};
    static final int numberOfOfficers = 2;

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoints(nodes).build();
        Session session = cluster.connect();
        MappingManager mappingManager = new MappingManager(session);
        Officer[] officers = new Officer[numberOfOfficers];
        Thread[] threads = new Thread[numberOfOfficers];
        for (int i = 0; i < numberOfOfficers; i++) {
            officers[i] = new Officer();
            threads[i] = new Thread(officers[i]);
            threads[i].start();
        }

        for (int i = 0; i < numberOfOfficers; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.print(e.getMessage());
            }
        }
    }
}
