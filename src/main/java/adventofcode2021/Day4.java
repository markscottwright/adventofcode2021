package adventofcode2021;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

public class Day4 {

	static public class BingoBoard {
		int[][] squares;

		static public BingoBoard parse(String inputString) {
			final var rows = inputString.split("\n");

			final var bingoBoard = new BingoBoard();
			bingoBoard.squares = new int[rows.length][];

			for (var i = 0; i < rows.length; ++i) {
				// get rid of any leading/trailing whitespace
				rows[i] = rows[i].strip();
				final var columns = rows[i].split("\\s+");
				assert columns.length == rows.length;
				bingoBoard.squares[i] = new int[columns.length];
				for (var j = 0; j < rows.length; ++j) {
					bingoBoard.squares[i][j] = Integer.valueOf(columns[j]);
				}
			}

			return bingoBoard;
		}

		public static Optional<BingoBoard> findWinner(List<BingoBoard> boards, Set<Integer> calledNumbers) {
			for (final BingoBoard board : boards) {
				if (board.isWinner(calledNumbers)) {
					return Optional.of(board);
				}
			}
			return Optional.empty();
		}

		private boolean isWinner(Set<Integer> calledNumbers) {
			final var n = squares.length;
			for (var i = 0; i < n; ++i) {
				if (rowIsWinner(i, calledNumbers) || columnIsWinner(i, calledNumbers)) {
					return true;
				}
			}
			return false;
		}

		private boolean rowIsWinner(int columnNumber, Set<Integer> calledNumbers) {
			for (final int[] square : squares) {
				if (!calledNumbers.contains(square[columnNumber])) {
					return false;
				}
			}
			return true;
		}

		private boolean columnIsWinner(int rowNumber, Set<Integer> calledNumbers) {
			for (var i = 0; i < squares.length; ++i) {
				if (!calledNumbers.contains(squares[rowNumber][i])) {
					return false;
				}
			}
			return true;
		}

		public static long findFirstWinner(List<BingoBoard> boards, List<Integer> calledNumbers) {
			final Set<Integer> calledSoFar = new HashSet<>();
			for (final Integer calledNumber : calledNumbers) {
				calledSoFar.add(calledNumber);
				final var winner = BingoBoard.findWinner(boards, calledSoFar);
				if (winner.isPresent()) {
					return calledNumber * winner.get().sumOfAllUnmarked(calledSoFar);
				}
			}

			throw new RuntimeException("No winner found");
		}

		private long sumOfAllUnmarked(Set<Integer> calledSoFar) {
			var unmarkedSum = 0L;

			final var n = squares.length;
			for (var i = 0; i < n; ++i) {
				for (var j = 0; j < n; ++j) {
					if (!calledSoFar.contains(squares[i][j])) {
						unmarkedSum += squares[i][j];
					}
				}
			}

			return unmarkedSum;
		}

		public static long findLastWinner(List<BingoBoard> boards, List<Integer> calledNumbers) {
			final var calledSoFar = new HashSet<Integer>();
			final var nonWinners = new HashSet<BingoBoard>(boards);
			for (final Integer calledNumber : calledNumbers) {
				calledSoFar.add(calledNumber);
				if (nonWinners.size() == 1) {
					final var lastBoard = nonWinners.iterator().next();
					if (lastBoard.isWinner(calledSoFar)) {
						return calledNumber * lastBoard.sumOfAllUnmarked(calledSoFar);
					}
				} else {
					nonWinners.removeIf(b -> b.isWinner(calledSoFar));
				}
			}

			throw new RuntimeException("No winner found");
		}
	}

	public static void main(String[] args) {
		try {
			final var input = new String(Day4.class.getResourceAsStream("/day4.dat").readAllBytes()).split("\n\n");
			final var calledNumbers = Arrays.stream(input[0].split(",")).map(Integer::valueOf).collect(toList());
			final var boards = range(1, input.length - 1).mapToObj(i -> BingoBoard.parse(input[i])).collect(toList());

			System.out.println("Day 4 part 1: " + BingoBoard.findFirstWinner(boards, calledNumbers));
			System.out.println("Day 4 part 1: " + BingoBoard.findLastWinner(boards, calledNumbers));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
