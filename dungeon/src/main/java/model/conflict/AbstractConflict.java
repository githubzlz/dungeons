package model.conflict;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-18 20:49
 * @description 战斗属性抽象类
 */
public abstract class AbstractConflict {

    /**
     * 血量
     */
    private int blood;

    /**
     * 攻击力
     */
    private int attack;

    /**
     * 物理防御力
     */
    private int defensePhysical;

    /**
     * 魔抗
     */
    private int defenseMagic;

    /**
     * 增加魔法防御
     * @param defenseMagic
     */
    public void addDefenseMagic(int defenseMagic){
        this.defenseMagic += defenseMagic;
    }

    /**
     * 扣除魔法防御
     * @param defenseMagic
     * @return
     */
    public boolean removeDefenseMagic(int defenseMagic){
        if(defenseMagic >= this.defenseMagic){
            return false;
        }
        this.defenseMagic -= defenseMagic;
        return true;
    }

    /**
     * 增加物理防御
     * @param defensePhysical
     */
    public void addDefensePhysical(int defensePhysical){
        this.defensePhysical += defensePhysical;
    }

    /**
     * 扣除物理防御
     * @param defensePhysical
     * @return
     */
    public boolean removeDefensePhysical(int defensePhysical){
        if(defensePhysical >= this.defensePhysical){
            return false;
        }
        this.defensePhysical -= defensePhysical;
        return true;
    }

    /**
     * 增加攻击
     * @param attack
     */
    public void addAttack(int attack){
        this.attack += attack;
    }

    /**
     * 扣除攻击
     * @param attack
     * @return
     */
    public boolean removeAttack(int attack){
        if(attack >= this.attack){
            return false;
        }
        this.attack -= attack;
        return true;
    }

    /**
     * 增加血量
     * @param blood
     */
    public void addBlood(int blood){
        this.blood += blood;
    }

    /**
     * 扣除血量
     * @param blood
     * @return
     */
    public boolean removeBlood(int blood){
        if(blood >= this.blood){
            return false;
        }
        this.blood -= blood;
        return true;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getDefensePhysical() {
        return defensePhysical;
    }

    public void setDefensePhysical(int defensePhysical) {
        this.defensePhysical = defensePhysical;
    }

    public int getDefenseMagic() {
        return defenseMagic;
    }

    public void setDefenseMagic(int defenseMagic) {
        this.defenseMagic = defenseMagic;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
