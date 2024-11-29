package backend.academy.fractal.flame.transformations;

import backend.academy.fractal.flame.ImageMatrix;

public class HeartTransformation extends BaseTransformation {
    public HeartTransformation(ImageMatrix img) {
        super(img);
    }

    @Override
    double countX(double x, double y) {
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        return r * Math.sin(r * theta);
    }

    @Override
    double countY(double x, double y) {
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        return -r * Math.cos(r * theta);
    }
}
