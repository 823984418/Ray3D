package net.dxzc.raytrace.java;

import net.dxzc.raytrace.Bitmap;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class BitmapImage {

    public static BufferedImage getImage(int width, int height, int[] data) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        img.setRGB(0, 0, width, height, data, 0, width);
        return img;
    }

    public static BufferedImage getImage(Bitmap map) {
        return getImage(map.getWidth(), map.getHeight(), map.getColors());
    }

    public static void showImage(Bitmap map) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel(new ImageIcon(getImage(map)));
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
    }

    public static void toGif(List<BufferedImage> imgs, int w, String path) {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.start(path);
        e.setRepeat(0);
        e.setDelay(w);
        for (BufferedImage img : imgs) {
            e.addFrame(img);
        }
        e.finish();
    }

}
