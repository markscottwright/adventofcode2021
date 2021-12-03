package adventofcode2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Day3Test {
	public final static String SAMPLE_DATA = """
			00100
			11110
			10110
			10111
			10101
			01111
			00111
			11100
			10000
			11001
			00010
			01010
			""";

	@Test
	public void testPowerConsumption() {
		String[] lines = SAMPLE_DATA.split("\n");
		assertThat(198).isEqualTo(Day3.powerConsumption(lines));
	}
	
	@Test
	public void testLifeSupport() {
		String[] lines = SAMPLE_DATA.split("\n");
		assertThat(230).isEqualTo(Day3.lifeSuport(lines));
	}
	
	@Test
	public void testNumBitsSetAtPosition() {
		assertThat(1).isEqualTo(Day3.numBitsSetAtPostion(new String[] {"101"}, 0));
		assertThat(1).isEqualTo(Day3.numBitsSetAtPostion(new String[] {"101", "001"}, 0));
		assertThat(0).isEqualTo(Day3.numBitsSetAtPostion(new String[] {"101", "001"}, 1));
		assertThat(2).isEqualTo(Day3.numBitsSetAtPostion(new String[] {"101", "001"}, 2));
	}
}
