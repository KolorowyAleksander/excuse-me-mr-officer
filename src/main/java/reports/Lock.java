package reports;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Accessor
interface LockAccessor {
    @Query("SELECT * FROM " + Config.keyspace + "." + Lock.tableName + " WHERE " + Lock.reportIdColumnName + "= (?)")
    Result<Lock> selectByReportId(String reportId);
}

@Table(
        keyspace = Config.keyspace,
        name = Lock.tableName,
        readConsistency = Constants.CONSISTENCY_ONE,
        writeConsistency = Constants.CONSISTENCY_ONE
)
@NoArgsConstructor
@Data
public class Lock {
    static final String tableName = "locks";

    static final String reportIdColumnName = "reportID";

    @PartitionKey
    @Column(name = reportIdColumnName)
    private String reportId;

    @Column(name = "officerID")
    private String officerId;

    @Column(name = "timestamp")
    private Date timestamp;

    public static Lock selectOne(MappingManager mm, String reportID) {
        LockAccessor lockAccessor = mm.createAccessor(LockAccessor.class);
        return lockAccessor.selectByReportId(reportID).one();
    }
}
