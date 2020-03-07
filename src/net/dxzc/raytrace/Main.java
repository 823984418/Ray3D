package net.dxzc.raytrace;

import net.dxzc.raytrace.java.BitmapImage;
import net.dxzc.raytrace.textures.Gauss;
import net.dxzc.raytrace.textures.Glass;
import net.dxzc.raytrace.textures.Light;
import net.dxzc.raytrace.textures.Mirror;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static net.dxzc.raytrace.Transfor.*;

public class Main {
    public static Model build(Texture[] textures, float r) {
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        float[] mo = Transfor.getMove(0, 0, -170f);
        float[] ts = Transfor.getRodrigues((float) Math.sin(r), 0, (float) Math.cos(r));
        Transfor.append(mo, ts, ts);
        OutputCanvas out = new OutputCanvas(buff);
        TransforCanvas canvas = new TransforCanvas(out, ts);

        float s = 100f;

        // 面
        canvas.draw(textures[1],
                s, -s, -s,
                s, s, -s,
                -s, s, -s);
        canvas.draw(textures[1],
                -s, s, -s,
                -s, -s, -s,
                s, -s, -s);

        canvas.draw(textures[2],
                -s, s, -s,
                -s, s, s,
                -s, -s, s);
        canvas.draw(textures[2],
                -s, -s, s,
                -s, -s, -s,
                -s, s, -s);


        canvas.draw(textures[3],
                s, s, s,
                s, -s, s,
                -s, -s, s);
        canvas.draw(textures[3],
                -s, -s, s,
                -s, s, s,
                s, s, s);

        canvas.draw(textures[5],
                s, s, s,
                s, s, -s,
                s, -s, -s);
        canvas.draw(textures[5],
                s, -s, -s,
                s, -s, s,
                s, s, s);

        // 底
        canvas.draw(textures[0],
                s, -s, s,
                s, -s, -s,
                -s, -s, -s);
        canvas.draw(textures[0],
                -s, -s, -s,
                -s, -s, s,
                s, -s, s);

        // 顶
        canvas.draw(textures[9],
                s, s, s,
                -s, s, -s,
                s, s, -s);
        canvas.draw(textures[9],
                -s, s, -s,
                s, s, s,
                -s, s, s);

        canvas.draw(textures[10],
                s, -s * 0.5f, s,
                s, -s * 0.5f, -s,
                -s, -s * 0.5f, -s);
        canvas.draw(textures[10],
                -s, -s * 0.5f, -s,
                -s, -s * 0.5f, s,
                s, -s * 0.5f, s);


        canvas.draw(textures[10],
                s, -s, -s,
                -s, -s * 0.5f, -s,
                s, -s * 0.5f, -s);
        canvas.draw(textures[10],
                -s, -s * 0.5f, -s,
                s, -s, -s,
                -s, -s, -s);

        canvas.draw(textures[10],
                -s, -s * 0.5f, -s,
                -s, -s, s,
                -s, -s * 0.5f, s);
        canvas.draw(textures[10],
                -s, -s, s,
                -s, -s * 0.5f, -s,
                -s, -s, -s);


        canvas.draw(textures[10],
                s, -s * 0.5f, s,
                -s, -s, s,
                s, -s, s);
        canvas.draw(textures[10],
                -s, -s, s,
                s, -s * 0.5f, s,
                -s, -s * 0.5f, s);

        canvas.draw(textures[10],
                s, -s * 0.5f, s,
                s, -s, -s,
                s, -s * 0.5f, -s);
        canvas.draw(textures[10],
                s, -s, -s,
                s, -s * 0.5f, s,
                s, -s, s);

        canvas.draw(textures[11],
                s, -s * 0.5f, s,
                -s, -s * 0.5f, s,
                -s, -s * 0.5f, -s);
        canvas.draw(textures[11],
                -s, -s * 0.5f, -s,
                s, -s * 0.5f, -s,
                s, -s * 0.5f, s);


        ts = Transfor.getRodrigues((float) -Math.sin(r), 0, (float) Math.cos(r));
        Transfor.append(mo, ts, ts);
        canvas = new TransforCanvas(out, ts);
        float m = -50f;
        float p = 25f;
        float q = 70f;

        canvas.draw(textures[12],
                p, q, p,
                p, q, -p,
                -p, q, -p);
        canvas.draw(textures[13],
                p, q, -p,
                p, q, p,
                -p, q, -p);

        canvas.draw(textures[12],
                -p, q, -p,
                -p, q, p,
                p, q, p);
        canvas.draw(textures[13],
                -p, q, p,
                -p, q, -p,
                p, q, p);

        canvas.draw(textures[12],
                p, q, p,
                0, m, 0,
                p, q, -p);
        canvas.draw(textures[12],
                p, q, -p,
                0, m, 0,
                -p, q, -p);
        canvas.draw(textures[12],
                -p, q, -p,
                0, m, 0,
                -p, q, p);
        canvas.draw(textures[12],
                -p, q, p,
                0, m, 0,
                p, q, p);

        canvas.draw(textures[13],
                0, m, 0,
                p, q, p,
                p, q, -p);
        canvas.draw(textures[13],
                0, m, 0,
                p, q, -p,
                -p, q, -p);
        canvas.draw(textures[13],
                0, m, 0,
                -p, q, -p,
                -p, q, p);
        canvas.draw(textures[13],
                0, m, 0,
                -p, q, p,
                p, q, p);


        int size = out.count;
        byte[] data = buff.toByteArray();
        ByteArrayModel model = new ByteArrayModel(textures, size, data);
        return model;
    }

