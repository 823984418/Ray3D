package net.dxzc.raytrace;

public class FilterCanvas extends Canvas {

    public FilterCanvas(Canvas baseCanvas) {
        this.baseCanvas = baseCanvas;
    }

    protected final Canvas baseCanvas;

    @Override
    public void draw(Texture texture,
                     float ax, float ay, float az,
                     float bx, float by, float bz,
                     float cx, float cy, float cz) {
        baseCanvas.draw(texture,
                ax, ay, az,
                bx, by, bz,
                cx, cy, cz);
    }

}
