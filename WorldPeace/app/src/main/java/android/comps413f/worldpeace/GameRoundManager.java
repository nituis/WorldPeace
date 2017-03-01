package android.comps413f.worldpeace;

/* deciding which player can get the current round to take action*/

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameRoundManager extends AppCompatActivity {

    private final Context context;
    private int player1 = 10;
    private int player2 = 10;
    private String user1name;
    private String user2name;
    Handler handler;
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;

    public GameRoundManager(Context context) {
        this.context = context;
        //sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        //editor = sharedpreferences.edit();
        //user1name = sharedpreferences.getString("player1name", "");
        user1name = "Player 1";
        //user2name = sharedpreferences.getString("player1name", ""); //
        user2name = "Player 2";
        //editor.commit();
    }

    public void initMe() {
        handler = new Handler();
    }

    public void getNextPlayer() {
        handler.post(new Runnable() {

            private void decision() {
                if ((player1 == 0 && player2 == 0) || (player1 == 1 && player2 == 1) || (player1 == 2 && player2 == 2)) {
                    WorldPeaceView.createPlayerFlag = 0;
                    Toast.makeText(context, "Tie, retry again!", Toast.LENGTH_SHORT).show();
                    getNextPlayer();
                }
                if ((player1 == 0 && player2 == 1) || (player1 == 1 && player2 == 2) || (player1 == 2 && player2 == 0)) {
                    WorldPeaceView.createPlayerFlag = 2;
                    ((MainActivity) context).getGamePanelPlayer1().setText("");
                    ((MainActivity) context).getGamePanelPlayer2().setText("your turn");
                } else if ((player2 == 0 && player1 == 1) || (player2 == 1 && player1 == 2) || (player2 == 2 && player1 == 0)) {
                    WorldPeaceView.createPlayerFlag = 1;
                    ((MainActivity) context).getGamePanelPlayer1().setText("your turn");
                    ((MainActivity) context).getGamePanelPlayer2().setText("");
                }
                System.out.println(player1 + "," + player2);


            }

            public void run() {
                final Dialog dialog = new Dialog(context);
                final Dialog dialog2 = new Dialog(context);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.custom);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setCancelable(false);
                dialog2.setContentView(R.layout.custom);

                TextView textView = (TextView) dialog.findViewById(R.id.textView);
                Button paper = (Button) dialog.findViewById(R.id.paper);
                Button sisscor = (Button) dialog.findViewById(R.id.sisscor);
                Button rock = (Button) dialog.findViewById(R.id.rock);
                textView.setText(user1name);
                paper.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        player1 = 0;
                        dialog2.show();
                    }
                });
                sisscor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        player1 = 1;
                        dialog2.show();
                    }
                });
                rock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        player1 = 2;
                        dialog2.show();
                    }
                });
                dialog.show();
                TextView textView2 = (TextView) dialog2.findViewById(R.id.textView);
                Button paper2 = (Button) dialog2.findViewById(R.id.paper);
                Button sisscor2 = (Button) dialog2.findViewById(R.id.sisscor);
                Button rock2 = (Button) dialog2.findViewById(R.id.rock);
                textView2.setText(user2name);

                paper2.setRotation(180);
                sisscor2.setRotation(180);
                rock2.setRotation(180);
                textView2.setRotation(180);

                paper2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        player2 = 0;
                        dialog.dismiss();
                        dialog2.dismiss();
                        decision();
                    }
                });
                sisscor2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        player2 = 1;
                        dialog.dismiss();
                        dialog2.dismiss();
                        decision();
                    }
                });
                rock2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        player2 = 2;
                        dialog.dismiss();
                        dialog2.dismiss();
                        decision();
                    }
                });
            }
        });


    }

}