package backend.academy.fractal.flame.render;

import backend.academy.fractal.flame.ImageMatrix;
import backend.academy.fractal.flame.Variation;
import backend.academy.fractal.flame.transformations.BaseTransformation;
import java.util.ArrayList;

public class OneThreadImageRenderer extends ImageRenderer {

    public OneThreadImageRenderer(int iterations, ArrayList<Variation> variations) {
        super(iterations, variations);
    }

    @Override
    void draw(ImageMatrix img, BaseTransformation transformation, double startX, double startY, int symmetry) {
        drawDefault(1, img, transformation, startX, startY, symmetry);
    }
}
