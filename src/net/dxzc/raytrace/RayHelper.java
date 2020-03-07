package net.dxzc.raytrace;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RayHelper {
    public static Bitmap draw(Model model, int width, int height, int time) throws InterruptedException {
        Bitmap map = new Bitmap(width, height);
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        RayQueue queue = new RayQueue(pool, Transfor.getUnit(), model);
        map.forEachPixel(((x, y, pixel) -> {
            queue.newTask(pixel, 1, 1, 1, -((float) x / width - 0.5f), ((float) y / height - 0.5f), 0.28f);
        }));
        Thread.sleep(time);
        int i = pool.shutdownNow().size();
        if (i != 0) {
            System.out.println("E:" + i);
        } else {
            System.out.println("O:" + pool.getTaskCount());
        }
        return map;
    }
}
