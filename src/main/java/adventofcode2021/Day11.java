package adventofcode2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Day11 {
    static class DumboOctopuses {
        HashMap<Point, Integer> octopusToEnergyLevel = new HashMap<>();
        int flashes = 0;

        static DumboOctopuses parse(String[] input) {
            DumboOctopuses dumboOctopuses = new DumboOctopuses();
            int y = 0;
            for (String line : input) {
                for (int x = 0; x < line.toCharArray().length; x++) {
                    char c = line.toCharArray()[x];
                    dumboOctopuses.octopusToEnergyLevel.put(new Point(x, y), c - '0');
                }
                y++;
            }

            return dumboOctopuses;
        }

        /**
         * @return true if all octopi flashed this time
         */
        boolean oneStep() {
            HashSet<Point> octopusToFlash = new HashSet<>();

            octopusToEnergyLevel.keySet().forEach(e -> {
                int level = octopusToEnergyLevel.get(e);
                level += 1;
                octopusToEnergyLevel.put(e, level);
                if (level == 10)
                    octopusToFlash.add(e);
            });

            while (!octopusToFlash.isEmpty()) {
                ArrayList<Point> neighborsOfFlashers = new ArrayList<>();
                flashes += octopusToFlash.size();

                // add all neighbors (that are valid positions) to the neighborsOfFlashers
                octopusToFlash.forEach(o -> neighborsOfFlashers.addAll(
                        o.getNeighborsWithDiagonals().stream().filter(octopusToEnergyLevel::containsKey).toList()));

                octopusToFlash.clear();
                for (Point o : neighborsOfFlashers) {
                    int level = octopusToEnergyLevel.get(o);
                    if (level < 10) {
                        level += 1;
                        octopusToEnergyLevel.put(o, level);
                        if (level == 10)
                            octopusToFlash.add(o);
                    }
                }
            }

            octopusToEnergyLevel.keySet().stream().filter(p -> octopusToEnergyLevel.get(p) > 9)
                    .forEach(p -> octopusToEnergyLevel.put(p, 0));
            return octopusToEnergyLevel.values().stream().allMatch(v -> v == 0);
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder();
            int maxX = octopusToEnergyLevel.keySet().stream().mapToInt(o -> o.x).max().getAsInt();
            int maxY = octopusToEnergyLevel.keySet().stream().mapToInt(o -> o.y).max().getAsInt();

            for (int y = 0; y <= maxY; ++y) {
                for (int x = 0; x <= maxX; ++x) {
                    out.append(octopusToEnergyLevel.get(new Point(x, y)));
                }
                out.append("\n");
            }
            return out.toString();
        }

        public int getNumFlashes() {
            return flashes;
        }
    }

    public static void main(String[] args) {
        DumboOctopuses dumboOctopuses = DumboOctopuses.parse(Common.inputAsArrayFor("day11"));
        for (int i = 0; i < 100; ++i)
            dumboOctopuses.oneStep();
        System.out.println("Day 11 part 1: " + dumboOctopuses.getNumFlashes());

        dumboOctopuses = DumboOctopuses.parse(Common.inputAsArrayFor("day11"));
        int step = 1;
        while (!dumboOctopuses.oneStep())
            step++;
        System.out.println("Day 11 part 2: " + step);
    }
}
