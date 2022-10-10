package adventofcode2021;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import adventofcode2021.Day5.Line;

public class Day5Test {

    @Test
    public void testPart1() {
        String input = """
                0,9 -> 5,9
                8,0 -> 0,8
                9,4 -> 3,4
                2,2 -> 2,1
                7,0 -> 7,4
                6,4 -> 2,0
                0,9 -> 2,9
                3,4 -> 1,4
                0,0 -> 8,8
                5,5 -> 8,2""";
        List<Line> lines = Arrays.stream(input.split("\n")).map(Line::parse).filter(l -> l.isHorizontal() || l.isVertical()).toList();


        Assertions.assertThat(Line.getIntersectingPoints(lines)).hasSize(5);
    }

    @Test
    public void testPart2() {
        String input = """
                0,9 -> 5,9
                8,0 -> 0,8
                9,4 -> 3,4
                2,2 -> 2,1
                7,0 -> 7,4
                6,4 -> 2,0
                0,9 -> 2,9
                3,4 -> 1,4
                0,0 -> 8,8
                5,5 -> 8,2""";
        List<Line> lines = Arrays.stream(input.split("\n")).map(Line::parse).toList();


        Assertions.assertThat(Line.getIntersectingPoints(lines)).hasSize(12);
    }
}
