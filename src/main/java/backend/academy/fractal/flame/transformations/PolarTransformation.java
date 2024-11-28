package backend.academy.fractal.flame.variations;

import backend.academy.fractal.flame.ImageMatrix;

public class PolarTransformation extends BaseTransformation {

    public PolarTransformation(ImageMatrix img) {
        super(img);
        xMin = yMin;
        xMax = yMax;
    }

    @Override
    double countX(double x, double y) {
        return Math.atan2(y, x) / Math.PI;
    }

    @Override
    double countY(double x, double y) {
        return Math.sqrt(x * x + y * y) - 1;
    }
}
