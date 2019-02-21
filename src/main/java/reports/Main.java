package reports;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import config.PropertiesGetter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String propertiesFileName = "config.properties";
        if (args.length > 0) {
            propertiesFileName = args[0];
        }
        PropertiesGetter properties = new PropertiesGetter(propertiesFileName);

        Cluster cluster = Cluster.builder().addContactPoints(properties.getNodesList()).build();
        Session session = cluster.connect();
        MappingManager mappingManager = new MappingManager(session);

        // creating instance
        int mapWidth = properties.getMapWidth();
        int mapHeight = properties.getMapHeight();

        // stating worker threads
        final int numberOfOfficers = properties.getNumberOfOfficers();
        final long waitTime = properties.getWaitTime();
        Officer[] officers = new Officer[numberOfOfficers];
        Thread[] threads = new Thread[numberOfOfficers];
        for (int i = 0; i < numberOfOfficers; i++) {
            officers[i] = new Officer(mappingManager, mapWidth, mapHeight, waitTime);
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

    private static void createInstance(int instanceSize, int mapWidth, int mapHeight, MappingManager mm) {
        Mapper<Report> m = mm.mapper(Report.class);

        for (int i = 0; i < instanceSize; i++) {
            Report r = Report.createRandom(mapWidth, mapHeight);
            m.save(r);
        }

        System.out.println(String.format("Instance of size %s created", instanceSize));
    }
}
