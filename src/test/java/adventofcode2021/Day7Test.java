package adventofcode2021;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import adventofcode2021.Day7.CrabFleet;

public class Day7Test {

    @Test
    public void testPart1() {
        CrabFleet fleet = CrabFleet.parse("16,1,2,0,4,2,7,1,2,14");

        Assertions.assertThat(fleet.costToMoveToWithConstantBurnRate(2)).isEqualTo(37);
        Assertions.assertThat(fleet.costToMoveToWithConstantBurnRate(1)).isEqualTo(41);
        Assertions.assertThat(fleet.costToMoveToWithConstantBurnRate(3)).isEqualTo(39);
        Assertions.assertThat(fleet.costToMoveToWithConstantBurnRate(10)).isEqualTo(71);

        Assertions.assertThat(fleet.findCheapestCostWithConstantBurnRate()).isEqualTo(37);
    }

    @Test
    public void testPart2() {
        CrabFleet fleet = CrabFleet.parse("16,1,2,0,4,2,7,1,2,14");

        Assertions.assertThat(fleet.costToMoveToWithIncreasingBurnRate(2)).isEqualTo(206);

        Assertions.assertThat(fleet.findCheapestCostWithIncreasingBurnRate()).isEqualTo(168);
    }
}
