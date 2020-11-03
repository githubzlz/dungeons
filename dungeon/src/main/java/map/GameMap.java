package map;

import model.location.Location;
import model.node.Node;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 11:38
 * @description 地图
 */
public class GameMap {

    /**
     * 地图层级
     */
    private Integer floor;

    /**
     * 地图背景
     */
    private Image background;

    /**
     * 地图之内的node共13*13个node
     */
    private Map<Location, Node> nodes = new HashMap<Location, Node>(169);

    /**
     * 英雄起始位置
     */
    private Location heroStartLocation;

    public GameMap(Integer floor) {
        this.floor = floor;
    }

    public Integer getFloor() {
        return floor;
    }

    public Location getHeroStartLocation() {
        return heroStartLocation;
    }

    public void setHeroStartLocation(Location heroStartLocation) {
        this.heroStartLocation = heroStartLocation;
    }

    public Map<Location, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Location, Node> nodes) {
        this.nodes = nodes;
    }

    public void putNote(Location location, Node node) {
        this.nodes.put(location, node);
    }

    public Image getBackground() {
        return background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }
}
