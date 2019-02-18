package reports;


import com.datastax.driver.mapping.MappingManager;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class Officer implements Runnable {
    private String id;
    private int positionX, positionY;

    private MappingManager mappingManager;

    Officer(MappingManager mappingManager) {
        this.mappingManager = mappingManager;

        this.id = UUID.randomUUID().toString();

        final Random random = new Random();
        this.positionX = random.nextInt(Config.mapWidth);
        this.positionY = random.nextInt(Config.mapHeight);
    }

    public void run() {
        System.out.println(String.format("%s %s %s", this.id, this.positionX, this.positionY));

        List<Report> reportList = Report.selectAll(this.mappingManager);

        System.out.println(reportList.stream().map(Report::getId).collect(Collectors.toList()));
    }
}
