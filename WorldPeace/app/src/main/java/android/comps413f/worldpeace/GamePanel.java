package android.comps413f.worldpeace;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;

import static android.comps413f.worldpeace.WorldPeaceView.createBulletFlag;
import static android.comps413f.worldpeace.WorldPeaceView.createCannonFlag;
import static android.comps413f.worldpeace.WorldPeaceView.createNet;
import static android.comps413f.worldpeace.WorldPeaceView.createSoldierFlag;

/* define & handle image buttons */

public class GamePanel  extends AppCompatActivity {
    ImageButton cannon;
    ImageButton bullet;
    ImageButton soldier;
    ImageButton defenseNet;
    TextView playerText;
    SharedPreferences.Editor editor;


    private Context context;
    private int playerId;
    private SoundManager soundManager;
    public boolean clickedOnce;



    public GamePanel(Context context, int playerId) {
        this.context = context;
        this.playerId = playerId;
        clickedOnce = false;

    }

    public LinearLayout getLinearLayout() {
        LinearLayout outerLinearLayout = new LinearLayout(context);
        outerLinearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout gamePanelLinearLayout = new LinearLayout(context);
        gamePanelLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        cannon = new ImageButton(context);
        bullet = new ImageButton(context);
        soldier = new ImageButton(context);
        defenseNet = new ImageButton(context);
        playerText = new TextView(context);


        if (playerId == 1){
            cannon.setImageResource(R.drawable.cannon);
            bullet.setImageResource(R.drawable.bullet);
            soldier.setImageResource(R.drawable.soldier);
            defenseNet.setImageResource(R.drawable.netline);
//            playerText.setText("player 1");
        } else if (playerId == 2){
            Drawable newDrawable = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), R.drawable.cannon));
            cannon.setImageDrawable(newDrawable);
            Drawable newDrawable2 = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), R.drawable.bullet));
            bullet.setImageDrawable(newDrawable2);
            Drawable newDrawable3 = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), R.drawable.soldier));
            soldier.setImageDrawable(newDrawable3);
            Drawable newDrawable4 = new BitmapDrawable(context.getResources(), Util.rotateDrawable(context.getResources(), R.drawable.netline));
            defenseNet.setImageDrawable(newDrawable4);
//            playerText.setText("player 2");
            playerText.setRotation(180);
        }

        int buttonWidth = (MainActivity.deviceWidth - 100) / 4;
        cannon.setLayoutParams(new ViewGroup.LayoutParams(buttonWidth, 200));
        cannon.setScaleType(ImageView.ScaleType.FIT_XY);
        bullet.setLayoutParams(new ViewGroup.LayoutParams(buttonWidth, 200));
        bullet.setScaleType(ImageView.ScaleType.FIT_XY);
        soldier.setLayoutParams(new ViewGroup.LayoutParams(buttonWidth, 200));
        soldier.setScaleType(ImageView.ScaleType.FIT_XY);
        defenseNet.setLayoutParams(new ViewGroup.LayoutParams(buttonWidth, 200));
        defenseNet.setScaleType(ImageView.ScaleType.FIT_XY);

        cannon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               if (WorldPeaceView.createPlayerFlag == playerId) {
                    if (clickedOnce){
                        reset();
                    }

                    soundManager.playSound(SoundManager.SoundType.SELECT);
                    WorldPeaceView.createCannonFlag = true;
                    clickedOnce = true;
//                    Toast.makeText(context, "Player " + playerId + " Selected Cannon", Toast.LENGTH_SHORT).show();
                   setText("Selected Cannon");
                } else {
                    message();
                }
            }
        });

        bullet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (WorldPeaceView.createPlayerFlag == playerId) {
                    if (clickedOnce){
                        reset();
                    }
                    soundManager.playSound(SoundManager.SoundType.SELECT);
                    WorldPeaceView.createBulletFlag = true;
                    clickedOnce = true;
//                    Toast.makeText(context, "Player " + playerId + " Selected Bullet", Toast.LENGTH_SHORT).show();
                    setText("Selected Bullet");
                } else {
                    message();
                }
            }
        });

        soldier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (WorldPeaceView.createPlayerFlag == playerId) {
                    if (clickedOnce){
                        reset();
                    }
                    soundManager.playSound(SoundManager.SoundType.SELECT);
                    WorldPeaceView.createSoldierFlag = true;
                    clickedOnce = true;
//                    Toast.makeText(context, "Player " + playerId + " Selected Soldier", Toast.LENGTH_SHORT).show();
                    setText("Selected Soldier");
                } else {
                    message();
                }
            }
        });

        defenseNet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (WorldPeaceView.createPlayerFlag == playerId) {
                    if (clickedOnce) {
                        reset();
                    }
                    WorldPeaceView.createNet = true;
                    clickedOnce = true;
//                    Toast.makeText(context, "Player " + playerId + " Selected Defense Net", Toast.LENGTH_SHORT).show();
                    setText("Selected Defence Net");
                }
            }
        });

        gamePanelLinearLayout.addView(cannon);
        gamePanelLinearLayout.addView(bullet);
        gamePanelLinearLayout.addView(soldier);
        gamePanelLinearLayout.addView(defenseNet);
        //gamePanelLinearLayout.addView(playerText);

        if (playerId == 1) {
            outerLinearLayout.addView(gamePanelLinearLayout);
            outerLinearLayout.addView(playerText);
        } else if (playerId == 2) {
            outerLinearLayout.addView(playerText);
            outerLinearLayout.addView(gamePanelLinearLayout);
        }

        return outerLinearLayout;
    }

    private void reset(){
        //ensure 1 button is clickedOnce
        createCannonFlag = false;
        createBulletFlag = false;
        createSoldierFlag = false;
        createNet = false;
    }

    private void message(){
        Toast.makeText(context, "Player " + playerId + ", it's not your turn!", Toast.LENGTH_SHORT).show();
    }

    /* Resumes or starts the animation. */
    public void resume() {
//        cannon.setClickable(true);
//        cannon.setEnabled(true);
        //bullet.setClickable(true);
//        soldier.setClickable(true);
//        defenseNet.setClickable(true);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cannon.setEnabled(true);
                bullet.setEnabled(true);
                soldier.setEnabled(true);
                defenseNet.setEnabled(true);
            }
        });
    }

    /*  Pauses or stops the animation. */
    public void pause() {
        //cannon.setClickable(false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cannon.setEnabled(false);
                bullet.setEnabled(false);
                soldier.setEnabled(false);
                defenseNet.setEnabled(false);
            }
        });

   //     bullet.setClickable(false);
   //     soldier.setClickable(false);
    //    defenseNet.setClickable(false);
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public void setText(String text) {
        if (!text.equals("")){
            text = ", " + text;
        }
        if (playerId == 1) {
            playerText.setText(((MainActivity) context).player1Name + text);
        } else if (playerId == 2) {
            playerText.setText(((MainActivity) context).player2Name + text);
        }

//        playerText.startAnimation(getBlinkAnimation());
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(playerText, "textColor",
                Color.RED, Color.BLUE, Color.RED, Color.BLUE);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();


    }


}
