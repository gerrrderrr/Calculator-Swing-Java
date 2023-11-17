package calculator.desk.visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class CharButton extends JButton {
    private final String character;
    private final Color background;
    private final Color characterColor;
    private final int arch;
    private final Font font;
    private boolean isOver;
    private boolean isClicked;
    private int shx = 25;
    private int shy = 5;
    private Timer timer;
    private Timer timerColor;
    private Color c1;
    private final Color c2 = new Color(192, 0, 255, 30);
    private final Color main = new Color(48, 20, 170, 30);

    public CharButton(String character, Color background, Color characterColor, int arch, Font font) {
        isOver = false;
        isClicked = false;
        this.character = character;
        this.background = background;
        this.characterColor = characterColor;
        this.arch = arch;
        this.font = font;
        initComponents();
    }

    private void initComponents() {
        c1 = main;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setText(character);
        setBackground(background);
        setForeground(characterColor);
        setFont(font);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isClicked) {
                    timer.stop();
                    timerColor.stop();
                    shx = 25;
                    shy = 5;
                    repaint();
                }
                isClicked = true;
                c1 = c2;
                timerColor = new Timer(1, x -> {
                    int r = c1.getRed();
                    int g = c1.getGreen();
                    int b = c1.getBlue();
                    int rn = r != main.getRed() ? r > main.getRed() ? r - 1 : r + 1 : r;
                    int gn = g != main.getGreen() ? g > main.getGreen() ? g - 1 : g + 1 : g;
                    int bn = b != main.getBlue() ? b > main.getBlue() ? b - 1 : b + 1 : b;
                    c1 = new Color(rn, gn, bn, 30);
                    repaint();
                    if (rn == main.getRed() && g == main.getGreen() && b == main.getBlue()) {
                        c1 = main;
                        timerColor.stop();
                        isClicked = false;
                    }
                });
                timerColor.start();
                timer = new Timer(20, x -> {
                    shx = shx > 5 ? shx - 1 : shx;
                    shy = shy < 25 ? shy + 1 : shy;
                    repaint();
                    if (shx == 5 && shy == 25) {
                        timer.stop();
                        shx = 25;
                        shy = 5;
                        isOver = false;

                    }
                });
                timer.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isClicked) {
                    isOver = false;
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float[] fractions = {.0f, .1f, .9f, 1};
        Color[] colors = {c1, background, background, c1};
        Point2D pointStart = new Point2D.Double(0, 0);
        Point2D pointEnd = new Point2D.Double(getWidth(), getHeight());
        if (!isOver) {
            g2d.setPaint(new LinearGradientPaint(pointStart,
                    pointEnd,
                    fractions,
                    colors,
                    MultipleGradientPaint.CycleMethod.NO_CYCLE,
                    MultipleGradientPaint.ColorSpaceType.SRGB,
                    AffineTransform.getShearInstance(shx, shy)));
        } else {
            g2d.setPaint(new LinearGradientPaint(pointStart,
                    pointEnd,
                    fractions,
                    colors,
                    MultipleGradientPaint.CycleMethod.NO_CYCLE,
                    MultipleGradientPaint.ColorSpaceType.SRGB,
                    AffineTransform.getShearInstance(shy, shx)));
        }
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arch, arch);
        super.paintComponent(graphics);
    }
}
