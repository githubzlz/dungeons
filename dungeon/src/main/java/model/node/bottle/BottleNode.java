package model.node.bottle;

import constants.GameEventEnum;
import model.location.Location;
import model.node.BaseNode;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-22 16:41
 * @description 血瓶
 */
public class BottleNode extends BaseNode {

    private int blood;

    public BottleNode(Location location, Image background, String type) {
        super(location, background, GameEventEnum.ADD_BLOOD);
        if("red".equals(type)){
            this.blood = 50;
        }else if("blue".equals(type)){
            this.blood = 100;
        }
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }
}
