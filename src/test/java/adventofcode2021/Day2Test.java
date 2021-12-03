package adventofcode2021;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import adventofcode2021.Day2.Position;

public class Day2Test {
	final static String SAMPLE = """
			forward 5
			down 5
			forward 8
			up 3
			down 8
			forward 2
			""";

	@Test
	public void testPart1() {
		var commands = Arrays.stream(SAMPLE.split("\\n")).map(Day2.SubmarineCommand::parse)
				.collect(Collectors.toList());
		Position position = Day2.movePart1(commands);
		assertThat(150).isEqualTo(position.x * position.y);
	}

	@Test
	public void testPart2() throws Exception {
		var commands = Arrays.stream(SAMPLE.split("\\n")).map(Day2.SubmarineCommand::parse)
				.collect(Collectors.toList());
		Position position = Day2.movePart2(commands);
		assertThat(900).isEqualTo(position.x * position.y);
	}
}
