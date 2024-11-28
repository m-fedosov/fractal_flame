package backend.academy.fractal.flame.variations;

import backend.academy.fractal.flame.ImageMatrix;

public class SphericalTransformation extends BaseTransformation {

    public SphericalTransformation(ImageMatrix img) {
        super(img);
    }

    @Override
    double countX(double x, double y) {
        double rSquared = x * x + y * y;
        return x / rSquared;
    }

    @Override
    double countY(double x, double y) {
        double rSquared = x * x + y * y;
        return y / rSquared;
    }

}
