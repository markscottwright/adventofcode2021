package adventofcode2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
    static class SyntaxAnalysis {
        @Override
        public String toString() {
            return "Error [incorrect=" + incorrect + ", pos=" + pos + "]";
        }

        // any may be null
        private Character incorrect;
        private int pos;
        private List<Character> toComplete;

        public SyntaxAnalysis(Character incorrect, int pos) {
            this.incorrect = incorrect;
            this.pos = pos;
        }

        public SyntaxAnalysis(List<Character> list) {
            this.toComplete = list;
        }

        boolean isError() {
            return incorrect != null;
        }

        boolean isIncomplete() {
            return toComplete != null;
        }

        public long points() {
            if (isError()) {
                assert incorrect != null;
                if (incorrect == ')') {
                    return 3;
                } else if (incorrect == ']') {
                    return 57;
                } else if (incorrect == '}') {
                    return 1197;
                } else {
                    assert incorrect == '>';
                    return 25137;
                }
            } else {
                long totalScore = 0;
                for (Character c : toComplete) {
                    totalScore *= 5;
                    if (c == ')')
                        totalScore += 1;
                    else if (c == ']')
                        totalScore += 2;
                    else if (c == '}')
                        totalScore += 3;
                    else if (c == '>')
                        totalScore += 4;
                    else
                        assert false;
                }
                return totalScore;
            }
        }
    }

    static SyntaxAnalysis parse(String line) {
        ArrayList<Character> stack = new ArrayList<Character>();
        for (int pos = 0; pos < line.toCharArray().length; pos++) {
            Character c = line.toCharArray()[pos];
            switch (c) {
            case '[':
            case '(':
            case '{':
            case '<': {
                stack.add(c);
                break;
            }
            case ']': {
                Character prev = pop(stack);
                if (prev != '[')
                    return new SyntaxAnalysis(c, pos);
                break;
            }
            case ')': {
                Character prev = pop(stack);
                if (prev != '(')
                    return new SyntaxAnalysis(c, pos);
                break;
            }
            case '}': {
                Character prev = pop(stack);
                if (prev != '{')
                    return new SyntaxAnalysis(c, pos);
                break;
            }
            case '>': {
                Character prev = pop(stack);
                if (prev != '<')
                    return new SyntaxAnalysis(c, pos);
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + c);
            }
        }

        Collections.reverse(stack);
        ;
        return new SyntaxAnalysis(
                stack.stream().map(c -> c == '<' ? '>' : c == '(' ? ')' : c == '[' ? ']' : '}').toList());
    }

    private static Character pop(ArrayList<Character> stack) {
        assert !stack.isEmpty();
        return stack.remove(stack.size() - 1);
    }

    public static void main(String[] args) {
        List<String> syntaxLines = Common.inputAsListFor("day10");
        long errorPointsSum = syntaxLines.stream().map(Day10::parse).filter(SyntaxAnalysis::isError)
                .mapToLong(SyntaxAnalysis::points).sum();
        System.out.println("Day 10 part 1: " + errorPointsSum);

        //@formatter:off
        ArrayList<Long> completionPoints = syntaxLines.stream()
                .map(Day10::parse)
                .filter(SyntaxAnalysis::isIncomplete)
                .map(SyntaxAnalysis::points)
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.sort(completionPoints);
        long middleCompletionPoints = completionPoints.get(completionPoints.size()/2);
        //@formatter:on

        System.out.println("Day 10 part 2: " + middleCompletionPoints);

    }
}
