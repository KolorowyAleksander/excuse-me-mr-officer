package reports;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.*;

import java.util.Date;

@Accessor
interface LogAccessor {
    @Query("SELECT * FROM " + Log.tableName)
    Result<Log> selectAll();
}

@Table(name = Log.tableName, readConsistency = Config.CONSISTENCY_ONE, writeConsistency = Config.CONSISTENCY_ONE)
public class Log {
    static final String tableName = "logs";

    @PartitionKey
    @Column(name = "officerId")
    private String officerId;

    @Column(name = "reportId")
    private String reportId;

    @Column(name = "departure")
    private Date departure;

    @Column(name = "arrival")
    private Date arrival;
}
