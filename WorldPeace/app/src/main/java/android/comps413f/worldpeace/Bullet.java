package android.comps413f.worldpeace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

/* define bullet's properties & behaviour */
public class Bullet extends Weapon {
    private Context context;
    private double bulletFirstPos ;
    private double dx;
    private double dy;
    private double angle;


    public Bullet(Context context) {
        alive = false;
        speed = 50;

        this.context = context;
        Drawable drawable = context.getResources().getDrawable(R.drawable.bullet);
        setDrawable(drawable);
    }

    public void createBullet(Cannon cannon, int playerId, PointF destPos) {
        alive = true;

        if (playerId == 1) {
            curPos.x = (2*cannon.getCurPos().x + cannon.getWidth()/2)/2;
            curPos.y = (2*cannon.getCurPos().y + cannon.getHeight())/2;
            bulletFirstPos = curPos.x;
        }else if(playerId == 2){
            curPos.x = (2*cannon.getCurPos().x +cannon.getWidth()/2)/2;
            curPos.y = (2*cannon.getCurPos().y - cannon.getHeight())/2;
            bulletFirstPos = curPos.x;
            Drawable newDrawable = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), R.drawable.bullet));
            setDrawable(newDrawable);
        }

        dx = destPos.x - curPos.x;
        dy = destPos.y - curPos.y;
        angle = Math.atan2(dy, dx);
        xSpeed = (float) (Math.cos(angle) * 20);
        ySpeed = (float) (Math.sin(angle) * 20);

        //this method is useless, instead curPos should be private in Weapon
        setPosition(curPos.x, curPos.y);
    }

    //move once
    public void move(){
        //Log.i("shoot", "bullet move");
        PointF newPos = new PointF();
         newPos.x = curPos.x;
        //calculate new position
         System.out.println("deviceWidth:"+MainActivity.deviceWidth+ "," + newPos.x );
//             float gameQuaduart = ((MainActivity.deviceWidth - 80) / 4);
//
//             if (playerId == 1 && (bulletFirstPos  < gameQuaduart)) {
//                 newPos.x = curPos.x + xSpeed;
//                 newPos.y = curPos.y + ySpeed;
//             } else if(playerId == 1 && (bulletFirstPos  > gameQuaduart) && (bulletFirstPos < (gameQuaduart * 3))){
//                 newPos.y = curPos.y + ySpeed;
//             } else if(playerId == 1 && (bulletFirstPos  > (gameQuaduart * 3)) ){
//                 newPos.x = curPos.x + xSpeed;
//                 newPos.y = curPos.y + ySpeed;
//             } else if (playerId == 2 && (bulletFirstPos  <gameQuaduart)) {
//                 newPos.x = curPos.x + xSpeed;
//                 newPos.y = curPos.y + ySpeed;
//             } else if(playerId == 2 && (bulletFirstPos  > gameQuaduart) && (bulletFirstPos < (gameQuaduart * 3))){
//                 newPos.y = curPos.y + ySpeed;
//             } else if(playerId == 2 && (bulletFirstPos  > (gameQuaduart* 3)) ){
//                 newPos.x = curPos.x - xSpeed;
//                 newPos.y = curPos.y + ySpeed;
//             }
             newPos.x = curPos.x + xSpeed;
             newPos.y = curPos.y + ySpeed;

            setPosition(newPos.x, newPos.y);
            Log.i("position", "POS:" + newPos.x + ", " + newPos.y);
        }

}
