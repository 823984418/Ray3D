package net.dxzc.raytrace;

public class DebugPixel extends Pixel {
    @Override
    protected void onLook(float[] transfor) {
        Transfor.debug(transfor);
    }
}
