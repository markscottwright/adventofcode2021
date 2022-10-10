package adventofcode2021;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

public class Day6 {

    static class FishCohorts {
        // how many fish are there per days left to reproduce
        HashMap<Integer, BigInteger> cohorts = new HashMap<>();

        FishCohorts reproduceForOneDay() {
            FishCohorts tomorrow = new FishCohorts();

            for (Integer daysLeft : cohorts.keySet()) {
                if (daysLeft > 0) {
                    tomorrow.addForDaysLeft(daysLeft - 1, cohorts.get(daysLeft));
                } else {
                    // reset reproducing fish' count to 6
                    tomorrow.addForDaysLeft(6, cohorts.get(0));

                    // and they reproduce with counter at 8
                    tomorrow.addForDaysLeft(8, cohorts.get(0));
                }
            }

            return tomorrow;
        }

        private void addForDaysLeft(int daysLeft, BigInteger fishCount) {
            BigInteger currentCountAtDaysLeft = cohorts.getOrDefault(daysLeft, ZERO);
            cohorts.put(daysLeft, currentCountAtDaysLeft.add(fishCount));
        }

        BigInteger totalFish() {
            BigInteger out = ZERO;
            for (BigInteger fishInCohort : cohorts.values())
                out = out.add(fishInCohort);
            return out;
        }

        static FishCohorts parse(String s) {
            FishCohorts out = new FishCohorts();
            Arrays.stream(s.strip().split(",")).map(Integer::parseInt).forEach(daysLeft -> {
                out.addForDaysLeft(daysLeft, ONE);
            });
            return out;
        }

        public FishCohorts reproduceForDays(int days) {
            FishCohorts out = this;
            for (int day = 0; day < days; ++day) {
                out = out.reproduceForOneDay();
            }
            return out;
        }
    }

    public static void main(String[] args) {
        try {
            FishCohorts originalLanternFishCohorts = FishCohorts.parse(Common.inputAsStringFor("day6"));

            System.out.println("Day 6 part 1: " + originalLanternFishCohorts.reproduceForDays(80).totalFish());
            System.out.println("Day 6 part 2: " + originalLanternFishCohorts.reproduceForDays(256).totalFish());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
