package backend.academy.fractal.flame;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Variation> variations = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            variations.add(Variation.create());
        }
        ImageMatrix image = ImageMatrix.create(2560, 1600);
        ImageRenderer.render(100_000_000, variations, image);
        ImageNormalizer.correction(image);
        ImageSaver.save(image);
    }

}
