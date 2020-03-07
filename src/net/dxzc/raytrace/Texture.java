package net.dxzc.raytrace;

public abstract class Texture {

    public static void initId(Texture[] textures) {
        for (int i = 0, l = textures.length; i < l; i++) {
            Texture texture = textures[i];
            if (texture != null) {
                texture.id = i;
            }
        }
    }

    public int id;

    public abstract void ray(RayQueue queue, Pixel pixel, float iy, float iz, float redPow, float greenPow, float bluePow);

}
