package calculator.desk.visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BorderButton extends JButton {
    private final int scale;
    private final BorderButtonType type;
    private final JFrame frame;
    private Polygon polygon;

    public BorderButton(int scale, BorderButtonType type, JFrame frame) {
        this.scale = scale;
        this.type = type;
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        initPolygon();
        BorderButtonPolygonIcon icon = new BorderButtonPolygonIcon(polygon);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(icon);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                icon.resetColor();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                icon.resetColor();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                icon.changeColor();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                icon.resetColor();
            }
        });
    }

    private void initPolygon() {
        switch (type) {
            case CLOSE: {
                createClosePolygon();
                addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
            }
            break;
            case MINIMIZE: {
                createMinimizePolygon();
                addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.setExtendedState(Frame.ICONIFIED);
                    }
                });
            }
            break;
        }
    }

    private void createClosePolygon() {
        int[] x = scaleCoordinates(new int[]{0, 2, 0, 1, 3, 5, 6, 4, 6, 5, 3, 1, 0});
        int[] y = scaleCoordinates(new int[]{1, 3, 5, 6, 4, 6, 5, 3, 1, 0, 2, 0, 1});
        int n = y.length;
        polygon = new Polygon(x, y, n);
    }

    private void createMinimizePolygon() {
        int[] x = scaleCoordinates(new int[]{0, 0, 6, 6, 0});
        int[] y = scaleCoordinates(new int[]{0, 2, 2, 0, 0});
        int n = y.length;
        polygon = new Polygon(x, y, n);
    }

    private int[] scaleCoordinates(int[] coordinates) {
        int[] scaled = new int[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            scaled[i] = coordinates[i] * scale;
        }
        return scaled;
    }
}
