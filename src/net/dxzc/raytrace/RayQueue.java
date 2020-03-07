package net.dxzc.raytrace;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public class RayQueue {

    public RayQueue(ExecutorService pool, float[] transfor, Model model) {
        this.pool = pool;
        this.transfor = transfor;
        this.model = model;
    }

    private ExecutorService pool;

    private float[] transfor;

    private Model model;

    public float getX() {
        return transfor[3];
    }

    public float getY() {
        return transfor[7];
    }

    public float getZ() {
        return transfor[11];
    }

    protected void newTask(Pixel pixel, float redPow, float greenPow, float bluePow, float[] add) {
        Transfor.append(add, transfor, add);
        try {
            pool.execute(new RayTask(pool, pixel, redPow, greenPow, bluePow, add, model));
        } catch (RejectedExecutionException e) {
            System.out.println("E");
        }
    }

    public void newTask(Pixel pixel, float redPow, float greenPow, float bluePow, float x, float y, float z) {
        newTask(pixel, redPow, greenPow, bluePow, Transfor.getRodrigues(x, y, z));
    }

}
