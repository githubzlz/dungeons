package model.hero;

import constants.DirectionEnum;
import event.GameEventContent;
import model.conflict.AbstractConflict;
import model.hero.tools.Tool;
import model.node.Node;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 11:33
 * @description 英雄
 */
public class Hero extends AbstractConflict implements HeroInterface {

    /**
     * 英雄所在的位置
     */
    private Node node;

    /**
     * 英雄的背景
     */
    private Map<String, Image> background;

    /**
     * 当前英雄的朝向
     */
    private DirectionEnum currentDirection;

    /**
     * 英雄的状态
     */
    private Integer heroStatus;

    /**
     * 当前英雄拥有的道具
     */
    private Map<String, Tool> tools = new HashMap<>();

    public Hero(Node node, Map<String, Image> imageMap){
        this.node = node;
        this.background = imageMap;
        //设置默认朝向,设置默认状态
        this.currentDirection = DirectionEnum.DOWN;
        this.heroStatus = 0;
    }

    public void addNewTool(String name, Tool tool){
        this.tools.put(name, tool);
    }

    public Tool getTool(String name){
        return this.tools.get(name);
    }

    public void setCurrentDirection(DirectionEnum currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Integer getHeroStatus() {
        return heroStatus;
    }

    public void setHeroStatus(Integer heroStatus) {
        this.heroStatus = heroStatus;
    }

    public DirectionEnum getCurrentDirection() {
        return currentDirection;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public Image getBackground(DirectionEnum direction) {
        return this.background.get(direction.getName());
    }

    @Override
    public synchronized void  step(DirectionEnum direction) {
        GameEventContent gameEventContent = new GameEventContent();
        gameEventContent.heroStep(direction);
    }
}
