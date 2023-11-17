package calculator.desk.calculation;

public class ArithmeticOperationImpl implements ArithmeticOperation {


    public ArithmeticOperationImpl() {
    }

    @Override
    public double division(double first, double second) {
        return first / second;
    }

    @Override
    public double multiplication(double first, double second) {
        return first * second;
    }

    @Override
    public double percentage(double number, double percent) {
        return number / 100 * percent;
    }

    @Override
    public double addition(double first, double second) {
        return first + second;
    }

    @Override
    public double subtraction(double first, double second) {
        return first - second;
    }
}
