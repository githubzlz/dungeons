package event;

import configuration.GameContent;
import constants.DirectionEnum;
import constants.DoorEnum;
import exception.DungeonException;
import map.GameMap;
import model.hero.Hero;
import model.hero.tools.Keys;
import model.location.Location;
import model.node.Node;
import model.node.ability.AbilityNode;
import model.node.bottle.BottleNode;
import model.node.doorsandkeys.DoorNode;
import model.node.doorsandkeys.KeyNode;
import model.node.empty.EmptyNode;
import model.node.enemies.EnemyNode;
import windows.MainWindowPanel;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-18 17:04
 * @description 游戏事件
 */
public class GameEventContent {

    /**
     * 游戏的容器
     */
    private GameContent gameContent;

    public GameEventContent(){
        this.gameContent = GameContent.gameContent();
    }

    /**
     * 英雄移动事件处理
     * @param direction
     */
    public void heroStep(DirectionEnum direction){
        try {
            //获取容器
            GameMap gameMap = GameContent.gameContent().getCurrentGameMap();
            //获取画板
            MainWindowPanel mainWindowJPanel = GameContent.gameContent().getMainWindowJPanel();
            //获取英雄
            Hero hero = GameContent.gameContent().getHero();
            //获取英雄当前位置
            Location location = hero.getNode().getLocation();
            //获取英雄当前朝向
            DirectionEnum currentDirection = hero.getCurrentDirection();
            //判断英雄是否需要切换朝向
            if(!currentDirection.equals(direction)){
                hero.setCurrentDirection(direction);
                // 重绘
                mainWindowJPanel.update(mainWindowJPanel.getGraphics());
            }

            //获取英雄将要去到的node
            Location next = getNextLocation(location, direction);
            Node nextNode = gameMap.getNodes().get(next);
            //触发事件，根据事件返回值判断英雄是否需要移动
            boolean moveNeeded = GameEventHandler.executeEvent(nextNode);

            //根据返回值处理英雄移动，将英雄移动分为4步
            //1.起手 2.中间 3.结尾 4.完成
            double x = (next.getX()-location.getX())*0.25;
            double y = (next.getY()-location.getY())*0.25;
            if(moveNeeded){
                for(int i=1; i<=4; i++){
                    // 英雄坐标移动向指定位置
                    location.setX(location.getX()+x);
                    location.setY(location.getY()+y);
                    // 设置英雄的状态
                    hero.setHeroStatus(i%4);
                    Thread.sleep(40);
                    // 重绘
                    mainWindowJPanel.update(mainWindowJPanel.getGraphics());
                }
                //清除计算误差
                location.setX(next.getX());
                location.setY(next.getY());
                mainWindowJPanel.update(mainWindowJPanel.getGraphics());
            }else {
                // 不需要移动的事件也要处理一下延时，防止事件在计时器中执行多次
                Thread.sleep(160);
            }
        }catch (DungeonException | InterruptedException e){
            System.out.println("移动失败");
            e.printStackTrace();
        }
    }

    /**
     * 切换上一张地图
     */
    public void switchToPreGameMap(){
        GameContent gameContent = GameContent.gameContent();
        //到达最底层的时候就不允许切换地图了
        if(gameContent.getCurrentGameMap().getFloor() <= 0){
            return;
        }
        gameContent.setNextGameMap(gameContent.getCurrentGameMap());
        gameContent.setCurrentGameMap(gameContent.getPreviousGameMap());

        //到达顶层的时候不存在上一层
        if(gameContent.getPreviousGameMap().getFloor() > 1){
            gameContent.setPreviousGameMap(gameContent.getGameMapList().get(gameContent.getPreviousGameMap().getFloor()-1));
        }else {
            gameContent.setPreviousGameMap(null);
        }
        //根据地图层级重设英雄的位置
        gameContent.getHero().setNode(new EmptyNode(gameContent.getCurrentGameMap().getHeroStartLocation()));
        if(gameContent.getMainWindowJPanel() != null){
            gameContent.getStartGame().setTitle("魔塔（第" + gameContent.getCurrentGameMap().getFloor() + "层）");
            gameContent.getMainWindowJPanel().update(gameContent.getMainWindowJPanel().getGraphics());
        }
    }

    /**
     * 切换下一张地图
     */
    public void switchToNextGameMap(){
        GameContent gameContent = GameContent.gameContent();
        //到达最底层的时候就不允许切换地图了
        if(gameContent.getCurrentGameMap().getFloor()+1 >= gameContent.getGameMapList().size()){
            return;
        }
        gameContent.setPreviousGameMap(gameContent.getCurrentGameMap());
        gameContent.setCurrentGameMap(gameContent.getNextGameMap());
        //到最底层的时候不存在下一层
        if(gameContent.getCurrentGameMap().getFloor()+1 < gameContent.getGameMapList().size()){
            gameContent.setNextGameMap(gameContent.getGameMapList().get(gameContent.getCurrentGameMap().getFloor()+1));
        }else {
            gameContent.setNextGameMap(null);
        }
        gameContent.getHero().setNode(new EmptyNode(gameContent.getCurrentGameMap().getHeroStartLocation()));
        if(gameContent.getMainWindowJPanel() != null){
            gameContent.getStartGame().setTitle("魔塔（第" + gameContent.getCurrentGameMap().getFloor() + "层）");
            gameContent.getMainWindowJPanel().update(gameContent.getMainWindowJPanel().getGraphics());
        }
    }

