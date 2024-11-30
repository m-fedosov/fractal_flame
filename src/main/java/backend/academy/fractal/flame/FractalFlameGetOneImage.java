package backend.academy.fractal.flame;

import backend.academy.fractal.flame.render.MultyThreadImageRenderer;
import java.util.ArrayList;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalFlameGetOneImage {
    @SuppressWarnings({"UncommentedMain", "MagicNumber"})
    public static void main(String[] args) {
        int drawIterations = 300_000_000;
        int threadCount = 10;
        int imgWidth = 2560;
        int imgHeight = 1600;
        int symmetry = 6;

        ArrayList<Variation> variations = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            variations.add(Variation.create());
        }
        ImageMatrix img = ImageMatrix.create(imgWidth, imgHeight);
//        new OneThreadImageRenderer(300_000_000, variations).render(img, symmetry);
        new MultyThreadImageRenderer(threadCount, drawIterations, variations).render(img, symmetry);
        ImageNormalizer.correction(img);
        ImageSaver.save(img);
    }

}
