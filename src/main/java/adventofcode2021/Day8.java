package adventofcode2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://adventofcode.com/2021/day/8
 */
public class Day8 {
    static class DisplaySample {
        @Override
        public String toString() {
            return "DisplaySample [signalPatterns=" + Arrays.toString(signalPatterns) + ", outputValue="
                    + Arrays.toString(outputValue) + "]";
        }

        String[] signalPatterns;
        String[] outputValue;
        static private HashMap<Integer, Set<Character>> digitToSegments = new HashMap<>();
        static private HashMap<Set<Character>, Integer> segmentsToDigit = new HashMap<>();
        static private HashMap<Character, HashSet<Integer>> segmentToDigits = new HashMap<>();
        static {
            digitToSegments.put(1, toCharacterSet("cf"));
            digitToSegments.put(7, toCharacterSet("acf"));
            digitToSegments.put(4, toCharacterSet("bcdf"));
            digitToSegments.put(2, toCharacterSet("acdeg"));
            digitToSegments.put(3, toCharacterSet("acdfg"));
            digitToSegments.put(5, toCharacterSet("abdfg"));
            digitToSegments.put(0, toCharacterSet("abcefg"));
            digitToSegments.put(6, toCharacterSet("abdefg"));
            digitToSegments.put(9, toCharacterSet("abcdfg"));
            digitToSegments.put(8, toCharacterSet("abcdefg"));

            for (Integer digit : digitToSegments.keySet()) {
                // create reverse map
                segmentsToDigit.put(digitToSegments.get(digit), digit);

                for (Character segment : digitToSegments.get(digit)) {
                    if (!segmentToDigits.containsKey(segment))
                        segmentToDigits.put(segment, new HashSet<>());
                    segmentToDigits.get(segment).add(digit);
                }
            }
        }

        static DisplaySample parse(String line) {
            DisplaySample out = new DisplaySample();
            String[] signalsAndOutput = line.split(" \\| ");
            out.signalPatterns = signalsAndOutput[0].split(" ");
            out.outputValue = signalsAndOutput[1].split(" ");
            assert out.signalPatterns.length == 10;
            assert out.outputValue.length == 4;
            return out;
        }

        int numUniqueDigits() {
            return (int) Arrays.stream(outputValue)
                    .filter(v -> (v.length() == 2 || v.length() == 4 || v.length() == 3 || v.length() == 7)).count();
        }

        HashMap<Integer, Set<Set<Character>>> possibleWiresForDigits() {
            HashMap<Integer, Set<Set<Character>>> possibleSignalsForDigit = new HashMap<>();
            for (String pattern : signalPatterns) {
                for (Integer digit : digitToSegments.keySet()) {
                    if (pattern.length() == digitToSegments.get(digit).size()) {
                        if (!possibleSignalsForDigit.containsKey(digit))
                            possibleSignalsForDigit.put(digit, new HashSet<>());
                        possibleSignalsForDigit.get(digit).add(toCharacterSet(pattern));
                    }
                }
            }

            return possibleSignalsForDigit;
        }

        HashMap<Integer, Set<Character>> allWiresForDigits() {
            HashMap<Integer, Set<Character>> allSignalsForDigit = new HashMap<>();
            for (String pattern : signalPatterns) {
                for (Integer digit : digitToSegments.keySet()) {
                    if (pattern.length() == digitToSegments.get(digit).size()) {
                        if (!allSignalsForDigit.containsKey(digit))
                            allSignalsForDigit.put(digit, new HashSet<>());
                        allSignalsForDigit.get(digit).addAll(toCharacterSet(pattern));
                    }
                }
            }

            return allSignalsForDigit;
        }

        HashMap<Set<Character>, Set<Set<Character>>> segmentsToPossibleWires() {
            HashMap<Integer, Set<Set<Character>>> possibleSignalsForDigit = new HashMap<>();
            for (String pattern : signalPatterns) {
                for (Integer digit : digitToSegments.keySet()) {
                    if (pattern.length() == digitToSegments.get(digit).size()) {
                        if (!possibleSignalsForDigit.containsKey(digit))
                            possibleSignalsForDigit.put(digit, new HashSet<>());
                        possibleSignalsForDigit.get(digit).add(toCharacterSet(pattern));
                    }
                }
            }

            HashMap<Set<Character>, Set<Set<Character>>> possibleWiresForSegments = new HashMap<>();
            for (Integer digit : possibleSignalsForDigit.keySet())
                possibleWiresForSegments.put(digitToSegments.get(digit), possibleSignalsForDigit.get(digit));

            return possibleWiresForSegments;
        }

