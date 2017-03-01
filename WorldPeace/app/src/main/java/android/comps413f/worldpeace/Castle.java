package android.comps413f.worldpeace;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/* define castle's properties & behaviour */

public class Castle extends Defense {
    private PointF destPos = new PointF();    // Position of destination
    private Context context;
    private CastleType type;
    public enum CastleType {
        TIN,
        HA,
        TI,
        PING;

        Drawable getDrawable(Context context) {
            switch (this) {
                case TIN:
                    return context.getResources().getDrawable(R.drawable.tin);
                case HA:
                    return context.getResources().getDrawable(R.drawable.ha);
                case TI:
                    return context.getResources().getDrawable(R.drawable.ti);
                case PING:
                    return context.getResources().getDrawable(R.drawable.ping);
            }
            return null;
        }

        int getResourcesId() {
            switch (this) {
                case TIN:
                    return R.drawable.tin;
                case HA:
                    return R.drawable.ha;
                case TI:
                    return R.drawable.ti;
                case PING:
                    return R.drawable.ping;
            }
            return 0;
        }
    }


    public Castle(Context context, CastleType type) {
        //speedMagnitude = 5;  // Moving speed of spaceship
        alive = false;
        this.context = context;
        this.type = type;
        setDrawable(type.getDrawable(context));

    }
    /** Create a Castle. */
    public void createCastle(float x, float y, Player player) {
        alive = true;

        // Add code here
        // Task 1: Create the spaceship
        // i. Initialize the position of the spaceship at the center of the arena
        // ii. Update the position of the spaceship
        //// iii. Stop the spaceship

        if (player.getPlayerId() == 1) {
            curPos.x = destPos.x = WorldPeaceView.ARENA_START_POS.x + (WorldPeaceView.arenaWidth / 2.f) - ((getWidth() / 2.f) + x / 2);
            curPos.y = destPos.y = WorldPeaceView.ARENA_START_POS.y + (WorldPeaceView.arenaHeight) - ((getHeight() + y));
        } else if (player.getPlayerId() == 2) {

            Drawable newDrawable = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), type.getResourcesId()));
            setDrawable(newDrawable);

            x = -x;

            curPos.x = destPos.x = WorldPeaceView.ARENA_START_POS.x + (WorldPeaceView.arenaWidth / 2.f) - ((getWidth() / 2.f) + x / 2);
            curPos.y = destPos.y = WorldPeaceView.ARENA_START_POS.y + y;
        }


        setPosition(curPos.x, curPos.y);
//        stop();
    }



}
