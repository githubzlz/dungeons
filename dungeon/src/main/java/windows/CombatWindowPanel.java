package windows;

import configuration.ConfigurationParser;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-22 19:10
 * @description 战斗面板
 */
public class CombatWindowPanel extends JPanel {

    public CombatWindowPanel(){
        //设置面板可见
        this.setVisible(false);
        //设置panel的位置
        this.setBounds(420,200,280, 120);
        this.setBackground(null);
        this.setOpaque(false);
    }

    @Override
    public void update(Graphics g) {
        Image imageBuffer = createImage(this.getWidth(), this.getHeight());
        Graphics graImage = imageBuffer.getGraphics();
        paint(graImage);
        graImage.dispose();
        g.drawImage(imageBuffer, 0, 0, this);
    }

    @Override
    public void paint(Graphics graphics){
        BufferedImage back = ConfigurationParser.getImage("image/comback.png");
        graphics.drawImage(back, 0,0, 280,120, null);

        BufferedImage number = ConfigurationParser.getImage("image/number/number.png");

        BufferedImage image = ConfigurationParser.getImage("image/enemies/1.png");
        graphics.drawImage(image, 30,40, 50,50, null);
        paintNumber(graphics, number, 158, 30, 10);

        BufferedImage image1 = ConfigurationParser.getImage("image/attack.png");
        graphics.drawImage(image1, 115,45, 40,40, null);

        BufferedImage image2 = ConfigurationParser.getImage("image/hero/down.png");
        graphics.drawImage(image2, 200,40, 250,90, 50,0,100,50, null);
        paintNumber(graphics, number, 60, 210, 10);
    }

    /**
     * 画数字信息的方法
     * @param graphics
     * @param image
     * @param number
     * @param startX
     * @param startY
     */
    private void paintNumber(Graphics graphics, BufferedImage image, int number, int startX, int startY){
        startY += 2;
        int height = 16;
        int width = 12;
        int numberWidth = image.getWidth()/10;
        int imageHeight = image.getHeight();
        String numberStr = String.valueOf(number);
        for(int i=0; i< numberStr.length(); i++){
            int num = Integer.parseInt(numberStr.substring(i,i+1));
            int start = numberWidth*num;
            int end = numberWidth*(num+1);
            graphics.drawImage(image, startX, startY,startX+width,startY+height, start, 0, end, imageHeight, null );
            startX += width;
        }
    }
}
