package net.dxzc.raytrace;

import java.util.Arrays;

public class TransforCanvas extends FilterCanvas {

    public TransforCanvas(Canvas baseCanvas, float[] transfor) {
        super(baseCanvas);
        this.transfor = transfor;
    }

    private float[] transfor;

    @Override
    public void draw(Texture texture,
                     float ax, float ay, float az,
                     float bx, float by, float bz,
                     float cx, float cy, float cz) {
        float[] t = transfor;
        baseCanvas.draw(texture,
                Transfor.getX(t, ax, ay, az), Transfor.getY(t, ax, ay, az), Transfor.getZ(t, ax, ay, az),
                Transfor.getX(t, bx, by, bz), Transfor.getY(t, bx, by, bz), Transfor.getZ(t, bx, by, bz),
                Transfor.getX(t, cx, cy, cz), Transfor.getY(t, cx, cy, cz), Transfor.getZ(t, cx, cy, cz));
    }

}
