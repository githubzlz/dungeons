package model.hero;

import constants.DirectionEnum;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 11:32
 * @description 英雄
 */
public interface HeroInterface {

    /**
     * 行走
     * @param direction 行走方向
     */
    void step(DirectionEnum direction);

}
