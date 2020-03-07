package net.dxzc.raytrace.textures;

import net.dxzc.raytrace.Pixel;
import net.dxzc.raytrace.RayQueue;
import net.dxzc.raytrace.Texture;

public class Mirror extends Texture {

    public Mirror(float rs, float gs, float bs) {
        redSub = rs;
        greenSub = gs;
        blueSub = bs;
    }

    private float redSub, greenSub, blueSub;

    @Override
    public void ray(RayQueue queue, Pixel pixel, float iy, float iz, float redPow, float greenPow, float bluePow) {
        float rr = redPow * redSub;
        float gr = greenPow * greenSub;
        float br = bluePow * blueSub;
        pixel.setColor(0, 0, 0, redPow - rr, greenPow - gr, bluePow - br);
        queue.newTask(pixel, rr, gr, br, 0, iy, -iz);
    }

}
