package constants;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-14 17:03
 * @description 键盘按键
 */
public enum KeyBoardEnum {

    /**
     * W
     */
    W(87, "W"),

    /**
     * A
     */
    A(65,"A"),

    /**
     * S
     */
    S(83, "S"),

    /**
     * D
     */
    D(68, "D");
    private int code;
    private String name;

    KeyBoardEnum(int code, String name) {
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
