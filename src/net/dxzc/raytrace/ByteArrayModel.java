package net.dxzc.raytrace;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ByteArrayModel extends Model {

    public ByteArrayModel(Texture[] textures, int size, byte[] data) {
        this.textures = textures;
        this.size = size;
        this.data = data;
    }

    private Texture[] textures;

    private int size;

    private byte[] data;

    @Override
    public void drawTo(Canvas canvas) {
        try {
            Texture[] ts = textures;
            DataInputStream stream = new DataInputStream(new ByteArrayInputStream(data));
            for (int i = 0, l = size; i < l; i++) {
                canvas.draw(ts[stream.readInt()],
                        stream.readFloat(), stream.readFloat(), stream.readFloat(),
                        stream.readFloat(), stream.readFloat(), stream.readFloat(),
                        stream.readFloat(), stream.readFloat(), stream.readFloat());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
