package adventofcode2021;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;

public class Day13 {
    public static class Fold {

        @Override
        public String toString() {
            return "Fold [axis=" + axis + ", position=" + position + "]";
        }

        private String axis;
        private int position;

        public Fold(String axis, int position) {
            this.axis = axis;
            this.position = position;
        }

    }

    static class TransparentPaper {

        private HashSet<Point> points;
        private ArrayList<Fold> folds;

        public TransparentPaper(HashSet<Point> points, ArrayList<Fold> folds) {
            this.points = points;
            this.folds = folds;
        }

        static TransparentPaper parse(String input) {
            String[] dotsAndFolds = input.split("\n\n");
            String[] dotsLines = dotsAndFolds[0].split("\n");
            HashSet<Point> points = new HashSet<Point>();
            for (String dotsLine : dotsLines) {
                String[] xAndY = dotsLine.split(",");
                points.add(new Point(Integer.parseInt(xAndY[0]), Integer.parseInt(xAndY[1])));
            }

            ArrayList<Fold> folds = new ArrayList<>();
            String[] foldLines = dotsAndFolds[1].split("\n");
            for (String foldLine : foldLines) {
                String[] axisAndPosition = foldLine.split(" ")[2].split("=");
                folds.add(new Fold(axisAndPosition[0], Integer.parseInt(axisAndPosition[1])));
            }
            return new TransparentPaper(points, folds);
        }

        @Override
        public String toString() {
            return "TransparentPaper [points=" + points + ", folds=" + folds + "]";
        }

        boolean foldOne() {
            if (folds.size() == 0)
                return false;

            Fold nextFold = folds.remove(0);
            HashSet<Point> newPoints = new HashSet<>();
            if (nextFold.axis.equals("x")) {
                for (Point point : points) {
                    if (point.x < nextFold.position)
                        newPoints.add(point);
                    else {
                        newPoints.add(new Point(nextFold.position - (point.x - nextFold.position), point.y));
                    }
                }
            } else {
                for (Point point : points) {
                    if (point.y < nextFold.position)
                        newPoints.add(point);
                    else {
                        newPoints.add(new Point(point.x, nextFold.position - (point.y - nextFold.position)));
                    }
                }
            }
            points = newPoints;

            return true;
        }

        int visiblePoints() {
            return points.size();
        }

        void printOn(PrintStream out) {
            int maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
            int maxY = points.stream().mapToInt(p -> p.y).max().getAsInt();

            for (int y = 0; y <= maxY; ++y) {
                for (int x = 0; x <= maxX; ++x)
                    if (points.contains(new Point(x, y)))
                        out.print("\u2588");
                    else
                        out.print(" ");
                out.println();
            }
        }
    }

    public static void main(String[] args) {
        TransparentPaper transparentPaper = TransparentPaper.parse(Common.inputAsStringFor("day13"));
        transparentPaper.foldOne();
        System.out.println("Day 13 part 1: " + transparentPaper.visiblePoints());

        while (transparentPaper.foldOne())
            ;

        System.out.println("Day 13 part 2: ");
        transparentPaper.printOn(System.out);
    }
}
