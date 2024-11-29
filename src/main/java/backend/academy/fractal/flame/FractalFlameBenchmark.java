package backend.academy.fractal.flame;

import backend.academy.fractal.flame.render.MultyThreadImageRenderer;
import backend.academy.fractal.flame.render.OneThreadImageRenderer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FractalFlameBenchmark {
    public static void main(String[] args) {

        int renderIterations = 10;
        int drawIterations = 300_000_000;
        int threadCount = 10;
        int imgWidth = 2560;
        int imgHeight = 1600;

        StringBuilder markdownReport = new StringBuilder();
        markdownReport.append("# Image Rendering Benchmark Report\n\n");

        // Однопоточный рендер
        System.out.println("Starting single-threaded rendering...");
        markdownReport.append("## Single-threaded Rendering\n");
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
        double singleThreadAvg = singleThreadTotal / (double) renderIterations;
        markdownReport.append("- **Total time:** ").append(singleThreadTotal).append(" ms\n");
        markdownReport.append("- **Average time per image:** ").append(singleThreadAvg).append(" ms\n\n");

        // Многопоточный рендер
        System.out.println("\nStarting multi-threaded rendering...");
        markdownReport.append("## Multi-threaded Rendering\n");
        long multiThreadStart = System.currentTimeMillis();
        for (int i = 1; i <= renderIterations; i++) {
            ArrayList<Variation> variations = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                variations.add(Variation.create());
            }
            ImageMatrix img = ImageMatrix.create(2560, 1600);
            new MultyThreadImageRenderer(threadCount, drawIterations, variations).render(img);
            ImageNormalizer.correction(img);
            ImageSaver.save(img);
            System.out.println("Multi-threaded image " + i + " generated.");
        }
        long multiThreadEnd = System.currentTimeMillis();
        long multiThreadTotal = multiThreadEnd - multiThreadStart;
        double multiThreadAvg = multiThreadTotal / (double) renderIterations;
        markdownReport.append("- **Total time:** ").append(multiThreadTotal).append(" ms\n");
        markdownReport.append("- **Average time per image:** ").append(multiThreadAvg).append(" ms\n\n");

        // Разница во времени
        markdownReport.append("## Time Difference\n");
        markdownReport.append("- **Total time difference:** ").append(singleThreadTotal - multiThreadTotal).append(" ms\n");
        markdownReport.append("- **Average time difference per image:** ")
            .append((int)(singleThreadAvg - multiThreadAvg)).append(" ms\n");

        // Запись результатов в файл
        saveReport(markdownReport.toString());
    }

    private static void saveReport(String report) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("benchmark_report.md"))) {
            writer.write(report);
            System.out.println("\nBenchmark results saved to 'benchmark_report.md'.");
        } catch (IOException e) {
            System.err.println("Error saving benchmark report: " + e.getMessage());
        }
    }
}