        static Set<Character> toCharacterSet(String pattern) {
            TreeSet<Character> out = new TreeSet<Character>(Character::compare);
            for (char c : pattern.toCharArray()) {
                out.add(c);
            }
            return out;
        }

        static int numUniqueDigits(Collection<? extends DisplaySample> samples) {
            return samples.stream().mapToInt(DisplaySample::numUniqueDigits).sum();
        }

        public HashMap<Character, Character> determineWireToSegmentMapping2() {
            HashMap<Character, Character> mapping = new HashMap<Character, Character>();
            TreeSet<Character> remainingWires = new TreeSet<>();
            TreeSet<Character> remainingSegments = new TreeSet<>();
            for (Character c : "abcdefg".toCharArray()) {
                remainingWires.add(c);
                remainingSegments.add(c);
            }

            HashMap<Character, Set<Integer>> possibleDigitsForWire = findPossibleDigitsForWire();
            System.out.println("wire to digits:" + possibleDigitsForWire);
            System.out.println("segment to digits:" + segmentToDigits);
            determineWireToSegmentMapping(mapping, remainingWires, remainingSegments, possibleDigitsForWire);
            return mapping;
        }

        @SuppressWarnings("unchecked")
        private boolean determineWireToSegmentMapping(HashMap<Character, Character> mapping,
                TreeSet<Character> remainingWires, TreeSet<Character> remainingSegments,
                HashMap<Character, Set<Integer>> possibleDigitsForWire) {

            if (remainingWires.isEmpty() && remainingSegments.isEmpty())
                return true;

            for (Character wire : (Set<Character>) remainingWires.clone()) {
                remainingWires.remove(wire);
                Set<Integer> possibleWireDigits = possibleDigitsForWire.get(wire);
                for (Character segment : (Set<Character>) remainingSegments.clone()) {
                    remainingSegments.remove(segment);

                    // does this wire appear in any the digits that this segment does?
                    var segmentDigits = segmentToDigits.get(segment);
                    if (intersects(possibleWireDigits, segmentDigits)) {
                        mapping.put(wire, segment);
                        System.out.println("Trying " + mapping);
                        if (determineWireToSegmentMapping(mapping, remainingWires, remainingSegments,
                                possibleDigitsForWire))
                            return true;
                        mapping.remove(wire);
                    }
                    remainingSegments.add(segment);
                }
                remainingWires.add(wire);
            }

            return false;
        }

        public static <T> boolean intersects(Set<T> a, Set<T> b) {
            for (T i : a)
                if (b.contains(i))
                    return true;
            return false;
        }

        private HashMap<Character, Set<Integer>> findPossibleDigitsForWire() {
            HashMap<Character, Set<Integer>> out = new HashMap<>();
            for (Character c : "abcdefg".toCharArray())
                out.put(c, new HashSet<>());

            for (String pattern : signalPatterns) {
                for (Integer digit : digitToSegments.keySet()) {
                    if (pattern.length() == digitToSegments.get(digit).size()) {
                        for (Character c : pattern.toCharArray())
                            out.get(c).add(digit);
                    }
                }
            }

            return out;
        }

        public HashMap<Character, Character> determineWireToSegmentMapping() {
            HashMap<Integer, Set<Set<Character>>> possibleWiresForDigits = possibleWiresForDigits();

            ArrayList<Integer> digitsToMap = new ArrayList<>();
            digitsToMap.addAll(possibleWiresForDigits.keySet());

            var allowedSegmentsForWire = new HashMap<Character, Set<Character>>();
            for (Integer digit : digitToSegments.keySet()) {
                Set<Character> segments = digitToSegments.get(digit);
                Set<Set<Character>> wires = possibleWiresForDigits.get(digit);
                for (Set<Character> wireSet : wires) {
                    for (Character wire : wireSet) {
                        Set<Character> allowedSegments = allowedSegmentsForWire.get(wire);
                        if (allowedSegments == null) {
                            HashSet<Character> allowedSegmentsCopy = new HashSet<>();
                            allowedSegmentsCopy.addAll(segments);
                            allowedSegmentsForWire.put(wire, allowedSegmentsCopy);
                        } else {
                            allowedSegments.addAll(segments);
                        }
                    }
                }
            }

            HashMap<Character, Character> out = new HashMap<Character, Character>();
            determineWireToSegmentMapping(digitsToMap, out, possibleWiresForDigits);
            return out;
        }

