package model.node.doorsandkeys;

import constants.DoorEnum;
import constants.GameEventEnum;
import model.location.Location;
import model.node.BaseNode;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-19 13:15
 * @description 钥匙
 */
public class KeyNode extends BaseNode {

    /**
     * 钥匙对应的门的类型
     */
    private DoorEnum doorEnum;

    public KeyNode(Location location, Image background, DoorEnum doorEnum) {
        super(location, background, GameEventEnum.KEY_ADD);
        this.doorEnum = doorEnum;
    }

    public DoorEnum getDoorEnum() {
        return doorEnum;
    }

    public void setDoorEnum(DoorEnum doorEnum) {
        this.doorEnum = doorEnum;
    }
}
