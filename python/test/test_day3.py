import common
import day3

SAMPLE = """
    00100
    11110
    10110
    10111
    10101
    01111
    00111
    11100
    10000
    11001
    00010
    01010"""


def test_num_bits_set():
    assert [1, 0, 1] == day3.num_bits_set(["101"])
    assert [1, 0, 1] == day3.num_bits_set(["101",
                                           "000"])
    assert [2, 0, 1] == day3.num_bits_set(["101",
                                           "100"])


def test_num_bits_set_at_position():
    assert 1 == day3.num_bits_set_at_position(["101"], 0)
    assert 0 == day3.num_bits_set_at_position(["101"], 1)
    assert 1 == day3.num_bits_set_at_position(["101"], 2)
    assert 1 == day3.num_bits_set_at_position(["101",
                                               "001"], 0)
    assert 0 == day3.num_bits_set_at_position(["101",
                                               "001"], 1)
    assert 2 == day3.num_bits_set_at_position(["101",
                                               "001"], 2)


def test_life_support_rating():
    assert 230 == day3.life_support_rating(common.lines_in_string(SAMPLE))


def test_power_consumption():
    assert 198 == day3.power_consumption(list(common.lines_in_string(SAMPLE)))
