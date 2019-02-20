package reports;


import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import java.util.*;
import java.util.stream.Stream;

public class Officer implements Runnable {
    private String id;
    private Lock currentLock;

    private int positionX, positionY;

    private MappingManager mappingManager;

    private static int WAIT_TIME = 100;

    Officer(MappingManager mappingManager) {
        this.mappingManager = mappingManager;

        this.id = UUID.randomUUID().toString();

        final Random random = new Random();
        this.positionX = random.nextInt(Config.mapWidth);
        this.positionY = random.nextInt(Config.mapHeight);
    }

    public void run() {
        while (true) {
            System.out.println(this.id + " current position: " + this.positionX + " " + this.positionY);
            // check if still job to do
            if (this.currentLock == null) {
                Lock l = lockAReport();

                if (l == null) {
                    // finished job for today!
                    System.out.println(this.id + " finished job for today!");
                    return;
                } else {
                    // going for a new Report
                    System.out.println(this.id + " going for a new report: " + l.getReportId());
                    this.currentLock = l;
                }
            }

            //move to the next position
            moveTowardsLockedReport();

            // wait for the next round
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void moveTowardsLockedReport() {
        // find where current report is
        Mapper<Report> reportMapper = mappingManager.mapper(Report.class);
        Report currentReport = reportMapper.get(this.currentLock.getReportId());

        int a = currentReport.getPositionX();
        int b = currentReport.getPositionY();

        // count which move will take me closer to current Report
        // this throws if it's coded wrong
        Tuple3 t = Stream.of(
                new Tuple2(this.positionX, this.positionY + 1),
                new Tuple2(this.positionX, this.positionY - 1),
                new Tuple2(this.positionX + 1, this.positionY),
                new Tuple2(this.positionX - 1, this.positionY)
        )
                .map(p -> new Tuple3(p.x, p.y, Math.sqrt(Math.pow(p.x - a, 2) + Math.pow(p.y - b, 2))))
                .min(Comparator.comparing(Tuple3::getDistance))
                .orElseThrow(RuntimeException::new);

        this.positionX = t.x;
        this.positionY = t.y;

        if (t.x == a && t.y == b) {
            // arrived at the current report
            System.out.println(this.id + " arrived a the current report: " + this.currentLock.getReportId());
            this.currentLock = null;

            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private Lock lockAReport() {
        List<Report> reports = Report.selectAll(this.mappingManager);

        for (Report r : reports) {
            Mapper<Lock> lockMapper = this.mappingManager.mapper(Lock.class);

            if (lockMapper.get(r.getId()) == null) {
                // lock a new Report for myself
                Lock lock = new Lock();
                lock.setOfficerId(this.id);
                lock.setReportId(r.getId());
                lock.setTimestamp(new Date());

                Mapper<Lock> m = this.mappingManager.mapper(Lock.class);
                m.save(lock);
                return lock;
            }
        }

        // no reports left without a lock - job finished
        return null;
    }
}
