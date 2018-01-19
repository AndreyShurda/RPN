import exception.IncorrectDataType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ValidationData {
    public static final char LEFT_BRACKET = '(';
    public static final char RIGHT_BRACKET = ')';


    public static boolean compile(String s) {
        try {
            check(s);
            return true;
        } catch (IncorrectDataType e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void check(String s) throws IncorrectDataType {
        char[] chars = s.toCharArray();
        List<String> list = new ArrayList<>();

        for (char ch : chars) {
            list.add(String.valueOf(ch));
        }
        check(list);
    }

    private static void check(List<String> elements) throws IncorrectDataType {
        if (elements == null) {
            return;
        }
        int rightBracketsCount = 0;
        int leftBracketsCount = 0;
        int operatorsCount = 0;
        int numbersCount = 0;
        String curElem = null;
        String prevElem = null;
        boolean firstElem = true;

        Iterator<String> iterator = elements.iterator();
        if (iterator == null)
            return;

        while (iterator.hasNext()) {
            curElem = iterator.next();

            if (!isOperator(curElem) && !isValue(curElem)) {
//            if (!isOperator(curElem)){
                throw new IncorrectDataType("Incorrect operator or number:\n" +
                        "Correct numbers form are example: \n(a+b)\n" +
                        "Correct operators are '+,-,*,/'");
            }

            if (firstElem) {
                if (isOperator(curElem) && (curElem.charAt(0) != LEFT_BRACKET))
                    throw new IncorrectDataType("The row must be begin with left bracket");
                firstElem = false;
            }

            if (isOperator(curElem)) {
                numbersCount = 0;
                if (curElem.charAt(0) == RIGHT_BRACKET)
                    rightBracketsCount++;
                else if (curElem.charAt(0) == LEFT_BRACKET)
                    leftBracketsCount++;
                else
                    operatorsCount++;
            }
            if (isValue(curElem)) {
                numbersCount++;
                operatorsCount = 0;
            }

            if (numbersCount > 1 || operatorsCount > 1)
                throw new IncorrectDataType("Count of number or operators incorrect");

            if (prevElem != null) {
                if (isOperator(prevElem) && (prevElem.charAt(0) == LEFT_BRACKET)) {
                    if (isOperator(curElem))
                        if ((curElem.charAt(0) != LEFT_BRACKET) && curElem.charAt(0) != RIGHT_BRACKET)
                            throw new IncorrectDataType("After left bracket must be '(' or ')'");
                }

                if (isOperator(prevElem) && (prevElem.charAt(0) == RIGHT_BRACKET)) {
                    if (isValue(curElem) || curElem.charAt(0) == LEFT_BRACKET) {
                        throw new IncorrectDataType("After right bracket must be operator");
                    }
                }
            }
            prevElem = curElem;
        }

        if (isOperator(curElem) && (curElem.charAt(0) != RIGHT_BRACKET))
            throw new IncorrectDataType("Last element must be number or right bracket");

        if (leftBracketsCount > rightBracketsCount)
            throw new IncorrectDataType("You must close \")\" bracket");
        if (leftBracketsCount < rightBracketsCount)
            throw new IncorrectDataType("You must add \"(\" bracket");

    }

    public static boolean isOperator(char ch) {
        if (("+-/*^()".indexOf(ch) != -1))
            return true;
        return false;
    }

    public static boolean isOperator(String string) {
        if (string == null || string.isEmpty())
            return false;
        return isOperator(string.charAt(0));
    }

    private static boolean isValue(String string) {
        if (string == null)
            return false;
        return string.matches("[a-zA-Z]");
    }
}