    static void test() {
        for (; ; ) {
        }
    }

    public static void main(String[] args) throws Throwable {
        if (false) {
            test();
            return;
        }

        Gauss.PowIterator.Builder b = new Gauss.PowIterator.Builder();
        b
                .addPoint(0, 0, 50)
                .addPoint(0, 0.1f, 40)
                .addPoint(0, 0.25f, 32)
                .addPoint(0, 0.5f, 30)
                .addPoint(0, 0.8f, 27)
                .addPoint(0, 1, 20)
                .addPoint(0, 2, 16)
                .addPoint(0, 5, 10)
                .addPoint(0.1f, 0.1f, 38)
                .addPoint(0.2f, 0.2f, 27)
                .addPoint(0.3f, 0.3f, 20)
                .addPoint(0.5f, 0.5f, 18)
                .addPoint(1, 1, 13)
                .addPoint(2, 2, 10)
                .addPoint(4, 4, 7);
        Gauss.PowIterator p = b.build();

        Texture[] textures = new Texture[16];
        textures[0] = new Light(0xAABBDD);
        textures[1] = new Gauss(0.8f, 0.5f, 0.5f, p);
        textures[2] = new Gauss(0.5f, 0.8f, 0.5f, p);
        textures[3] = new Gauss(0.5f, 0.5f, 0.8f, p);
        textures[4] = new Gauss(0.5f, 0.5f, 0.2f, p);
        textures[5] = new Mirror(0.8f, 0.8f, 0.8f);
        textures[6] = new Light(0x776699);
        textures[7] = new Light(0x667799);
        textures[8] = new Light(0x669977);
        textures[9] = new Light(0x779966);

        textures[10] = new Glass(Glass.Media.AIR, Glass.Media.WATER, 1);
        textures[11] = new Glass(Glass.Media.WATER, Glass.Media.AIR, 1);

        textures[12] = new Glass(Glass.Media.AIR, Glass.Media.JEWEL, 0.1f);
        textures[13] = new Glass(Glass.Media.JEWEL, Glass.Media.AIR, 0.1f);


        Texture.initId(textures);

        if (false) {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame frame = new JFrame("HelloWorldSwing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel label = new JLabel();
            frame.add(label);
            frame.pack();
            javax.swing.SwingUtilities.invokeLater(() -> {
                frame.setVisible(true);
            });
            long t = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                float a = (float) Math.PI * 0.00003f * (System.currentTimeMillis() - t + 1000f);
                Model model = build(textures, a);
                Bitmap map = RayHelper.draw(model, 300, 300, 5000);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    label.setIcon(new ImageIcon(BitmapImage.getImage(map)));
                    label.repaint();
                });
            }
        } else {
            LinkedList<BufferedImage> is = new LinkedList<>();
            int t = 120;
            int n = t / 2;
            int si = 400;
            int w = si * si / 20;
            for (int i = 0; i < t; i++) {
                float s = (float) Math.PI * i / n + 0.01f;
                Model m = build(textures, s);
                Bitmap map = RayHelper.draw(m, si, si, w);
                is.add(BitmapImage.mix(map));
                System.out.println(i);
            }
            BitmapImage.toGif(is, 0, "D://1.gif");
        }
    }
}
