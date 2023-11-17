package calculator.desk.observer;

import calculator.desk.calculation.BasicCommand;

public interface PerformBaseAction {
    void perform(BasicCommand command);
}
