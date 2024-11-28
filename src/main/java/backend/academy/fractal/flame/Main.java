package backend.academy.fractal.flame;

import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Variation> variations = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            variations.add(Variation.create());
        }
        Image image = Image.create(1920, 1080);
        Renderer.render(100_000_000, variations, image);
        ImageSaver saver = new ImageSaver();
        saver.save(image, Path.of("image.png"));
    }

}
