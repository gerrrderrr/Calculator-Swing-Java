package calculator.desk.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public final class FrameDragByComponent {

    private static int pX;
    private static int pY;

    private FrameDragByComponent() {
    }

    public static void set(JFrame frame, Component component) {
        component.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                componentDragged(e, frame, component);
            }
        });
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                componentPressed(e);
            }
        });
    }

    private static void componentPressed(MouseEvent e) {
        pX = e.getX();
        pY = e.getY();
    }

    private static void componentDragged(MouseEvent e, JFrame frame, Component component) {
        frame.setLocation(frame.getLocation().x + e.getX() - pX, frame.getLocation().y + e.getY() - pY);
    }
}
