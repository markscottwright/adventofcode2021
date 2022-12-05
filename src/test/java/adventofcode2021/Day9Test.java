package adventofcode2021;

import java.util.ArrayList;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import adventofcode2021.Day9.HeightMap;

public class Day9Test {
    static String input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
            """;

    @Test
    public void test() {
        HeightMap map = HeightMap.parse(input.split("\n"));
        Set<Point> minima = map.findMinima();
        int riskValueSum = map.getRiskValueSum(minima);
        Assertions.assertThat(riskValueSum).isEqualTo(15);
    }

    @Test
    public void testPart2() throws Exception {
        HeightMap map = HeightMap.parse(input.split("\n"));
        ArrayList<Set<Point>> basins = new ArrayList<Set<Point>>();
        map.findMinima().stream().map(map::findBasinFor).forEach(basins::add);
        basins.sort((basin1, basin2) -> -Integer.compare(basin1.size(), basin2.size()));
        int threeLargest = basins.get(0).size() * basins.get(1).size() * basins.get(2).size();
        Assertions.assertThat(threeLargest).isEqualTo(1134);
    }

}
