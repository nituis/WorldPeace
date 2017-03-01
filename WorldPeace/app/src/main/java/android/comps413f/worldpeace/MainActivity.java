package android.comps413f.worldpeace;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/* declare layout & custom view*/

public class MainActivity extends AppCompatActivity {

    private WorldPeaceView animationView;
    private GamePanel gamePanelPlayer1;
    private GamePanel gamePanelPlayer2;
    private GameRoundManager gameRoundManager;
    private SoundManager soundManager;
    SharedPreferences sharedpreferences;
    SharedPreferences settingPref;
    SharedPreferences.Editor settingEditor;
    public static String player1Name;
    public static String player2Name;
    public static int deviceWidth;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedpreferences   = getSharedPreferences("Session", Context.MODE_PRIVATE);
        settingPref = getSharedPreferences("Setting", Context.MODE_PRIVATE);
        settingEditor = settingPref.edit();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        deviceWidth = metrics.widthPixels;
        soundManager = new SoundManager(this);

        gameRoundManager = new GameRoundManager(this);
        gameRoundManager.initMe();

        animationView = new WorldPeaceView(this);
        animationView.initMe();
        setContentView(R.layout.activity_main);
        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_main);
        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 5.0f);

        gamePanelPlayer1 = new GamePanel(this, 1);
        gamePanelPlayer2 = new GamePanel(this, 2);
        gamePanelPlayer1.setSoundManager(soundManager);
        gamePanelPlayer2.setSoundManager(soundManager);
        animationView.setSoundManager(soundManager);
//        button.setLayoutParams(param); button2.setLayoutParams(param);
        animationView.setLayoutParams(param2);

        ll.addView(gamePanelPlayer2.getLinearLayout());
        ll.addView(animationView);
        ll.addView(gamePanelPlayer1.getLinearLayout());

    }

    public void onResume() {
        super.onResume();
        animationView.resume();
        gamePanelPlayer1.resume();
        gamePanelPlayer2.resume();

        boolean soundSetting = settingPref.getBoolean("Sound", true);
        animationView.setSound(soundSetting);


    }

    protected void onPause() {
        super.onPause();
        animationView.pause();
        gamePanelPlayer1.pause();
        gamePanelPlayer2.pause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        if (menu != null) {
            boolean soundSetting = settingPref.getBoolean("Sound", true);
            boolean vibrateSetting = settingPref.getBoolean("Vibrate", true);
            MenuItem sound = menu.findItem(R.id.sound);
            MenuItem vibrate = menu.findItem(R.id.vibrate);
            sound.setChecked(soundSetting);
            vibrate.setChecked(vibrateSetting);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sound:
                // Add code here
                // Task: Enable/Disable the sound effect
                // Method setSound is used for sound configuration
//                settingPref.getBoolean("Sound", !item.isChecked());
                item.setChecked(!item.isChecked());
                animationView.setSound(item.isChecked());
                settingEditor.putBoolean("Sound", item.isChecked());
                break;

            case R.id.vibrate:
                item.setChecked(!item.isChecked());
                animationView.setVibrate(item.isChecked());
                settingEditor.putBoolean("Vibrate", item.isChecked());
                break;
            case R.id.about:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.about_title)
                        .setMessage(R.string.about_msg)
                        .setNeutralButton(android.R.string.ok, null)
                        .show();
                break;
            case R.id.pause:
                PauseManager pause = animationView.getPause();
                if (pause.getPause())
                    pause.setPause(false);
                else
                    pause.setPause(true);

                break;
            case R.id.restart:
                Intent intent = new Intent(this, MainActivity.class);
                this.finish();
                startActivity(intent);
                break;
        }
        settingEditor.commit();
        return false;
    }

    public GameRoundManager getGameRoundManager() {
        return gameRoundManager;
    }

    public void setGameRoundManager(GameRoundManager gameRoundManager) {
        this.gameRoundManager = gameRoundManager;
    }

    public GamePanel getGamePanelPlayer1() {
        return gamePanelPlayer1;
    }

    public GamePanel getGamePanelPlayer2() {
        return gamePanelPlayer2;
    }

    public void updatePlayerName() {
        player1Name = sharedpreferences.getString("player1name", "Player 1");
        player2Name = sharedpreferences.getString("player2name", "Player 2");
    }

}
