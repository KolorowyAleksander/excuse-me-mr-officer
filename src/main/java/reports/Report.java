package reports;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Accessor
interface ReportAccessor {
    @Query("SELECT * FROM " + Config.keyspace + "." + Report.tableName)
    Result<Report> selectAll();
}


@Table(
        keyspace = Config.keyspace,
        name = Report.tableName,
        readConsistency = Constants.CONSISTENCY_ONE,
        writeConsistency = Constants.CONSISTENCY_ONE
)
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

    public static List<Report> selectAll(MappingManager mappingManager) {
        ReportAccessor reportAccessor = mappingManager.createAccessor(ReportAccessor.class);
        return reportAccessor.selectAll().all();
    }
}
