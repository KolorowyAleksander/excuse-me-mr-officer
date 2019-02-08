package reports;
import com.datastax.driver.mapping.annotations.*;

@Table(name="reports", readConsistency = "ONE", writeConsistency = "ONE")
public class Main {

  public static void main(String[] args) {
    System.out.println("Kopytko!");
  }
}
