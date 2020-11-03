package event;

import configuration.GameContent;
import constants.DirectionEnum;
import constants.KeyBoardEnum;
import model.hero.Hero;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-12 11:10
 * @description 根据键盘事件操作英雄移动
 */
public class KeyEventStarter implements KeyListener {

    /**
     * 当前英雄
     */
    private Hero hero;

    /**
     * 事件是否需要被阻塞
     */
    private static boolean BLOCK_UP = false;

    /**
     * 事件是否需要被阻塞
     */
    private static boolean BLOCK_DOWN = false;

    /**
     * 事件是否需要被阻塞
     */
    private static boolean BLOCK_LEFT = false;

    /**
     * 事件是否需要被阻塞
     */
    private static boolean BLOCK_RIGHT = false;

    /**
     * 构造方法读取当前地图
     */
    public KeyEventStarter(){
        //获取英雄
        this.hero = GameContent.gameContent().getHero();
        this.setNotBlock();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyBoardEnum.W.getCode()){
            BLOCK_UP = true;
        }else if(e.getKeyCode() == KeyBoardEnum.A.getCode()){
            BLOCK_LEFT = true;
        }else if(e.getKeyCode() == KeyBoardEnum.D.getCode()){
            BLOCK_RIGHT = true;
        }else if(e.getKeyCode() == KeyBoardEnum.S.getCode()){
            BLOCK_DOWN = true;
        }
    }

    private void setNotBlock(){
        new Timer(5, e -> {
            if(BLOCK_UP){
                this.hero.step(DirectionEnum.UP);
            }
            if(BLOCK_LEFT){
                this.hero.step(DirectionEnum.LEFT);
            }
            if(BLOCK_RIGHT){
                this.hero.step(DirectionEnum.RIGHT);
            }
            if(BLOCK_DOWN){
                this.hero.step(DirectionEnum.DOWN);
            }
        }).start();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyBoardEnum.W.getCode()){
            BLOCK_UP = false;
        }else if(e.getKeyCode() == KeyBoardEnum.A.getCode()){
            BLOCK_LEFT = false;
        }else if(e.getKeyCode() == KeyBoardEnum.D.getCode()){
            BLOCK_RIGHT = false;
        }else if(e.getKeyCode() == KeyBoardEnum.S.getCode()){
            BLOCK_DOWN = false;
        }
    }
}
