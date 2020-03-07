package net.dxzc.raytrace.textures;

import net.dxzc.raytrace.Pixel;
import net.dxzc.raytrace.RayQueue;
import net.dxzc.raytrace.Texture;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Gauss extends Texture {

    public static class PowIterator {

        public static class Builder {

            private static class Point {

                private Point(float x, float y) {
                    this.x = x;
                    this.y = y;
                }

                private float x, y;

                @Override
                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (o instanceof Point) {
                        Point point = (Point) o;
                        return Float.compare(point.x, x) == 0 &&
                                Float.compare(point.y, y) == 0;
                    }
                    return false;
                }

                @Override
                public int hashCode() {
                    return Float.hashCode(x) ^ Float.hashCode(y);
                }

            }

            private HashMap<Point, Float> points = new HashMap<>();

            public Builder addPoint(float x, float y, float pow) {
                Point p = new Point(x, y);
                Float v = points.get(p);
                if (v == null) {
                    points.put(p, pow);
                } else {
                    points.put(p, v + pow);
                }
                return this;
            }

            public Builder addPowIterator(PowIterator add, float b, float p) {
                int s = add.size;
                float[] xs = add.dxs, ys = add.dys, ps = add.pows;
                for (int i = 0; i < s; i++) {
                    addPoint(b * xs[i], b * ys[i], p * ps[i]);
                }
                return this;
            }

            public PowIterator build() {
                int size = points.size();
                float[] xs = new float[size];
                float[] ys = new float[size];
                float[] ps = new float[size];
                int i = 0;
                float pps = 0;
                for (Map.Entry<Point, Float> e : points.entrySet()) {
                    Point p = e.getKey();
                    xs[i] = p.x;
                    ys[i] = p.y;
                    float pp = e.getValue();
                    ps[i] = pp;
                    pps += pp;
                    i++;
                }
                pps *= 8;
                for (i = 0; i < size; i++) {
                    ps[i] /= pps;
                }
                return new PowIterator(size, xs, ys, ps);
            }

        }

        public PowIterator(int size, float[] dxs, float[] dys, float[] pows) {
            this.size = size;
            this.dxs = dxs;
            this.dys = dys;
            this.pows = pows;
        }

        private int size;

        private float[] dxs;

        private float[] dys;

        private float[] pows;

        @Override
        public String toString() {
            int s = size;
            if (s == 1) {
                return "PowIterator{}";
            }
            float[] gi = pows, xs = dxs, ys = dys;
            StringBuilder sb = new StringBuilder("PowIterator{");
            sb.append(xs[0]);
            sb.append(", ");
            sb.append(ys[0]);
            sb.append(":");
            sb.append(gi[0]);
            for (int i = 1; i < s; i++) {
                sb.append("|");
                sb.append(xs[i]);
                sb.append(", ");
                sb.append(ys[i]);
                sb.append(":");
                sb.append(gi[i]);
            }
            sb.append("}");
            return sb.toString();
        }

        private static void appendFloatArray(StringBuilder sb, float[] array, int size) {
            if (size == 0) {
                sb.append("new float[]{}");
                return;
            }
            sb.append("new float[]{");
            sb.append(array[0]);
            for (int i = 1; i < size; i++) {
                sb.append(", ");
                sb.append(array[i]);
            }
            sb.append(")");
        }

        public String toSource() {
            int s = size;
            StringBuilder sb = new StringBuilder("new Gauss.PowIterator(");
            sb.append(s);
            sb.append(", ");
            appendFloatArray(sb, dxs, size);
            sb.append(", ");
            appendFloatArray(sb, dys, size);
            sb.append(", ");
            appendFloatArray(sb, pows, s);
            sb.append(")");
            return sb.toString();
        }
    }

    public Gauss(float r, float g, float b, PowIterator gauss) {
        gaussRedPow = r;
        gaussGreenPow = g;
        gaussBluePow = b;
        powIterator = gauss;
    }

    private float gaussRedPow, gaussGreenPow, gaussBluePow;

    private PowIterator powIterator;

    @Override
    public void ray(RayQueue queue, Pixel pixel, float iy, float iz, float redPow, float greenPow, float bluePow) {
        if (redPow + greenPow + bluePow < 0.1f) {
            pixel.setColor(gaussRedPow, gaussGreenPow, gaussBluePow, redPow, greenPow, bluePow);
            return;
        }
        float r = (float) Math.sqrt(iy * iy + iz * iz);
        iy /= r;
        iz /= -r;
        float[] dxs = powIterator.dxs;
        float[] dys = powIterator.dys;
        float[] gi = powIterator.pows;
        int size = powIterator.size;
        float grp = gaussRedPow * redPow, ggp = gaussGreenPow * greenPow, gbp = gaussBluePow * bluePow;
        pixel.setColor(0, 0, 0, redPow - grp, greenPow - ggp, bluePow - gbp);
        for (int i = 0; i < size; i++) {
            float x = dxs[i];
            float y = dys[i];
            float p = gi[i];
            if (Float.compare(x, 0) == 0) {
                if (Float.compare(y, 0) == 0) {
                    float rp = grp * p * 8, gp = ggp * p * 8, bp = gbp * p * 8;
                    queue.newTask(pixel, rp, gp, bp, 0, iy, iz);
                } else {
                    float rp = grp * p * 2, gp = ggp * p * 2, bp = gbp * p * 2;
                    queue.newTask(pixel, rp, gp, bp, 0, iy + y, iz);
                    queue.newTask(pixel, rp, gp, bp, 0, iy - y, iz);
                    queue.newTask(pixel, rp, gp, bp, y, iy, iz);
                    queue.newTask(pixel, rp, gp, bp, -y, iy, iz);
                }
            } else if (Float.compare(x, y) == 0) {
                float rp = grp * p * 2, gp = ggp * p * 2, bp = gbp * p * 2;
                queue.newTask(pixel, rp, gp, bp, x, iy + x, iz);
                queue.newTask(pixel, rp, gp, bp, x, iy - x, iz);
                queue.newTask(pixel, rp, gp, bp, -x, iy + x, iz);
                queue.newTask(pixel, rp, gp, bp, -x, iy - x, iz);
            } else {
                float rp = grp * p, gp = ggp * p, bp = gbp * p;
                queue.newTask(pixel, rp, gp, bp, x, iy + y, iz);
                queue.newTask(pixel, rp, gp, bp, -x, iy + y, iz);
                queue.newTask(pixel, rp, gp, bp, x, iy - y, iz);
                queue.newTask(pixel, rp, gp, bp, -x, iy - y, iz);
                queue.newTask(pixel, rp, gp, bp, y, iy + x, iz);
                queue.newTask(pixel, rp, gp, bp, -y, iy + x, iz);
                queue.newTask(pixel, rp, gp, bp, y, iy - x, iz);
                queue.newTask(pixel, rp, gp, bp, -y, iy - x, iz);
            }
        }
    }
}
