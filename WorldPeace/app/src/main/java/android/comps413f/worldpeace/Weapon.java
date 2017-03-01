package android.comps413f.worldpeace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/* define template for weapon object's properties & behaviour */

public class Weapon {
    boolean alive;
    //current position
    final PointF curPos  = new PointF();
    //speed of weapon
    int speed;
    float xSpeed;
    float ySpeed;
    private Drawable drawable;


    public void setPosition(float x, float y) {
        // Add code here
        // Task 3: update the position and bounds of the sprite with the input
        curPos.set(x, y);
        updateBounds();
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    protected void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }


    public int getWidth() {
        return drawable.getIntrinsicWidth();
    }

    /** Returns the height of this sprite. */
    public int getHeight() {
        return drawable.getIntrinsicHeight();
    }

    /** Returns the bounds of this sprite. */
    public Rect getBounds() {
        return drawable.getBounds();
    }

    public PointF getCurPos() {
        return curPos;
    }

    public Bitmap getBitmap() {
        return ((BitmapDrawable) drawable.getCurrent()).getBitmap();
    }

    public boolean isAlive() {
        return alive;
    }

    public void stop(){
        speed = 0;
    }


    private void updateBounds(){
        if (drawable != null)
            drawable.setBounds((int)curPos.x, (int)curPos.y,
                    (int)(curPos.x + getWidth()), (int)(curPos.y + getHeight()));
    }

    public void drawOn(Canvas canvas) {
        if (drawable != null)
            if (isAlive())
                drawable.draw(canvas);
    }


    public boolean collideBoundary(int playerId) {
        float newY = 0;
        float newX = curPos.x;
        if (playerId == 1) {
            newY = curPos.y - speed;
        } else if (playerId == 2) {
            newY = curPos.y + speed;
        }

        if ((newY < WorldPeaceView.ARENA_START_POS.y) ||
           (newY + getHeight() > WorldPeaceView.ARENA_START_POS.y + WorldPeaceView.arenaHeight))
            return true;

        return false;
    }


    public boolean collideWith(Weapon weapon) {
        return collideWith(weapon.getBounds(), weapon.getBitmap());
    }

    /** Returns whether this sprite collides with another sprite, by bounds and bitmap. */
    public boolean collideWith(Rect bounds, Bitmap bitmap) {
        return Util.collidePixel(getBounds(), bounds, getBitmap(), bitmap);
    }

    public boolean collideNet(int playerId, DefenseNet net){
        return false;

    }
}
