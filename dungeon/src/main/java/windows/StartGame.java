package windows;

import configuration.GameContent;
import constants.MapConstants;
import event.KeyEventStarter;

import javax.swing.*;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 11:33
 * @description
 */
public class StartGame extends JFrame {

    public static void main(String[] args) {

        StartGame startGame = new StartGame();
        startGame.setTitle("魔塔");
        startGame.setLayout(null);
        //设置大小
        startGame.setSize(MapConstants.WIDTH_X * 50+240, MapConstants.HEIGHT_Y * 50+20);
        //设置居中
        startGame.setLocationRelativeTo(null);
        //设置可关闭
        startGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置绝对布局（窗口里面的内容不会随着窗口的改变而改变）
        startGame.setLayout(null);
        //设置窗口不可拉伸改变大小
        startGame.setResizable(false);
        //设置面板可见
        startGame.setVisible(true);


        //创建主界面画板
        MainWindowPanel mainWindowPanel = new MainWindowPanel();
        startGame.getLayeredPane().add(mainWindowPanel,JLayeredPane.DEFAULT_LAYER);
        //创建英雄信息面板
        HeroInfoWindowPanel heroInfoWindowPanel = new HeroInfoWindowPanel();
        startGame.getLayeredPane().add(heroInfoWindowPanel, JLayeredPane.PALETTE_LAYER);
        //创建战斗信息面板
//        CombatWindowPanel combatWindowPanel = new CombatWindowPanel();
//        startGame.getLayeredPane().add(combatWindowPanel, JLayeredPane.DRAG_LAYER);

        //初始化容器并将画板放入容器
        GameContent.gameContent().setStartGame(startGame);
        GameContent.gameContent().setMainWindowJPanel(mainWindowPanel);
        GameContent.gameContent().setHeroInfoWindowPanel(heroInfoWindowPanel);
//        GameContent.gameContent().setCombatWindowPanel(combatWindowPanel);
        //给画板添加按键事件
        startGame.addKeyListener(new KeyEventStarter());
    }
}
