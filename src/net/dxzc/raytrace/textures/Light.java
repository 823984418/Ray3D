package net.dxzc.raytrace.textures;

import net.dxzc.raytrace.Pixel;
import net.dxzc.raytrace.RayQueue;
import net.dxzc.raytrace.Texture;

public class Light extends Texture {

    public Light(int color) {
        this(Pixel.getRed(color), Pixel.getGreen(color), Pixel.getBlue(color));
    }

    public Light(float r, float g, float b) {
        red = r;
        green = g;
        blue = b;
    }

    private float red, green, blue;

    @Override
    public void ray(RayQueue queue, Pixel pixel, float iy, float iz, float redPow, float greenPow, float bluePow) {
        pixel.setColor(red, green, blue, redPow, greenPow, bluePow);
    }

}
