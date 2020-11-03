package windows;

import configuration.GameContent;
import constants.DoorEnum;
import constants.HeroStatusEnum;
import constants.MapConstants;
import map.GameMap;
import model.hero.Hero;
import model.location.Location;
import model.node.Node;
import model.node.doorsandkeys.DoorNode;
import model.node.doorsandkeys.KeyNode;
import model.node.enemies.EnemyNode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 14:01
 * @description 窗口
 */
public class MainWindowPanel extends JPanel{

    public MainWindowPanel(){
        this.setLayout(null);
        //设置大小
        this.setSize(MapConstants.WIDTH_X * 50, MapConstants.HEIGHT_Y * 50);
        //设置绝对布局（窗口里面的内容不会随着窗口的改变而改变）
        this.setLayout(null);
        //设置面板可见
        this.setVisible(true);
        //设置panel的位置
        this.setBounds(240,0,MapConstants.WIDTH_X * 50, MapConstants.HEIGHT_Y * 50);
    }

    @Override
    public void update(Graphics g) {
        Image imageBuffer = createImage(this.getWidth(), this.getHeight());
        Graphics graImage = imageBuffer.getGraphics();
        paint(graImage);
        graImage.dispose();
        g.drawImage(imageBuffer, 0, 0, this);
        GameContent.gameContent().getHeroInfoWindowPanel().update(GameContent.gameContent().getHeroInfoWindowPanel().getGraphics());
    }

    @Override
    public void paint(Graphics graphics) {
        GameMap currentGameMap = GameContent.gameContent().getCurrentGameMap();
        if(currentGameMap != null){
           paintMapBackGrounds(graphics, currentGameMap);
           paintNodes(graphics, currentGameMap);
           paintHero(graphics);
       }
    }

    /**
     * 画背景
     * @param graphics graphics
     */
    private void paintMapBackGrounds(Graphics graphics, GameMap gameMap){
        for(int x = 1; x<= MapConstants.WIDTH_X; x++){
            for(int y=1;y<= MapConstants.HEIGHT_Y;y++){
                graphics.drawImage(gameMap.getBackground(), (x-1)*50, (y-1)*50,50,50,null);
            }
        }
    }

    /**
     * 画node节点
     * @param graphics graphics
     */
    private void paintNodes(Graphics graphics, GameMap gameMap){
        Map<Location, Node> nodes = gameMap.getNodes();
        nodes.forEach((location,node) ->{
            if(node.getBackground() != null){
                // 画门
                if(node instanceof DoorNode | node instanceof KeyNode){
                    paintDoorsAndKeys(graphics, node);
                }else if(node instanceof EnemyNode){
                    paintEnemyNode(graphics, node);
                }else {
                    int x = (int)(location.getX()-1)*50;
                    int y = (int)(location.getY()-1)*50;
                    graphics.drawImage(node.getBackground(), x, y,50,50,null);
                }
            }
        });
    }

    /**
     * 画英雄
     * @param graphics graphics
     */
    public void paintHero(Graphics graphics){
        Hero hero = GameContent.gameContent().getHero();
        Node node = hero.getNode();
        Location location = node.getLocation();

        //根据朝向获取英雄的图片资源
        BufferedImage image = (BufferedImage) hero.getBackground(hero.getCurrentDirection());
        //获取英雄状态
        HeroStatusEnum heroStatusEnum = HeroStatusEnum.getHeroStatusEnum(hero.getHeroStatus());
        int width = image.getWidth();
        int height = image.getHeight();
        int heroWidth = width/3;
        int x = (int)((location.getX()-1)*50);
        int y = (int)((location.getY()-1)*50);
        assert heroStatusEnum != null;
        switch (heroStatusEnum){
            case NONE:
            case WALKING_MIDDLE:
                graphics.drawImage(image, x, y,x+50,y+50, heroWidth, 0, heroWidth*2, height, null );
                break;
            case WALKING_FIRST_STEP:
                graphics.drawImage(image, x, y,x+50,y+50, 0, 0, heroWidth, height, null );
                break;
            case WALKING_LAST_STEP:
                graphics.drawImage(image, x, y,x+50,y+50, heroWidth*2, 0, heroWidth*3, height, null );
                break;
            default:
                System.out.println("错误的英雄状态");
        }
    }

    /**
     * 画门
     * @param graphics graphics
     * @param node node
     */
    private void paintDoorsAndKeys(Graphics graphics, Node node){
        //转化为门的类型
        DoorEnum doorEnum;
        Location location;
        BufferedImage image;
        if(node instanceof DoorNode){
            DoorNode door = (DoorNode)node;
            doorEnum = door.getDoorEnum();
            location = door.getLocation();
            image = (BufferedImage) door.getBackground();
        }else {
            KeyNode key = (KeyNode)node;
            doorEnum = key.getDoorEnum();
            location = key.getLocation();
            image = (BufferedImage) key.getBackground();
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int doorWidth = width/4;
        int x = (int)((location.getX()-1)*50);
        int y = (int)((location.getY()-1)*50);
        switch (doorEnum){
            case YELLOW:
                graphics.drawImage(image, x, y,x+50,y+50, 0, 0, doorWidth, height, null );
                break;
            case BLUE:
                graphics.drawImage(image, x, y,x+50,y+50, doorWidth, 0, doorWidth*2, height, null );
                break;
            case RED:
                graphics.drawImage(image, x, y,x+50,y+50, doorWidth*2, 0, doorWidth*3, height, null );
                break;
            default:
        }
    }

    /**
     * 绘制敌人
     * @param graphics
     * @param node
     */
    private void paintEnemyNode(Graphics graphics, Node node){
        EnemyNode enemyNode = (EnemyNode) node;
        Location location = node.getLocation();
        int state = enemyNode.getState();
        if(state == 0){
            int x = (int)(location.getX()-1)*50;
            int y = (int)(location.getY()-1)*50;
            graphics.drawImage(node.getBackground(), x, y,50,50,null);
        //战斗特效
        }else {
            BufferedImage fightCartoon = (BufferedImage) enemyNode.getFightCartoon();
            int width = fightCartoon.getWidth();
            int height = fightCartoon.getHeight();
            int one = width/5;
            int x = (int)((location.getX()-1)*50);
            int y = (int)((location.getY()-1)*50);
            graphics.drawImage(fightCartoon, x, y,x+50,y+50, one*(state-1), 0, one*state, height, null );
            graphics.drawString("战斗中", 10, 10);
        }
    }
}
