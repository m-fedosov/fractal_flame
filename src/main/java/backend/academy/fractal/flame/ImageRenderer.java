package backend.academy.fractal.flame;

import java.util.ArrayList;
import java.util.Random;

public class ImageRenderer {

    /**
     * You can understand this magic by reading that <a href="https://habr.com/ru/articles/251537/">article</a>
     */
    public static void render(int iterations, ArrayList<Variation> variations, ImageMatrix image) {
        // коэфиценты соонтношения для генерации без тёмных областей по бокам
        int imgWidth  = image.width();
        int imgHeight = image.height();
        double xMax = (double) imgWidth / imgHeight;
        double xMin = -xMax;
        double yMin = -1;
        double yMax = 1;

        Random r = new Random();
        double newX = r.nextDouble(xMin, xMax);
        double newY = r.nextDouble(yMin, yMax);
        //Первые 20 итераций точку не рисуем, т.к. сначала надо найти начальную
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
            int x1 = imgWidth  - (int) (((xMax - newX) / (xMax - xMin)) * imgWidth);
            int y1 = imgHeight - (int) (((yMax - newY) / (yMax - yMin)) * imgHeight);
            Pixel p = image.pixel(x1, y1);
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
