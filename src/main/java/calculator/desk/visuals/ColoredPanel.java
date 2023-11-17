package calculator.desk.visuals;

import calculator.desk.observer.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;


public class ColoredPanel extends JPanel {
    private boolean isClicked;
    private final List<Point> points;
    private double lastPointRadius;
    private boolean nNumber;
    private boolean justStarted;

    public ColoredPanel() {
        nNumber = false;
        justStarted = true;
        addListener();
        points = new ArrayList<>();
        lastPointRadius = 72;
        isClicked = false;
        setOpaque(false);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                isClicked = true;
                repaint();
                super.mouseDragged(e);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                isClicked = false;
                repaint();
                super.mouseReleased(e);
            }
        });
    }

    private void addListener() {
        Event.getInstance().setRepaintScreen(() -> {
            nNumber = true;
            revalidate();
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (getMousePosition() != null && isClicked) {
            float[] dist = {0.0f, 0.3f, 0.7f, 0.8f, 1.0f};
            Color c1 = new Color(197, 24, 255, 30);
            Color c2 = new Color(37, 180, 185, 15);
            Color c3 = new Color(40, 85, 206, 10);
            Color c4 = new Color(40, 85, 206, 5);
            Color c5 = new Color(0, 0, 0, 0);
            Color[] colors = {c1, c2, c3, c4, c5};
            Point p = null;
            try {
                p = getMousePosition().getLocation();
            } catch (NullPointerException ignored) {
            }
            double radius;
            if (p != null) {
                if (!points.isEmpty() && Math.abs(points.get(points.size() - 1).x - p.x) <= 5 &&
                        Math.abs(points.get(points.size() - 1).x - p.x) <= 5) {
                    lastPointRadius = lastPointRadius + 0.2;
                    radius = lastPointRadius;
                } else {
                    radius = 72;
                    lastPointRadius = radius;
                }
                points.add(p);
                g2d.setPaint(new RadialGradientPaint(p, (float) radius, dist, colors));
            } else {
                g2d.setColor(c5);
            }
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
        if (getMousePosition() != null && !isClicked && !justStarted) {
            g2d.clearRect(0, 0, getWidth(), getHeight());
            points.clear();
            super.paintComponent(g);
        } else if (nNumber) {
            g2d.clearRect(0, 0, getWidth(), getHeight());
            nNumber = false;
            super.paintComponent(g);
        } else {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .04f));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            if (justStarted) {
                justStarted = false;
            }
            super.paintComponent(g);
        }
    }
}
