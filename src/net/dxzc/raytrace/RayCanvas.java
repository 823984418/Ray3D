package net.dxzc.raytrace;

public class RayCanvas extends Canvas {

    /**
     * 求此三角形在z轴上的情况.
     * 如果三角形法线朝负或者三角形内无交点则返回{@code NaN}
     *
     * @return z轴坐标或者{@code NaN}
     */
    public static float triangle(float ax, float ay, float az,
                                 float bx, float by, float bz,
                                 float cx, float cy, float cz) {
        float e1x = bx - ax, e1y = by - ay;
        float e2x = cx - ax, e2y = cy - ay;
        float det = e1x * e2y - e1y * e2x;
        if (det <= 0) {
            return Float.NaN;
        }
        float u = ax * e1y - ay * e1x;
        if (u < 0 || u > det) {
            return Float.NaN;
        }
        float qz = e2x * ay - e2y * ax;
        if (qz < 0 || u + qz > det) {
            return Float.NaN;
        }
        float e1z = bz - az;
        float e2z = cz - az;
        float qx = e2y * az - e2z * ay, qy = e2z * ax - e2x * az;
        return (e1x * qx + e1y * qy + e1z * qz) / det;
    }

    public RayCanvas() {
        z = Float.NEGATIVE_INFINITY;
    }

    private Texture textureBuff;

    private float fox, foy, foz;

    private float z;

    public float getFox() {
        return fox;
    }

    public float getFoy() {
        return foy;
    }

    public float getFoz() {
        return foz;
    }

    public float getZ() {
        return z;
    }

    public Texture getTexture() {
        return textureBuff;
    }

    @Override
    public void draw(Texture texture,
                     float ax, float ay, float az,
                     float bx, float by, float bz,
                     float cx, float cy, float cz) {
        float b = triangle(ax, ay, az, bx, by, bz, cx, cy, cz);
        if (Float.isNaN(b) || b > -0.01f) {
            return;
        }
        if (z < b) {
            z = b;
            textureBuff = texture;
            float e1x = bx - ax, e1y = by - ay, e1z = bz - az;
            float e2x = cx - ax, e2y = cy - ay, e2z = cz - az;
            float fx = e1y * e2z - e1z * e2y;
            float fy = e1z * e2x - e1x * e2z;
            float fz = e1x * e2y - e1y * e2x;
            float r = (float) Math.sqrt(fx * fx + fy * fy + fz * fz);
            fox = fx / r;
            foy = fy / r;
            foz = fz / r;
        }
    }

}
