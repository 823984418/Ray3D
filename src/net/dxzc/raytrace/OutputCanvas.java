package net.dxzc.raytrace;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutputCanvas extends Canvas {

    public OutputCanvas(OutputStream stream) {
        this.stream = new DataOutputStream(stream);
    }

    public int count;

    private DataOutputStream stream;

    @Override
    public void draw(Texture texture, float ax, float ay, float az, float bx, float by, float bz, float cx, float cy, float cz) {
        try {
            stream.writeInt(texture.id);
            stream.writeFloat(ax);
            stream.writeFloat(ay);
            stream.writeFloat(az);
            stream.writeFloat(bx);
            stream.writeFloat(by);
            stream.writeFloat(bz);
            stream.writeFloat(cx);
            stream.writeFloat(cy);
            stream.writeFloat(cz);
            count++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
