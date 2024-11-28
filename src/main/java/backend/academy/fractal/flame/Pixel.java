package backend.academy.fractal.flame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pixel {
    private int r, g, b;     // Pixel color
    private int cnt = 0; // How many times we paint this pixel
    private double normal;   // Like opacity - pixel with more counter is brighter

    Pixel (int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
