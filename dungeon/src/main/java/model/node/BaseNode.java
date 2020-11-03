package model.node;

import constants.GameEventEnum;
import model.location.Location;

import java.awt.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-12 15:16
 * @description
 */
public abstract class BaseNode implements Node {

    /**
     * node的位置
     */
    private Location location;

    /**
     * 背景元素
     */
    private Image background;

    /**
     * 指定英雄遇到改node会发生的事件枚举
     */
    private GameEventEnum event;

    protected BaseNode(Location location, Image background, GameEventEnum event) {
        this.location = location;
        this.background = background;
        this.event = event;
    }

    @Override
    public Image getBackground() {
        return this.background;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public GameEventEnum getEvent() {
        return this.event;
    }
}
