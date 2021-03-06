package reports;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;
import java.util.UUID;

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
@AllArgsConstructor
@Data
public class Report {
    static final String tableName = "reports";
    static final String idColumnName = "id";

    @PartitionKey
    @Column(name = idColumnName)
    private String id;

    @Column(name = "x")
    private int positionX;

    @Column(name = "y")
    private int positionY;

    public static List<Report> selectAll(MappingManager mappingManager) {
        ReportAccessor reportAccessor = mappingManager.createAccessor(ReportAccessor.class);
        return reportAccessor.selectAll().all();
    }

    public static Report createRandom(int mapWidth, int mapHeight) {
        Random random = new Random();
        Report r = new Report();

        r.positionX = random.nextInt(mapWidth);
        r.positionY = random.nextInt(mapHeight);
        r.id = UUID.randomUUID().toString();

        return r;
    }
}
