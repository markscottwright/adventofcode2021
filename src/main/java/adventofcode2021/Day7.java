package adventofcode2021;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.collections4.bag.HashBag;

public class Day7 {
    static class CrabFleet {
        private HashBag<Integer> positions = new HashBag<>();
        private Integer maxPosition;
        private Integer minPosition;

        static CrabFleet parse(String s) {
            CrabFleet out = new CrabFleet();
            Arrays.stream(s.trim().split(",")).map(Integer::parseInt).forEach(out.positions::add);
            out.maxPosition = out.positions.uniqueSet().stream().max(Integer::compare).get();
            out.minPosition = out.positions.uniqueSet().stream().min(Integer::compare).get();
            return out;
        }

        long findCheapestCostWithConstantBurnRate() {
            long cheapestCost = Integer.MAX_VALUE;
//            int cheapestPosition = -1;
            for (int pos = minPosition; pos <= maxPosition; ++pos) {
                long cost = costToMoveToWithConstantBurnRate(pos);
                if (cost < cheapestCost) {
                    cheapestCost = cost;
//                    cheapestPosition = pos;
                }
            }

            return cheapestCost;
        }

        long findCheapestCostWithIncreasingBurnRate() {
            long cheapestCost = Integer.MAX_VALUE;
//            int cheapestPosition = -1;
            for (int pos = minPosition; pos <= maxPosition; ++pos) {
                long cost = costToMoveToWithIncreasingBurnRate(pos);
                if (cost < cheapestCost) {
                    cheapestCost = cost;
//                    cheapestPosition = pos;
                }
            }

            return cheapestCost;
        }

        long costToMoveToWithConstantBurnRate(int pos) {
            long cost = positions.stream().mapToLong(p -> Math.abs(p - pos)).sum();
            return cost;
        }

        long costToMoveToWithIncreasingBurnRate(int pos) {
            long cost = positions.stream().mapToLong(p -> {
                // sum(1...n) = n(n+1)/2
                int n = Math.abs(p - pos);
                return (n * (n + 1)) / 2;
            }).sum();
            return cost;
        }
    }

    public static void main(String[] args) {
        CrabFleet fleet = CrabFleet.parse(Common.inputAsStringFor("day7"));
        System.out.println("Day 7 part 1: " + fleet.findCheapestCostWithConstantBurnRate());
        System.out.println("Day 7 part 2: " + fleet.findCheapestCostWithIncreasingBurnRate());
    }
}
