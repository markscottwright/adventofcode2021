package adventofcode2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9 {
    static class HeightMap {
        HashMap<Point, Integer> map = new HashMap<>();

        static HeightMap parse(String[] lines) {
            HeightMap heightMap = new HeightMap();

            int y = 0;
            for (String line : lines) {
                for (int x = 0; x < line.length(); x++) {
                    heightMap.map.put(new Point(x, y), line.charAt(x) - '0');
                }
                y++;
            }

            return heightMap;
        }

        public boolean isMinimum(Point p) {
            return map.get(p) < p.getNeighbors().stream().filter(map::containsKey).mapToInt(map::get).min().getAsInt();
        }

        public int getRiskValueSum(Set<Point> minima) {
            return minima.stream().mapToInt(p -> map.get(p) + 1).sum();
        }

        public Set<Point> findMinima() {
            return map.keySet().stream().filter(p -> isMinimum(p)).collect(Collectors.toSet());
        }

        public Set<Point> findBasinFor(Point p) {
            HashSet<Point> basin = new HashSet<Point>();
            ArrayList<Point> newPointsInBasin = new ArrayList<Point>();
            newPointsInBasin.add(p);

            while (!newPointsInBasin.isEmpty()) {
                Point candidate = newPointsInBasin.remove(0);
                basin.add(candidate);
                for (Point neighbor : candidate.getNeighbors()) {
                    if (map.containsKey(neighbor) && !basin.contains(neighbor) && map.get(neighbor) != 9
                            && map.get(neighbor) > map.get(candidate))
                        newPointsInBasin.add(neighbor);
                }
            }

            return basin;
        }
    }

    public static void main(String[] args) {
        String[] inputLines = Common.inputAsArrayFor("day9");
        HeightMap heightMap = HeightMap.parse(inputLines);
        long part1 = heightMap.getRiskValueSum(heightMap.findMinima());
        System.out.println("Day 9 part 1: " + part1);

        ArrayList<Set<Point>> basins = new ArrayList<Set<Point>>();
        heightMap.findMinima().stream().map(heightMap::findBasinFor).forEach(basins::add);
        basins.sort((basin1, basin2) -> -Integer.compare(basin1.size(), basin2.size()));
        int threeLargest = basins.get(0).size() * basins.get(1).size() * basins.get(2).size();
        System.out.println("Day 9 part 2: " + threeLargest);

    }
}
