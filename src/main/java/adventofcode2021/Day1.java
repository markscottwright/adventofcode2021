package adventofcode2021;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

	static long numMeasurementIncreases(List<Integer> measurements) {
		long count = 0;
		for (int i = 1; i < measurements.size(); i++) {
			if (measurements.get(i - 1) < measurements.get(i))
				count++;
		}
		return count;
	}

	static List<Integer> toWindowOfThreeSums(List<Integer> measurements) {
		var sums = new ArrayList<Integer>();
		for (int i = 2; i < measurements.size(); i++) {
			sums.add(measurements.get(i - 2) + measurements.get(i - 1) + measurements.get(i));
		}
		return sums;
	}

	public static void main(String[] args) {
		System.out.println("Day 1 part 1: " + numMeasurementIncreases(
				Common.listOfInputLinesFor("day1").stream().map(Integer::valueOf).collect(Collectors.toList())));
		System.out.println("Day 1 part 2: " + numMeasurementIncreases(toWindowOfThreeSums(
				Common.listOfInputLinesFor("day1").stream().map(Integer::valueOf).collect(Collectors.toList()))));
	}

}
