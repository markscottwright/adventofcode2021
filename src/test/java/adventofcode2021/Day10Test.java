package adventofcode2021;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.junit.Test;

import adventofcode2021.Day10.SyntaxAnalysis;

public class Day10Test {

    @Test
    public void test() {
        var input = """
                [({(<(())[]>[[{[]{<()<>>
                [(()[<>])]({[<{<<[]>>(
                {([(<{}[<>[]}>{[]{[(<()>
                (((({<>}<{<{<>}{[]{[]{}
                [[<[([]))<([[{}[[()]]]
                [{[{({}]{}}([{[{{{}}([]
                {<[[]]>}<{[{[{[]{()[[[]
                [<(<(<(<{}))><([]([]()
                <{([([[(<>()){}]>(<<{{
                <{([{{}}[<[[[<>{}]]]>[]]
                """;

        long errorPointsSum = Arrays.stream(input.split("\n")).map(Day10::parse).filter(SyntaxAnalysis::isError)
                .mapToLong(SyntaxAnalysis::points).sum();
        assertThat(errorPointsSum).isEqualTo(26397);

        //@formatter:off
        ArrayList<Long> completionPoints = Arrays.stream(input.split("\n"))
                .map(Day10::parse)
                .filter(SyntaxAnalysis::isIncomplete)
                .map(SyntaxAnalysis::points)
                .collect(Collectors.toCollection(ArrayList::new));
        //@formatter:on
        Collections.sort(completionPoints);
        long middleScore = completionPoints.get(completionPoints.size() / 2);
        assertThat(middleScore).isEqualTo(288957);
    }

}
