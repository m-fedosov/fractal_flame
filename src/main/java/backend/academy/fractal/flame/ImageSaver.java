package backend.academy.fractal.flame;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;

@SuppressWarnings("HideUtilityClassConstructor")
@AllArgsConstructor
public class ImageSaver {

    public static void save(ImageMatrix image) {
        try {
            String randomHash = UUID.randomUUID().toString();
            String fileName = "image" + randomHash + ".png";
            ImageIO.write(
                convertImageToBufferedImage(image),
                "png",
                Path.of("images/" + fileName).toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage convertImageToBufferedImage(ImageMatrix img) {
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
