package adventofcode2021;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.Test;

import adventofcode2021.Day6.FishCohorts;

public class Day6Test {

    @Test
    public void test() {
        FishCohorts initialFish = FishCohorts.parse("3,4,3,1,2");
        assertThat(initialFish.totalFish()).isEqualTo(BigInteger.valueOf(5));

        FishCohorts day18FishCohorts = initialFish.reproduceForDays(18);
        assertThat(day18FishCohorts.totalFish()).isEqualTo(BigInteger.valueOf(26));

        FishCohorts day80FishCohorts = initialFish.reproduceForDays(80);
        assertThat(day80FishCohorts.totalFish()).isEqualTo(BigInteger.valueOf(5934));
    }

}
