import day1
from day1 import num_measurement_increases, parse


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


def test_num_measurement_increases():
    assert num_measurement_increases(parse(SAMPLE.splitlines())) == 7


def test_window_of_3_sums():
    assert num_measurement_increases(day1.window_of_3_sums(parse(SAMPLE.splitlines()))) == 5
