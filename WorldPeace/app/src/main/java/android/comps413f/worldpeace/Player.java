package android.comps413f.worldpeace;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Vector;

import static android.comps413f.worldpeace.R.drawable.abc_tab_indicator_material;
import static android.comps413f.worldpeace.R.drawable.soldier;

/* simulating a player*/

public class Player {

    private int playerId;
    private Context context;



    private int castleDestroyed;

    private Flag flag1;
    private Flag flag2;
    private Flag flag3;
    private Flag flag4;
    private Flag flag5;

    private boolean flagHasBeenDestroyed;
    private  DefenseNet net1;
    private  DefenseNet net2;
    //private final DefenseNet net3;

    private Castle tin;
    private Castle ha;
    private Castle ti;
    private Castle ping;



    private ArrayList<Cannon> cannons;
    private ArrayList<Soldier> soldiers;
    private ArrayList<Flag> flags;

    //private Soldier soldier;
    //test
    ///public static Cannon cannon;
    //Context cs;


    public Player(Context context, int playerId) {

        this.playerId = playerId;

        tin = new Castle(context, Castle.CastleType.TIN);
        ha = new Castle(context, Castle.CastleType.HA);
        ti = new Castle(context, Castle.CastleType.TI);
        ping = new Castle(context, Castle.CastleType.PING);
        flag1 = new Flag(context, Flag.FlagCount.flag1);
        flag2 = new Flag(context, Flag.FlagCount.flag2);
        flag3 = new Flag(context, Flag.FlagCount.flag3);
        flag4 = new Flag(context, Flag.FlagCount.flag4);
        flag5 = new Flag(context, Flag.FlagCount.flag5);
        net1 = null;
        net2 = null;
        flagHasBeenDestroyed = false;
        castleDestroyed = 0;
        //net1 = new DefenseNet(context, DefenseNet.NetCount.net1);
        //net2 = new DefenseNet(context, DefenseNet.NetCount.net2);
        //net3 = new DefenseNet(context, DefenseNet.NetCount.net3);
        //soldier = new Soldier(context);
        //test
        //cannon = new Cannon(context);

        this.context = context;
        cannons = new ArrayList<>();
        soldiers = new ArrayList<>();
    }

    public void startGame() {
        if (tin != null) {
            tin.createCastle(tin.getWidth(), tin.getHeight(), this);
        }
        ha.createCastle(-ha.getWidth(), ha.getHeight(), this);
        ti.createCastle(ti.getWidth(), 0, this);
        ping.createCastle(-ping.getWidth(), 0, this);
        flag1.createFlag(0, this);
        flag2.createFlag(flag2.getWidth(), this);
        flag3.createFlag(-flag3.getWidth(), this);
        flag4.createFlag(flag4.getWidth() * 2, this);
        flag5.createFlag(-flag5.getWidth() * 2, this);
        if (net1 != null) {
            net1.createDefenseNet(net1.getWidth(), net1.getHeight(), this);
        }
        if (net2 != null) {
            net2.createDefenseNet(net2.getWidth(), net2.getHeight(), this);
        }
        //net3.createFlag(net1.getWidth(), net1.getHeight());
        //soldier.createSoldier(soldier.getWidth(), this);
        //test
        //cannon.createCannon(5, 5, playerId);
    }

    //this method will be called periodically for changes
    //!!should check whether = null
    public void drawOn(Canvas canvas) {
        if (tin != null) {
            tin.drawOn(canvas);
        }
        if (ha != null) {
            ha.drawOn(canvas);
        }
        if (ti != null) {
            ti.drawOn(canvas);
        }
        if (ping != null) {
            ping.drawOn(canvas);
        }
        if (flag1 != null) {
            flag1.drawOn(canvas);
        }
        if (flag2 != null) {
            flag2.drawOn(canvas);
        }
        if (flag3 != null) {
            flag3.drawOn(canvas);
        }
        if (flag4 != null) {
            flag4.drawOn(canvas);
        }
        if (flag5 != null) {
            flag5.drawOn(canvas);
        }

        if (net1 != null) {
            net1.drawOn(canvas);
        }
        if (net2 != null) {
            net2.drawOn(canvas);
        }


        for (Cannon cannon : cannons) {
            cannon.drawOn(canvas);
            if (cannon.getBullet() != null ){
                cannon.getBullet().drawOn(canvas);
            }
        }

        for (Soldier soldier : soldiers) {
            soldier.drawOn(canvas);
        }
        //for(DefenseNet elementNets : defensenet){
         //   elementNets.drawOn(canvas);
       // }
        //soldier.drawOn(canvas);
        //cannon.drawOn(canvas);
        //net3.drawOn(canvas);
    }
    public boolean isFlagHasBeenDestroyed() {
        return flagHasBeenDestroyed;
    }

    public void setFlagHasBeenDestroyed(boolean flagHasBeenDestroyed) {
        this.flagHasBeenDestroyed = flagHasBeenDestroyed;
    }

    public int getCastleDestroyed() {
        return castleDestroyed;
    }

    public void setCastleDestroyed(int castleDestroyed) {
        this.castleDestroyed = castleDestroyed;
    }

    public void setFlag1(Flag flag1) {
        this.flag1 = flag1;
    }

    public void setFlag2(Flag flag2) {
        this.flag2 = flag2;
    }

    public void setFlag3(Flag flag3) {
        this.flag3 = flag3;
    }

    public void setFlag4(Flag flag4) {
        this.flag4 = flag4;
    }

    public void setFlag5(Flag flag5) {
        this.flag5 = flag5;
    }

    public void setNet1(DefenseNet net1){
        this.net1 = net1;
    }

