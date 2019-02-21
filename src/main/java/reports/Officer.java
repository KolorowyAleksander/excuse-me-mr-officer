package reports;


import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Officer implements Runnable {
    private String id;
    private Lock currentLock;

    private int positionX, positionY;

    private MappingManager mappingManager;

    private static int WAIT_TIME = 100;

    private List<Report> sortedReports;
    private Report currentReport;

    Officer(MappingManager mappingManager) {
        this.mappingManager = mappingManager;

        this.id = UUID.randomUUID().toString();

        final Random random = new Random();
        this.positionX = random.nextInt(Config.mapWidth);
        this.positionY = random.nextInt(Config.mapHeight);
    }

    public void run() {
        this.sortedReports = Report.selectAll(this.mappingManager);

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
        int a = this.currentReport.getPositionX();
        int b = this.currentReport.getPositionY();

        // count which move will take me closer to current Report
        // this throws if it's coded wrong
        Tuple3 t = Stream.of(
                new Tuple2(this.positionX, this.positionY + 1),
                new Tuple2(this.positionX, this.positionY - 1),
                new Tuple2(this.positionX + 1, this.positionY),
                new Tuple2(this.positionX - 1, this.positionY)
        )
                .map(p -> new Tuple3(p.x, p.y, countDistance(p.x, p.y, a, b)))
                .min(Comparator.comparing(Tuple3::getDistance))
                .orElseThrow(RuntimeException::new);

        // update current position
        this.positionX = t.x;
        this.positionY = t.y;

        if (t.x == a && t.y == b) {
            // arrived at the current report
            System.out.println(this.id + " arrived a the current report: " + this.currentLock.getReportId());

            Log log = new Log();
            log.setOfficerId(this.id);
            log.setReportId(this.currentLock.getReportId());
            log.setDeparture(this.currentLock.getTimestamp());
            log.setArrival(new Date());
            Mapper<Log> mapper = this.mappingManager.mapper(Log.class);
            mapper.save(log);

            this.currentLock = null;

            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private Lock lockAReport() {
        int a = this.positionX;
        int b = this.positionY;

        // sort the list of reports by distance from current position
        this.sortedReports = sortByDistance(this.sortedReports, a, b);

        Iterator<Report> iterator = this.sortedReports.iterator();

        while (iterator.hasNext()) {
            Report r = iterator.next();

            Mapper<Lock> lockMapper = this.mappingManager.mapper(Lock.class);

            if (lockMapper.get(r.getId()) == null) {
                this.currentReport = r;
                // lock a new Report for myself
                Lock lock = new Lock();
                lock.setOfficerId(this.id);
                lock.setReportId(r.getId());
                lock.setTimestamp(new Date());

                Mapper<Lock> m = this.mappingManager.mapper(Lock.class);
                m.save(lock);
                return lock;
            }

            iterator.remove();
        }

        // no reports left without a lock - job finished
        return null;
    }

    // sort list of reports by distance from (a,b)
    public static List<Report> sortByDistance(List<Report> reports, int a, int b) {
        return reports.stream()
                .map(r -> new ReportTuple(r, countDistance(a, b, r.getPositionX(), r.getPositionY())))
                .sorted(Comparator.comparing(ReportTuple::getDistance))
                .map(t -> t.r)
                .collect(Collectors.toList());
    }

    // count distance between (a,b) and (x,y)
    private static double countDistance(int a, int b, int x, int y) {
        return Math.sqrt(Math.pow(x - a, 2) + Math.pow(y - b, 2));
    }
}
