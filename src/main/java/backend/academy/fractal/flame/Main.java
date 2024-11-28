package backend.academy.fractal.flame;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        AffineCoeficents coeficents = AffineCoeficents.create();
        FractalImage image = FractalImage.create(2000, 2000);
        ImageSaver saver = new ImageSaver();
        saver.save(image, Path.of("image.png"));
    }
}