    public void setNet2(DefenseNet net2){
        this.net2 = net2;
    }


    public int getPlayerId() {
        return playerId;
    }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public Context getContext(){
        return context;
    }
    public ArrayList<Cannon> getCannons() {
        return cannons;
    }
    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }


    public int addCannon(Cannon cannon) {
        if ((playerId == 1 && cannon.curPos.y < WorldPeaceView.arenaHeight / 2)
                || (playerId == 2 && cannon.curPos.y > WorldPeaceView.arenaHeight / 2)) {
            return 1;
        }
        if (checkCollision(cannon))
            return 2;
        if (cannon.collideBoundary(playerId))
            return 3;
        if (checkCollision())
            return 4;

        cannons.add(cannon);
        return 0;
    }

    public boolean addSoldier(Soldier soldier) {
        if ((playerId == 1 && soldier.curPos.y < WorldPeaceView.arenaHeight / 2)
                || (playerId == 2 && soldier.curPos.y > WorldPeaceView.arenaHeight / 2)) {
            return false;
        }
        soldiers.add(soldier);
        return true;
    }

    public boolean removeSoldier(Soldier soldier) {
        soldiers.remove(soldier);
        return true;
    }

    public String addDefenceNet() {
        String check = "castle";
        if (castleDestroyed == 0 && !isFlagHasBeenDestroyed()){
            if (net1 == null) {
                net1 =  new DefenseNet(context, DefenseNet.NetCount.net1);
                net1.createDefenseNet(net1.getWidth(), net1.getHeight(), this);
                check="ok";
            }else if (net1 != null && net2 == null){
                net2 = new DefenseNet(context, DefenseNet.NetCount.net2);
                net2.createDefenseNet(net2.getWidth(), net2.getHeight(), this);
                check = "ok";
            } else if(net1!=null && net2!=null){
                check = "number";
                return check;
            }
        }
        return check;
    }

    public Cannon findCannon(int x, int y){
        for (Cannon target: cannons){
            //Log.i("shoot", "searching: "+target.getCurPos().x + ", " + target.getCurPos().y + " VS " + x + ", " + y);
            if (checkCannonSelected(x, y, target)) {
                //Log.i("shoot", "searched");
                return target;
            }
        }

        return null;
    }

    public boolean checkCannonSelected(int x, int y, Cannon cannon){
        Log.i("shoott", playerId+":  checking selected: curPos=("+cannon.getCurPos().x
                +", "+ cannon.getCurPos().y +") / (x, y)=("+x+", "+y+") / (height, width)=("+cannon.getHeight()+", "+cannon.getWidth()+")");
            if (x <= (cannon.getCurPos().x + cannon.getWidth())
                    && x >= cannon.getCurPos().x
                    && y <= (cannon.getCurPos().y + cannon.getHeight())
                    && y >= cannon.getCurPos().y) {
                return true;
            }
        return false;
    }

    //check whether weapon will collide with cannons before adding weapon
    public boolean checkCollision(Weapon weapon){
        boolean result = false;
        if (cannons != null){
            for (Cannon cannon: cannons){
                result = cannon.collideWith(weapon);
                if (result) {
                    return result;
                }
                break;
            }
        }
        if (flags != null) {
            for (Flag flag : flags) {
                result = flag.collideWith(weapon);
                if (result) {
                    return result;
                }
                break;
            }
        }
        if (net1 != null) {
            if (net1.collideWith(weapon)) {
                result = true;
            }
        }
        if (net2 != null) {
            if (net2.collideWith(weapon)) {
                result = true;
            }
        }
        if (tin != null) {
            if (tin.collideWith(weapon)) {
                result = true;
            }
        }
        if (ha != null) {
            if (ha.collideWith(weapon)) {
                result = true;
            }
        }
        if (ti != null) {
            if (tin.collideWith(weapon)) {
                result = true;
            }
        }
        if (ping != null) {
            if (ping.collideWith(weapon)) {
                result = true;
            }
        }
        return result;
    }

    //check collision with net
    public boolean checkCollision(){
        boolean collideNet1 = false;
        boolean collideNet2 = false;

        if (cannons != null){
            for (Cannon cannon: cannons){
                collideNet1 = cannon.collideNet(playerId, net1);
                collideNet2 = cannon.collideNet(playerId, net2);
            }
        }

        if (collideNet1 || collideNet2)
            return true;
        else
            return false;
    }

    public Castle getTin() {
        return tin;
    }

    public void setTin(Castle tin) {
        this.tin = tin;
    }

    public Castle getHa() {
        return ha;
    }

    public void setHa(Castle ha) {
        this.ha = ha;
    }

    public Castle getTi() {
        return ti;
    }

    public void setTi(Castle ti) {
        this.ti = ti;
    }

    public Castle getPing() {
        return ping;
    }

    public void setPing(Castle ping) {
        this.ping = ping;
    }

    public boolean checkAllFlagDestroyed() {
        if (flag1 != null) {
            return false;
        } else if (flag2 != null) {
            return false;
        } else if (flag3 != null) {
            return false;
        } else if (flag4 != null) {
            return false;
        } else if (flag5 != null) {
            return false;
        }
        return true;
    }

    public Flag getFlag1() {
        return flag1;
    }

    public Flag getFlag2() {
        return flag2;
    }

    public Flag getFlag3() {
        return flag3;
    }

    public Flag getFlag4() {
        return flag4;
    }

    public Flag getFlag5() {
        return flag5;
    }

    public DefenseNet getNet1() {
        return net1;
    }
    public DefenseNet getNet2() {
        return net2;
    }


}

