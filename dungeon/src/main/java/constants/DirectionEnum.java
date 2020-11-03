package constants;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-14 16:59
 * @description 方位枚举
 */
public enum DirectionEnum {
    /**
     * 上
     */
    UP(1,"up"),

    /**
     * 下
     */
    DOWN(2, "down"),

    /**
     * 左
     */
    LEFT(3,"left"),

    /**
     * 右
     */
    RIGHT(4, "right");

    private int code;
    private String name;

    DirectionEnum(int code, String name) {
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
