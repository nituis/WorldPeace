package android.comps413f.worldpeace;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/* define defense net's properties & behaviour */

public class DefenseNet extends Defense {
    private PointF destPos = new PointF(); // Position of destination
    private Context context;
    private NetCount number;
    public enum NetCount {
        net1,
        net2;


        Drawable getDrawable(Context context) {
            switch (this) {
                case net1:
                    return context.getResources().getDrawable(R.drawable.netline);
                case net2:
                    return context.getResources().getDrawable(R.drawable.netline2);


            }
            return null;
        }
        int getResourcesId() {
            switch (this) {
                case net1:
                    return R.drawable.netline;
                case net2:
                    return R.drawable.netline2;

            }
            return 0;
        }
    }

    public DefenseNet(Context context, NetCount number) {
        //speedMagnitude = 3;  // Moving speed of bullet
        alive = false;
        this.context = context;
        this.number = number;
        Drawable drawable = context.getResources().getDrawable(R.drawable.netline);
        //setDrawable(drawable);
        setDrawable(number.getDrawable(context));
    }

    public void createDefenseNet(float x,float y, Player player) {
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

            curPos.x = destPos.x = WorldPeaceView.ARENA_START_POS.x + (WorldPeaceView.arenaWidth / 2.f) - ((getWidth() / 2.f));
            curPos.y = destPos.y = WorldPeaceView.ARENA_START_POS.y + (WorldPeaceView.arenaHeight ) - ((getHeight()));
        } else if (player.getPlayerId() == 2) {

            Drawable newDrawable = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), number.getResourcesId()));
            setDrawable(newDrawable);

            x = -x;
            curPos.x = destPos.x = WorldPeaceView.ARENA_START_POS.x + (WorldPeaceView.arenaWidth / 2.f) - ((getWidth() / 2.f)) ;
            curPos.y = destPos.y = WorldPeaceView.ARENA_START_POS.y ;
        }
        setPosition(curPos.x, curPos.y);
        //updateVelocity(destPos);
    }

}
