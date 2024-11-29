package backend.academy.fractal.flame.transformations;

import backend.academy.fractal.flame.ImageMatrix;

public class SinusoidalTransformation extends BaseTransformation {
    public SinusoidalTransformation(ImageMatrix img) {
        super(img);
        xMin = yMin;
        xMax = yMax;
    }

    @Override
    double countX(double x, double y) {
        return Math.sin(x);
    }

    @Override
    double countY(double x, double y) {
        return Math.sin(y);
    }
}
