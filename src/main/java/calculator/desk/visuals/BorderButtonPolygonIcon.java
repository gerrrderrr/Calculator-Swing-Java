package calculator.desk.visuals;

import javax.swing.*;
import java.awt.*;

public class BorderButtonPolygonIcon extends JPanel {
    private final Polygon polygon;
    private static final float HUE_MIN = 0;
    private static final float HUE_MAX = 1;
    private Timer timer;
    private float hue = HUE_MIN;
    private Color color;
    private Color color1 = Color.black;
    private Color color2 = Color.black;
    private final float delta = 0.01f;

    public BorderButtonPolygonIcon(Polygon polygon) {
        this.polygon = polygon;
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(0, 0, 0, 0));
        setColors();
    }

    private void setColors() {
        color = new Color(38, 31, 86);
    }

    public void changeColor() {
        color = new Color(75, 36, 108);
        timer = new Timer(10, x -> {
            hue += delta;
            if (hue > HUE_MAX) {
                hue = HUE_MIN;
            }
            color1 = Color.getHSBColor(hue, 1, 1);
            color2 = Color.getHSBColor(hue + 16 * delta, 1, 1);
            repaint();
        });
        timer.start();
    }

    public void resetColor() {
        timer.stop();
        setColors();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(new GradientPaint(0, 0, color1, getWidth(), 0, color2));
        g2d.drawPolygon(polygon);
        g2d.setColor(color);
        g2d.fillPolygon(polygon);
        super.paintComponent(g);
    }
}
