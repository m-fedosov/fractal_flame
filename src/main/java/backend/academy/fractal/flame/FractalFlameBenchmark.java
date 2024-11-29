package backend.academy.fractal.flame;

import backend.academy.fractal.flame.render.MultyThreadImageRenderer;
import backend.academy.fractal.flame.render.OneThreadImageRenderer;
import java.util.ArrayList;

public class FractalFlameBenchmark {
    public static void main(String[] args) {

        int renderIterations = 5;
        int drawIterations = 300_000_000;
        int threadCount = 10;
        int imgWidth = 2560;
        int imgHeight = 2560;


        // Single-threaded rendering
        System.out.println("Starting single-threaded rendering...");
        long singleThreadStart = System.currentTimeMillis();
        for (int i = 1; i <= renderIterations; i++) {
            ArrayList<Variation> variations = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                variations.add(Variation.create());
            }
            ImageMatrix img = ImageMatrix.create(imgWidth, imgHeight);
            new OneThreadImageRenderer(drawIterations, variations).render(img);
            ImageNormalizer.correction(img);
            ImageSaver.save(img);
            System.out.println("Single-threaded image " + i + " generated.");
        }
        long singleThreadEnd = System.currentTimeMillis();
        long singleThreadTotal = singleThreadEnd - singleThreadStart;
        System.out.println("Single-threaded rendering finished. Total time: " + singleThreadTotal + " ms");
        System.out.println("Average time per image: " + (singleThreadTotal / (double) renderIterations) + " ms");

        // Multi-threaded rendering
        System.out.println("\nStarting multi-threaded rendering...");
        long multiThreadStart = System.currentTimeMillis();
        for (int i = 1; i <= renderIterations; i++) {
            ArrayList<Variation> variations = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                variations.add(Variation.create());
            }
            ImageMatrix img = ImageMatrix.create(imgWidth, imgHeight);
            new MultyThreadImageRenderer(threadCount, drawIterations, variations).render(img);
            ImageNormalizer.correction(img);
            ImageSaver.save(img);
            System.out.println("Multi-threaded image " + i + " generated.");
        }
        long multiThreadEnd = System.currentTimeMillis();
        long multiThreadTotal = multiThreadEnd - multiThreadStart;
        System.out.println("Multi-threaded rendering finished. Total time: " + multiThreadTotal + " ms");
        System.out.println("Average time per image: " + (multiThreadTotal / (double) renderIterations) + " ms");

        System.out.println("\nDifference in rendering time:");
        System.out.println("Total time difference: " + (singleThreadTotal - multiThreadTotal) + " ms");
        System.out.println("Average time difference per image: " +
                           ((singleThreadTotal / (double) renderIterations) - (multiThreadTotal / (double) renderIterations)) + " ms");
    }
}

