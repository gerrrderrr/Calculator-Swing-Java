package calculator.desk.visuals;

import calculator.desk.util.AutoTextWrapper;
import calculator.desk.util.ImageReader;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class CalculatorPanel extends JPanel {
    private Screen screen;
    private ButtonsPanel buttons;
    private JPanel intermediateResultPanel;
    private JTextPane textPane;
    private ImageReader reader = new ImageReader();

    public CalculatorPanel() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(0, 0, 0, 0));
        setIntermediateResultPanel();
        screen = new Screen(textPane);
        buttons = new ButtonsPanel();
        addComponentsToLayout();
    }

    private void setIntermediateResultPanel() {
        intermediateResultPanel = new JPanel();
        intermediateResultPanel.setBackground(Color.BLACK);
        intermediateResultPanel.setLayout(new MigLayout("fillx", "[][]", "[]"));
        intermediateResultPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(15, 15, 15)));
        setTextPane();
        JButton button = setButton();
        intermediateResultPanel.add(button, "al left bottom");
        intermediateResultPanel.add(textPane, "grow");
    }

    private JButton setButton() {
        JButton button = new JButton();
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setIcon(new ImageIcon(reader.getImage("/images/copy.png")));
        button.setRolloverIcon(new ImageIcon(reader.getImage("/images/copy-rollover.png")));
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textPane.getText();
                if (!text.isEmpty()) {
                    Toolkit.getDefaultToolkit()
                            .getSystemClipboard()
                            .setContents(
                                    new StringSelection(textPane.getText()),
                                    null
                            );
                }
            }
        });
        return button;
    }

    private void setTextPane() {
        textPane = new JTextPane();
        textPane.setBackground(Color.BLACK);
        textPane.setFont(new Font("ROBOTO", Font.PLAIN, 25));
        textPane.setSelectedTextColor(new Color(81, 47, 112));
        textPane.setSelectionColor(new Color(15, 15, 15));
        textPane.setEditorKit(new AutoTextWrapper());
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        textPane.setEditable(false);
    }

    private void addComponentsToLayout() {
        setLayout(new MigLayout("fill", "[]", "[30%!][10%!][]"));
        add(screen, "grow, wrap");
        add(intermediateResultPanel, "grow, wrap");
        add(buttons, "grow");
    }
}
