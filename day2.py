from typing import Iterator

SAMPLE = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2"""


def distance_travelled_part1(direction_and_distances: Iterator[tuple[str, int]]) -> tuple[int, int]:
    x = y = 0
    for direction, distance in direction_and_distances:
        if direction == 'forward':
            x += distance
        elif direction == 'down':
            y += distance
        else:
            assert direction == 'up'
            y -= distance

    return x, y


def distance_travelled_part2(direction_and_distances: Iterator[tuple[str, int]]) -> tuple[int, int]:
    aim = x = y = 0
    for direction, distance in direction_and_distances:
        if direction == 'forward':
            x += distance
            y += aim * distance
        elif direction == 'down':
            aim += distance
        else:
            assert direction == 'up'
            aim -= distance

    return x, y


def main():
    sample_distance_travelled_part1 = distance_travelled_part1(parse(SAMPLE.splitlines()))
    assert 150 == sample_distance_travelled_part1[0] * sample_distance_travelled_part1[1]
    sample_distance_travelled_part2 = distance_travelled_part2(parse(SAMPLE.splitlines()))
    assert 900 == sample_distance_travelled_part2[0] * sample_distance_travelled_part2[1]

    part1_distance_travelled = distance_travelled_part1(parse(open("day2.dat")))
    print("dat 2 part 1:", part1_distance_travelled[0] * part1_distance_travelled[1])

    part2_distance_travelled = distance_travelled_part2(parse(open("day2.dat")))
    print("dat 2 part 2:", part2_distance_travelled[0] * part2_distance_travelled[1])


def parse(string_iterator: Iterator[str]) -> Iterator[tuple[str, int]]:
    return [(line.split()[0], int(line.split()[1]))
            for line in string_iterator if line.strip() != '']


if __name__ == '__main__':
    main()
