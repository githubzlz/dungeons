package model.node.wall;

import constants.GameEventEnum;
import model.location.Location;
import model.node.BaseNode;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 11:59
 * @description 地图点
 */
public class WallNode extends BaseNode {

    public WallNode(Location location, Image background) {
        super(location, background, GameEventEnum.WALL);
    }
}
