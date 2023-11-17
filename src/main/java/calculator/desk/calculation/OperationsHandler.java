package calculator.desk.calculation;

import calculator.desk.observer.Event;
import calculator.desk.visuals.MathProblemDisplayer;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OperationsHandler {
    private final String END_MESSAGE_FOR_SAVED = "THAT WAS THE LAST ONE";
    private final String NO_SAVED = "THERE IS NOTHING HERE";
    private final int MAX_CAPACITY = 10;
    private final int MAX_NUM = 9;
    Calculation calculation = new Calculation();
    private final List<String> saved = new ArrayList<>(MAX_CAPACITY);
    private final List<String> problem = new ArrayList<>();
    private final MathProblemDisplayer output;
    private final JTextPane intermediateResult;
    private int indexMR = 0;
    private int problemLength = 0;

    public OperationsHandler(MathProblemDisplayer output, JTextPane intermediateResult) {
        this.output = output;
        this.intermediateResult = intermediateResult;
        addListeners();
    }

    private void addListeners() {
        Event.getInstance().setUpdateNumbers(this::updateNumber);
        Event.getInstance().setSetOperation(this::addOperation);
        Event.getInstance().setPerformBaseAction(this::performCommand);
    }

    private void updateNumber(JButton button) {
        checkIfItIsANewProblem();
        resetMRIndex();
        String text = button.getText();
        if (text.equals(Arithmetic.CHANGE_SIGN)) {
            String newOutput = changeTheSignOfANumber();
            clearOutputPanel();
            addTextToOutputPanel(newOutput);
        } else if (text.equals(Arithmetic.POINT)) {
            addPointToTheNumber(text);
        } else if (tryingToDivideOnZero(text)) {
            addTextToOutputPanel(text + Arithmetic.POINT);
        } else {
            String lastNumber = getLastNum();
            if (lastNumber.length() <= MAX_NUM) {
                if (lastNumber.length() == 1 && lastNumber.startsWith("0")) {
                    addTextToOutputPanel(Arithmetic.POINT);
                }
                addTextToOutputPanel(text);
            }
        }
    }

    private void checkIfItIsANewProblem() {
        if (isOutputContains(Arithmetic.EQUALS) || isOutputContains(END_MESSAGE_FOR_SAVED) || isOutputContains(NO_SAVED)) {
            clearIntermediateResultPanel();
            clearOutputPanel();
        }
    }

    private boolean isOutputContains(String text) {
        return output.getText().contains(text);
    }

    private void clearIntermediateResultPanel() {
        StyledDocument doc = intermediateResult.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearOutputPanel() {
        output.removeText();
    }

    private void resetMRIndex() {
        if (indexMR != 0) {
            indexMR = 0;
        }
    }

    private String changeTheSignOfANumber() {
        if (problem.isEmpty()) {
            return changeFirstNumberSign();
        } else {
            int length = getOutputLength() - problemLength + 1;
            String number = getOutputText(problemLength - 1, length);
            return number.length() == 1 ? getOutputText(0, problemLength) : changeLastNumberSign(number);
        }
    }

    private String changeFirstNumberSign() {
        String number = output.getText();
        return number.startsWith(Arithmetic.SUBTRACTION) ? number.substring(1) : Arithmetic.SUBTRACTION + number;
    }

    private int getOutputLength() {
        return output.getText().length();
    }

    private String getOutputText(int offset, int length) {
        try {
            return output.getText(offset, length);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private String changeLastNumberSign(String number) {
        String beginning = getOutputText(0, problemLength - 1);
        String newNumber = getUpdatedNumber(number);
        updateSignInProblem();
        return beginning + newNumber;
    }

    private String getUpdatedNumber(String number) {
        String character = String.valueOf(number.charAt(1));
        return number.startsWith(Arithmetic.SUBTRACTION) ?
                Arithmetic.ADDITION + number.substring(1) :
                number.startsWith(Arithmetic.ADDITION) ?
                        Arithmetic.SUBTRACTION + number.substring(1) :
                        character.equals(Arithmetic.SUBTRACTION) ?
                                number.charAt(0) + number.substring(2) :
                                number.charAt(0) + Arithmetic.SUBTRACTION + number.substring(1);
    }

    private void updateSignInProblem() {
        String sign = problem.get(problem.size() - 1);
        if (sign.equals(Operation.ADDITION.name()) || sign.equals(Operation.SUBTRACTION.name())) {
            problem.remove(problem.size() - 1);
            problem.add(sign.equals(Operation.ADDITION.name()) ? Operation.SUBTRACTION.name() : Operation.ADDITION.name());
        }
    }

    private void addTextToOutputPanel(String text) {
        output.addText(text);
    }

    private boolean tryingToDivideOnZero(String text) {
        return output.getText().endsWith(Arithmetic.DIVISION) && text.equals("0");
    }

    private void addPointToTheNumber(String text) {
        String lastNumber = getLastNum();
        if (lastNumber.isEmpty()) {
            addTextToOutputPanel("0" + text);
        } else if (!lastNumber.contains(Arithmetic.POINT)) {
            addTextToOutputPanel(text);
        }
    }

    private String getLastNum() {
        return output.getText().substring(problemLength);
    }

    private void addOperation(JButton button) {
        if (isAcceptableToAddOperation()) {
            String text = button.getText();
            Operation operation = getOperation(text);
            String lastNum = getOutputText(problemLength, getOutputLength() - problemLength);
            problem.add(lastNum);
            if (operation == Operation.EQUALS) {
                double answer = calculation.calculate(problem);
                String roundedAnswer = roundIfNeeded(answer);
                addAnswerToIntermediateResultPanel(roundedAnswer);
                addTextToOutputPanel(text + roundedAnswer);
                clearProblemData();
            } else {
                problem.add(Objects.requireNonNull(operation).name());
                problemLength = problemLength + lastNum.length() + 1;
                addTextToOutputPanel(text);
                calculateAnswerToIntermediateResultPanel();
            }
        }
    }

    private boolean isAcceptableToAddOperation() {
        return getOutputLength() != 0 &&
                !isOutputContains(Arithmetic.EQUALS) &&
                lastCharIsNotArithmeticOperation() &&
                !getLastCharacter().equals(Arithmetic.POINT) &&
                isNotConsistOfZeros();
    }

    private boolean lastCharIsNotArithmeticOperation() {
        String number = getLastCharacter();
        return getOperation(number) == null;
    }

    private String getLastCharacter() {
        return String.valueOf(output.getText().charAt(output.getText().length() - 1));
    }

    private boolean isNotConsistOfZeros() {
        String text = getLastNum();
        if (text.startsWith("0")) {
            String[] split = text.split("\\.");
            if (split.length == 1) {
                return true;
            } else {
                return !split[1].chars().allMatch(x -> x == '0');
            }
        } else {
            return true;
        }
    }

    private Operation getOperation(String text) {
        switch (text) {
            case Arithmetic.ADDITION:
                return Operation.ADDITION;
            case Arithmetic.SUBTRACTION:
                return Operation.SUBTRACTION;
            case Arithmetic.DIVISION:
                return Operation.DIVISION;
            case Arithmetic.MULTIPLICATION:
                return Operation.MULTIPLICATION;
            case Arithmetic.PERCENTAGE:
                return Operation.PERCENT;
            case Arithmetic.EQUALS:
                return Operation.EQUALS;
        }
        return null;
    }

    private String roundIfNeeded(double answer) {
        if (answer == Math.floor(answer) && !String.valueOf(answer).contains("E")) {
            return String.valueOf(Math.round(answer));
        } else {
            return String.valueOf(answer);
        }
    }

    private void addAnswerToIntermediateResultPanel(String answer) {
        clearIntermediateResultPanel();
        StyledDocument doc = intermediateResult.getStyledDocument();
        try {
            doc.insertString(0, answer, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearProblemData() {
        problemLength = 0;
        problem.clear();
    }

    private void calculateAnswerToIntermediateResultPanel() {
        double answer = calculation.calculate(problem);
        String rounded = roundIfNeeded(answer);
        addAnswerToIntermediateResultPanel(rounded);
    }

    private void performCommand(BasicCommand command) {
        switch (command) {
            case CL: {
                String text = output.getText();
                if (text.length() >= problemLength + 1 && !isOutputContains(Arithmetic.EQUALS)) {
                    clearOutputPanel();
                    String newText = text.substring(0, text.length() - 1);
                    addTextToOutputPanel(newText);
                }
            }
            break;
            case AC: {
                resetPanels();
                clearProblemData();
                output.repaintScreen();
            }
            break;
            case MC: {
                memoryClean();
            }
            break;
            case MS: {
                memorySave(output.getText());
            }
            break;
            case MR: {
                String number = memoryRead();
                resetPanels();
                clearProblemData();
                output.addText(number);
            }
            break;
        }
    }

    public void resetPanels() {
        clearIntermediateResultPanel();
        output.removeText();
    }

    public void memoryClean() {
        indexMR = 0;
        saved.clear();
    }

    public void memorySave(String number) {
        if (saved.size() == MAX_CAPACITY) {
            saved.remove(0);
            saved.add(number);
        }
        saved.add(number);
    }

    private String memoryRead() {
        if (saved.size() != indexMR) {
            String number = saved.get((saved.size() - 1) - indexMR);
            indexMR++;
            return number;
        } else if (saved.isEmpty()) {
            return NO_SAVED;
        } else return END_MESSAGE_FOR_SAVED;
    }
}
