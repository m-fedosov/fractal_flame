package backend.academy.fractal.flame;

import backend.academy.fractal.flame.render.MultyThreadImageRenderer;
import backend.academy.fractal.flame.render.OneThreadImageRenderer;
import com.beust.jcommander.Parameter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import lombok.Getter;

@SuppressWarnings({"MagicNumber", "RegexpSinglelineJava", "MultipleStringLiterals"})
@Getter
public class Runner {

    @Parameter(names = "--benchmark", description = "Run benchmark for single-threaded and multi-threaded rendering")
    private boolean benchmark;

    @Parameter(names = "--generate", description = "Generate a single image")
    private boolean generate;

    @Parameter(names = "--renderIterations", description = "Number of render iterations for the benchmark")
    private int renderIterations = 10;

    @Parameter(names = "--drawIterations", description = "Number of draw iterations per image")
    private int drawIterations = 300_000_000;

    @Parameter(names = "--threadCount", description = "Number of threads for multi-threaded rendering")
    private int threadCount = 10;

    @Parameter(names = "--imgWidth", description = "Width of the generated image")
    private int imgWidth = 2560;

    @Parameter(names = "--imgHeight", description = "Height of the generated image")
    private int imgHeight = 1600;

    @Parameter(names = "--symmetry", description = "Symmetry level for the generated image")
    private int symmetry = 1;

    public void runBenchmark() {
        StringBuilder markdownReport = new StringBuilder();
        markdownReport.append("# Image Rendering Benchmark Report\n\n");

        // Single-threaded rendering
        System.out.println("Starting single-threaded rendering...");
        markdownReport.append("## Single-threaded Rendering\n");
        long singleThreadStart = System.currentTimeMillis();
        for (int i = 1; i <= renderIterations; i++) {
            ArrayList<Variation> variations = createVariations();
            ImageMatrix img = ImageMatrix.create(imgWidth, imgHeight);
            new OneThreadImageRenderer(drawIterations, variations).render(img, symmetry);
            ImageNormalizer.correction(img);
            ImageSaver.save(img);
            System.out.println("Single-threaded image " + i + " generated.");
        }
        long singleThreadEnd = System.currentTimeMillis();
        long singleThreadTotal = singleThreadEnd - singleThreadStart;
        markdownReport.append("- **Total time:** ").append(singleThreadTotal).append(" ms\n");

        // Multi-threaded rendering
        System.out.println("\nStarting multi-threaded rendering...");
        markdownReport.append("## Multi-threaded Rendering\n");
        long multiThreadStart = System.currentTimeMillis();
        for (int i = 1; i <= renderIterations; i++) {
            ArrayList<Variation> variations = createVariations();
            ImageMatrix img = ImageMatrix.create(imgWidth, imgHeight);
            new MultyThreadImageRenderer(threadCount, drawIterations, variations).render(img, symmetry);
            ImageNormalizer.correction(img);
            ImageSaver.save(img);
            System.out.println("Multi-threaded image " + i + " generated.");
        }
        long multiThreadEnd = System.currentTimeMillis();
        long multiThreadTotal = multiThreadEnd - multiThreadStart;
        markdownReport.append("- **Total time:** ").append(multiThreadTotal).append(" ms\n");

        saveReport(markdownReport.toString());
    }

    public void generateSingleImage() {
        System.out.println("Generating a single image...");
        ArrayList<Variation> variations = createVariations();
        ImageMatrix img = ImageMatrix.create(imgWidth, imgHeight);
        new MultyThreadImageRenderer(threadCount, drawIterations, variations).render(img, symmetry);
        ImageNormalizer.correction(img);
        ImageSaver.save(img);
        System.out.println("Single image generated and saved.");
    }

    private ArrayList<Variation> createVariations() {
        ArrayList<Variation> variations = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            variations.add(Variation.create());
        }
        return variations;
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private static void saveReport(String report) {
        try (BufferedWriter writer
                 = new BufferedWriter(new FileWriter("benchmark_report.md", Charset.defaultCharset()))) {
            writer.write(report);
            System.out.println("\nBenchmark results saved to 'benchmark_report.md'.");
        } catch (IOException e) {
            System.err.println("Error saving benchmark report: " + e.getMessage());
        }
    }
}
