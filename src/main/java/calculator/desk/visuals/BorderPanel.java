package calculator.desk.visuals;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class BorderPanel extends JPanel {
    private final int SCALE = 3;
    private final JPanel panel;
    private final JFrame frame;
    private JPanel buttonsPanel;
    private JButton close;
    private JButton minimize;

    public BorderPanel(JPanel panel, JFrame frame) {
        this.panel = panel;
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {
        setOpaque(false);
        setButtons();
        setButtonsPanel();
        addComponentsToLayout();
    }

    private void setButtons() {
        close = new BorderButton(SCALE, BorderButtonType.CLOSE, frame);
        minimize = new BorderButton(SCALE, BorderButtonType.MINIMIZE, frame);
    }

    private void setButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(0, 0, 0, 0));
        buttonsPanel.setLayout(new MigLayout("fill", "[fill, 80%, shrink 100][][]", "[]"));
        buttonsPanel.add(minimize, "cell 1 0, grow, w 55::20%, gap top " + (6 * (SCALE - 1)));
        buttonsPanel.add(close, "cell 2 0, w 55::20%,grow");
    }

    private void addComponentsToLayout() {
        setLayout(new BorderLayout());
        add(buttonsPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
        super.paintComponent(graphics);
    }
}
