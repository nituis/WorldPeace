package android.comps413f.worldpeace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/* control the whole game logic*/
public class WorldPeaceView extends SurfaceView {
    //define numeric limitations of all game objects
    /* Delay in each animation cycle, in ms. */
    static final int CYCLE_DELAY = 30;
    /* Timer for the game loop. */
    private Timer timer = null;
    private long startTime = 0;
    private long totalTime = 0;
    private Context context;
    private static int winner = 0;

    private static final int TEXT_SIZE = 24;

    public static final Point ARENA_START_POS = new Point(0, 0);   // Offset for drawing arena
    public static int arenaWidth;
    public static int arenaHeight;
    private Arena arena;

    private SoundManager soundManager;

    /* Size of the pause drawable on the screen. */
    public static final int DRAWABLE_WIDTH = 100;
    public static final int DRAWABLE_HEIGHT = 50;

    /*
    0 = no current player
    1 = current player 1
    2 = current player 2
     */
    public static int createPlayerFlag = 0;
    public static boolean createCannonFlag = false;
    public static boolean createBulletFlag = false;
    public static boolean createSoldierFlag = false;
    public static boolean drawingBulletFlag = false;
    public static boolean drawingSoldierFlag1 = false;
    public static boolean drawingSoldierFlag2 = false;
    public static boolean decidingNextPlayer = false;
    public static boolean createNet = false;
    public static boolean bulletDesSettingFlag = false;
    public static boolean reachMaxNet = false;
    private static Player playerBullet = null;

    private Cannon selectedCannon = null;

    private int soldierDeadCount = 0;

    private final Player player1;
    private final Player player2;

    private Bullet bullet;
    private ArrayList<Soldier> soldiers1;
    private ArrayList<Soldier> soldiers2;

    //private Flag flag4;
//    private Flag flag2;

    private boolean gameOver;
    private PauseManager pause;
    private boolean alertShown = false;


    /**
     * Saving and handling of user input of touch events
     */
    private class UserInput {
        /* Whether there is a user input present. */
        boolean present = false;
        /* Action of the user input {@link MotionEvent}. */
        int action;
        /*  x, y positions of the user input {@link MotionEvent}. */
        int x, y;

        /**
         * Sets the user input mouse event for later processing.
         * This method is called in event handlers, i.e., in the main UI thread.
         */
        synchronized void save(MotionEvent event) {
            present = true;
            action = event.getAction();
            x = (int) event.getX();
            y = (int) event.getY();
        }

