package backend.academy.fractal.flame.render;

import backend.academy.fractal.flame.ImageMatrix;
import backend.academy.fractal.flame.Pixel;
import backend.academy.fractal.flame.PixelXY;
import backend.academy.fractal.flame.Variation;
import backend.academy.fractal.flame.transformations.BaseTransformation;
import backend.academy.fractal.flame.transformations.DiscTransformation;
import backend.academy.fractal.flame.transformations.HeartTransformation;
import backend.academy.fractal.flame.transformations.LinearTransformation;
import backend.academy.fractal.flame.transformations.PolarTransformation;
import backend.academy.fractal.flame.transformations.SinusoidalTransformation;
import backend.academy.fractal.flame.transformations.SphericalTransformation;
import java.util.ArrayList;
import java.util.Random;
import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract public class ImageRenderer {
    int iterations;
    ArrayList<Variation> variations;

    /**
     * You can understand this magic by reading that <a href="https://habr.com/ru/articles/251537/">article</a>
     */
    public void render(ImageMatrix img, int symmetry) {
        // коэфиценты соонтношения для генерации без тёмных областей по бокам
        int imgWidth  = img.width();
        int imgHeight = img.height();
        double xMax = (double) imgWidth / imgHeight;
        double xMin = -xMax;
        double yMin = -1;
        double yMax = 1;

        Random r = new Random();
        double x = r.nextDouble(xMin, xMax);
        double y = r.nextDouble(yMin, yMax);

        // Применяем одну из трансформаций
        Random random = new Random();
        BaseTransformation[] transformations = {
            new DiscTransformation(img),
            new HeartTransformation(img),
            new LinearTransformation(img),
            new PolarTransformation(img),
            new SinusoidalTransformation(img),
            new SphericalTransformation(img)
        };
        BaseTransformation transformation = transformations[random.nextInt(transformations.length)];

        // Первые 20 итераций точку не рисуем, т.к. сначала надо найти начальную
        final int SKIP_STEPS = 20;
        for (int step = 0; step < SKIP_STEPS; step++) {
            //Выбираем одно из аффинных преобразований
            Variation variation = variations.get(r.nextInt(0, variations.size()));
            //и применяем его
            x = variation.a() * x + variation.b() * y + variation.c();
            y = variation.d() * x + variation.e() * y + variation.f();
        }

        draw(img, transformation, x, y, symmetry);
    }

    abstract void draw(ImageMatrix img, BaseTransformation transformation, double startX, double startY, int symmetry);

    void drawDefault(int nThreads, ImageMatrix img, BaseTransformation trans, double startX, double startY, int sym) {
        double x = startX;
        double y = startY;
        Random r = new Random();
        double angle = 0.0;
        for (int step = 0; step < iterations / nThreads / sym; step++) {
            Variation variation = variations.get(r.nextInt(0, variations.size()));
            x = variation.a() * x + variation.b() * y + variation.c();
            y = variation.d() * x + variation.e() * y + variation.f();
            ArrayList<PixelXY> symmetryPixels = new ArrayList<>();
            symmetryPixels.add(trans.getNextXY(x, y));
            // Вычисляем координаты точки и симметричных ей точек
            for (int i = 1; i < sym; i++) {
                angle += Math.PI * 2 / sym;
                symmetryPixels.add(rotatePixelXY(symmetryPixels.getFirst(), angle, img.width(), img.height()));
            }
            for (PixelXY symmetryPixel : symmetryPixels) {
                Pixel p = img.pixel(symmetryPixel.x(), symmetryPixel.y());
                // Рисуем точку только если она попала в область изображения
                if (p == null) {
                    continue;
                }
                // Задаём цвет точки
                int pCnt = p.cnt();
                if (pCnt == 0) {
                    //Попали в первый раз, берем стартовый цвет у соответствующего аффинного преобразования
                    p.r(p.r());
                    p.g(p.g());
                    p.b(p.b());
                } else {
                    //Попали не в первый раз, считаем так:
                    p.r((p.r() + variation.pixel().r()) / 2);
                    p.g((p.g() + variation.pixel().g()) / 2);
                    p.b((p.b() + variation.pixel().b()) / 2);
                }
                //Увеличиваем счетчик точки на единицу
                p.cnt(pCnt + 1);
            }
        }
    }

    PixelXY rotatePixelXY(PixelXY p, double angle, int imgWidth, int imgHeight) {
        // Центр изображения
        int centerX = imgWidth / 2;
        int centerY = imgHeight / 2;
        // Перевод в систему координат центра
        double translatedX = p.x() - centerX;
        double translatedY = p.y() - centerY;
        // Поворот точки вокруг центра
        int rotatedX = (int) (translatedX * Math.cos(angle) - translatedY * Math.sin(angle));
        int rotatedY = (int) (translatedX * Math.sin(angle) + translatedY * Math.cos(angle));
        // Возврат в исходную систему координат
        return new PixelXY(rotatedX + centerX, rotatedY + centerY);
    }
}
