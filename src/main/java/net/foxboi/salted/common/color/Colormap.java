package net.foxboi.salted.common.color;

public class Colormap {
    private final int wdt;
    private final int hgt;
    private final int[] pixels;

    public Colormap(int wdt, int hgt, int[] pixels) {
        this.wdt = wdt;
        this.hgt = hgt;
        this.pixels = pixels;
    }

    public int get(double tmp, double df) {
        df *= tmp;
        int x = (int) ((1.0 - tmp) * (wdt - 1));
        int y = (int) ((1.0 - df) * (hgt - 1));
        int idx = y * wdt + x;

        return idx >= pixels.length ? 0xFFFFF0FF : pixels[idx];
    }

    public int getDefaultColor() {
        return get(0.5, 1.0);
    }
}
