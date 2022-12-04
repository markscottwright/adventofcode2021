package adventofcode2021;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import adventofcode2021.Day8.DisplaySample;

public class Day8Test {
    static String sampleInput = """
            be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
            edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
            fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
            fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
            aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
            fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
            dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
            bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
            egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
            gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
            """;

    @Test
    public void testPartOne() {
        List<DisplaySample> samples = Arrays.stream(sampleInput.split("\n")).map(DisplaySample::parse).toList();
        Assertions.assertThat(DisplaySample.numUniqueDigits(samples)).isEqualTo(26);

    }

    @Test
    public void testPartTwo() {
        DisplaySample sample = DisplaySample
                .parse("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf");
        int outputValue = sample.determineOutputValue();
        Assertions.assertThat(outputValue).isEqualTo(5353);

        long inputDisplayTotal = Arrays.stream(sampleInput.split("\n")).map(DisplaySample::parse).mapToLong(DisplaySample::determineOutputValue).sum();
        Assertions.assertThat(inputDisplayTotal).isEqualTo(61229);
    }
}
