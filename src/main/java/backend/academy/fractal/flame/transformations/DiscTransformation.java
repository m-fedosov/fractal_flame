package backend.academy.fractal.flame.transformations;

import backend.academy.fractal.flame.ImageMatrix;

public class DiscTransformation extends BaseTransformation {
    public DiscTransformation(ImageMatrix img) {
        super(img);
    }

    @Override
    double countX(double x, double y) {
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        return (1 / Math.PI) * theta * Math.sin(Math.PI * r);
    }

    @Override
    double countY(double x, double y) {
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        return (1 / Math.PI) * theta * Math.cos(Math.PI * r);
    }
}

