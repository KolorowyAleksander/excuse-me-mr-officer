package reports;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.*;

@Accessor
interface ReportAccessor {
    @Query("SELECT * FROM " + Report.tableName)
    Result<Report> selectAll();
}


@Table(name = Report.tableName, readConsistency = "ONE", writeConsistency = "ONE")
public class Report {
    static final String tableName = "report";
    @PartitionKey
    @Column
    private String id;

    @Column
    private int positionX;

    @Column
    private int positionY;
}
