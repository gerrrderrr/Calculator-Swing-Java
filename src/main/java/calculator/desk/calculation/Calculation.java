package calculator.desk.calculation;

import java.util.List;
import java.util.Objects;

public class Calculation {
    private final ArithmeticOperationImpl arithmeticOperation = new ArithmeticOperationImpl();

    public Calculation() {
    }

    public double calculate(List<String> problem) {
        if (problem.size() <= 2) {
            return Double.parseDouble(problem.get(0));
        }
        double first = 0;
        Operation operationForFirst = null;
        double second = 0;
        Operation operationForSecond = null;
        for (int i = 0; i < problem.size(); i = i + 2) {
            if (i == 0) {
                first = Double.parseDouble(problem.get(i));
                operationForFirst = Operation.valueOf(problem.get(i + 1));
            } else if (isLast(i, problem)) {
                if (second != 0) {
                    second = performOperation(operationForSecond, second, Double.parseDouble(problem.get(i)));
                } else {
                    second = Double.parseDouble(problem.get(i));
                }
                return performOperation(operationForFirst, first, second);
            } else if (nextIsAdditionOrSubtraction(i, problem)) {
                if (second != 0) {
                    second = performOperation(operationForSecond, second, Double.parseDouble(problem.get(i)));
                    operationForSecond = null;
                } else {
                    second = Double.parseDouble(problem.get(i));
                }
                first = performOperation(operationForFirst, first, second);
                second = 0;
                operationForFirst = Operation.valueOf(problem.get(i + 1));
            } else {
                if (second != 0) {
                    second = performOperation(operationForSecond, second, Double.parseDouble(problem.get(i)));
                } else {
                    second = Double.parseDouble(problem.get(i));
                }
                operationForSecond = Operation.valueOf(problem.get(i + 1));
            }
        }
        return performOperation(Objects.requireNonNull(operationForFirst), first, second);
    }

    private boolean isLast(int i, List<String> problem) {
        return problem.size() - 1 == i;
    }

    private double performOperation(Operation operation, double firstNumber, double secondNumber) {
        switch (operation) {
            case ADDITION: {
                return arithmeticOperation.addition(firstNumber, secondNumber);
            }
            case SUBTRACTION: {
                return arithmeticOperation.subtraction(firstNumber, secondNumber);
            }
            case MULTIPLICATION: {
                return arithmeticOperation.multiplication(firstNumber, secondNumber);
            }
            case DIVISION: {
                return arithmeticOperation.division(firstNumber, secondNumber);
            }
            case PERCENT: {
                return arithmeticOperation.percentage(firstNumber, secondNumber);
            }
        }
        return 0;
    }

    private boolean nextIsAdditionOrSubtraction(int i, List<String> problem) {
        return problem.get(i + 1).equals(Operation.ADDITION.name()) || problem.get(i + 1).equals(Operation.SUBTRACTION.name());
    }
}
