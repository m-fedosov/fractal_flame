package backend.academy.fractal.flame;

import backend.academy.fractal.flame.transformations.BaseTransformation;
import java.util.ArrayList;
import java.util.Random;

public class OneThreadImageRenderer extends ImageRenderer {

    public OneThreadImageRenderer(int iterations, ArrayList<Variation> variations) {
        super(iterations, variations);
    }

    @Override
    void draw(ImageMatrix img, BaseTransformation transformation, double startX, double startY) {
        drawDefault(1, img, transformation, startX, startY);
    }
}