        private boolean determineWireToSegmentMapping(ArrayList<Integer> digitsToMap,
                HashMap<Character, Character> wiresToSegments,
                HashMap<Integer, Set<Set<Character>>> possibleWiresForDigits) {
            if (wiresToSegments.size() == 7)
                return true;

            int nextDigitToMap = digitsToMap.remove(0);
            Set<Character> possibleSegments = digitToSegments.get(nextDigitToMap);
            System.out.println("mapping " + nextDigitToMap + " to " + possibleSegments);
            for (Set<Character> wireSet : possibleWiresForDigits.get(nextDigitToMap)) {
                if (determineWireToSegmentMapping(wireSet, possibleSegments, digitsToMap, wiresToSegments,
                        possibleWiresForDigits))
                    return true;
            }
            digitsToMap.add(0, nextDigitToMap);
            return false;
        }

        private boolean determineWireToSegmentMapping(Set<Character> wireSet, Set<Character> possibleSegments,
                ArrayList<Integer> digitsToMap, HashMap<Character, Character> wiresToSegments,
                HashMap<Integer, Set<Set<Character>>> possibleWiresForDigits) {
            if (wireSet.isEmpty())
                return determineWireToSegmentMapping(digitsToMap, wiresToSegments, possibleWiresForDigits);

            for (Character wire : wireSet) {
                for (Character segment : possibleSegments) {
                    Character currentWireMapping = wiresToSegments.get(wire);
                    boolean alreadyMapped = currentWireMapping != null;

                    // already mapped, and to something else
                    if (alreadyMapped && currentWireMapping != segment)
                        continue;

                    // something else mapped to this segment
                    if (wiresToSegments.containsValue(segment))
                        continue;

                    wiresToSegments.put(wire, segment);
                    Set<Character> unmappedWires = wireSet.stream().filter(w -> w != wire).collect(Collectors.toSet());
                    Set<Character> unmappedSegments = possibleSegments.stream().filter(s -> s != segment)
                            .collect(Collectors.toSet());
                    if (determineWireToSegmentMapping(unmappedWires, unmappedSegments, digitsToMap, wiresToSegments,
                            possibleWiresForDigits))
                        return true;
                    if (!alreadyMapped)
                        wiresToSegments.remove(wire);
                }
            }

            return false;
        }

        public void allAssignments() {
            HashMap<Set<Character>, Set<Set<Character>>> segmentsToPossibleWires = segmentsToPossibleWires();
            ArrayList<Set<Character>> unassigned = new ArrayList<Set<Character>>();
            unassigned.addAll(segmentsToPossibleWires.keySet());
            HashMap<Set<Character>, Set<Character>> wiresToSegments = new HashMap<>();
            allAssignments(unassigned, segmentsToPossibleWires, wiresToSegments);
        }

        private void allAssignments(List<Set<Character>> unassignedSegments,
                HashMap<Set<Character>, Set<Set<Character>>> segmentsToPossibleWires,
                HashMap<Set<Character>, Set<Character>> wiresToSegments) {

            if (unassignedSegments.isEmpty()) {
                System.out.println(wiresToSegments);
                return;
            }

            Set<Character> nextSegments = unassignedSegments.remove(0);
            for (Set<Character> wires : segmentsToPossibleWires.get(nextSegments)) {
                wiresToSegments.put(wires, nextSegments);
                allAssignments(unassignedSegments, segmentsToPossibleWires, wiresToSegments);
                wiresToSegments.remove(wires);
            }
            unassignedSegments.add(0, nextSegments);
        }

        public List<Set<Character>> patternsOfLength(int i) {
            return Arrays.stream(signalPatterns).filter(p -> p.length() == i).map(DisplaySample::toCharacterSet)
                    .toList();
        }

        static private Set<Character> notInAll(Set<Character> set1, Set<Character> set2, Set<Character> set3) {
            Stream<Character> notIn2or3 = set1.stream().filter(c -> !set2.contains(c) || !set3.contains(c));
            Stream<Character> notIn1or3 = set2.stream().filter(c -> !set1.contains(c) || !set3.contains(c));
            Stream<Character> notIn1or2 = set3.stream().filter(c -> !set1.contains(c) || !set2.contains(c));
            return Stream.concat(Stream.concat(notIn2or3, notIn1or3), notIn1or2).collect(Collectors.toSet());
        }

