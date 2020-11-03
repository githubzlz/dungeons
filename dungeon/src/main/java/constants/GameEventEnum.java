package constants;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-15 14:38
 * @description 游戏事件枚举
 */
public enum  GameEventEnum {

    /**
     * 没有事件
     */
    NONE(0, "没有事件"),

    /**
     * 撞墙
     */
    WALL(1, "撞墙"),

    /**
     * 下楼梯
     */
    DOWN_FLOOR(2, "下楼梯"),

    /**
     * 上楼梯
     */
    UP_FLOOR(3, "上楼梯"),

    /**
     * 开门
     */
    DOOR_OPEN(4,"开门"),

    /**
     * 捡钥匙
     */
    KEY_ADD(5,"捡钥匙"),

    /**
     * 增加血量
     */
    ADD_BLOOD(6, "增加血量"),

    /**
     * 增强属性
     */
    ADD_ABILITY(7, "增强属性"),

    /**
     * 战斗
     */
    COMBAT(8, "战斗");

    private int code;
    private String name;

    GameEventEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
