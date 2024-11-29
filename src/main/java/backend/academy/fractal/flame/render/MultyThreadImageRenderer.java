package backend.academy.fractal.flame.render;

import backend.academy.fractal.flame.ImageMatrix;
import backend.academy.fractal.flame.Variation;
import backend.academy.fractal.flame.transformations.BaseTransformation;
import java.util.ArrayList;

public class MultyThreadImageRenderer extends ImageRenderer {
    int nThreads = 1;

    public MultyThreadImageRenderer(int nThreads, int iterations, ArrayList<Variation> variations) {
        super(iterations, variations);
        this.nThreads = nThreads;
    }

    @Override
    void draw(ImageMatrix img, BaseTransformation transformation, double startX, double startY, int symmetry) {
        Thread.Builder builder = Thread.ofVirtual();
        Runnable task = () -> {
            drawDefault(nThreads, img, transformation, startX, startY, symmetry);
        };

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < nThreads; i++) {
            threads.add(builder.start(task));
        }

        for (int i = 0; i < nThreads; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
