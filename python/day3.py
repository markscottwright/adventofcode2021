import common


def num_bits_set(all_numbers: list[str]) -> list[int]:
    bit_counts = [0] * len(all_numbers[0])
    for number in all_numbers:
        for i in range(len(number)):
            if number[i] == '1':
                bit_counts[i] = bit_counts[i] + 1
    return bit_counts


def num_bits_set_at_position(all_numbers: list[str], position: int) -> int:
    bit_count = 0
    for number in all_numbers:
        if number[position] == '1':
            bit_count = bit_count + 1
    return bit_count


def power_consumption(all_numbers: list[str]) -> int:
    all_numbers_len = len(all_numbers)
    counts = num_bits_set(all_numbers)
    gamma_rate = epsilon_rate = 0
    for count in counts:
        gamma_rate = gamma_rate << 1
        epsilon_rate = epsilon_rate << 1
        if count > all_numbers_len / 2:
            gamma_rate = gamma_rate + 1
        else:
            epsilon_rate = epsilon_rate + 1

    return gamma_rate * epsilon_rate


def life_support_rating(all_numbers: list[str]) -> int:
    oxygen_generator_numbers = all_numbers
    co2_scrubber_numbers = all_numbers

    for bit_position in range(len(all_numbers[0])):
        print(bit_position, len(oxygen_generator_numbers), len(co2_scrubber_numbers))
        if len(oxygen_generator_numbers) == 0 and len(co2_scrubber_numbers) == 0:
            break

        if len(oxygen_generator_numbers) > 1:
            # aka '1' is more common or same
            if num_bits_set_at_position(oxygen_generator_numbers, bit_position) >= len(oxygen_generator_numbers) / 2:
                oxygen_generator_numbers = [n for n in oxygen_generator_numbers if n[bit_position] == '1']
            else:
                oxygen_generator_numbers = [n for n in oxygen_generator_numbers if n[bit_position] == '0']

        if len(co2_scrubber_numbers) > 1:
            # aka '1' is more common or same
            if num_bits_set_at_position(co2_scrubber_numbers, bit_position) >= len(co2_scrubber_numbers) / 2:
                co2_scrubber_numbers = [n for n in co2_scrubber_numbers if n[bit_position] == '0']
            else:
                co2_scrubber_numbers = [n for n in co2_scrubber_numbers if n[bit_position] == '1']

    return int(oxygen_generator_numbers[0], 2) * int(co2_scrubber_numbers[0], 2)


def main():
    print("day 3 part 1:", power_consumption(common.lines_in_file("day3.dat")))
    print("day 3 part 2:", life_support_rating(common.lines_in_file("day3.dat")))


if __name__ == '__main__':
    main()