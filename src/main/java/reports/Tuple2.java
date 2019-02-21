package reports;

public class Tuple2 {
    public int x;
    public int y;

    @java.beans.ConstructorProperties({"x", "y"})
    public Tuple2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Tuple2)) return false;
        final Tuple2 other = (Tuple2) o;
        if (this.getX() != other.getX()) return false;
        if (this.getY() != other.getY()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getX();
        result = result * PRIME + this.getY();
        return result;
    }

    public String toString() {
        return "Tuple2(x=" + this.getX() + ", y=" + this.getY() + ")";
    }
}
