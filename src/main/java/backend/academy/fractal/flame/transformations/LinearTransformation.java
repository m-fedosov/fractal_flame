package backend.academy.fractal.flame.variations;

import backend.academy.fractal.flame.ImageMatrix;

public class LinearTransformation extends BaseTransformation {
    public LinearTransformation(ImageMatrix img) {
        super(img);
    }

    @Override
    double countX(double x, double y) {
        return x;
    }

    @Override
    double countY(double x, double y) {
        return y;
    }
}
