package backend.academy.fractal.flame.transformations;

import backend.academy.fractal.flame.ImageMatrix;
import backend.academy.fractal.flame.PixelXY;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformationTest {
    private static ImageMatrix img;

    @BeforeAll
    static void setImg() {
        img = ImageMatrix.create(100, 100);
    }

    @Test
    void discTransformationGetNextXYTest() {
        PixelXY pixelXY;
        DiscTransformation transformation = new DiscTransformation(img);
        pixelXY = transformation.getNextXY(0, 0);
        assertEquals(50, pixelXY.x());
        assertEquals(50, pixelXY.y());

        pixelXY = transformation.getNextXY(100, 100);
        assertEquals(38, pixelXY.x());
        assertEquals(47, pixelXY.y());

        pixelXY = transformation.getNextXY(50, 50);
        assertEquals(60, pixelXY.x());
        assertEquals(43, pixelXY.y());

        pixelXY = transformation.getNextXY(42, 24);
        assertEquals(58, pixelXY.x());
        assertEquals(54, pixelXY.y());
    }

    @Test
    void heartTransformationGetNextXYTest() {
        PixelXY pixelXY;
        HeartTransformation transformation = new HeartTransformation(img);
        pixelXY = transformation.getNextXY(0, 0);
        assertEquals(50, pixelXY.x());
        assertEquals(50, pixelXY.y());

        pixelXY = transformation.getNextXY(5, 5);
        assertEquals(-185, pixelXY.x());
        assertEquals(-213, pixelXY.y());

        pixelXY = transformation.getNextXY(10, 10);
        assertEquals(-652, pixelXY.x());
        assertEquals(-28, pixelXY.y());

        pixelXY = transformation.getNextXY(20, 20);
        assertEquals(-263, pixelXY.x());
        assertEquals(1429, pixelXY.y());
    }

    @Test
    void linearTransformationGetNextXYTest() {
        PixelXY pixelXY;
        LinearTransformation transformation = new LinearTransformation(img);
        pixelXY = transformation.getNextXY(0, 0);
        assertEquals(50, pixelXY.x());
        assertEquals(50, pixelXY.y());

        pixelXY = transformation.getNextXY(5, 5);
        assertEquals(300, pixelXY.x());
        assertEquals(300, pixelXY.y());

        pixelXY = transformation.getNextXY(10, 10);
        assertEquals(550, pixelXY.x());
        assertEquals(550, pixelXY.y());

        pixelXY = transformation.getNextXY(20, 20);
        assertEquals(1050, pixelXY.x());
        assertEquals(1050, pixelXY.y());
    }

    @Test
    void polarTransformationGetNextXYTest() {
        PixelXY pixelXY;
        PolarTransformation transformation = new PolarTransformation(img);
        pixelXY = transformation.getNextXY(0, 0);
        assertEquals(50, pixelXY.x());
        assertEquals(0, pixelXY.y());

        pixelXY = transformation.getNextXY(5, 5);
        assertEquals(63, pixelXY.x());
        assertEquals(353, pixelXY.y());

        pixelXY = transformation.getNextXY(10, 10);
        assertEquals(63, pixelXY.x());
        assertEquals(707, pixelXY.y());

        pixelXY = transformation.getNextXY(20, 20);
        assertEquals(63, pixelXY.x());
        assertEquals(1414, pixelXY.y());
    }

    @Test
    void sinusoidalTransformationGetNextXYTest() {
        PixelXY pixelXY;
        SinusoidalTransformation transformation = new SinusoidalTransformation(img);
        pixelXY = transformation.getNextXY(0, 0);
        assertEquals(50, pixelXY.x());
        assertEquals(50, pixelXY.y());

        pixelXY = transformation.getNextXY(5, 5);
        assertEquals(3, pixelXY.x());
        assertEquals(3, pixelXY.y());

        pixelXY = transformation.getNextXY(10, 10);
        assertEquals(23, pixelXY.x());
        assertEquals(23, pixelXY.y());

        pixelXY = transformation.getNextXY(20, 20);
        assertEquals(96, pixelXY.x());
        assertEquals(96, pixelXY.y());
    }

    @Test
    void sphericalTransformationGetNextXYTest() {
        PixelXY pixelXY;
        SphericalTransformation transformation = new SphericalTransformation(img);
        pixelXY = transformation.getNextXY(0, 0);
        assertEquals(100, pixelXY.x());
        assertEquals(100, pixelXY.y());

        pixelXY = transformation.getNextXY(5, 5);
        assertEquals(55, pixelXY.x());
        assertEquals(55, pixelXY.y());

        pixelXY = transformation.getNextXY(10, 10);
        assertEquals(53, pixelXY.x());
        assertEquals(53, pixelXY.y());

        pixelXY = transformation.getNextXY(20, 20);
        assertEquals(52, pixelXY.x());
        assertEquals(52, pixelXY.y());
    }
}

