package adventofcode2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import adventofcode2021.Day11.DumboOctopuses;

public class Day11Test {

    @Test
    public void test() {
        DumboOctopuses dumboOctopuses = Day11.DumboOctopuses.parse("""
                11111
                19991
                19191
                19991
                11111
                """.split("\n"));
        dumboOctopuses.oneStep();
        assertThat(dumboOctopuses.toString()).isEqualTo("""
                34543
                40004
                50005
                40004
                34543
                """);

        DumboOctopuses dumboOctopuses2 = Day11.DumboOctopuses.parse("""
                5483143223
                2745854711
                5264556173
                6141336146
                6357385478
                4167524645
                2176841721
                6882881134
                4846848554
                5283751526
                """.split("\n"));

        for (int i = 0; i < 10; ++i)
            dumboOctopuses2.oneStep();
        assertThat(dumboOctopuses2.toString()).isEqualTo("""
                0481112976
                0031112009
                0041112504
                0081111406
                0099111306
                0093511233
                0442361130
                5532252350
                0532250600
                0032240000
                """);
        assertThat(dumboOctopuses2.getNumFlashes()).isEqualTo(204);
        for (int i = 10; i < 100; ++i)
            dumboOctopuses2.oneStep();
        assertThat(dumboOctopuses2.getNumFlashes()).isEqualTo(1656);
    }

}
