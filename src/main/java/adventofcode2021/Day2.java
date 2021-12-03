package adventofcode2021;

import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
	static public class Position {
		final long x;
		final long y;

		public Position(long x, long y) {
			this.x = x;
			this.y = y;
		}
	}

	static public class SubmarineCommand {
		private Direction direction;
		private int distance;

		enum Direction {
			forward, down, up
		}

		public SubmarineCommand(Direction direction, int distance) {
			this.direction = direction;
			this.distance = distance;
		}

		static SubmarineCommand parse(String line) {
			String[] directionAndDistance = line.split("\\s+");
			return new SubmarineCommand(Direction.valueOf(directionAndDistance[0]),
					Integer.valueOf(directionAndDistance[1]));
		}
	}

	public static void main(String[] args) {
		var commands = Common.linesInInputToList("day2").stream().map(l -> SubmarineCommand.parse(l))
				.collect(Collectors.toList());

		Position position = movePart1(commands);
		System.out.println("Day 2 part 1: " + (position.x * position.y));
		position = movePart2(commands);
		System.out.println("Day 2 part 2: " + (position.x * position.y));
	}

	public static Position movePart2(List<SubmarineCommand> commands) {
		long aim = 0;
		var position = new Position(0, 0);
		for (var command : commands) {
			switch (command.direction) {
			case forward:
				position = new Position(position.x + command.distance, position.y + command.distance * aim);
				break;
			case down:
				aim += command.distance;
				break;
			case up:
				aim -= command.distance;
				break;
			}
		}
		return position;
	}

	public static Position movePart1(List<SubmarineCommand> commands) {
		Position position = new Position(0, 0);
		for (var command : commands) {
			position = switch (command.direction) {
			case forward -> new Position(position.x + command.distance, position.y);
			case down -> new Position(position.x, position.y + command.distance);
			case up -> new Position(position.x, position.y - command.distance);
			};
		}
		return position;
	}

}
