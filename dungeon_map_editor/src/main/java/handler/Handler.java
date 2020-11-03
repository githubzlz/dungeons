package handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import map.GameMap;
import node.Location;
import node.Node;

import java.util.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-23 18:56
 * @description
 */
public class Handler {


    public void parseMap(GameMap gameMap){
        Map<Location, Node> nodes = gameMap.getNodes();

        List<Node> walls = new ArrayList<>();

        List<Node> yDoor = new ArrayList<>();
        List<Node> bDoor = new ArrayList<>();
        List<Node> rDoor = new ArrayList<>();

        List<Node> yKey = new ArrayList<>();
        List<Node> bKey = new ArrayList<>();
        List<Node> rKey = new ArrayList<>();

        List<Node> enemies = new ArrayList<>();

        List<Node> rStone = new ArrayList<>();
        List<Node> bStone = new ArrayList<>();

        List<Node> rBottle = new ArrayList<>();
        List<Node> bBottle = new ArrayList<>();

        List<Node> fails = new ArrayList<>();
        Node hero = null;
        Node stairwaysDown = null;
        Node stairwaysUp = null;

        Iterator<Map.Entry<Location, Node>> iterator = nodes.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Location, Node> entry = iterator.next();
            Node node = entry.getValue();
            String name = node.getName();
            if(NameConstants.HERO.equals(name)){
                hero = node;
                continue;
            }
            if(NameConstants.WALL.equals(name)){
                walls.add(node);
                continue;
            }
            if(NameConstants.DOOR_YELLOW.equals(name)){
                yDoor.add(node);
                continue;
            }
            if(NameConstants.DOOR_BLUE.equals(name)){
                bDoor.add(node);
                continue;
            }
            if(NameConstants.DOOR_RED.equals(name)){
                rDoor.add(node);
                continue;
            }
            if(NameConstants.RED_BOTTLE.equals(name)){
                rBottle.add(node);
                continue;
            }
            if(NameConstants.BLUE_BOTTLE.equals(name)){
                bBottle.add(node);
                continue;
            }
            if(NameConstants.RED_STONE.equals(name)){
                rStone.add(node);
                continue;
            }
            if(NameConstants.BLUE_STONE.equals(name)){
                bStone.add(node);
                continue;
            }
            if(NameConstants.KEY_YELLOW.equals(name)){
                yKey.add(node);
                continue;
            }
            if(NameConstants.KEY_BLUE.equals(name)){
                bKey.add(node);
                continue;
            }
            if(NameConstants.KEY_RED.equals(name)){
                rKey.add(node);
                continue;
            }
            if(NameConstants.STAIRWAY_DOWN.equals(name)){
                stairwaysDown = node;
            }
            if(NameConstants.STAIRWAY_UP.equals(name)){
                stairwaysUp = node;
            }
            fails.add(node);
        }


        JSONObject floor = new JSONObject();
        JSONArray location;

        JSONObject background = new JSONObject();
        background.put("map_resource", "image/background1.png");
        floor.put("background", background);

        if(hero != null){
            JSONObject heroJ = new JSONObject();
            location = new JSONArray();
            location.add(hero.getLocation().toString());
            heroJ.put("location",location);
            floor.put("hero", heroJ);
        }

        if(!walls.isEmpty()){
            JSONObject wallJ = new JSONObject();
            Node f = walls.get(0);
            location = new JSONArray();
            wallJ.put("map_resource", f.getImage());

            for (Node wall : walls) {
                location.add(wall.getLocation().toString());
            }
            wallJ.put("location", location);
            floor.put("wall", wallJ);
        }

        if(stairwaysDown != null || stairwaysUp != null){
            JSONObject stairwaysJ = new JSONObject();
            if(stairwaysDown != null){
                JSONObject down = new JSONObject();
                down.put("map_resource", stairwaysDown.getImage());
                location = new JSONArray();
                location.add(stairwaysDown.getLocation().toString());
                down.put("location", location);
                stairwaysJ.put("down", down);
            }
            if(stairwaysUp != null){
                JSONObject up = new JSONObject();
                up.put("map_resource", stairwaysUp.getImage());
                location = new JSONArray();
                location.add(stairwaysUp.getLocation().toString());
                up.put("location", location);
                stairwaysJ.put("up", up);
            }
            floor.put("stairway", stairwaysJ);
        }

        if(!yDoor.isEmpty() || !bDoor.isEmpty() || !rDoor.isEmpty()){
            JSONObject doors = new JSONObject();
            JSONObject type = new JSONObject();
            String sources = "";
            if(!yDoor.isEmpty()){
                JSONObject yellow = new JSONObject();
                location = new JSONArray();
                for (int i = yDoor.size() - 1; i >= 0; i--) {
                    location.add(yDoor.get(i).getLocation().toString());
                }
                yellow.put("location", location);
                type.put("yellow", yellow);
                sources = yDoor.get(0).getImage();
            }
            if(!bDoor.isEmpty()){
                JSONObject blue = new JSONObject();
                location = new JSONArray();
                for (int i = bDoor.size() - 1; i >= 0; i--) {
                    location.add(bDoor.get(i).getLocation().toString());
                }
                blue.put("location", location);
                type.put("blue", blue);
                sources = bDoor.get(0).getImage();
            }
            if(!rDoor.isEmpty()){
                JSONObject red = new JSONObject();
                location = new JSONArray();
                for (int i = rDoor.size() - 1; i >= 0; i--) {
                    location.add(rDoor.get(i).getLocation().toString());
                }
                red.put("location", location);
                type.put("red", red);
                sources = rDoor.get(0).getImage();
            }
            doors.put("type",type);
            doors.put("map_resource", sources);
            floor.put("doors", doors);
        }

        String s = floor.toJSONString();
        System.out.println(s);
    }

}
