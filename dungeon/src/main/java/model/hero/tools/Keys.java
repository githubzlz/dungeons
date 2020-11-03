package model.hero.tools;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-18 20:59
 * @description 钥匙
 */
public class Keys implements Tool{

    private int yellow;

    private int blue;

    private int red;

    public Keys(int yellow, int blue, int red) {
        this.yellow = yellow;
        this.blue = blue;
        this.red = red;
    }

    public boolean removeOneYellowKey(){
        if(this.yellow <=0){
            return false;
        }
        this.yellow--;
        return true;
    }

    public boolean removeOneBlueKey(){
        if(this.blue <=0){
            return false;
        }
        this.blue--;
        return true;
    }

    public boolean removeOneRedKey(){
        if(this.red <=0){
            return false;
        }
        this.red--;
        return true;
    }

    public int addYellowKey(){
        return this.yellow++;
    }

    public int addBlueKey(){
        return this.blue++;
    }

    public int addRedKey(){
        return this.red++;
    }

    public int getYellow() {
        return yellow;
    }

    public int getBlue() {
        return blue;
    }

    public int getRed() {
        return red;
    }
}
