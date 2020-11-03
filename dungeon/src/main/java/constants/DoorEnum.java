package constants;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-17 17:37
 * @description 门的枚举
 */
public enum DoorEnum {

    YELLOW(0,"黄色的门"),
    BLUE(1, "蓝色的门"),
    RED(2, "红色的门");

    private int code;
    private String name;

    DoorEnum(int code, String name) {
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
