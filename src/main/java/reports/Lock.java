package reports;


import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.*;

import java.util.Date;

@Accessor
interface LockAccessor {
    @Query("SELECT * FROM " + Lock.tableName)
    Result<Lock> selectAll();
}

@Table(name = Lock.tableName, readConsistency = Config.CONSISTENCY_ONE, writeConsistency = Config.CONSISTENCY_ONE)
public class Lock {
    static final String tableName = "locks";

    @PartitionKey
    @Column(name = "reportId")
    private String reportId;

    @Column(name = "officerId")
    private String officerId;

    @Column(name = "timestamp")
    private Date timestamp;
}
