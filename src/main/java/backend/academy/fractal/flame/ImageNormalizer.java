package backend.academy.fractal.flame;

public class ImageNormalizer {

    /**
     * You can understand this magic by reading that <a href="https://habr.com/ru/articles/251537/">article</a>
     */
    public static void correction(ImageMatrix image) {
        double max = 0.0;
        double gamma = 1.5;
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                Pixel p = image.pixel(x, y);
                if (p.cnt() != 0) {
                    p.normal(Math.log10(p.cnt()));
                    if (p.normal() > max) {
                        max = p.normal();
                    }
                }
            }
        }
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                Pixel p = image.pixel(x, y);
                p.normal(p.normal() / max);
                double nCoefficient = Math.pow(p.normal(), (1.0 / gamma));
                p.r((int) (p.r() * nCoefficient));
                p.g((int) (p.g() * nCoefficient));
                p.g((int) (p.b() * nCoefficient));
            }
        }
    }
}
