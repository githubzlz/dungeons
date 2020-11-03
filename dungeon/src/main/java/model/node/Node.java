package model.node;

import constants.GameEventEnum;
import model.location.Location;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 12:00
 * @description 地图元素
 */
public interface Node {

    /**
     * 获取背景图片
     * @return 图片信息
     */
    Image getBackground();

    /**
     * 获取node的坐标
     * @return 图片信息
     */
    Location getLocation();

    /**
     * 获取事件枚举
     * @return
     */
    GameEventEnum getEvent();
}
