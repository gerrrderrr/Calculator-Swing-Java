package calculator.desk.scrollbar;


import javax.swing.*;
import java.awt.*;

public class MainScrollBar extends JScrollBar {
    public MainScrollBar() {
        setUI(new ThinScrollbar());
        setPreferredSize(new Dimension(5, 5));
        setBackground(new Color(58, 47, 112));
        setUnitIncrement(20);
    }
}
