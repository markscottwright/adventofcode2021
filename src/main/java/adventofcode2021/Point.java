package adventofcode2021;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Collection<Point> getNeighbors() {
        ArrayList<Point> neighbors = new ArrayList<>();
        neighbors.add(new Point(x - 1, y));
        neighbors.add(new Point(x + 1, y));
        neighbors.add(new Point(x, y - 1));
        neighbors.add(new Point(x, y + 1));
        return neighbors;
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

}
