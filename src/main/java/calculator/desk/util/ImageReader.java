package calculator.desk.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public final class ImageReader {
    public ImageReader() {
    }

    public Image getImage(String path) {
        Image i;
        try {
            i = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
}
