package backend.academy.fractal.flame;

import backend.academy.fractal.flame.transformations.BaseTransformation;
import com.google.common.util.concurrent.AtomicDouble;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

public class MultyThreadImageRenderer extends ImageRenderer {
    int nThreads = 1;

    public MultyThreadImageRenderer(int nThreads, int iterations, ArrayList<Variation> variations) {
        super(iterations, variations);
        this.nThreads = nThreads;
    }

    @Override
    void draw(ImageMatrix img, BaseTransformation transformation, double startX, double startY) {
        Thread.Builder builder = Thread.ofVirtual();
        Runnable task = () -> {
            drawDefault(nThreads, img, transformation, startX, startY);
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
