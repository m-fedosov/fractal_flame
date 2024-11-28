package backend.academy.fractal.flame;

import lombok.Getter;
import java.util.Random;

@Getter
public class Variation {
    private Pixel pixel;
    private Double a;
    private Double b;
    private Double c;
    private Double d;
    private Double e;
    private Double f;

    private Variation() {}

    /**
     * It's a real magic, read about it <a href="https://habr.com/ru/articles/251537/">here</a>
     */
    public static Variation create() {
        Variation coeficents = new Variation();
        Random r = new Random();
        while (true) {
            coeficents.a = r.nextDouble(-1, 1);
            coeficents.b = r.nextDouble(-1, 1);
            coeficents.c = r.nextDouble(-1, 1);
            coeficents.d = r.nextDouble(-1.5, 1.5);
            coeficents.e = r.nextDouble(-1, 1);
            coeficents.f = r.nextDouble(-1.5, 1.5);
            if (coeficents.isGoodForAffineCoefficient()) {
                Color color = Color.getRandom();
                coeficents.pixel = new Pixel(color.r(),color.g(), color.b(), 0);
                return coeficents;
            }
        }
    }

    private boolean isGoodForAffineCoefficient() {
        if (Math.sqrt(a) + Math.sqrt(d) >= 1) {
            return false;
        }
        if (Math.sqrt(b) + Math.sqrt(e) >= 1) {
            return false;
        }
        if (Math.sqrt(a) + Math.sqrt(b) + Math.sqrt(d) + Math.sqrt(e) >= 1 + Math.sqrt(a * e - b * d)) {
            return false;
        }
        return true;
    }
}
