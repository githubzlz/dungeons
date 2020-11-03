package model.node.empty;

import constants.GameEventEnum;
import model.location.Location;
import model.node.Node;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-17 17:05
 * @description
 */
public class EmptyNode implements Node {

    /**
     * node的位置
     */
    private Location location;

    /**
     * 指定英雄遇到改node会发生的事件枚举
     */
    private GameEventEnum event = GameEventEnum.NONE;

    public EmptyNode(Location location) {
        this.location = location;
    }

    @Override
    public Image getBackground() {
        return null;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public GameEventEnum getEvent() {
        return this.event;
    }

}
