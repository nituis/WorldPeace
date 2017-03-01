package android.comps413f.worldpeace;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Vibrator;

/* managing sound */

public class SoundManager {
    /**
     * Type of sound effect.
     */
    public static enum SoundType {
        CORRECT, INCORRECT, WIN, SELECT, PLACECANNON, SHOOT, SOLDIER, EXPLOSION, SCREEM, WALL
    }

    private Vibrator vibrator;
    ;
    /**
     * Whether to play sound or not.
     */
    boolean soundOn;
    boolean vibrateOn;

    /**
     * Sound pool for playing sounds.
     */
    private SoundPool soundPool;

    /**
     * Sound IDs, for the types of music and sounds.
     */
    private final int[] soundIDs = new int[SoundType.values().length];
    /**
     * Stream IDs, for the types of music and sounds.
     */
    private final int[] streamIDs = new int[SoundType.values().length];

    /**
     * Construct the sound manager.
     */
    public SoundManager(Context context) {
        soundOn = true;
        vibrateOn = true;
        // Add code here
        // Task 1: Load the audio resource files
        // i. Creates a SoundPool object
        // ii. Save the sound IDs returned by the load method
        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

//        soundIDs[SoundType.CORRECT.ordinal()] = soundPool.load(context, R.raw.correct, 1);
//        soundIDs[SoundType.INCORRECT.ordinal()] = soundPool.load(context, R.raw.incorrect, 1);
//        soundIDs[SoundType.WIN.ordinal()] = soundPool.load(context, R.raw.win, 1);
        soundIDs[SoundType.SELECT.ordinal()] = soundPool.load(context, R.raw.select, 1);
        soundIDs[SoundType.PLACECANNON.ordinal()] = soundPool.load(context, R.raw.placecannon, 1);
        soundIDs[SoundType.SHOOT.ordinal()] = soundPool.load(context, R.raw.shoot, 1);
        soundIDs[SoundType.SOLDIER.ordinal()] = soundPool.load(context, R.raw.soldier, 1);
        soundIDs[SoundType.EXPLOSION.ordinal()] = soundPool.load(context, R.raw.explosion, 1);
        soundIDs[SoundType.SCREEM.ordinal()] = soundPool.load(context, R.raw.screem, 1);
        soundIDs[SoundType.WALL.ordinal()] = soundPool.load(context, R.raw.wall, 1);
    }

    /**
     * Sound the value of soundOn.
     */
    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }

    public void setVibrateOn(boolean vibrateOn) {
        this.vibrateOn = vibrateOn;
    }

    /**
     * Plays a resource sound file if soundOn is set.
     */
    void playSound(SoundType soundType) {
        // Add code here
        // Task 2: Play different sound effect according to soundType
        if (soundOn) {
            int index = soundType.ordinal();
            streamIDs[index] = soundPool.play(soundIDs[index], 1, 1, 0, 0, 1);
        }
    }

    void vibrate() {
        if (vibrateOn) {
            vibrator.vibrate(500);
        }
    }

}
