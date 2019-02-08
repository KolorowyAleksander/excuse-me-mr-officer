package reports;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Accessor
interface ReportAccessor {
    @Query("SELECT * FROM " + Report.tableName)
    Result<Report> selectAll();
}


@Table(name = Report.tableName, readConsistency = Config.CONSISTENCY_ONE, writeConsistency = Config.CONSISTENCY_ONE)
@NoArgsConstructor
@Data
public class Report {
    static final String tableName = "reports";

    @PartitionKey
    @Column(name = "id")
    private String id;

    @Column(name = "positionX")
    private int positionX;

    @Column(name = "positionY")
    private int positionY;
}
