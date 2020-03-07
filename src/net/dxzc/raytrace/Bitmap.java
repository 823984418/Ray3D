package net.dxzc.raytrace;

public class Bitmap {

    public static interface EachPixel {

        void of(int x, int y, Pixel pixel);

    }

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        int size = width * height;
        Pixel[] ds = data = new Pixel[size];
        for (int i = 0; i < size; i++) {
            ds[i] = new Pixel();
        }
    }

    private int width;

    private int height;

    private Pixel[] data;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pixel getPixel(int x, int y) {
        return data[x + width * y];
    }

    public void setPixel(int x, int y, Pixel pixel) {
        if (pixel == null) {
            throw new NullPointerException();
        }
        data[x + width * y] = pixel;
    }

    public int[] getColors() {
        int size = width * height;
        Pixel[] ds = data;
        int[] colors = new int[size];
        for (int i = 0; i < size; i++) {
            colors[i] = ds[i].getColor();
        }
        return colors;
    }

    public void forEachPixel(EachPixel callback) {
        Pixel[] ds = data;
        int w = width;
        int h = height;
        int i = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Pixel pixel = ds[i++];
                callback.of(x, y, pixel);
            }
        }
    }

}
