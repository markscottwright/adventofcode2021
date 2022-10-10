package adventofcode2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

import adventofcode2021.Day5.Line;
import adventofcode2021.Day5.Point;

public class Day5 {
    public static class Point {

        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
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

        @Override
        public String toString() {
            return "Point [x=" + x + ", y=" + y + "]";
        }

    }

    static public class Line {

        @Override
        public String toString() {
            return "Line [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
        }

        private int x1;
        private int y1;
        private int x2;
        private int y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        boolean isHorizontal() {
            return y1 == y2;
        }

        boolean isVertical() {
            return x1 == x2;
        }

        Optional<Point> intersection(Line other) {
            if (isHorizontal() && other.isVertical() && ((Math.min(x1, x2) <= other.x1) && (Math.max(x1, x2) > other.x1)
                    && (y1 >= Math.min(other.y1, other.y2) && y1 <= Math.min(other.y1, other.y2)))) {
                return Optional.ofNullable(new Point(other.x1, y1));
            } else if (isVertical() && other.isHorizontal()
                    && ((Math.min(other.x1, other.x2) <= x1) && (Math.max(other.x1, other.x2) > x1)
                            && (other.y1 >= Math.min(y1, y2) && other.y1 <= Math.min(y1, y2)))) {
                return Optional.ofNullable(new Point(x1, other.y1));
            }

            return Optional.empty();
        }

        static Line parse(String s) {
            Pattern pattern = Pattern.compile("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)");

            Matcher matcher = pattern.matcher(s);
            matcher.matches();
            return new Line(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
        }

        public Collection<? extends Point> getPoints() {
            ArrayList<Point> out = new ArrayList<Point>();
            if (isHorizontal()) {
                for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); ++x)
                    out.add(new Point(x, y1));
            } else if (isVertical()) {
                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); ++y)
                    out.add(new Point(x1, y));
            } else {
                int xStep = x1 < x2 ? 1 : -1;
                int yStep = y1 < y2 ? 1 : -1;
                for (int x = x1, y = y1; x != x2; x += xStep, y += yStep) {
                    out.add(new Point(x, y));
                }
                out.add(new Point(x2, y2));
            }

            return out;
        }

        public static List<Point> getIntersectingPoints(List<Line> lines) {
            Bag<Point> points = new HashBag<>();
            for (Line line : lines) {
                points.addAll(line.getPoints());
            }
            return points.uniqueSet().stream().filter(p -> points.getCount(p) > 1).toList();
        }
    }

    public static void main(String[] args) throws IOException {
        List<Line> lines = Common.parseInputFor("day5", Line::parse);
        List<Line> horizontalAndVerticalLines = lines.stream().filter(l -> l.isHorizontal() || l.isVertical()).toList();

        System.out.println("Part 1:" + Line.getIntersectingPoints(horizontalAndVerticalLines).size());
        System.out.println("Part 2:" + Line.getIntersectingPoints(lines).size());
    }
}
