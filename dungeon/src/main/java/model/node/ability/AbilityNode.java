package model.node.ability;

import constants.GameEventEnum;
import model.location.Location;
import model.node.BaseNode;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-22 16:46
 * @description 能力宝石
 */
public class AbilityNode extends BaseNode {

    private int defAbility;
    private int attAbility;

    public AbilityNode(Location location, Image background, String type) {
        super(location, background, GameEventEnum.ADD_ABILITY);
        if("red".equals(type)){
            this.defAbility = 0;
            this.attAbility = 1;
        }else if("blue".equals(type)){
            this.defAbility = 1;
            this.attAbility = 0;
        }
    }

    public int getDefAbility() {
        return defAbility;
    }

    public void setDefAbility(int defAbility) {
        this.defAbility = defAbility;
    }

    public int getAttAbility() {
        return attAbility;
    }

    public void setAttAbility(int attAbility) {
        this.attAbility = attAbility;
    }
}
