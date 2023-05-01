import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("""
                ---------------------------------Calculator---------------------------------
                Enter an arithmetic expression in following form (a + b, a - b, a * b, a / b).
                Numbers cannot be greater than 10 and less than 1.
                Both roman and arabic numerals can be used.
                Input:""");

        String line = new String();
        Scanner scanner = new Scanner(System.in);
        line = scanner.nextLine();
        System.out.println("Result:");
        System.out.println(calc(line));
        System.out.println("----------------------------------------------------------------------------");
    }

    public static String calc(String input) {
        String result = new String();
        input = removeSpaces(input);
        try {
            checkFormat(input);
            boolean romanNumbersFlag = false;
            char operand;

            char[] chars = input.toCharArray();
            int operandIndex = -1;
            for (int i = 0; i < chars.length; i++) {
                if (checkIfOperand(chars[i])) {
                    operandIndex = i;
                }

            }

            operand = chars[operandIndex];
            chars[operandIndex] = '\n';

            String tmp = new String(chars);

            Scanner scanner = new Scanner(tmp);

            int a = 0;
            int b = 0;

            String line1 = scanner.nextLine();
            Scanner lineScanner = new Scanner(line1);
            if (lineScanner.hasNextInt()) {
                a = lineScanner.nextInt();

            } else if (lineScanner.hasNext()) {
                String tmp1 = lineScanner.next();
                if (checkIfRomanNumber(tmp1)) {
                    a = romanToInt(tmp1);
                    romanNumbersFlag = true;
                }
            }

            line1 = scanner.nextLine();
            lineScanner = new Scanner(line1);
            if (lineScanner.hasNextInt() && !romanNumbersFlag) {
                b = lineScanner.nextInt();
            } else if (lineScanner.hasNext() && romanNumbersFlag) {
                String tmp1 = lineScanner.next();
                if (checkIfRomanNumber(tmp1)) {
                    b = romanToInt(tmp1);
                }
            }
            int resultInt = -1;
            switch (operand) {
                case '+' -> resultInt = MathOperations.sum(a, b);
                case '-' -> resultInt = MathOperations.subtract(a, b);
                case '/' -> resultInt = MathOperations.divide(a, b);
                case '*' -> resultInt = MathOperations.multiply(a, b);
            }
            ;

            if (romanNumbersFlag) {
                if (resultInt < 1) throw new IllegalArgumentException("Invalid arguments! Check your input.");
                result = intToRoman(resultInt);
            } else {
                result = Integer.toString(resultInt);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static void checkFormat(String input) {
        char[] chars = input.toCharArray();
        int operandsCounter = 0;
        int operandIndex = -1;
        for (int i = 0; i < chars.length; i++) {
            if (checkIfOperand(chars[i])) {
                operandsCounter++;
                operandIndex = i;
            }

        }
        if (operandsCounter != 1) throw new IllegalArgumentException("Error! Invalid input.");

        chars[operandIndex] = '\n';

        String tmp = new String(chars);

        Scanner scanner = new Scanner(tmp);

        boolean romanNumbersFlag = false;
        int a, b;

        String line = scanner.nextLine();
        Scanner lineScanner = new Scanner(line);
        if (lineScanner.hasNextInt()) {
            a = lineScanner.nextInt();
            if (!((a >= 1) && (a <= 10))) throw new IllegalArgumentException("Invalid first argument! Arguments cannot be greater than 10 and less than 1. Check your input.");
        } else if (lineScanner.hasNext()) {
            String tmp1 = lineScanner.next();
            if (checkIfRomanNumber(tmp1)) {
                a = romanToInt(tmp1);
                if (!((a >= 1) && (a <= 10)))
                    throw new IllegalArgumentException("Invalid first argument! Arguments cannot be greater than 10 and less than 1. Check your input.");
                romanNumbersFlag = true;
            } else {
                throw new IllegalArgumentException("Invalid first argument! Check your input.");
            }
        } else {
            throw new IllegalArgumentException("Invalid first argument! Check your input.");
        }

        line = scanner.nextLine();
        lineScanner = new Scanner(line);
        if (lineScanner.hasNextInt() && !romanNumbersFlag) {
            b = lineScanner.nextInt();
            if (!((b >= 1) && (b <= 10))) throw new IllegalArgumentException("Invalid second argument! Arguments cannot be greater than 10 and less than 1. Check your input.");
        } else if (lineScanner.hasNext() && romanNumbersFlag) {
            String tmp1 = lineScanner.next();
            if (checkIfRomanNumber(tmp1)) {
                b = romanToInt(tmp1);
                if (!((b >= 1) && (b <= 10)))
                    throw new IllegalArgumentException("Invalid second argument! Arguments cannot be greater than 10 and less than 1. Check your input.");
            } else {
                throw new IllegalArgumentException("Invalid second argument! Check your input.");
            }
        } else {
            throw new IllegalArgumentException("Invalid second argument! Check your input.");
        }
    }

    public static boolean checkIfRomanNumber(String line) {
        String[] romanNumbers = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (int i = 0; i < romanNumbers.length; i++) {
            if (line.equals(romanNumbers[i])) return true;
        }
        return false;
    }

    public static int romanToInt(String line) {
        return switch (line) {
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            case "VI" -> 6;
            case "VII" -> 7;
            case "VIII" -> 8;
            case "IX" -> 9;
            case "X" -> 10;
            default -> 0;
        };
    }

    public static String intToRoman(int num) {

        int[] ints = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumbers = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder numeric = new StringBuilder();
        int bitDepth;
        int index = 0;
        while (index < ints.length) {
            bitDepth = num / ints[index];
            for (int i = 0; i < bitDepth; i++) {
                numeric.append(romanNumbers[index]);
            }
            num -= bitDepth * ints[index];
            index++;
        }
        return numeric.toString();
    }

    public static boolean checkIfOperand(char c) {
        return (c == '+') || (c == '-') || (c == '/') || (c == '*');
    }

    public static String removeSpaces(String line) {
        char[] tmp = line.toCharArray();
        int size = tmp.length;
        int spaceCounter = 0;
        boolean[] marks = new boolean[size];

        Arrays.fill(marks, true);

        for (int i = 0; i < size; i++) {
            if (tmp[i] == ' ') {
                marks[i] = false;
                spaceCounter++;
            }
        }

        int resultLength = size - spaceCounter;
        char[] resultChars = new char[resultLength];
        int ctr = 0;
        for (int i = 0; i < size; i++) {
            if (marks[i]) {
                resultChars[ctr] = tmp[i];
                ctr++;
            }
        }
        return new String(resultChars);
    }
}
