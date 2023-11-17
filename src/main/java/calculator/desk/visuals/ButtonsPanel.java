package calculator.desk.visuals;

import calculator.desk.calculation.BasicCommand;
import calculator.desk.observer.Event;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

public class ButtonsPanel extends JPanel {
    private CharButton buttonZero;
    private CharButton buttonOne;
    private CharButton buttonTwo;
    private CharButton buttonThree;
    private CharButton buttonFour;
    private CharButton buttonFive;
    private CharButton buttonSix;
    private CharButton buttonSeven;
    private CharButton buttonEight;
    private CharButton buttonNine;
    private CharButton buttonAC;
    private CharButton buttonC;
    private CharButton buttonMC;
    private CharButton buttonMR;
    private CharButton buttonMS;
    private CharButton divide;
    private CharButton multiply;
    private CharButton percent;
    private CharButton addition;
    private CharButton subtraction;
    private CharButton equals;
    private CharButton comma;
    private CharButton plusMinus;

    public ButtonsPanel() {
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.BLACK);
        setButtons();
        addButtonsListeners();
        addComponentsToLayout();
    }

    private void setButtons() {
        Color c1 = new Color(58, 47, 112);
        Color c2 = new Color(81, 47, 112);
        Font font = new Font("ROBOTO", Font.PLAIN, 30);
        Font fontForBasic = new Font("ROBOTO", Font.PLAIN, 25);
        buttonZero = new CharButton("0", c1, Color.WHITE, 50, font);
        buttonOne = new CharButton("1", c1, Color.WHITE, 50, font);
        buttonTwo = new CharButton("2", c1, Color.WHITE, 50, font);
        buttonThree = new CharButton("3", c1, Color.WHITE, 50, font);
        buttonFour = new CharButton("4", c1, Color.WHITE, 50, font);
        buttonFive = new CharButton("5", c1, Color.WHITE, 50, font);
        buttonSix = new CharButton("6", c1, Color.WHITE, 50, font);
        buttonSeven = new CharButton("7", c1, Color.WHITE, 50, font);
        buttonEight = new CharButton("8", c1, Color.WHITE, 50, font);
        buttonNine = new CharButton("9", c1, Color.WHITE, 50, font);
        buttonAC = new CharButton("AC", c2, Color.WHITE, 50, fontForBasic);
        buttonC = new CharButton("CL", c2, Color.WHITE, 50, fontForBasic);
        buttonMC = new CharButton("MC", c2, Color.WHITE, 50, fontForBasic);
        buttonMR = new CharButton("MR", c2, Color.WHITE, 50, fontForBasic);
        buttonMS = new CharButton("MS", c2, Color.WHITE, 50, fontForBasic);
        divide = new CharButton("/", c2, Color.WHITE, 50, fontForBasic);
        multiply = new CharButton("×", c2, Color.WHITE, 50, fontForBasic);
        percent = new CharButton("%", c2, Color.WHITE, 50, fontForBasic);
        addition = new CharButton("+", c2, Color.WHITE, 50, fontForBasic);
        subtraction = new CharButton("-", c2, Color.WHITE, 50, fontForBasic);
        equals = new CharButton("=", c2, Color.WHITE, 50, font);
        comma = new CharButton(".", c2, Color.WHITE, 50, font);
        plusMinus = new CharButton("+/-", c2, Color.WHITE, 50, fontForBasic);
    }

    private void addButtonsListeners() {
        addNumberAction(buttonZero);
        addNumberAction(buttonOne);
        addNumberAction(buttonTwo);
        addNumberAction(buttonThree);
        addNumberAction(buttonFour);
        addNumberAction(buttonFive);
        addNumberAction(buttonSix);
        addNumberAction(buttonSeven);
        addNumberAction(buttonEight);
        addNumberAction(buttonNine);
        addNumberAction(plusMinus);
        addNumberAction(comma);
        addBaseAction(buttonAC);
        addBaseAction(buttonC);
        addBaseAction(buttonMC);
        addBaseAction(buttonMR);
        addBaseAction(buttonMS);
        addOperationAction(divide);
        addOperationAction(multiply);
        addOperationAction(percent);
        addOperationAction(addition);
        addOperationAction(subtraction);
        addOperationAction(equals);
    }

    private void addNumberAction(JButton button) {
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event.getInstance().getUpdateNumbers().update(button);
            }
        });
    }

    private void addOperationAction(JButton button) {
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event.getInstance().getSetOperation().setOperation(button);
            }
        });
    }

    private void addBaseAction(JButton button) {
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event.getInstance().getPerformBaseAction().perform(BasicCommand.valueOf(button.getText().toUpperCase(Locale.ROOT)));
            }
        });
    }

    private void addComponentsToLayout() {
        setLayout(new MigLayout("fill", "[][][][][]", "[][][][][]"));
        add(buttonMS, "grow");
        add(buttonMC, "grow");
        add(buttonMR, "grow");
        add(buttonC, "grow");
        add(buttonAC, "grow, wrap");
        add(buttonOne, "grow");
        add(buttonTwo, "grow");
        add(buttonThree, "grow");
        add(addition, "grow");
        add(subtraction, "grow, wrap");
        add(buttonFour, "grow");
        add(buttonFive, "grow");
        add(buttonSix, "grow");
        add(multiply, "grow");
        add(divide, "grow, wrap");
        add(buttonSeven, "grow");
        add(buttonEight, "grow");
        add(buttonNine, "grow");
        add(percent, "grow");
        add(plusMinus, "grow, wrap");
        add(comma, "grow");
        add(buttonZero, "grow");
        add(equals, "grow, span 3");
    }
}
