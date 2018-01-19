import exception.IncorectArgumentExeption;

import java.util.*;

public class RPN {

    public static void main(String[] args) {
        RPN rpn = new RPN();
        rpn.transform();
    }

    public void transform() {
        Scanner in = new Scanner(System.in);
        String command = "";
        do {
            System.out.println("Input:");
            Integer count = Integer.valueOf(in.nextLine().trim());

            try {
                isCorrectNumberOfExpression(count);

                String[] strings = inputExpressions(in, count);

                String[] outputs = outputExpressions(strings);
                for (String output : outputs) {
                    System.out.println(output);
                }
            } catch (IncorectArgumentExeption e) {
                System.out.println(e.getMessage());
            }
            System.out.print("If you want to continue press any key or for exit press \"q\":");
            command = in.nextLine();
        } while (!command.equals("q"));
    }

    private String[] outputExpressions(String[] strings) throws IncorectArgumentExeption {
        String[] results = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            String exp = strings[i];
            if (ValidationData.compile(exp)) {
                results[i] = createStringRPN(exp).trim();
            } else {
                results[i] = "The expressions by number " + (i + 1) + " is not valid: " + exp;
            }
        }
        return results;
    }

    private String[] inputExpressions(Scanner in, Integer count) {
        String[] strings = new String[count];
        for (Integer i = 0; i < count; i++) {
            String string = in.nextLine();

            strings[i] = string.trim();
        }
        return strings;
    }

    protected String createStringRPN(String s) throws IncorectArgumentExeption {
        Deque<Character> stack = new LinkedList<>();
        String result = "";
        char[] chars = s.toCharArray();

        isCorrectLengthOfExpression(chars.length);

        for (char ch : chars) {
            if (ch == '(')
                continue;

            if (isOperator(ch)) {
                stack.offerFirst(ch);
                continue;
            } else if (ch == ')') {
                result += stack.pop();
                continue;
            } else {
                result += String.valueOf(ch);
                continue;
            }
        }
        return result;
    }

    protected boolean isValidateExpression(String s) {
        return s.matches("^[a-zA-Z+*-^/()]+$");
    }

    protected void isCorrectNumberOfExpression(int count) throws IncorectArgumentExeption {
        if (count > 100) {
            throw new IncorectArgumentExeption("first argument \"t\" [the number of expressions <= 100]");
        }
    }

    protected void isCorrectLengthOfExpression(int len) throws IncorectArgumentExeption {
        if (len > 400) {
            throw new IncorectArgumentExeption("the expression [length <= 400]");
        }
    }

    protected boolean isOperator(char ch) {
        if (("+-/*^".indexOf(ch) != -1))
            return true;
        return false;
    }

}