package net.dxzc.raytrace;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class RayTask implements Runnable {

    public RayTask(ExecutorService pool, Pixel pixel, float rp, float gp, float bp, float[] transfor, Model model) {
        this.pool = pool;
        this.pixel = pixel;
        redPow = rp;
        greenPow = gp;
        bluePow = bp;
        this.transfor = transfor;
        this.model = model;
        pixel.onLook(transfor);
    }

    private ExecutorService pool;

    private Pixel pixel;

    private float redPow, greenPow, bluePow;

    private float[] transfor;

    private Model model;

    @Override
    public void run() {
        RayCanvas rayCanvas = new RayCanvas();
        TransforCanvas canvas = new TransforCanvas(rayCanvas, transfor);
        model.drawTo(canvas);
        float z = rayCanvas.getZ();
        if (Float.isInfinite(z)) {
            pixel.setColor(0, 0, 0, redPow, greenPow, bluePow);
            return;
        }
        float gx = rayCanvas.getFox();
        float gy = rayCanvas.getFoy();
        float gz = rayCanvas.getFoz();

        float[] r = Transfor.getDeViewZ(gx, gy, gz);
        Transfor.append(r, Transfor.getMove(0, 0, -z), r);
        Transfor.append(r, transfor, r);
        RayQueue queue = new RayQueue(pool, r, model);
        rayCanvas.getTexture().ray(queue, pixel, -z * (float) Math.sqrt(1 - gz * gz), -z * gz, redPow, greenPow, bluePow);
    }

}
