package adventofcode2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Day12 {
    static class CaveMap {

        final HashMap<String, HashSet<String>> canVisitFrom;

        public CaveMap(HashMap<String, HashSet<String>> canVisitFrom) {
            this.canVisitFrom = canVisitFrom;
        }

        static CaveMap parse(String[] input) {
            HashMap<String, HashSet<String>> canVisitFrom = new HashMap<>();
            for (String line : input) {
                String[] fromTo = line.split("-");
                if (!canVisitFrom.containsKey(fromTo[0]))
                    canVisitFrom.put(fromTo[0], new HashSet<>());
                canVisitFrom.get(fromTo[0]).add(fromTo[1]);
                if (!canVisitFrom.containsKey(fromTo[1]))
                    canVisitFrom.put(fromTo[1], new HashSet<>());
                canVisitFrom.get(fromTo[1]).add(fromTo[0]);
            }
            return new CaveMap(canVisitFrom);
        }

        HashSet<ArrayList<String>> findAllPaths() {
            HashSet<String> visited = new HashSet<>();
            HashSet<ArrayList<String>> paths = new HashSet<>();

            ArrayList<String> pathSoFar = new ArrayList<>();
            pathSoFar.add("start");
            visited.add("start");

            findAllPathsFrom(pathSoFar, visited, null, paths);

            return paths;
        }

        @SuppressWarnings("unchecked")
        private void findAllPathsFrom(ArrayList<String> pathSoFar, HashSet<String> visited, HashSet<String> secondVisit,
                HashSet<ArrayList<String>> paths) {

            String pos = pathSoFar.get(pathSoFar.size() - 1);
            for (String nextPos : canVisitFrom.get(pos)) {
                if (pos.equals("end")) {
                    paths.add((ArrayList<String>) pathSoFar.clone());
                }

                // handle a second visit to a small cave
                else if (!isLargeCave(nextPos) && !nextPos.equals("start") && !nextPos.equals("end")
                        && visited.contains(nextPos) && secondVisit != null && secondVisit.isEmpty()) {
                    pathSoFar.add(nextPos);
                    secondVisit.add(nextPos);
                    findAllPathsFrom(pathSoFar, visited, secondVisit, paths);
                    secondVisit.remove(nextPos);
                    pathSoFar.remove(pathSoFar.size() - 1);
                }

                // handle a large cave or a first visit to a small cave
                else if (isLargeCave(nextPos) || !visited.contains(nextPos)) {
                    pathSoFar.add(nextPos);
                    visited.add(nextPos);
                    findAllPathsFrom(pathSoFar, visited, secondVisit, paths);
                    visited.remove(nextPos);
                    pathSoFar.remove(pathSoFar.size() - 1);
                }
            }
        }

        private boolean isLargeCave(String nextPos) {
            return Character.isUpperCase(nextPos.charAt(0));
        }

        public HashSet<ArrayList<String>> findAllPathsWithSecondVisitsAllowed() {
            HashSet<String> visited = new HashSet<>();
            HashSet<ArrayList<String>> paths = new HashSet<>();

            ArrayList<String> pathSoFar = new ArrayList<>();
            pathSoFar.add("start");
            visited.add("start");

            findAllPathsFrom(pathSoFar, visited, new HashSet<>(), paths);

            return paths;
        }
    }

    public static void main(String[] args) {
        CaveMap caveMap = Day12.CaveMap.parse(Common.inputAsArrayFor("day12"));
        HashSet<ArrayList<String>> allPaths = caveMap.findAllPaths();
        System.out.println("Day 12 part 1: " + allPaths.size());
        allPaths = caveMap.findAllPathsWithSecondVisitsAllowed();
        System.out.println("Day 12 part 2: " + allPaths.size());
    }
}
