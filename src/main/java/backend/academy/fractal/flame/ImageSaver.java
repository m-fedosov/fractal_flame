package backend.academy.fractal.flame;

import lombok.AllArgsConstructor;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

@AllArgsConstructor
public class ImageSaver {

    public void save(Image image, Path path) {
        if (!path.toString().toLowerCase().endsWith(".png")) {
            throw new RuntimeException("The valid format is png only, like \"image.png\"");
        }
        try {
            ImageIO.write(convertImageToBufferedImage(image), "png", path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage convertImageToBufferedImage(Image img) {
        BufferedImage renderedImage = new BufferedImage(img.width(), img.height(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < img.width(); x++) {
            for (int y = 0; y < img.height(); y++) {
                Pixel pixel = img.pixel(x, y);
                assert pixel != null;
                Color color = new Color(pixel.r(), pixel.g(), pixel.b());
                renderedImage.setRGB(x, y, color.getRGB());
            }
        }
        return renderedImage;
    }
}
