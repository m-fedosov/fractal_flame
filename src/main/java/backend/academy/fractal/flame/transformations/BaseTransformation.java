package backend.academy.fractal.flame.transformations;

import backend.academy.fractal.flame.ImageMatrix;
import backend.academy.fractal.flame.PixelXY;
import java.util.Random;

abstract public class BaseTransformation {
    double xMax;
    double xMin;
    double yMax;
    double yMin;
    int width;
    int height;
    Random r;

    BaseTransformation(ImageMatrix img) {
        r = new Random();
        width  = img.width();
        height = img.height();

        xMax = (double) width / height;
        xMin = -xMax;
        yMin = -1;
        yMax = 1;
    }

    public PixelXY getNextXY(double x, double y) {
        int x1 = width  - (int) (((xMax - countX(x, y)) / (xMax - xMin)) * width);
        int y1 = height - (int) (((yMax - countY(x, y)) / (yMax - yMin)) * height);
        return new PixelXY(x1, y1);
    }

    abstract double countX(double x, double y);

    abstract double countY(double x, double y);
}
