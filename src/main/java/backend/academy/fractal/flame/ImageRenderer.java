package backend.academy.fractal.flame;

import backend.academy.fractal.flame.transformations.BaseTransformation;
import backend.academy.fractal.flame.transformations.DiscTransformation;
import backend.academy.fractal.flame.transformations.HeartTransformation;
import backend.academy.fractal.flame.transformations.LinearTransformation;
import backend.academy.fractal.flame.transformations.PolarTransformation;
import backend.academy.fractal.flame.transformations.SinusoidalTransformation;
import backend.academy.fractal.flame.transformations.SphericalTransformation;
import java.util.ArrayList;
import java.util.Random;

public class ImageRenderer {

    /**
     * You can understand this magic by reading that <a href="https://habr.com/ru/articles/251537/">article</a>
     */
    public static void render(int iterations, ArrayList<Variation> variations, ImageMatrix img) {
        // коэфиценты соонтношения для генерации без тёмных областей по бокам
        int imgWidth  = img.width();
        int imgHeight = img.height();
        double xMax = (double) imgWidth / imgHeight;
        double xMin = -xMax;
        double yMin = -1;
        double yMax = 1;

        Random r = new Random();
        double newX = r.nextDouble(xMin, xMax);
        double newY = r.nextDouble(yMin, yMax);
        // Какую трансформацию применяем
        Random random = new Random();

        // Массив классов трансформаций
        BaseTransformation[] transformations = {
//            new DiscTransformation(img),
//            new HeartTransformation(img),
            new LinearTransformation(img),
            new PolarTransformation(img),
            new SinusoidalTransformation(img),
//            new SphericalTransformation(img)
        };

        // Выбор случайной трансформации
        BaseTransformation transformation = transformations[random.nextInt(transformations.length)];

        // Первые 20 итераций точку не рисуем, т.к. сначала надо найти начальную
        int SKIP_STEPS = 20;
        for (int step = -SKIP_STEPS; step < iterations; step++) {
            //Выбираем одно из аффинных преобразований
            Variation variation = variations.get(r.nextInt(0, variations.size()));
            //и применяем его
            newX = variation.a() * newX + variation.b() * newY + variation.c();
            newY = variation.d() * newX + variation.e() * newY + variation.f();
            //Применяем нелинейное преобразование;
            if (step < 0) {
                continue;
            }
            //Вычисляем координаты точки, а затем задаем цвет
            PixelXY pixelXY = transformation.getNextXY(newX, newY);
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
