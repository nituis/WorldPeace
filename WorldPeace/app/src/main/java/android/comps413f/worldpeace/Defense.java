package android.comps413f.worldpeace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/* define template for defense object's properties & behaviour */

public class Defense {
    /**
     * Collision result, either a side of a {@link Rect} or none.
     */
    enum CollisionResult {
        TOP, BOTTOM, LEFT, RIGHT, NONE
    }

    ;

    /**
     * Top-left position, x and y, of the sprite in world coordinates.
     */
    final PointF curPos = new PointF();
    /** Speed, x and y, of the sprite. */
    //final PointF speed = new PointF();
    /** Speed magnitude in x or y direction. */
    //int speedMagnitude;
    /**
     * True if sprite is alive.
     */
    boolean alive;
    /**
     * Drawable for the sprite.
     */
    private Drawable drawable;

    /**
     * number of live
     */
    int noOfLive = 7;

    /** Stops moving the sprite. */
//    public void stop() {
//        // Add code here
//        // Task 1: Stop the sprite by setting the speed of x and y to 0
//        speed.set(0, 0);
//    }

    /** Sets up the velocity of the sprite to move to a direction. */
//    public void updateVelocity(PointF destPos) {
//        double dx = destPos.x - curPos.x;
//        double dy = destPos.y - curPos.y;
//        double angle = Math.atan2(dy, dx);
//        speed.x = (float) (Math.cos(angle) * speedMagnitude);
//        speed.y = (float) (Math.sin(angle) * speedMagnitude);
//    }

    /**
     * Updates the bounds of the drawable for drawing.
     */
    private void updateBounds() {
        if (drawable != null)
            drawable.setBounds((int) curPos.x, (int) curPos.y,
                    (int) (curPos.x + getWidth()), (int) (curPos.y + getHeight()));
    }

    /**
     * Sets the position of this sprite. Subclasses should use this method to
     * update the position so that bounds are updated accordingly.
     */
    public void setPosition(float x, float y) {
        // Add code here
        // Task 3: update the position and bounds of the sprite with the input
        curPos.set(x, y);
        updateBounds();
    }

    /**
     * Sets the drawable for this sprite. Subclasses should use this method to
     * update the position so that bounds are updated accordingly.
     */
    protected void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    protected Drawable getDrawable() {
        return this.drawable;
    }

    ;

    /** Moves the sprite. */
//    public void move() {
//        // Add code here
//        // Task 2: Moves the sprite
//        // Moves the sprite if it is alive and not stop
//        if (alive && (speed.x != 0 || speed.y != 0))
//            setPosition(curPos.x + speed.x, curPos.y + speed.y);
//    }

    /**
     * Draws this sprite.
     */
    public void drawOn(Canvas canvas) {
        if (drawable != null)
            if (isAlive())
                drawable.draw(canvas);
    }

    /**
     * Returns the width of this sprite.
     */
    public int getWidth() {
        return drawable.getIntrinsicWidth();
    }

    /**
     * Returns the height of this sprite.
     */
    public int getHeight() {
        return drawable.getIntrinsicHeight();
    }

    /**
     * Returns the bounds of this sprite.
     */
    public Rect getBounds() {
        return drawable.getBounds();
    }

    public Bitmap getBitmap() {
        return ((BitmapDrawable) drawable.getCurrent()).getBitmap();
    }


    /**
     * Returns the position of the sprite.
     */
    public PointF getCurPos() {
        return curPos;
    }

    /**
     * Set alive of sprite.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Evaluate if sprite is alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Detects whether there is a collision between the sprite and the
     * boundaries of its containing view after one move.
     * @return the side of collision or none
     */
//    public CollisionResult collideBoundary() {
//        float newX = curPos.x + speed.x;
//        float newY = curPos.y + speed.y;
//        if (newX < EscapeFromBulletView.ARENA_START_POS.x) {
//            return CollisionResult.LEFT;
//        }
//        else if (newY < EscapeFromBulletView.ARENA_START_POS.y) {
//            return CollisionResult.TOP;
//        }
//        else if (newX + getWidth() > EscapeFromBulletView.ARENA_START_POS.x + EscapeFromBulletView.arenaWidth) {
//            return CollisionResult.RIGHT;
//        }
//        else if (newY + getHeight() > EscapeFromBulletView.ARENA_START_POS.y + EscapeFromBulletView.arenaHeight) {
//            return CollisionResult.BOTTOM;
//        }
//        else
//            return CollisionResult.NONE;
//    }
//
//    /** Returns the bitmap of this sprite. */
//    public Bitmap getBitmap() {
//        return ((BitmapDrawable) drawable.getCurrent()).getBitmap();
//    }
//
//    /** Returns whether this sprite collides with another sprite. */
//    public boolean collideWith(Sprite sprite) {
//        return collideWith(sprite.getBounds(), sprite.getBitmap());
//    }
//
//    /** Returns whether this sprite collides with another sprite, by bounds and bitmap. */
//    public boolean collideWith(Rect bounds, Bitmap bitmap) {
//        return CollisionDetection.collidePixel(getBounds(), bounds, getBitmap(), bitmap);
//    }
    public boolean collideWith(Weapon weapon) {
        return collideWith(weapon.getBounds(), weapon.getBitmap());
    }

    /**
     * Returns whether this sprite collides with another sprite, by bounds and bitmap.
     */
    public boolean collideWith(Rect bounds, Bitmap bitmap) {
        return Util.collidePixel(getBounds(), bounds, getBitmap(), bitmap);
    }

}
