package backend.academy.fractal.flame;

import backend.academy.fractal.flame.render.MultyThreadImageRenderer;
import java.util.ArrayList;

public class FractalFlameGetOneImage {

    public static void main(String[] args) {
        int drawIterations = 300_000_000;
        int threadCount = 10;
        int imgWidth = 2560;
        int imgHeight = 1600;

        ArrayList<Variation> variations = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            variations.add(Variation.create());
        }
        ImageMatrix img = ImageMatrix.create(imgWidth, imgHeight);
//        new OneThreadImageRenderer(300_000_000, variations).render(img);
        new MultyThreadImageRenderer(threadCount, drawIterations, variations).render(img);
        ImageNormalizer.correction(img);
        ImageSaver.save(img);
    }

}
