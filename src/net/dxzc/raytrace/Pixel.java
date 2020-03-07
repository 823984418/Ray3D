package net.dxzc.raytrace;

public class Pixel {

    public static int getInit(int i, int max) {
        if (i < 0) {
            return 0;
        }
        if (i > max) {
            return max;
        }
        return i;
    }

    public static int getColor(float r, float g, float b) {
        int ri = getInit((int) (r * 256), 255);
        int gi = getInit((int) (g * 256), 255);
        int bi = getInit((int) (b * 256), 255);
        return (0xFF << 24) + (ri << 16) + (gi << 8) + bi;
    }

    public static float getRed(int c) {
        return ((c >>> 16) & 255) / 256.0f;
    }

    public static float getGreen(int c) {
        return ((c >>> 8) & 255) / 256.0f;
    }

    public static float getBlue(int c) {
        return (c & 255) / 256.0f;
    }

    public Pixel() {
    }

    private float red, green, blue;

    private float redPow, greenPow, bluePow;

    public float getRed() {
        return red / redPow;
    }

    public float getGreen() {
        return green / greenPow;
    }

    public float getBlue() {
        return blue / bluePow;
    }

    public int getColor() {
        return getColor(getRed(), getGreen(), getBlue());
    }

    protected void onLook(float[] transfor) {
    }

    public synchronized void setColor(float r, float g, float b, float rp, float gp, float bp) {
        red += r * rp;
        green += g * gp;
        blue += b * bp;
        redPow += rp;
        greenPow += gp;
        bluePow += bp;
    }

    @Override
    public String toString() {
        return "Pixel{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
