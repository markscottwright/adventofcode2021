from typing import Iterator


def lines_in_string(multiline_string: str) -> list[str]:
    """
    split multiline_string, remove empty lines and whitespace around each line
    :param multiline_string:
    :return: itertor of strings
    """
    return [l.strip() for l in multiline_string.splitlines() if l != "" and not l.isspace()]


def lines_in_file(filename: str) -> list[str]:
    """
    return lines in filename, with empty lines and surrounding whitespace removed
    :param filename:
    :return:
    """
    with open(filename) as f:
        return [l.strip() for l in f.readlines() if l != "" and not l.isspace()]