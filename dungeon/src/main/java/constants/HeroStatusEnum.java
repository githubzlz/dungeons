package constants;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-16 15:30
 * @description 英雄状态枚举
 */
public enum  HeroStatusEnum {

    NONE(0, "静止"),
    WALKING_FIRST_STEP(1, "行走前"),
    WALKING_MIDDLE(2, "行走中"),
    WALKING_LAST_STEP(3, "行走后");

    private int code;
    private String name;

    HeroStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static HeroStatusEnum getHeroStatusEnum(int code){
        for (HeroStatusEnum value : HeroStatusEnum.values()) {
            if(value.getCode() == code){
                return value;
            }
        }
        return null;
    }
}
