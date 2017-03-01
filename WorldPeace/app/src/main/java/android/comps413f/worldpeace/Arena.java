package android.comps413f.worldpeace;

import android.comps413f.worldpeace.WorldPeaceView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/* define the template for the area that game objects can appear within it */
public class Arena {
    /** Bounds of this arena. */
    protected RectF bounds;
    /** Color of this arena. */
    private int color;
    /** Paint object used in {@link #drawOn(Canvas)}. */
    private Paint paint = new Paint();


    /** Constructs a arena. */
    public Arena() {
        this.color = Color.CYAN;
    }

    /** Set bounds of the arena. */
    public void setArenaBounds() {
        bounds = new RectF(WorldPeaceView.ARENA_START_POS.x,
                WorldPeaceView.ARENA_START_POS.y,
                WorldPeaceView.ARENA_START_POS.x + WorldPeaceView.arenaWidth,
                WorldPeaceView.ARENA_START_POS.y + WorldPeaceView.arenaHeight);
    }

    /** Draws this arena on a canvas. */
    public void drawOn(Canvas canvas) {
        // Add code here
        // Task: Setup the paint object and draw the rectangular arena on the canvas
        // i. Set the style of the paint attribute to "STROKE" style
        // ii. Set the color of the paint attribute by using the "color" value
        // iii. Draw the arena in rectangular shape with "bounds" and "paint" attribute
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        canvas.drawRect(bounds, paint);
    }

}
