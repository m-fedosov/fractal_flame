package backend.academy.fractal.flame.variations;

import backend.academy.fractal.flame.PixelXY;

public interface BaseTransformation {
    PixelXY getNextXY(double x, double y);
}