    /**
     * 开门事件
     * @param node
     */
    boolean openTheDoor(Node node){
        Node empty = new EmptyNode(node.getLocation());
        GameMap currentGameMap = gameContent.getCurrentGameMap();
        Keys keys = (Keys) gameContent.getHero().getTool("key");
        DoorNode door = (DoorNode) node;
        DoorEnum doorEnum = door.getDoorEnum();
        switch (doorEnum){
            case YELLOW:
                boolean success = keys.removeOneYellowKey();
                if(success){
                    currentGameMap.getNodes().replace(node.getLocation(), empty);
                    return true;
                }
                return false;
            case BLUE:
                success = keys.removeOneBlueKey();
                if(success){
                    currentGameMap.getNodes().replace(node.getLocation(), empty);
                    return true;
                }
                return false;
            case RED:
                success = keys.removeOneRedKey();
                if(success){
                    currentGameMap.getNodes().replace(node.getLocation(), empty);
                    return true;
                }
                return false;
            default:
                System.out.println("未知的门类型");
        }
        return true;
    }

    /**
     * 开门事件
     * @param node
     */
    boolean getOneKey(Node node){
        Node empty = new EmptyNode(node.getLocation());
        GameMap currentGameMap = gameContent.getCurrentGameMap();
        currentGameMap.getNodes().replace(node.getLocation(), empty);
        Keys keys = (Keys) gameContent.getHero().getTool("key");
        KeyNode key = (KeyNode) node;
        DoorEnum doorEnum = key.getDoorEnum();
        switch (doorEnum){
            case YELLOW:
                keys.addYellowKey();
                break;
            case BLUE:
                keys.addBlueKey();
                break;
            case RED:
                keys.addRedKey();
                break;
            default:
                System.out.println("未知的门类型");
        }
        return true;
    }

    /**
     * 吃血瓶
     * @param node
     * @return
     */
    boolean addBlood(Node node){

        //获取地图，英雄
        GameMap currentGameMap = gameContent.getCurrentGameMap();
        Hero hero = gameContent.getHero();

        //node转化为血瓶node
        BottleNode bottleNode = (BottleNode) node;

        //英雄加血
        hero.addBlood(bottleNode.getBlood());

        //清空血瓶
        Node empty = new EmptyNode(node.getLocation());
        currentGameMap.getNodes().replace(node.getLocation(), empty);
        return true;
    }

    /**
     * 吃宝石
     * @param node
     * @return
     */
    boolean addAbility(Node node){
        //获取地图，英雄
        GameMap currentGameMap = gameContent.getCurrentGameMap();
        Hero hero = gameContent.getHero();

        //node转化为血瓶node
        AbilityNode abilityNode = (AbilityNode) node;

        //英雄增加能力
        hero.addDefensePhysical(abilityNode.getDefAbility());
        hero.addAttack(abilityNode.getAttAbility());

        //清空血瓶
        Node empty = new EmptyNode(node.getLocation());
        currentGameMap.getNodes().replace(node.getLocation(), empty);
        return true;
    }

    /**
     * 战斗
     * @param node
     * @return
     */
    boolean fight(Node node){
        try {
            GameMap currentGameMap = gameContent.getCurrentGameMap();
            EnemyNode enemyNode = (EnemyNode)currentGameMap.getNodes().get(node.getLocation());


            /**
             * 假设怪血1000，攻bai250，防200。
             * 自己血1500，攻680，防60。
             * 则自己损血（250-60）*（1000/（680-200）（近似值））du=380
             */
            //判断英雄是否可以战胜改怪物
            Hero hero = gameContent.getHero();
            int eB = enemyNode.getBlood();
            int eD = enemyNode.getDefensePhysical();
            int eA = enemyNode.getAttack();
            int hB = hero.getBlood();
            int hD = hero.getDefensePhysical();
            int hA = hero.getAttack();

            //单次战斗损耗-英雄先攻击
            int cD1 = hA-eD;
            int cD2 = eA-hD;
            if(cD1 <= 0){
                System.out.println("无法战胜");
                return false;
            }
            if(cD2 <= 0){
                //将怪物node移除
                Node empty = new EmptyNode(node.getLocation());
                currentGameMap.getNodes().replace(node.getLocation(), empty);
                return true;
            }
            //开始战斗
            int i=1;
            while (true){
                //怪物扣血
                eB -= cD1;
                if(eB <= 0){
                    break;
                }

                //人物扣血
                boolean b = hero.removeBlood(cD2);
                if(!b){
                    System.out.println("无法战胜");
                    hero.setBlood(hB);
                    return false;
                }

                //重绘
                for (int j=1; j<6; j++){
                    enemyNode.setState(i++/6);
                    gameContent.getMainWindowJPanel().update(gameContent.getMainWindowJPanel().getGraphics());
                }
            }

            //将怪物node移除
            Node empty = new EmptyNode(node.getLocation());
            currentGameMap.getNodes().replace(node.getLocation(), empty);
            return true;
        }catch (Exception e){
            return false;
        }

    }


    /**
     * 获取英雄即将到达的位置
     * @param location location
     * @param  direction direction
     * @return Location
     * @throws DungeonException DungeonException
     */
    private Location getNextLocation(Location location, DirectionEnum direction) throws DungeonException {
        if(DirectionEnum.UP.equals(direction)){
            return new Location(location.getX(),location.getY()-1);
        }
        if(DirectionEnum.DOWN.equals(direction)){
            return new Location(location.getX(),location.getY()+1);
        }
        if(DirectionEnum.LEFT.equals(direction)){
            return new Location(location.getX()-1,location.getY());
        }
        if(DirectionEnum.RIGHT.equals(direction)){
            return new Location(location.getX()+1,location.getY());
        }
        throw new DungeonException("获取方向异常");
    }
}
