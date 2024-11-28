package backend.academy.fractal.flame;

public record Image(Pixel[] data, int width, int height) {

    public static Image create(int width, int height) {
        Pixel[] data = new Pixel[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = Color.BLACK;
                data[x + y * width] = new Pixel(color.r(), color.g(), color.b(), 0);
            }
        }
        return new Image(data, width, height);
    }

    private boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        if (!contains(x, y)) {
            return null;
        }
        return data[x + y * width];
    }
}
