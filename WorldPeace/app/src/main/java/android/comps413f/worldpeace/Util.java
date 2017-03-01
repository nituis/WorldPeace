package android.comps413f.worldpeace;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/* define useful functions */

public class Util {
    /** Buffers used by {@link #collidePixel(Rect, Rect, Bitmap, Bitmap)}. */
    private static int[] pixels1 = null, pixels2 = null;
    /** Rect used by {@link #collidePixel(Rect, Rect, Bitmap, Bitmap)}. */
    private static final Rect rect = new Rect();

    /**
     * Performs pixel-based collision detection using instance buffers and the
     * {@link Bitmap#getPixels(int[], int, int, int, int, int, int)} method.
     * Returns true if non-transparent pixels of the two {@link Rect}s collide.
     */
    public static boolean collidePixel(Rect rect1, Rect rect2, Bitmap bitmap1, Bitmap bitmap2) {
        if (!rect.setIntersect(rect1, rect2)) // No intercept in Rects
            return false;

        // Using the two intersecting regions from the two bitmaps
        int numPixels = rect.width() * rect.height();
        if (pixels1 == null || pixels1.length < numPixels)
            pixels1 = new int[numPixels];
        bitmap1.getPixels(pixels1, 0, rect.width(), rect.left - rect1.left, rect.top - rect1.top, rect.width(), rect.height());
        if (pixels2 == null || pixels2.length < numPixels)
            pixels2 = new int[numPixels];
        bitmap2.getPixels(pixels2, 0, rect.width(), rect.left - rect2.left, rect.top - rect2.top, rect.width(), rect.height());
        for (int i = 0; i < numPixels; i++) {
            if ((pixels1[i] & 0xff000000) != 0 && (pixels2[i] & 0xff000000) != 0) // Both not transparent
                return true;
        }
        return false;
    }

    public static Bitmap rotateDrawable(Resources resources, int resId) {
        Bitmap bmpOriginal = BitmapFactory.decodeResource(resources, resId);
        Bitmap bmpResult = Bitmap.createBitmap(bmpOriginal.getHeight(), bmpOriginal.getWidth(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bmpResult);
        int pivot = bmpOriginal.getHeight() / 2;
        tempCanvas.rotate(180, pivot, pivot);
        tempCanvas.drawBitmap(bmpOriginal, 0, 0, null);
        return bmpResult;
    }
}
