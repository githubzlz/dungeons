package configuration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import constants.DoorEnum;
import exception.DungeonException;
import map.GameMap;
import model.hero.Hero;
import model.hero.tools.Keys;
import model.hero.tools.Tool;
import model.location.Location;
import model.node.Node;
import model.node.ability.AbilityNode;
import model.node.bottle.BottleNode;
import model.node.doorsandkeys.DoorNode;
import model.node.doorsandkeys.KeyNode;
import model.node.empty.EmptyNode;
import model.node.enemies.EnemyNode;
import model.node.stairway.StairwayDownNode;
import model.node.stairway.StairwayUpNode;
import model.node.wall.WallNode;
import util.GameUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 11:40
 * @description 地图工厂
 */
public class ConfigurationParser {

    /**
     * 使用 类加载器 加载 图片文件
     */
    private static ClassLoader loader = ConfigurationParser.class.getClassLoader();

    /**
     * 地图资源
     */
    private Map<String, GameMap> map = new HashMap<>();

    /**
     * 英雄资源
     */
    private Hero hero;

    /**
     * 饿汉式加载，保证资源解析器唯一
     */
    private static final ConfigurationParser DEFAULT_MAP_FACTORY = new ConfigurationParser();

    /**
     * 私有构造方法，保证地资源解析器全局唯一
     */
    private ConfigurationParser(){
        //创建资源加载器，加载地图资源文件信息
        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        //获取地图资源
        Map<String, String> fileContentCache = configurationLoader.getFileContentCache();
        //初始化英雄,并从fileContentCache中移除配置信息
        initHero(fileContentCache);
        //根据文件，初始化地图
        fileContentCache.forEach((key, value) ->{
            // 创建地图
            GameMap gameMap = new GameMap(Integer.parseInt(key.substring(key.length()-1)));
            initGameMap(value, gameMap);
            this.map.put(key, gameMap);
        });
    }

    /**
     * 初始化英雄信息
     * @param fileContentCache fileContentCache
     */
    private void initHero(Map<String, String> fileContentCache){
        String heroConfig = fileContentCache.get("hero");
        fileContentCache.remove("hero");
        JSONObject heroJson = JSONObject.parseObject(heroConfig);
        //节点信息
        List<Location> locations = getLocationInfo(heroJson);
        //图片资源信息
        JSONObject heroResource = heroJson.getJSONObject("resource");
        Map<String, Image> imageMap = new HashMap<>(4);
        BufferedImage imageLeft = getImage(heroResource.getString("left"));
        BufferedImage imageRight = getImage(heroResource.getString("right"));
        BufferedImage imageUp = getImage(heroResource.getString("up"));
        BufferedImage imageDown = getImage(heroResource.getString("down"));
        imageMap.put("left", imageLeft);
        imageMap.put("right", imageRight);
        imageMap.put("up", imageUp);
        imageMap.put("down", imageDown);
        //战斗相关信息
        int blood = heroJson.getInteger("blood");
        int attack = heroJson.getInteger("attack");
        int defensePhysical = heroJson.getInteger("defense_physical");
        int defenseMagic = heroJson.getInteger("defense_magic");
        //道具相关信息
        JSONObject tools = heroJson.getJSONObject("tools");
        JSONObject keysJson = tools.getJSONObject("keys");
        //初始化英雄
        Node node = new EmptyNode(locations.get(0));
        this.hero = new Hero(node, imageMap);
        this.hero.setBlood(blood);
        this.hero.setAttack(attack);
        this.hero.setDefensePhysical(defensePhysical);
        this.hero.setDefenseMagic(defenseMagic);
        Tool tool = new Keys(keysJson.getInteger("yellow"),keysJson.getInteger("blue"),keysJson.getInteger("red"));
        this.hero.addNewTool("key", tool);
    }

    /**
     * 根据解析配置文件的信息设置地图节点的属性
     * @param configuration 配置文件信息
     * @param gameMap 地图
     */
    private void initGameMap(String configuration, GameMap gameMap){
        JSONObject jsonObject = JSONObject.parseObject(configuration);
        //背景
        setBackGround(jsonObject.getJSONObject("background"), gameMap);
        //墙体
        setWallNode(jsonObject.getJSONObject("wall"), gameMap);
        //当前地图英雄起始位置
        setHeroNode(jsonObject.getJSONObject("hero"), gameMap);
        //楼梯
        setStairway(jsonObject.getJSONObject("stairway"), gameMap);
        //门
        setDoors(jsonObject.getJSONObject("doors"), gameMap);
        //钥匙
        setKeysNode(jsonObject.getJSONObject("keys"), gameMap);
        //敌人
        setEnemiesNode(jsonObject.getJSONArray("enemies"), gameMap);
        //宝石
        setAbilityStone(jsonObject.getJSONObject("ability_stone"), gameMap);
        //血瓶
        setBloodBottle(jsonObject.getJSONObject("blood_bottle"), gameMap);
    }

