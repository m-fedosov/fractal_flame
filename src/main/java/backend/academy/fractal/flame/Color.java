package backend.academy.fractal.flame;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Random;

@AllArgsConstructor
@Getter
public enum Color {
    BLACK(0, 0, 0),
    SEA_BLUE(11, 106, 136),
    SKY_BLUE(45, 197, 244),
    TEAL_BLUE(11, 106, 136),
    PURPLE(112, 50, 126),
    LAVENDER(146, 83, 161),
    CRIMSON(164, 41, 99),
    CHERRY_RED(236, 1, 90),
    PINK(240, 99, 164),
    SALMON(241, 97, 100),
    ORANGE(248, 158, 79),
    SUN_YELLOW(252, 238, 33);

    final int r, g, b;

    public static Color getRandom() {
        Color[] colors = values();
        Random r = new Random();
        return colors[r.nextInt(colors.length)];
    }
}
