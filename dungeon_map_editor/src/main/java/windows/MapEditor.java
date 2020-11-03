package windows;

import handler.Handler;
import handler.HandlerEditor;
import map.EditorContent;
import map.GameMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 11:33
 * @description
 */
public class MapEditor extends JFrame {

    public static void main(String[] args) throws InterruptedException, IOException {
        MapEditor mapEditor = new MapEditor();
        mapEditor.setTitle("魔塔地图编辑器");
        //设置大小
        mapEditor.setSize(50*13+50*13, 50*13+29);
        mapEditor.setLayout(null);
        //设置居中
        mapEditor.setLocationRelativeTo(null);
        //设置可关闭
        mapEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口不可拉伸改变大小
        mapEditor.setResizable(false);
        //设置面板可见
        mapEditor.setVisible(true);

        //初始化地图面板
        MapPanel mapPanel = new MapPanel();
        mapPanel.setBounds(0,0,50*13, 50*13);
        mapPanel.setBackground(Color.BLACK);
        mapEditor.add(mapPanel);
        mapEditor.setFocusable(true);

        //初始化控件选择面板
        EditorMapPanel editorMapPanel = new EditorMapPanel();
        editorMapPanel.setBounds(655,0,50*13, 50*13);
        editorMapPanel.setLayout(null);
        mapEditor.add(editorMapPanel);

        //初始化容器
        EditorContent editorContent = EditorContent.getEditorContent();
        editorContent.setEditorMapPanel(editorMapPanel);
        editorContent.setMapPanel(mapPanel);
        editorContent.setGameMap(new GameMap(1));

        //初始化按钮组
        HandlerEditor he = new HandlerEditor();
        List<JButton> buttons = he.jButtonConfig();
        for (int i = 0; i < buttons.size(); i++) {
            editorMapPanel.add(buttons.get(i));
            int x = 20+60*(i%10);
            int y = 100;
            if((10+60*i) >= 580){
                y +=60*(i/10);
            }
            buttons.get(i).setBounds(x,y, 50,50);
        }

        //生成按钮
        JButton jButton = new JButton("生成地图");
        jButton.setBounds(520,600,  100,40);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Handler handler = new Handler();
                GameMap gameMap = EditorContent.getEditorContent().getGameMap();
                handler.parseMap(gameMap);
                //EditorContent.getEditorContent().setGameMap(new GameMap(1));
                mapEditor.repaint();
            }
        });

        editorMapPanel.add(jButton);

        mapEditor.repaint();
    }

}
