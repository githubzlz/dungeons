package model.node.stairway;

import constants.GameEventEnum;
import model.location.Location;
import model.node.BaseNode;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-14 17:33
 * @description 楼梯节点
 */
public class StairwayUpNode extends BaseNode {

    public StairwayUpNode(Location location, Image background) {
        super(location, background, GameEventEnum.UP_FLOOR);
    }
}
