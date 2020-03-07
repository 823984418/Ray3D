package net.dxzc.raytrace.textures;

import net.dxzc.raytrace.Pixel;
import net.dxzc.raytrace.RayQueue;
import net.dxzc.raytrace.Texture;

public class Glass extends Texture {

    public static class Media {

        public static Media AIR = new Media(1, 1f, 1f, 1f);

        public static Media WATER = new Media(1.3330f, 1f, 1f, 1f);

        public static Media JEWEL = new Media(1.2f, 0.999f, 0.999f, 1f);

        public Media(float ra, float rs, float gs, float bs) {
            ratio = ra;
            redSub = rs;
            greenSub = gs;
            blueSub = bs;
        }

        private float ratio;

        private float redSub, greenSub, blueSub;

    }

    public Glass(Media out, Media into, float fs) {
        this.out = out;
        this.into = into;
        this.fs = fs;
    }

    private Media out;

    private Media into;

    private float fs;

    @Override
    public void ray(RayQueue queue, Pixel pixel, float iy, float iz, float redPow, float greenPow, float bluePow) {
        Media os = out;
        Media is = into;
        float ra = os.ratio / is.ratio;
        float far = (float) Math.sqrt(iy * iy + iz * iz);
        float rf = (float) Math.pow(out.redSub, far);
        float gf = (float) Math.pow(out.greenSub, far);
        float bf = (float) Math.pow(out.blueSub, far);
        float rs = redPow * rf;
        float gs = greenPow * gf;
        float bs = bluePow * bf;
        pixel.setColor(0, 0, 0, redPow - rs, greenPow - gs, bluePow - bs);
        if (redPow + greenPow + bluePow < 0.08f) {
            return;
        }
        float sin = iy / far;
        float cos = iz / far;
        float reflex = fs * Math.min(1, (float) Math.pow(sin * ra, 5));
        float refract = 1 - reflex;
        if (sin * ra < 1) {
            queue.newTask(pixel, rs * refract, gs * refract, bs * refract, 0, iy * ra, iz);
        }
        queue.newTask(pixel, rs * reflex, gs * reflex, bs * reflex, 0, iy, -iz);
    }


}
