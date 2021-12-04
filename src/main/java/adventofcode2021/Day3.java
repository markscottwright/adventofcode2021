package adventofcode2021;

import static adventofcode2021.Common.inputAsArrayFor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day3 {

	public static long lifeSuport(String[] numbers) {
		String[] oxygenGeneratorNumbers = numbers;
		String[] co2ScrubberNumbers = numbers;

		for (int bitPosition = 0; bitPosition < numbers[0].length(); ++bitPosition) {
			final int safeBitPosition = bitPosition;
			if (oxygenGeneratorNumbers.length > 1) {
				// note that if the array length is odd, then len/2 is less than half. "most"
				// means one more than len/2 in that case
				if (numBitsSetAtPostion(oxygenGeneratorNumbers,
						safeBitPosition) >= (oxygenGeneratorNumbers.length / 2 + oxygenGeneratorNumbers.length % 2)) {
					oxygenGeneratorNumbers = Arrays.stream(oxygenGeneratorNumbers)
							.filter(n -> n.charAt(safeBitPosition) == '1').toArray(String[]::new);
				} else {
					oxygenGeneratorNumbers = Arrays.stream(oxygenGeneratorNumbers)
							.filter(n -> n.charAt(safeBitPosition) == '0').toArray(String[]::new);
				}
			}
			if (co2ScrubberNumbers.length > 1) {
				// note that if the array length is odd, then len/2 is less than half. "most"
				// means one more than len/2 in that case
				if (numBitsSetAtPostion(co2ScrubberNumbers,
						safeBitPosition) >= (co2ScrubberNumbers.length / 2 + co2ScrubberNumbers.length % 2)) {
					co2ScrubberNumbers = Arrays.stream(co2ScrubberNumbers).filter(n -> n.charAt(safeBitPosition) == '0')
							.toArray(String[]::new);
				} else {
					co2ScrubberNumbers = Arrays.stream(co2ScrubberNumbers).filter(n -> n.charAt(safeBitPosition) == '1')
							.toArray(String[]::new);
				}
			}
		}

		return Long.valueOf(oxygenGeneratorNumbers[0], 2) * Long.valueOf(co2ScrubberNumbers[0], 2);
	}

	static public int[] numBitsSet(String[] allNumbers) {
		int[] bitCounts = new int[allNumbers[0].length()];
		for (String number : allNumbers) {
			for (int i = 0; i < number.length(); i++) {
				if (number.charAt(i) == '1')
					bitCounts[i] += 1;
			}
		}
		return bitCounts;
	}

	public static int numBitsSetAtPostion(String[] numbers, int bitPosition) {
		int bitCount = 0;
		for (String number : numbers)
			if (number.charAt(bitPosition) == '1')
				bitCount += 1;
		return bitCount;
	}

	public static long powerConsumption(String[] lines) {
		int[] numBitsSet = numBitsSet(lines);
		String gammaRateString = Arrays.stream(numBitsSet).mapToObj(n -> n >= lines.length / 2)
				.map(mostCommon -> mostCommon ? "1" : "0").collect(Collectors.joining());
		String epsilonRateString = "";
		for (char c : gammaRateString.toCharArray())
			epsilonRateString += c == '1' ? "0" : "1";
		long powerConsumption = Long.valueOf(gammaRateString, 2) * Long.valueOf(epsilonRateString, 2);
		return powerConsumption;
	}

	public static void main(String[] args) {
		System.out.println("Day 3 part 1: " + powerConsumption(inputAsArrayFor("day3")));
		System.out.println("Day 3 part 2: " + lifeSuport(inputAsArrayFor("day3")));
	}
}
