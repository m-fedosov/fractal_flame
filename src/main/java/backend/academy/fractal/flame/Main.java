package backend.academy.fractal.flame;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Variation> variations = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            variations.add(Variation.create());
        }
        ImageMatrix img = ImageMatrix.create(2560, 1600);
//        new OneThreadImageRenderer(300_000_000, variations).render(img);
        new MultyThreadImageRenderer(10, 300_000_000, variations).render(img);
        ImageNormalizer.correction(img);
        ImageSaver.save(img);
    }

}
