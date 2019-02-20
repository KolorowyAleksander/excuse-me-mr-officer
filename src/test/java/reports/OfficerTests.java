package reports;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OfficerTests {

    @Test
    public void testSortByDistance() {
        int a = 10;
        int b = 10;

        List<Report> reports = Stream.of(
                new Report("2", 15, 15),
                new Report("3", 0, 75),
                new Report("1", 10, 10)
        ).collect(Collectors.toList());

        List<Report> sortedReports = Officer.sortByDistance(reports, a, b);

        assert (sortedReports.get(0).equals(reports.get(2)));
        assert (sortedReports.get(1).equals(reports.get(0)));
        assert (sortedReports.get(2).equals(reports.get(1)));
    }
}