        static public HashSet<Character> setIntersection(Set<Character> set1, Set<Character> set2) {
            HashSet<Character> set1Copy = new HashSet<Character>();
            set1Copy.addAll(set1);
            set1Copy.removeIf(c -> !set2.contains(c));
            return set1Copy;
        }

        static public HashSet<Character> setSubtract(Set<Character> set1, Set<Character> set2) {
            HashSet<Character> set1Copy = new HashSet<Character>();
            set1Copy.addAll(set1);
            set1Copy.removeAll(set2);
            return set1Copy;
        }

        static public Character setSubtractSingle(Set<Character> set1, Set<Character> set2) {
            HashSet<Character> set1Copy = new HashSet<Character>();
            set1Copy.addAll(set1);
            set1Copy.removeAll(set2);
            assert set1Copy.size() == 1;
            return set1Copy.iterator().next();
        }

        public HashMap<Character, Character> getWireToSegmentAssignment() {
            HashMap<Integer, Set<Character>> allWiresForDigits = allWiresForDigits();
            HashMap<Character, Character> wireToSegment = new HashMap<>();
            Character wireForA = setSubtractSingle(allWiresForDigits.get(7), allWiresForDigits.get(1));
            HashSet<Character> wiresForCF = setIntersection(allWiresForDigits.get(7), allWiresForDigits.get(1));
            HashSet<Character> wiresForBD = setSubtract(allWiresForDigits.get(4), allWiresForDigits.get(1));
            HashSet<Character> wiresForAEG = setSubtract(allWiresForDigits.get(8), allWiresForDigits.get(4));
            HashSet<Character> wiresForEG = setSubtract(wiresForAEG, allWiresForDigits.get(7));
            List<Set<Character>> patternsFor069 = patternsOfLength(6);
            Set<Character> wiresForCED = notInAll(patternsFor069.get(0), patternsFor069.get(1), patternsFor069.get(2));
            Set<Character> wiresForCE = setSubtract(wiresForCED, wiresForBD);
            Character wireForD = setSubtractSingle(wiresForCED, wiresForCE);
            Character wireForB = setSubtractSingle(wiresForBD, Set.of(wireForD));
            Character wireForG = setSubtractSingle(wiresForEG, wiresForCE);
            Character wireForE = setSubtractSingle(wiresForEG, Set.of(wireForG));
            Character wireForF = setSubtractSingle(wiresForCF, wiresForCE);
            Character wireForC = setSubtractSingle(wiresForCF, Set.of(wireForF));
            wireToSegment.put(wireForA, 'a');
            wireToSegment.put(wireForB, 'b');
            wireToSegment.put(wireForC, 'c');
            wireToSegment.put(wireForD, 'd');
            wireToSegment.put(wireForE, 'e');
            wireToSegment.put(wireForF, 'f');
            wireToSegment.put(wireForG, 'g');
            return wireToSegment;
        }

        public static Set<Character> getSegments(HashMap<Character, Character> wireToSegmentAssignment,
                Set<Character> wires) {
            return wires.stream().map(w -> wireToSegmentAssignment.get(w)).collect(Collectors.toSet());
        }

        public static Integer getDigit(HashMap<Character, Character> wireToSegmentAssignment,
                Set<Character> wires) {
            return segmentsToDigit.get(getSegments(wireToSegmentAssignment, wires));
        }

        public int determineOutputValue() {
            HashMap<Character, Character> wireToSegmentAssignment = getWireToSegmentAssignment();
            Integer[] output = Arrays.stream(outputValue).map(DisplaySample::toCharacterSet)
                    .map(wires-> DisplaySample.getDigit(wireToSegmentAssignment, wires)).toArray(Integer[]::new);
            return output[0] * 1000 + output[1] * 100 + output[2] * 10 + output[3];
        }
    }

    public static void main(String[] args) {
        List<DisplaySample> samples = Common.parseInputFor("day8", DisplaySample::parse);
        System.out.println("Day 8 part 1: " + DisplaySample.numUniqueDigits(samples));

        long outputValueSum = samples.stream().mapToLong(DisplaySample::determineOutputValue).sum();
        System.out.println("Day 8 part 2: " + outputValueSum);
    }
}
