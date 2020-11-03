package event;

import constants.GameEventEnum;
import model.node.Node;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-15 14:30
 * @description 处理游戏中的各种事件
 */
public class GameEventHandler {

    private static GameEventContent eventContent = new GameEventContent();

    /**
     * 匹配并且执行事件
     * @param nextNode
     * @return
     */
    public static boolean executeEvent(Node nextNode) throws InterruptedException {
        //不存在的空气节点，直接返回true，表示没有事件，英雄可以移动
        if(null == nextNode){
            return true;
        }
        GameEventEnum event = nextNode.getEvent();
        switch (event){
            case WALL:
                //墙：无事件，仅不允许移动
                return false;
            case DOWN_FLOOR:
                //执行下楼梯事件，英雄无需移动，全部地图重绘
                eventContent.switchToNextGameMap();
                return false;
            case UP_FLOOR:
                //执行上楼梯事件，英雄无需移动，全部地图重绘
                eventContent.switchToPreGameMap();
                return false;
            case DOOR_OPEN:
                //执行开门事件
                return eventContent.openTheDoor(nextNode);
            case KEY_ADD:
                //捡钥匙事件
                return eventContent.getOneKey(nextNode);
            case COMBAT:
                //战斗
                return eventContent.fight(nextNode);
            case ADD_BLOOD:
                //捡血瓶
                return eventContent.addBlood(nextNode);
            case ADD_ABILITY:
                //吃宝石
                return eventContent.addAbility(nextNode);
            case NONE:
                // 空节点，无事件，英雄移动
                return true;
            default:
                System.out.println("未知的事件");
                return false;
        }
    }
}
