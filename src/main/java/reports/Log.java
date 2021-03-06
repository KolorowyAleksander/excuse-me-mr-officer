package reports;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Accessor
interface LogAccessor {
    @Query("SELECT * FROM " + Log.tableName)
    Result<Log> selectAll();
}


@Table(
        keyspace = Config.keyspace,
        name = Log.tableName,
        readConsistency = Constants.CONSISTENCY_ONE,
        writeConsistency = Constants.CONSISTENCY_ONE
)
@NoArgsConstructor
@Data
public class Log {
    static final String tableName = "logs";

    @PartitionKey
    @Column(name = "officerID")
    private String officerId;

    @Column(name = "reportID")
    private String reportId;

    @Column(name = "departure")
    private Date departure;

    @Column(name = "arrival")
    private Date arrival;
}