        /**
         * Handles the user input to set the paddle's position.
         * This method is called in the thread of the game loop.
         */
        synchronized void handle() {
            if (present && createPlayerFlag != 0) {
                if (action == MotionEvent.ACTION_DOWN) {
                    if (!pause.getPause()) {
                        if (createCannonFlag) {
                            Player player;
                            if (createPlayerFlag == 1) {
                                player = player1;
                            } else {
                                player = player2;
                            }

                            //Toast.makeText(getContext(), "Now player " + player.getPlayerId() + "'s turn!", Toast.LENGTH_SHORT).show();

                            Cannon cannon = new Cannon(getContext());
                            cannon.createCannon(x, y, player.getPlayerId());
                            int success = player.addCannon(cannon);
                            if (success > 0) {
                                switch (success){
                                    case 1: handler.post(new Runnable() {
                                                public void run() {
//                                                    Toast.makeText(getContext(), "You cannot place on the opposite side.", Toast.LENGTH_LONG).show();
                                                    String alert = "You cannot place on the opposite side.";
                                                    if (createPlayerFlag == 1) {
                                                        ((MainActivity) context).getGamePanelPlayer1().setText(alert);
                                                    } else {
                                                        ((MainActivity) context).getGamePanelPlayer2().setText(alert);
                                                    }
                                                }
                                             }); break;
                                    case 2: handler.post(new Runnable() {
                                                public void run() {
//                                                    Toast.makeText(getContext(), "You cannot build on other objects.", Toast.LENGTH_LONG).show();
                                                    String alert = "You cannot build on other objects.";
                                                    if (createPlayerFlag == 1) {
                                                        ((MainActivity) context).getGamePanelPlayer1().setText(alert);
                                                    } else {
                                                        ((MainActivity) context).getGamePanelPlayer2().setText(alert);
                                                    }
                                                }
                                            }); break;
                                    case 3: handler.post(new Runnable() {
                                        public void run() {
//                                            Toast.makeText(getContext(), "You cannot build on boundary or outside this area", Toast.LENGTH_LONG).show();
                                            String alert = "You cannot build on boundary or outside this area";
                                            if (createPlayerFlag == 1) {
                                                ((MainActivity) context).getGamePanelPlayer1().setText(alert);
                                            } else {
                                                ((MainActivity) context).getGamePanelPlayer2().setText(alert);
                                            }
                                        }
                                    }); break;
                                    case 4: handler.post(new Runnable() {
                                        public void run() {
//                                            Toast.makeText(getContext(), "You cannot build on your nets", Toast.LENGTH_LONG).show();
                                            String alert = "You cannot build on your nets";
                                            if (createPlayerFlag == 1) {
                                                ((MainActivity) context).getGamePanelPlayer1().setText(alert);
                                            } else {
                                                ((MainActivity) context).getGamePanelPlayer2().setText(alert);
                                            }
                                        }
                                    }); break;
                                }
                            } else {
                                soundManager.playSound(SoundManager.SoundType.PLACECANNON);
                                createCannonFlag = false;
                                decidingNextPlayer = true;
                            }
                        } else if (createBulletFlag) {

                            if (createPlayerFlag == 1) {
                                playerBullet = player1;
                            } else {
                                playerBullet = player2;
                            }
                            Log.i("player", playerBullet.getPlayerId()+", createBulletFlag");
                            //Toast.makeText(getContext(), "Now player " + player.getPlayerId() + "'s turn!", Toast.LENGTH_SHORT).show();

                            selectedCannon = playerBullet.findCannon(x, y);
                            if (selectedCannon != null) {
                                bulletDesSettingFlag = true;
                                createBulletFlag = false;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String alert = getResources().getString(R.string.pleaseChooseDest);
                                        if (createPlayerFlag == 1) {
                                            ((MainActivity) context).getGamePanelPlayer1().setText(alert);
                                        } else {
                                            ((MainActivity) context).getGamePanelPlayer2().setText(alert);
                                        }
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    public void run() {
                                        String alert = getResources().getString(R.string.pleaseChooseYourCannon);
                                        if (createPlayerFlag == 1) {
                                            ((MainActivity) context).getGamePanelPlayer1().setText(alert);
                                        } else {
                                            ((MainActivity) context).getGamePanelPlayer2().setText(alert);
                                        }

//                                        Toast.makeText(getContext(), "Please choose the YOUR cannons", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            //Log.i("shoot", createPlayerFlag + " wins");
                        } else if (createSoldierFlag) {
                            Player player;
                            if (createPlayerFlag == 1) {
                                player = player1;
                            } else {
                                player = player2;
                            }
                            //Toast.makeText(getContext(), "Now player " + player.getPlayerId() + "'s turn!", Toast.LENGTH_SHORT).show();

                            Soldier soldier = new Soldier(getContext());
                            soldier.createSoldier(x, y, player.getPlayerId());
                            boolean success = player.addSoldier(soldier);
                            if (player.getPlayerId() == 1)
                                soldiers1 = player.getSoldiers();
                            else if (player.getPlayerId() == 2)
                                soldiers2 = player.getSoldiers();

                            if (!success) {
                                handler.post(new Runnable() {
                                    public void run() {
//                                        Toast.makeText(getContext(), "You cannot place on the opposite side.", Toast.LENGTH_LONG).show();
                                        String alert = "You cannot place on the opposite side.";
                                        if (createPlayerFlag == 1) {
                                            ((MainActivity) context).getGamePanelPlayer1().setText(alert);
                                        } else {
                                            ((MainActivity) context).getGamePanelPlayer2().setText(alert);
                                        }
                                    }
                                });

                            } else {
                                soundManager.playSound(SoundManager.SoundType.SOLDIER);
                                createCannonFlag = false;
                                if (player.getPlayerId() == 1)
                                    drawingSoldierFlag1 = true;
                                else if (player.getPlayerId() == 2)
                                    drawingSoldierFlag2 = true;
                                decidingNextPlayer = false;
                            }
                        } else if (bulletDesSettingFlag) {
                            Log.i("player", playerBullet.getPlayerId()+", bulletDesSettingFlag");
                            selectedCannon.shootBullet(playerBullet.getPlayerId(), new PointF(x, y));
                            soundManager.playSound(SoundManager.SoundType.SHOOT);
                            bullet = selectedCannon.getBullet();
                            bulletDesSettingFlag = false;
                            drawingBulletFlag = true;
                        }
                    }
                }
                present = false;
                }
            }
        }
    /** User input object of touch events. */
    private UserInput userInput = new UserInput();

    /** Task for the game loop. */
    private class AnimationTask extends TimerTask {
        @Override
        public void run() {

            if (gameOver) {
                if (!alertShown) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String winnerName = winner == 1 ? ((MainActivity) context).player1Name : ((MainActivity) context).player2Name;
                            AlertDialog.Builder alert = new AlertDialog.Builder(context).setTitle("Game End").setMessage("The game is end.\nWinner is " + winnerName).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((MainActivity) context).finish();
                                }
                            });
                            alert.show();
                        }
                    });
                    alertShown = true;
                }
            } else {
                userInput.handle();

                ((MainActivity) getContext()).updatePlayerName();


                Player player, opponentPlayer;
                if (createPlayerFlag == 1) {
                    player = player1;
                    opponentPlayer = player2;
                } else {
                    player = player2;
                    opponentPlayer = player1;
                }
                if (decidingNextPlayer) {
                    ((MainActivity) getContext()).getGameRoundManager().getNextPlayer();
                    createPlayerFlag = 0;
                    decidingNextPlayer = false;
                }
                if (drawingSoldierFlag1) {
                    soldierKill(soldiers1, opponentPlayer, player);
                }
                if (drawingSoldierFlag2) {
                    soldierKill(soldiers2, opponentPlayer, player);
                }

                if (drawingBulletFlag) {
                    //Log.i("player", playerBullet.getPlayerId()+" drawingBulletFlag");
                    if (bullet.isAlive()) {
                        if (bullet.collideBoundary(playerBullet.getPlayerId())) {
                            //destroy things
                            bullet.setAlive(false);
                            drawingBulletFlag = false;
                            decidingNextPlayer = true;
                        }
                        //Log.i("shoot", playerBullet.getPlayerId()+"bullet current position: " + bullet.getCurPos().x + " " + bullet.getCurPos().y);
                        bullet.move();

                        Cannon collidedCannon = null;
                        for (Cannon cannon : opponentPlayer.getCannons()) {
                            //collidedCannon =  opponentPlayer.findCannon((int)bullet.getCurPos().x, (int)bullet.getCurPos().y);
                            if (bullet.collideWith(cannon))
                                collidedCannon = cannon;
                        }
                        if (collidedCannon != null) {
                            opponentPlayer.getCannons().remove(collidedCannon);
                            soundManager.playSound(SoundManager.SoundType.EXPLOSION);
                            soundManager.vibrate();
                            bullet.setAlive(false);
                            drawingBulletFlag = false;
                            decidingNextPlayer = true;
                        }

                        boolean netDestroyed = false;

                        if (opponentPlayer.getNet2() != null && opponentPlayer.getNet2().collideWith(bullet.getBounds(), bullet.getBitmap())) {
                            Log.i("netDes", "2destroyed");
                            opponentPlayer.setNet2(null);
                            netDestroyed = true;
                        } else if (opponentPlayer.getNet1() != null && opponentPlayer.getNet1().collideWith(bullet.getBounds(), bullet.getBitmap())) {
                            Log.i("netDes", "1destroyed");
                            opponentPlayer.setNet1(null);
                            netDestroyed = true;
                        }


                        if (netDestroyed) {
                            soundManager.playSound(SoundManager.SoundType.EXPLOSION);
                            soundManager.vibrate();
                            bullet.setAlive(false);
                            //drawingBulletFlag = false;
                            decidingNextPlayer = true;
                            netDestroyed = false;
                        }

                        boolean flagDestroyed = false;

                        if (opponentPlayer.getFlag1() != null && opponentPlayer.getFlag1().collideWith(bullet.getBounds(), bullet.getBitmap())) {
                            Log.i("flagDes", "1 destroyed");
                            opponentPlayer.setFlag1(null);
                            flagDestroyed = true;
                        } else if (opponentPlayer.getFlag2() != null && opponentPlayer.getFlag2().collideWith(bullet.getBounds(), bullet.getBitmap())) {
                            Log.i("flagDes", "2 destroyed");
                            opponentPlayer.setFlag2(null);
                            flagDestroyed = true;
                        } else if (opponentPlayer.getFlag3() != null && opponentPlayer.getFlag3().collideWith(bullet.getBounds(), bullet.getBitmap())) {
                            Log.i("flagDes", "3 destroyed");
                            opponentPlayer.setFlag3(null);
                            flagDestroyed = true;
                        } else if (opponentPlayer.getFlag4() != null && opponentPlayer.getFlag4().collideWith(bullet.getBounds(), bullet.getBitmap())) {
                            Log.i("flagDes", "4 destroyed");
                            opponentPlayer.setFlag4(null);
                            flagDestroyed = true;
                        } else if (opponentPlayer.getFlag5() != null && opponentPlayer.getFlag5().collideWith(bullet.getBounds(), bullet.getBitmap())) {
                            Log.i("flagDes", "5 destroyed");
                            opponentPlayer.setFlag5(null);
                            flagDestroyed = true;
                        }
                        if (flagDestroyed) {
                            opponentPlayer.setFlagHasBeenDestroyed(true);
                            soundManager.playSound(SoundManager.SoundType.EXPLOSION);
                            soundManager.vibrate();
                            bullet.setAlive(false);
                            //drawingBulletFlag = false;
                            decidingNextPlayer = true;
                            flagDestroyed = false;
                        }
                    }
                }
                if (createNet) {
                    String success = player.addDefenceNet();

                    if (success.equals("number")) {
                        handler.post(new Runnable() {
                            public void run() {
                                String alert = getResources().getString(R.string.addDefenceMax);
                                if (createPlayerFlag == 1) {
                                    ((MainActivity) context).getGamePanelPlayer1().setText(alert);
                                } else {
                                    ((MainActivity) context).getGamePanelPlayer2().setText(alert);
                                }
//                                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (success.equals("castle")) {
                        handler.post(new Runnable() {
                            public void run() {
                                String alert = getResources().getString(R.string.addDefenseAfterFlagOrCastleDestroyed);
                                if (createPlayerFlag == 1) {
                                    ((MainActivity) context).getGamePanelPlayer1().setText(alert);
                                } else {
                                    ((MainActivity) context).getGamePanelPlayer2().setText(alert);
                                }
                            }
                        });
                    } else {
                        soundManager.playSound(SoundManager.SoundType.WALL);
                        ((MainActivity) getContext()).getGameRoundManager().getNextPlayer();
                        createPlayerFlag = 0;
                    }
                    createNet = false;
                }

                if (opponentPlayer.getCastleDestroyed() == 4) {
                    gameOver = true;
                    winner = player.getPlayerId();
                }
            }


            // v. Draw the game objects
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                canvas.drawRGB(0, 0, 0);
                arena.drawOn(canvas);
//                if (!gameOver) {
                if (!pause.getPause()) {
                    player1.drawOn(canvas);
                    player2.drawOn(canvas);
                }
//                pause.getDrawable().draw(canvas);
//                newGameDrawable.draw(canvas);
                drawGameText(canvas);
                getHolder().unlockCanvasAndPost(canvas);
            }
        }

        /**Paint object for painting text.*/
        private Paint textPaint = new Paint();

        /**Draws text for the game.*/
        private void drawGameText(Canvas canvas) {
            Resources res = getResources();
            textPaint.setColor(Color.LTGRAY);
            textPaint.setTextSize(TEXT_SIZE);

            // Calculation of time elapse
            double gameTime = 0;
            if (gameOver) {
                if (startTime > 0) {
                    totalTime += (System.currentTimeMillis() - startTime);
                    startTime = 0;
                }
                gameTime = totalTime / 1000.0;

                handler.post(new Runnable() {
                    public void run() {
//                        Toast.makeText(getContext(), "Total time used:" + (totalTime / 1000.0) + "\nWinner is Player "+winner, Toast.LENGTH_LONG).show();
                    }
                });
            } else if (pause.getPause()) {
                gameTime = totalTime / 1000.0;
                ((MainActivity) context).getGamePanelPlayer1().pause();
                ((MainActivity) context).getGamePanelPlayer2().pause();
            } else {
                gameTime = (System.currentTimeMillis() - startTime + totalTime) / 1000.0;
                ((MainActivity) context).getGamePanelPlayer1().resume();
                ((MainActivity) context).getGamePanelPlayer2().resume();
            }

            // Add code here
            // Task 1: Draw game information
            // i. Draw time elapsed
            // ii. Draw "Game over" in the center of the canvas if necessary
            textPaint.setTextAlign(Paint.Align.RIGHT);
//            canvas.drawText(res.getString(R.string.time_elapse, gameTime), getWidth(), TEXT_SIZE, textPaint);
            if (gameOver) {
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);

//                canvas.drawText(res.getString(R.string.time_elapse, gameTime), getWidth() / 2, getHeight() / 2 + (2 * TEXT_SIZE), textPaint);
            } else if (pause.getPause()) {
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(res.getString(R.string.game_pause), getWidth() / 2, getHeight() / 2, textPaint);
            }
        }
    }
    /* Resumes or starts the animation. */
    public void resume() {
        if (timer == null)
            timer = new Timer();
        timer.schedule(new WorldPeaceView.AnimationTask(), 0, CYCLE_DELAY);
    }

    /*  Pauses or stops the animation. */
    public void pause() {
        timer.cancel();
        timer = null;
        totalTime += (System.currentTimeMillis() - startTime);
        pause.setPause(true);
    }

    /**
     * Starts a new game. This method may be called from the UI thread so it
     * sends the code to the timer thread for execution.
     */
    private void newGame() {
        gameOver = false;
        alertShown = false;
        winner = 0;
        startTime = 0;
        totalTime = 0;
        pause.setPause(true);

        createPlayerFlag = 0;
        createCannonFlag = false;
        createBulletFlag = false;
        createSoldierFlag = false;
        drawingBulletFlag = false;
        drawingSoldierFlag1 = false;
        drawingSoldierFlag2 = false;
        decidingNextPlayer = false;
        createNet = false;
        bulletDesSettingFlag = false;
        reachMaxNet = false;
        playerBullet = null;

        Rect pauseBounds = new Rect(getWidth() / 2 - DRAWABLE_WIDTH, 0, getWidth() / 2, DRAWABLE_HEIGHT);
//       pause.setBounds(pauseBounds);
        pause.setPause(true);

        Rect newGameBounds = new Rect(getWidth() / 2, 0, getWidth() / 2 + DRAWABLE_WIDTH, DRAWABLE_HEIGHT);
//        newGameDrawable.setBounds(newGameBounds);

        arenaWidth = getWidth() - (2 * ARENA_START_POS.x);
        arenaHeight = getHeight() - (2 * ARENA_START_POS.y);

        // Setup arena, spaceship, and bullets
        arena.setArenaBounds();
        player1.startGame();
        player2.startGame();

        ((MainActivity) getContext()).getGameRoundManager().getNextPlayer();
        createPlayerFlag = 0;

        //Toast.makeText(context, "Now player 1's turn!", Toast.LENGTH_SHORT).show();

        pause.setPause(false);
    }


    private void soldierKill(ArrayList<Soldier> soldiers, Player opponentPlayer, Player player) {
        ArrayList<Soldier> removeList = new ArrayList<>();
        if (soldiers != null) {
            int playerId = player.getPlayerId();

            for (Soldier soldier : soldiers) {
                if (soldier.isAlive()) {
                    //add collideWith(Weapon weapon)
                    if (soldier.collideBoundary(playerId)) {
                        //destroy things
                        soldier.setAlive(false);
                        removeList.add(soldier);
                    }
                    //Log.i("shoot", playerBullet.getPlayerId() + "bullet current position: " + bullet.getCurPos().x + " " + bullet.getCurPos().y);
                    soldier.move(playerId);

                    Cannon collidedCannon = null;
                    for (Cannon cannon : opponentPlayer.getCannons()) {
                        if (soldier.collideWith(cannon))
                            collidedCannon = cannon;
                        //collidedCannon = opponentPlayer.findCannon((int)soldier.getCurPos().x, (int)soldier.getCurPos().y);
                    }
                    if (collidedCannon != null) {
                        opponentPlayer.getCannons().remove(collidedCannon);
                        soundManager.playSound(SoundManager.SoundType.SCREEM);
                        soundManager.vibrate();
                    }

                    Flag flag1 = opponentPlayer.getFlag1();
                    Flag flag2 = opponentPlayer.getFlag2();
                    Flag flag3 = opponentPlayer.getFlag3();
                    Flag flag4 = opponentPlayer.getFlag4();
                    Flag flag5 = opponentPlayer.getFlag5();
                    DefenseNet net1 = opponentPlayer.getNet1();
                    DefenseNet net2 = opponentPlayer.getNet2();


                    if ((flag1 != null && flag1.collideWith(soldier.getBounds(), soldier.getBitmap()))
                        || (flag2 != null && flag2.collideWith(soldier.getBounds(), soldier.getBitmap()))
                        || (flag3 != null && flag3.collideWith(soldier.getBounds(), soldier.getBitmap()))
                        || (flag4 != null && flag4.collideWith(soldier.getBounds(), soldier.getBitmap()))
                        || (flag5 != null && flag5.collideWith(soldier.getBounds(), soldier.getBitmap()))
                            || ( net1 != null && net1.collideWith(soldier.getBounds(), soldier.getBitmap()))
                            || ( net2 != null && net2.collideWith(soldier.getBounds(), soldier.getBitmap()))) {
                        soldier.setAlive(false);
                        removeList.add(soldier);
                        soundManager.playSound(SoundManager.SoundType.SCREEM);
                        soundManager.vibrate();
                    }

                    if (opponentPlayer.checkAllFlagDestroyed()) {
                        Castle tin = opponentPlayer.getTin();
                        if (tin != null && tin.collideWith(soldier.getBounds(), soldier.getBitmap())) {
                            opponentPlayer.setTin(null);
                            soundManager.playSound(SoundManager.SoundType.SCREEM);
                            soundManager.vibrate();
                            opponentPlayer.setCastleDestroyed(opponentPlayer.getCastleDestroyed()+1);
                        }
                        Castle ha = opponentPlayer.getHa();
                        if (ha != null && ha.collideWith(soldier.getBounds(), soldier.getBitmap())) {
                            opponentPlayer.setHa(null);
                            soundManager.playSound(SoundManager.SoundType.SCREEM);
                            soundManager.vibrate();
                            opponentPlayer.setCastleDestroyed(opponentPlayer.getCastleDestroyed()+1);
                        }
                        Castle ti = opponentPlayer.getTi();
                        if (ti != null && ti.collideWith(soldier.getBounds(), soldier.getBitmap())) {
                            opponentPlayer.setTi(null);
                            soundManager.playSound(SoundManager.SoundType.SCREEM);
                            soundManager.vibrate();
                            opponentPlayer.setCastleDestroyed(opponentPlayer.getCastleDestroyed()+1);
                        }
                        Castle ping = opponentPlayer.getPing();
                        if (ping != null && ping.collideWith(soldier.getBounds(), soldier.getBitmap())) {
                            opponentPlayer.setPing(null);
                            soundManager.playSound(SoundManager.SoundType.SCREEM);
                            soundManager.vibrate();
                            opponentPlayer.setCastleDestroyed(opponentPlayer.getCastleDestroyed()+1);
                        }
                    }
                }
            }

            for (Soldier s : removeList) {
                player.removeSoldier(s);
            }

            if (soldiers.size() == 0) {
                if (playerId == 1) {
                    drawingSoldierFlag1 = false;
                } else if (playerId == 2) {
                    drawingSoldierFlag2 = false;
                }
                decidingNextPlayer = true;
                createSoldierFlag = false;
            }
        }
    }


    /**
     * Constructs an animation view. This performs initialization including the
     * setup of the game loop, and event handlers for key presses and touches.
     */
    public WorldPeaceView(Context context) {
        super(context);

        this.context = context;

        //the following should be inside newGame()
        pause = new PauseManager(context, true);
        soundManager = new SoundManager(context);
//        newGameDrawable = context.getResources().getDrawable(R.drawable.newgame);

        // Create arena, spaceship, and bullets
        arena = new Arena();
        player1 = new Player(context, 1);
        player2 = new Player(context, 2);

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userInput.save(event);
                return true;
            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                while (getWidth() == 0)
                    ; // Wait for layout
                newGame();
            }
        }, 0);

    }

    Handler handler;

    public void initMe() {
        handler = new Handler();
    }

    public PauseManager getPause() {
        return pause;
    }

    public void setSound(boolean soundOn) {
        soundManager.setSoundOn(soundOn);
    }

    public void setVibrate(boolean vibrateOn) {
        soundManager.setVibrateOn(vibrateOn);
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

}