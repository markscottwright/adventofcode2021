from typing import Iterator


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
    with open("day2.dat") as f:
        part1_distance_travelled = distance_travelled_part1(parse(f))
        print("dat 2 part 1:", part1_distance_travelled[0] * part1_distance_travelled[1])

    with open("day2.dat") as f:
        part2_distance_travelled = distance_travelled_part2(parse(f))
        print("dat 2 part 2:", part2_distance_travelled[0] * part2_distance_travelled[1])


def parse(string_iterator: Iterator[str]) -> Iterator[tuple[str, int]]:
    return [(line.split()[0], int(line.split()[1]))
            for line in string_iterator if line.strip() != '']


if __name__ == '__main__':
    main()
