package reports;


import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Accessor
interface LockAccessor {
    @Query("SELECT * FROM " + Lock.tableName + " WHERE " + Lock.reportIdColumnName + "= (?)")
    Result<Lock> selectByReportId(String reportId);
}

@Table(name = Lock.tableName, readConsistency = Config.CONSISTENCY_ONE, writeConsistency = Config.CONSISTENCY_ONE)
@NoArgsConstructor
@Data
public class Lock {
    static final String tableName = "locks";

    static final String reportIdColumnName = "reportId";

    @PartitionKey
    @Column(name = reportIdColumnName)
    private String reportId;

    @Column(name = "officerId")
    private String officerId;

    @Column(name = "timestamp")
    private Date timestamp;


}
