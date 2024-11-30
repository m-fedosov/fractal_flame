package backend.academy.fractal.flame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pixel {
    volatile private int r;
    volatile private int g;
    volatile private int b;     // Pixel color
    volatile private int cnt = 0; // How many times we paint this pixel
    private double normal;   // Like opacity - pixel with more counter is brighter

    Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
