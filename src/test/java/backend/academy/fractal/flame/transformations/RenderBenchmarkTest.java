package backend.academy.fractal.flame.transformations;

import backend.academy.fractal.flame.ImageMatrix;
import backend.academy.fractal.flame.Variation;
import backend.academy.fractal.flame.render.MultyThreadImageRenderer;
import backend.academy.fractal.flame.render.OneThreadImageRenderer;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RenderBenchmarkTest {

    @Test
    void CompareSingleThreadRenderAndMultyThreadRender() {
        int drawIterations = 1_000_000;
        int threadCount = 10;
        int imgWidth = 1920;
        int imgHeight = 1080;
        int symmetry = 1;

        ArrayList<Variation> variations = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            variations.add(Variation.create());
        }
        ImageMatrix img = ImageMatrix.create(imgWidth, imgHeight);
        long singleThreadStart = System.currentTimeMillis();
        new OneThreadImageRenderer(drawIterations, variations).render(img, symmetry);
        long singleThreadEnd = System.currentTimeMillis();
        long singleThreadTotal = singleThreadEnd - singleThreadStart;

        long multyThreadStart = System.currentTimeMillis();
        new MultyThreadImageRenderer(threadCount, drawIterations, variations).render(img, symmetry);
        long multyThreadEnd = System.currentTimeMillis();
        long multyThreadTotal = multyThreadEnd - multyThreadStart;

        assertThat(singleThreadTotal).isGreaterThan(multyThreadTotal);
    }
}
