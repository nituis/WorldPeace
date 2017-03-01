package android.comps413f.worldpeace;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/* define flag's properties & behaviour */

public class Flag extends Defense{
    private PointF destPos = new PointF();    // Position of destination
    private Context context;
    private FlagCount number;
    public enum FlagCount {
        flag1,
        flag2,
        flag3,
        flag4,
        flag5;

        Drawable getDrawable(Context context) {
            return context.getResources().getDrawable(R.drawable.flag);
        }
        int getResourcesId() {
            return R.drawable.flag;
            }

        }

    /** Constructs a bullet. */
    public Flag(Context context, FlagCount number) {
        //speedMagnitude = 3;  // Moving speed of bullet
        alive = false;
        this.context = context;
        this.number = number;
        Drawable drawable = context.getResources().getDrawable(R.drawable.flag);
        //setDrawable(drawable);
        setDrawable(number.getDrawable(context));
    }

    /** Create a new bullet. */
    public void createFlag(float x, Player player) {
        alive = true;

        // Minimum distance between bullet and spaceship
        //double minDistance = speedMagnitude / (EscapeFromBulletView.CYCLE_DELAY / 1000.f);
        // Add code here
        // Task 1: Create a bullet
        // i. Generate a random position for the bullet which fulfill the minimum distance criteria
        // ii. Update the position of the bullet
        // iii. Update the moving direction of the bullet
        double xDist;
        double yDist;

//        curPos.x = (int) (Math.random() * (WorldPeaceView.arenaWidth - getWidth())) + WorldPeaceView.ARENA_START_POS.x;
//        curPos.y = (int) (Math.random() * (WorldPeaceView.arenaHeight - getHeight())) + WorldPeaceView.ARENA_START_POS.y;
        //xDist = destPos.x - curPos.x;
        //yDist = destPos.y - curPos.y;
        if (player.getPlayerId() == 1) {

            curPos.x = destPos.x = WorldPeaceView.ARENA_START_POS.x + (WorldPeaceView.arenaWidth / 2.f) - ((getWidth() / 2.f) + x);
            curPos.y = destPos.y = WorldPeaceView.ARENA_START_POS.y + (WorldPeaceView.arenaHeight) - getHeight() * 6.2f;

        }else if(player.getPlayerId() == 2){
            Drawable newDrawable = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), number.getResourcesId()));
            setDrawable(newDrawable);

            x = -x;
            curPos.x = destPos.x = WorldPeaceView.ARENA_START_POS.x + (WorldPeaceView.arenaWidth / 2.f) - ((getWidth() / 2.f) + x );
            curPos.y = destPos.y = WorldPeaceView.ARENA_START_POS.y + getHeight() * 5;
        }
        //updateVelocity(destPos);
           setPosition(curPos.x, curPos.y);

    }

//    @Override
//    public void move() {
//        // Add code here
//        // Task 2: Set alive to false if it hits the bounds of arena
//        //         Otherwise, move the bullet with superclass's move method
//        if (collideBoundary() != CollisionResult.NONE)
//            alive = false;
//        else
//            super.move();
//    }

}
