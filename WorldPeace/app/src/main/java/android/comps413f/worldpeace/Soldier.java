package android.comps413f.worldpeace;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/* define soldier's properties & behaviour */

public class Soldier extends Weapon {
    private PointF destPos = new PointF();
    private Context context;

    /** Constructs a spaceship. */
    public Soldier(Context context) {
        speed = 10;
        alive = false;
        this.context = context;
        Drawable drawable = context.getResources().getDrawable(R.drawable.soldier);
        setDrawable(drawable);
    }

    public void createSoldier(float x, float y, int playerId) {
        alive = true;
        curPos.x = x;
        curPos.y = y;
        if (playerId == 2){
            Drawable newDrawable = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), R.drawable.soldier));
            setDrawable(newDrawable);
        }
        setPosition(curPos.x, curPos.y);
        //Log.i("cannon", "SOLDIER" + curPos.x + " " + curPos.y);
    }

    public void move(int playerId) {
        PointF newPos = new PointF();
        newPos.x = curPos.x;
        if (playerId == 1){
            newPos.y = curPos.y - speed;
        } else if (playerId == 2){
            newPos.y = curPos.y + speed;
        }
        setPosition(newPos.x, newPos.y);
        //Log.i("shoot", "bullet new position: "+getCurPos().x + " " + getCurPos().y);
    }
}