    /**
     * 设置背景
     * @param background 背景配置文件信息
     * @param gameMap 当前地图
     */
    private void setBackGround(JSONObject background, GameMap gameMap){
        //获取背景信息
        String backgroundResource = background.getString("map_resource");
        Image image = getImage(backgroundResource);
        gameMap.setBackground(image);
    }

    /**
     * 设置墙体
     * @param wall 墙体配置文件信息
     * @param gameMap 当前地图
     */
    private void setWallNode(JSONObject wall, GameMap gameMap){
        //节点信息
        List<Location> locations = getLocationInfo(wall);
        //图片资源信息
        String wallResource = wall.getString("map_resource");
        BufferedImage image = getImage(wallResource);
        //创建节点
        locations.forEach( location -> {
            Node node = new WallNode(location, image);
            gameMap.putNote(location, node);
        });
    }

    /**
     * 设置英雄位置
     * @param hero 英雄配置文件信息
     * @param gameMap 地图
     */
    private void setHeroNode(JSONObject hero, GameMap gameMap){
        //节点信息
        List<Location> locations = getLocationInfo(hero);
        //英雄位置只能存在一个
        if(locations.size() != 1){
            return;
        }
        gameMap.setHeroStartLocation(locations.get(0));
    }

    /**
     * 设置楼梯
     * @param stairway 楼梯配置文件信息
     * @param gameMap 地图
     */
    private void setStairway(JSONObject stairway, GameMap gameMap){
        if(null == stairway){
            return;
        }
        //下楼梯节点信息
        JSONObject down = stairway.getJSONObject("down");
        if(null != down){
            List<Location> locations = getLocationInfo(down);
            //图片资源信息
            String stairwayResource = down.getString("map_resource");
            BufferedImage image = getImage(stairwayResource);
            //英雄位置只能存在一个
            if(locations.size() != 1){
                return;
            }
            Node node = new StairwayDownNode(locations.get(0), image);
            gameMap.putNote(locations.get(0), node);
        }
        //上楼梯节点信息
        JSONObject up = stairway.getJSONObject("up");
        if(null != up){
            List<Location> locations = getLocationInfo(up);
            //图片资源信息
            String resource = up.getString("map_resource");
            BufferedImage image = getImage(resource);
            //英雄位置只能存在一个
            if(locations.size() != 1){
                return;
            }
            Node node = new StairwayUpNode(locations.get(0), image);
            gameMap.putNote(locations.get(0), node);
        }
    }

    /**
     * 设置门
     * @param door
     * @param gameMap
     */
    private void setDoors(JSONObject door, GameMap gameMap){
        if(null == door){
            return;
        }
        JSONObject types = door.getJSONObject("type");
        String resource = door.getString("map_resource");
        BufferedImage image = getImage(resource);

        //设置黄色的门
        List<Location> yellow = getLocationInfo(types.getJSONObject("yellow"));
        yellow.forEach(location -> {
            Node node = new DoorNode(location, image, DoorEnum.YELLOW);
            gameMap.putNote(location, node);
        });
        //设置蓝色的门
        List<Location> blue = getLocationInfo(types.getJSONObject("blue"));
        blue.forEach(location -> {
            Node node = new DoorNode(location, image, DoorEnum.BLUE);
            gameMap.putNote(location, node);
        });
        //设置红色的门
        List<Location> red = getLocationInfo(types.getJSONObject("red"));
        red.forEach(location -> {
            Node node = new DoorNode(location, image, DoorEnum.RED);
            gameMap.putNote(location, node);
        });
    }

