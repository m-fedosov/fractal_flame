package backend.academy.fractal.flame.variations;

import backend.academy.fractal.flame.ImageMatrix;
import backend.academy.fractal.flame.PixelXY;
import java.util.Random;

public class LinearTransformation implements BaseTransformation {
    double xMax, xMin, yMax, yMin;
    int width, height;
    Random r;

    public LinearTransformation(ImageMatrix img) {
        r = new Random();
        width  = img.width();
        height = img.height();

        xMax = (double) width / height;
        xMin = -xMax;
        yMin = -1;
        yMax = 1;
    }

    @Override
    public PixelXY getNextXY(double x, double y) {
        int x1 = width  - (int) (((xMax - x) / (xMax - xMin)) * width);
        int y1 = height - (int) (((yMax - y) / (yMax - yMin)) * height);
        return new PixelXY(x1, y1);
    }
}
