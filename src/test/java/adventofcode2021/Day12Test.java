package adventofcode2021;

import java.util.ArrayList;
import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import adventofcode2021.Day12.CaveMap;

public class Day12Test {

    @Test
    public void testPart1Sample1() {
        CaveMap caveMap = Day12.CaveMap.parse("""
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end
                """.split("\n"));
        HashSet<ArrayList<String>> allPaths = caveMap.findAllPaths();
        Assertions.assertThat(allPaths).hasSize(10);
    }

    @Test
    public void testPart1Sample2() {
        CaveMap caveMap = Day12.CaveMap.parse("""
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
                """.split("\n"));
        HashSet<ArrayList<String>> allPaths = caveMap.findAllPaths();
        Assertions.assertThat(allPaths).hasSize(19);
    }

    @Test
    public void testPart1Sample3() {
        CaveMap caveMap = Day12.CaveMap.parse("""
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW
                """.split("\n"));
        HashSet<ArrayList<String>> allPaths = caveMap.findAllPaths();
        Assertions.assertThat(allPaths).hasSize(226);
    }

    @Test
    public void testPart2Sample1() {
        CaveMap caveMap = Day12.CaveMap.parse("""
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end
                """.split("\n"));
        HashSet<ArrayList<String>> allPaths = caveMap.findAllPathsWithSecondVisitsAllowed();
        Assertions.assertThat(allPaths).hasSize(36);
    }

    @Test
    public void testPart2Sample2() {
        CaveMap caveMap = Day12.CaveMap.parse("""
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
                """.split("\n"));
        HashSet<ArrayList<String>> allPaths = caveMap.findAllPathsWithSecondVisitsAllowed();
        Assertions.assertThat(allPaths).hasSize(103);
    }
}
