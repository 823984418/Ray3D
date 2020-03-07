package net.dxzc.raytrace;

import java.util.Arrays;

public class Transfor {

    //  0  1  2  3
    //  4  5  6  7
    //  8  9 10 11
    // -- -- --  E

    private static final float[] UNIT = {
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
    };

    public static float[] getUnit() {
        return UNIT.clone();
    }

    public static float[] getMove(float dx, float dy, float dz) {
        float[] r = getUnit();
        r[3] = dx;
        r[7] = dy;
        r[11] = dz;
        return r;
    }

    /**
     * 将射线旋转到z的正半轴的旋转变换.
     *
     * @return 变换
     */
    public static float[] getRodrigues(float x, float y, float z) {
        //cos(a)I + (1 - cos(a))nnT + sin(a)n^
        float r = (float) Math.sqrt(x * x + y * y + z * z);
        x /= r;
        y /= r;
        z /= r;
        // n = (y, -x, 0)
        float cos = z;
        float zcos = 1 - z;
        float sin = (float) Math.sqrt(1 - cos * cos);
        float nx = y, ny = -x;
        if (sin != 0) {
            nx /= sin;
            ny /= sin;
        } else {
            nx = 1;
            ny = 0;
        }
        float[] t = new float[]{
                cos + zcos * nx * nx, zcos * ny * nx, ny * sin, 0,
                zcos * ny * nx, cos + zcos * ny * ny, -nx * sin, 0,
                -ny * sin, nx * sin, cos, 0,
        };
        return t;
    }

    public static float[] getDeViewZ(float gx, float gy, float gz) {
        float gr = (float) Math.sqrt(gx * gx + gy * gy + gz * gz);
        if (gr == 0) {
            throw new RuntimeException();
        }
        gx /= gr;
        gy /= gr;
        gz /= gr;
        float dot = gz;
        float tx = -gx * dot;
        float ty = -gy * dot;
        float tz = 1 - gz * dot;
        float tr = (float) Math.sqrt(tx * tx + ty * ty + tz * tz);
        if (tr == 0) {
            if (dot > 0) {
                return getUnit();
            } else {
                return new float[]{
                        1, 0, 0, 0,
                        0, -1, 0, 0,
                        0, 0, -1, 0,
                };
            }
        }
        tx /= tr;
        ty /= tr;
        tz /= tr;
        float xx = -gy, xy = gx;
        float xr = (float) Math.sqrt(xx * xx + xy * xy);
        xx /= xr;
        xy /= xr;

        float[] r = new float[]{
                xx, xy, 0, 0,
                tx, ty, tz, 0,
                gx, gy, gz, 0,
        };
        append(new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
        }, r, r);
        return r;
    }

    public static void append(float[] left, float[] right, float[] result) {
        float t0 = left[0] * right[0] + left[1] * right[4] + left[2] * right[8];
        float t1 = left[0] * right[1] + left[1] * right[5] + left[2] * right[9];
        float t2 = left[0] * right[2] + left[1] * right[6] + left[2] * right[10];
        float t3 = left[0] * right[3] + left[1] * right[7] + left[2] * right[11]
                + left[3];

        float t4 = left[4] * right[0] + left[5] * right[4] + left[6] * right[8];
        float t5 = left[4] * right[1] + left[5] * right[5] + left[6] * right[9];
        float t6 = left[4] * right[2] + left[5] * right[6] + left[6] * right[10];
        float t7 = left[4] * right[3] + left[5] * right[7] + left[6] * right[11]
                + left[7];

        float t8 = left[8] * right[0] + left[9] * right[4] + left[10] * right[8];
        float t9 = left[8] * right[1] + left[9] * right[5] + left[10] * right[9];
        float t10 = left[8] * right[2] + left[9] * right[6] + left[10] * right[10];
        float t11 = left[8] * right[3] + left[9] * right[7] + left[10] * right[11]
                + left[11];
        result[0] = t0;
        result[1] = t1;
        result[2] = t2;
        result[3] = t3;
        result[4] = t4;
        result[5] = t5;
        result[6] = t6;
        result[7] = t7;
        result[8] = t8;
        result[9] = t9;
        result[10] = t10;
        result[11] = t11;
    }

    public static float getX(float[] transfor, float ox, float oy, float oz) {
        return transfor[0] * ox + transfor[1] * oy + transfor[2] * oz + transfor[3];
    }

    public static float getY(float[] transfor, float ox, float oy, float oz) {
        return transfor[4] * ox + transfor[5] * oy + transfor[6] * oz + transfor[7];
    }

    public static float getZ(float[] transfor, float ox, float oy, float oz) {
        return transfor[8] * ox + transfor[9] * oy + transfor[10] * oz + transfor[11];
    }

    public static float getMod(float[] transfor) {
        return transfor[0] * transfor[5] * transfor[10] +
                transfor[1] * transfor[6] * transfor[8] +
                transfor[2] * transfor[4] * transfor[9] -
                transfor[0] * transfor[6] * transfor[9] -
                transfor[1] * transfor[4] * transfor[10] -
                transfor[2] * transfor[5] * transfor[8];
    }

    public static void debug(float[] transfor) {
        System.out.printf(
                "%s\n" +
                        "%f %f %f %f\n" +
                        "%f %f %f %f\n" +
                        "%f %f %f %f\n",
                transfor.toString(),
                transfor[0], transfor[1], transfor[2], transfor[3],
                transfor[4], transfor[5], transfor[6], transfor[7],
                transfor[8], transfor[9], transfor[10], transfor[11]
        );
    }

}
