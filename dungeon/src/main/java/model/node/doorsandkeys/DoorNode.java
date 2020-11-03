package model.node.doorsandkeys;

import constants.DoorEnum;
import constants.GameEventEnum;
import model.location.Location;
import model.node.BaseNode;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-17 17:03
 * @description 黄色的门
 */
public class DoorNode extends BaseNode {

    /**
     * 门的类型
     */
    private DoorEnum doorEnum;

    /**
     * 门的状态 0：关 1：开
     * 默认 关
     */
    private Integer status = 0;

    public DoorNode(Location location, Image background, DoorEnum doorEnum) {
        super(location, background, GameEventEnum.DOOR_OPEN);
        this.doorEnum = doorEnum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DoorEnum getDoorEnum() {
        return doorEnum;
    }

    public void setDoorEnum(DoorEnum doorEnum) {
        this.doorEnum = doorEnum;
    }
}
