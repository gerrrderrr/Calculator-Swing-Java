package calculator.desk.visuals;

import calculator.desk.calculation.OperationsHandler;
import calculator.desk.scrollbar.MainScrollBar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class Screen extends JLayeredPane {
    private JPanel wrapperPanel;

    public Screen(JTextPane pane) {
        initComponents(pane);
    }

    private void initComponents(JTextPane pane) {
        setLayout(new OverlayLayout(this));
        initWrapperPanel();
        initNumberLayer(pane);
        setColoredPanel();
    }

    private void initWrapperPanel() {
        wrapperPanel = new JPanel();
        wrapperPanel.setOpaque(false);
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
    }

    private void initNumberLayer(JTextPane pane) {
        MathProblemDisplayer mathProblemDisplayer = new MathProblemDisplayer();
        new OperationsHandler(mathProblemDisplayer, pane);
        JScrollPane sp = new JScrollPane();
        JViewport vp = sp.getViewport();
        sp.setOpaque(false);
        vp.setOpaque(false);
        sp.setBorder(null);
        sp.setVerticalScrollBar(new MainScrollBar());
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setViewportView(mathProblemDisplayer);
        wrapperPanel.add(sp);
    }

    public void setColoredPanel() {
        ColoredPanel coloredPanel = new ColoredPanel();
        coloredPanel.setLayout(new MigLayout("fillx", "[]", "push[]push"));
        coloredPanel.add(wrapperPanel, "grow");
        add(coloredPanel);
    }
}
