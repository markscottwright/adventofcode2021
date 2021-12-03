from day2 import distance_travelled_part1, parse, distance_travelled_part2

SAMPLE: str = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2"""


def test_distance_travelled_part1():
    sample_distance_travelled_part1 = distance_travelled_part1(parse(SAMPLE.splitlines()))
    assert 150 == sample_distance_travelled_part1[0] * sample_distance_travelled_part1[1]


def test_distance_travelled_part2():
    sample_distance_travelled_part2 = distance_travelled_part2(parse(SAMPLE.splitlines()))
    assert 900 == sample_distance_travelled_part2[0] * sample_distance_travelled_part2[1]
