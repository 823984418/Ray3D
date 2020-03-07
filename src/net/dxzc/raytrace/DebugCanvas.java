package net.dxzc.raytrace;

import java.io.PrintStream;
import java.util.Formatter;

public class DebugCanvas extends Canvas {

    private static final int XL = 5;

    public DebugCanvas(PrintStream debugPrint) {
        this.debugPrint = debugPrint;
    }

    private PrintStream debugPrint;

    @Override
    public void draw(Texture texture,
                     float ax, float ay, float az,
                     float bx, float by, float bz,
                     float cx, float cy, float cz) {
        debugPrint.println(texture.id);
        debugPrint.printf("%4f %4f %4f\n", ax, ay, az);
        debugPrint.printf("%4f %4f %4f\n", bx, by, bz);
        debugPrint.printf("%4f %4f %4f\n", cx, cy, cz);
        debugPrint.println();
    }

}
