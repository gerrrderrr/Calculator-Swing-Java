package calculator.desk.visuals;

import calculator.desk.util.FrameDragByComponent;
import calculator.desk.util.ImageReader;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class CalculatorFrame extends JFrame {
    private ImageReader reader = new ImageReader();
    public CalculatorFrame() {
        initComponents();
    }

    private void initComponents() {
        CalculatorPanel panel = new CalculatorPanel();
        BorderPanel border = new BorderPanel(panel, this);
        setUndecorated(true);
        setIconImage(reader.getImage("/images/ico.png"));
        setSize(500, 800);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setContentPane(border);
        FrameDragByComponent.set(this, border);
        setVisible(true);
    }
}