    /**
     * 解析钥匙相关信息
     * @param keys
     * @param gameMap
     */
    private void setKeysNode(JSONObject keys, GameMap gameMap){
        if(null == keys){
            return;
        }
        JSONObject types = keys.getJSONObject("type");
        String resource = keys.getString("map_resource");
        BufferedImage image = getImage(resource);

        //设置黄色的钥匙
        List<Location> yellow = getLocationInfo(types.getJSONObject("yellow"));
        yellow.forEach(location -> {
            Node node = new KeyNode(location, image, DoorEnum.YELLOW);
            gameMap.putNote(location, node);
        });
        //设置蓝色的门
        List<Location> blue = getLocationInfo(types.getJSONObject("blue"));
        blue.forEach(location -> {
            Node node = new KeyNode(location, image, DoorEnum.BLUE);
            gameMap.putNote(location, node);
        });
        //设置红色的门
        List<Location> red = getLocationInfo(types.getJSONObject("red"));
        red.forEach(location -> {
            Node node = new KeyNode(location, image, DoorEnum.RED);
            gameMap.putNote(location, node);
        });
    }

    /**
     * 敌人
     * @param enemies
     * @param gameMap
     */
    private void setEnemiesNode(JSONArray enemies, GameMap gameMap){
        if(null == enemies){
            return;
        }
        List<JSONObject> list = enemies.toJavaList(JSONObject.class);
        if(null == list || list.isEmpty()){
            return;
        }
        for (JSONObject jsonObject : list) {
            BufferedImage image = getImage(jsonObject.getString("map_resource"));
            BufferedImage image2 = getImage(jsonObject.getString("fight_cartoon"));
            List<Location> locationInfo = getLocationInfo(jsonObject);
            locationInfo.forEach(location -> {
                Node node = new EnemyNode(location, image, image2,
                        jsonObject.getInteger("blood"),
                        jsonObject.getInteger("attack"),
                        jsonObject.getInteger("defense_physical"),
                        jsonObject.getInteger("defense_magic"));
                gameMap.putNote(location, node);
            });
        }
    }

    /**
     * 能力宝石
     * @param bottle
     * @param gameMap
     */
    private void setAbilityStone(JSONObject bottle, GameMap gameMap){
        if(null == bottle){
            return;
        }

        JSONObject red = bottle.getJSONObject("red");
        JSONObject blue = bottle.getJSONObject("blue");

        List<Location> locationInfo = getLocationInfo(red);
        Image image = getImage(red.getString("map_resource"));
        for (Location location : locationInfo) {
            Node node = new AbilityNode(location, image, "red");
            gameMap.putNote(location, node);
        }
        locationInfo = getLocationInfo(blue);
        image = getImage(blue.getString("map_resource"));
        for (Location location : locationInfo) {
            Node node = new AbilityNode(location, image, "blue");
            gameMap.putNote(location, node);
        }
    }

    /**
     * 血瓶
     * @param bottle
     * @param gameMap
     */
    private void setBloodBottle(JSONObject bottle, GameMap gameMap){
        if(null == bottle){
            return;
        }

        JSONObject red = bottle.getJSONObject("red");
        JSONObject blue = bottle.getJSONObject("blue");

        List<Location> locationInfo = getLocationInfo(red);
        Image image = getImage(red.getString("map_resource"));
        for (Location location : locationInfo) {
            Node node = new BottleNode(location, image, "red");
            gameMap.putNote(location, node);
        }
        locationInfo = getLocationInfo(blue);
        image = getImage(blue.getString("map_resource"));
        for (Location location : locationInfo) {
            Node node = new BottleNode(location, image, "blue");
            gameMap.putNote(location, node);
        }
    }

    /**
     * 根据配置获取节点坐标信息
     * @param info
     * @return
     */
    private List<Location> getLocationInfo(JSONObject info){
        if(null == info){
            return new ArrayList<>();
        }
        JSONArray locations = info.getJSONArray("location");
        if(locations == null){
            return new ArrayList<>();
        }
        List<String> heroLocationList = locations.toJavaList(String.class);
        return GameUtil.parseLocation(heroLocationList);
    }

    /**
     * 读取图片
     * @param source
     * @return
     */
    public static BufferedImage getImage(String source){
        try{
            InputStream resourceAsStream = loader.getResourceAsStream(source);
            if(null == resourceAsStream){
                throw new DungeonException("图片文件读取失败");
            }
            return ImageIO.read(resourceAsStream);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Map<String, GameMap> getGameMaps(){
        return DEFAULT_MAP_FACTORY.map;
    }
    public static Hero getHero(){
        return DEFAULT_MAP_FACTORY.hero;
    }
}
