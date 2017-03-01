package android.comps413f.worldpeace;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

/* define cannon's properties & behaviour */

public class Cannon extends Weapon {
    private PointF destPos = new PointF();    // destination position of bullet
    private Context context;
    private Bullet bullet;

    /** Constructs a spaceship. */
    public Cannon(Context context) {
        alive = false;
        speed = 0;
        this.context = context;
        Drawable drawable = context.getResources().getDrawable(R.drawable.cannon);
        setDrawable(drawable);
    }

    public void createCannon(float x, float y, int playerId) {
        alive = true;
        curPos.x = x - (getWidth() / 2);
        curPos.y = y - (getHeight() / 2);
        if (playerId == 2){
            Drawable newDrawable = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), R.drawable.cannon));
            setDrawable(newDrawable);
        }
        setPosition(curPos.x, curPos.y);
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void shootBullet(int playerId, PointF destPos) {
        PointF bulletPos = new PointF();
        bullet = new Bullet(context);
        bullet.createBullet(this, playerId, destPos);
        //Log.i("shoot", "prepare shoot & created bullet");
        WorldPeaceView.drawingBulletFlag = true;
    }

    //override
    public boolean collideBoundary(int playerId) {
        Log.i("hi", "success");
        if ((curPos.x + getWidth() > WorldPeaceView.ARENA_START_POS.x + WorldPeaceView.arenaWidth)
                || (curPos.y + getHeight() > WorldPeaceView.ARENA_START_POS.y + WorldPeaceView.arenaHeight)
                        || (curPos.x < WorldPeaceView.ARENA_START_POS.x)
                        || (curPos.y < WorldPeaceView.ARENA_START_POS.y))
            return true;

        return false;
    }
}
