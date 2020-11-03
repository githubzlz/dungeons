package handler;

import map.EditorContent;
import node.Node;
import windows.MapPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-23 18:59
 * @description
 */
public class HandlerEditor {

    /**
     * 按钮配置
     * @return
     */
    public List<JButton> jButtonConfig(){
        List<JButton> buttons = new ArrayList<>();

        //英雄
        JButton button = getB("image/hero/hero.png", NameConstants.HERO);
        buttons.add(button);

        //墙
        button = getB("image/wall1.png", NameConstants.WALL);
        buttons.add(button);

        //门
        button = getB("image/doorandkeys/door1.png", NameConstants.DOOR_YELLOW);
        buttons.add(button);
        button = getB("image/doorandkeys/door2.png", NameConstants.DOOR_BLUE);
        buttons.add(button);
        button = getB("image/doorandkeys/door3.png", NameConstants.DOOR_RED);
        buttons.add(button);
        button = getB("image/doorandkeys/door4.png", NameConstants.DOOR_COM);
        buttons.add(button);

        //楼梯 stairway
        button = getB("image/stairway/stairway_down.png", NameConstants.STAIRWAY_DOWN);
        buttons.add(button);
        button = getB("image/stairway/stairway_up.png", NameConstants.STAIRWAY_UP);
        buttons.add(button);

        //钥匙
        button = getB("image/doorandkeys/key1.png", NameConstants.KEY_YELLOW);
        buttons.add(button);
        button = getB("image/doorandkeys/key2.png", NameConstants.KEY_BLUE);
        buttons.add(button);
        button = getB("image/doorandkeys/key3.png", NameConstants.KEY_RED);
        buttons.add(button);
        button = getB("image/doorandkeys/key4.png", NameConstants.KEY_COM);
        buttons.add(button);

        //血瓶
        button = getB("image/blood_bottle/blue_bottle.png", NameConstants.BLUE_BOTTLE);
        buttons.add(button);
        button = getB("image/blood_bottle/red_bottle.png", NameConstants.RED_BOTTLE);
        buttons.add(button);

        //宝石
        button = getB("image/ability_stone/blue_stone.png", NameConstants.BLUE_STONE);
        buttons.add(button);
        button = getB("image/ability_stone/red_stone.png", NameConstants.RED_STONE);
        buttons.add(button);

        //敌人
        button = getB("image/enemies/1.png", NameConstants.ENEMY_1);
        buttons.add(button);
        button = getB("image/enemies/2.png", NameConstants.ENEMY_2);
        buttons.add(button);
        button = getB("image/enemies/3.png", NameConstants.ENEMY_3);
        buttons.add(button);

        return buttons;
    }

    private JButton getB(String sources, String name){
        ImageIcon icon = new ImageIcon();
        Node node = new Node();
        BufferedImage read = getImage(sources);
        node.setImage(sources);
        node.setBack(read);
        JButton jButton = new JButton();
        icon.setImage(read);
        jButton.setIcon(icon);
        jButton.addActionListener( a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name",name);
            map.put("image",sources);
            map.put("node", node);
            EditorContent.getEditorContent().setCurrentInfo(map);
            EditorContent.getEditorContent().getEditorMapPanel().repaint();
        });

        return jButton;
    }

    private BufferedImage getImage(String sources){
        InputStream resourceAsStream = MapPanel.class.getClassLoader().getResourceAsStream(sources);
        if(null == resourceAsStream){
            return null;
        }
        try {
            return ImageIO.read(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
