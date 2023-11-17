package calculator.desk.visuals;

import calculator.desk.observer.Event;
import calculator.desk.util.AutoTextWrapper;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class MathProblemDisplayer extends JTextPane {

    public MathProblemDisplayer() {
        initComponents();
    }

    private void initComponents() {
        setEditorKit(new AutoTextWrapper());
        CaretInvisible caret = new CaretInvisible();
        setCaret(caret);
        setOpaque(false);
        setEditable(false);
        setForeground(new Color(170, 130, 255));
        setAlignment();
        uploadFont();
        setFont(new Font("DIGITAL-7", Font.PLAIN, 50));
    }

    private void setAlignment() {
        StyledDocument doc = getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_RIGHT);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    private void uploadFont() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(this.getClass().getResourceAsStream("/font/digital-7.ttf"))));
        } catch (IOException | FontFormatException ignored) {
        }
    }

    public void addText(String text) {
        StyledDocument doc = getStyledDocument();
        try {
            int offset = doc.getLength();
            doc.insertString(offset, text, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
        repaintScreen();
    }

    public void repaintScreen() {
        Event.getInstance().getRepaintScreen().repaintScreen();
    }

    public void removeText() {
        StyledDocument doc = getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1f));
        super.paintComponent(g);
    }
}
