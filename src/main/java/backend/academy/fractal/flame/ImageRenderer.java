package backend.academy.fractal.flame;

import backend.academy.fractal.flame.transformations.BaseTransformation;
import backend.academy.fractal.flame.transformations.DiscTransformation;
import backend.academy.fractal.flame.transformations.HeartTransformation;
import backend.academy.fractal.flame.transformations.LinearTransformation;
import backend.academy.fractal.flame.transformations.PolarTransformation;
import backend.academy.fractal.flame.transformations.SinusoidalTransformation;
import backend.academy.fractal.flame.transformations.SphericalTransformation;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Random;

@AllArgsConstructor
abstract public class ImageRenderer {
    int iterations;
    ArrayList<Variation> variations;

    /**
     * You can understand this magic by reading that <a href="https://habr.com/ru/articles/251537/">article</a>
     */
    public void render(ImageMatrix img) {
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
        int SKIP_STEPS = 20;
        for (int step = 0; step < SKIP_STEPS; step++) {
            //Выбираем одно из аффинных преобразований
            Variation variation = variations.get(r.nextInt(0, variations.size()));
            //и применяем его
            x = variation.a() * x + variation.b() * y + variation.c();
            y = variation.d() * x + variation.e() * y + variation.f();
        }

        draw(img, transformation, x, y);
    }

    abstract void draw(ImageMatrix img, BaseTransformation transformation, double startX, double startY);

    void drawDefault(int nThreads, ImageMatrix img, BaseTransformation transformation, double startX, double startY) {
        double x = startX, y = startY;
        Random r = new Random();
        for (int step = 0; step < iterations / nThreads; step++) {
            Variation variation = variations.get(r.nextInt(0, variations.size()));
            x = variation.a() * x + variation.b() * y + variation.c();
            y = variation.d() * x + variation.e() * y + variation.f();
            //Вычисляем координаты точки, а затем задаем цвет
            PixelXY pixelXY = transformation.getNextXY(x, y);
            Pixel p = img.pixel(pixelXY.x(), pixelXY.y());
            //Если точка попала в область изображения
            if (p == null) {
                continue;
            }
            //то проверяем, первый ли раз попали в нее
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
