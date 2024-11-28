package backend.academy.fractal.flame;

import java.util.ArrayList;
import java.util.Random;

public class Renderer {

    /**
     * You can understand this magic by reading that <a href="https://habr.com/ru/articles/251537/">article</a>
     */
    public static void render(int iterations, ArrayList<Variation> variations, Image image) {
        // коэфиценты соонтношения для генерации без тёмных областей по бокам
        double xMax = (double) image.width() / image.height();
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
            if (step >= 0) {
                //Вычисляем координаты точки, а затем задаем цвет
                int x1 = image.width()  - (int) (((xMax - newX) / (xMax - xMin)) * image.width());
                int y1 = image.height() - (int) (((yMax - newY) / (yMax - yMin)) * image.height());
                Pixel p = image.pixel(x1, y1);
                //Если точка попала в область изображения
                if (p != null) {
                    //то проверяем, первый ли раз попали в нее
                    int p_cnt = p.counter();
                    if (p_cnt == 0) {
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
                    p.counter(p_cnt + 1);
                }
            }
        }
    }

}
