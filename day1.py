SAMPLE = """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263"""


def parse(input_lines: list[str]) -> list[int]:
    return [int(s) for s in input_lines if not s.isspace() and s != '']


def window_of_3_sums(measurements: list[int]) -> list[int]:
    return [measurements[i - 2] + measurements[i - 1] + measurements[i]
            for i in range(2, len(measurements))]


def num_measurement_increases(measurements: list[int]):
    """return number of times the next element is larger"""
    return sum(measurements[i - 1] < measurements[i] for i in range(1, len(measurements)))


def main():
    assert num_measurement_increases(parse(SAMPLE.splitlines())) == 7
    assert num_measurement_increases(window_of_3_sums(parse(SAMPLE.splitlines()))) == 5

    with open("day1.dat") as f:
        print("day 1: part 1", num_measurement_increases(parse(f.readlines())))
    with open("day1.dat") as f:
        print("day 1: part 2", num_measurement_increases(window_of_3_sums(parse(f.readlines()))))


if __name__ == '__main__':
    main()
