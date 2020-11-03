package model.node.enemies;

import constants.GameEventEnum;
import model.location.Location;
import model.node.BaseNode;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-20 19:41
 * @description 怪物
 */
public class EnemyNode extends BaseNode {

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
     * 战斗特效
     */
    private Image fightCartoon;

    /**
     * 状态 0：存活 1-5：战斗中
     */
    private Integer state;

    public EnemyNode(Location location, Image background, Image fightCartoon, int blood, int attack, int defensePhysical, int defenseMagic) {
        super(location, background, GameEventEnum.COMBAT);
        this.fightCartoon = fightCartoon;
        this.blood = blood;
        this.attack = attack;
        this.defensePhysical = defensePhysical;
        this.defenseMagic = defenseMagic;
        this.state = 0;
    }

    public int getBlood() {
        return blood;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefensePhysical() {
        return defensePhysical;
    }

    public int getDefenseMagic() {
        return defenseMagic;
    }

    public Image getFightCartoon() {
        return fightCartoon;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
