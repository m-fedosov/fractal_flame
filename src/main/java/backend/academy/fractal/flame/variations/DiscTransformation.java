package backend.academy.fractal.flame.variations;

import backend.academy.fractal.flame.ImageMatrix;
import backend.academy.fractal.flame.PixelXY;

public class DiscTransformation implements BaseTransformation {
    double xMax, xMin, yMax, yMin;
    int width, height;

    public DiscTransformation(ImageMatrix img) {
        width = img.width();
        height = img.height();

        xMax = (double) width / height;
        xMin = -xMax;
        yMin = -1;
        yMax = 1;
    }

    @Override
    public PixelXY getNextXY(double x, double y) {
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        double newX = (1 / Math.PI) * theta * Math.sin(Math.PI * r);
        double newY = (1 / Math.PI) * theta * Math.cos(Math.PI * r);

        int x1 = width - (int) (((xMax - newX) / (xMax - xMin)) * width);
        int y1 = height - (int) (((yMax - newY) / (yMax - yMin)) * height);
        return new PixelXY(x1, y1);
    }
}

