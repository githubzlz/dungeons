package windows;

import configuration.ConfigurationParser;
import configuration.GameContent;
import constants.MapConstants;
import model.hero.Hero;
import model.hero.tools.Keys;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-19 13:48
 * @description 英雄信息的画板
 */
public class HeroInfoWindowPanel extends JPanel {

    public HeroInfoWindowPanel(){
        this.setLayout(null);
        //设置大小
        this.setSize(240, MapConstants.HEIGHT_Y * 50);
        //设置绝对布局（窗口里面的内容不会随着窗口的改变而改变）
        this.setLayout(null);
        //设置面板可见
        this.setVisible(true);
        //设置panel的位置
        this.setBounds(0,0,240, MapConstants.HEIGHT_Y * 50+20);
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
    public void paint(Graphics graphics) {

        BufferedImage background = ConfigurationParser.getImage("image/back.png");
        graphics.drawImage(background, 0, 0,240,650, null );

        paintAttackInfo(graphics);
        paintKeysInfo(graphics);

    }

    /**
     * 攻击防御血量等信息
     * @param graphics
     */
    private void paintAttackInfo(Graphics graphics){
        // 字体资源
        int x = 30;
        int y = 80;
        int fontHeight = 20;
        int fontWidth = 40;
        int space = 35;
        BufferedImage font = ConfigurationParser.getImage("image/hero/info/info_font.png");
        BufferedImage number = ConfigurationParser.getImage("image/number/number.png");
        Hero hero = GameContent.gameContent().getHero();
        graphics.drawImage(font, x, y,x+fontWidth,y+fontHeight, 100, 50, 200, 100, null );
        paintNumber(graphics, number, hero.getBlood(), x+60, y);
        graphics.drawImage(font, x, y+space,x+fontWidth,y+fontHeight+space, 0, 0, 100, 50, null );
        paintNumber(graphics, number, hero.getAttack(), x+60, y+space);
        graphics.drawImage(font, x, y+space*2,x+fontWidth,y+fontHeight+space*2, 100, 0, 200, 50, null );
        paintNumber(graphics, number, hero.getDefensePhysical(), x+60, y+space*2);
        graphics.drawImage(font, x, y+space*3,x+fontWidth,y+fontHeight+space*3,0, 50, 100, 100, null );
        paintNumber(graphics, number, hero.getDefenseMagic(), x+60, y+space*3);
    }

    /**
     * 钥匙的数量
     * @param graphics
     */
    private void paintKeysInfo(Graphics graphics){
        int x = 30;
        int y = 230;
        int space = 40;
        Hero hero = GameContent.gameContent().getHero();
        Keys keys = (Keys) hero.getTool("key");
        BufferedImage number = ConfigurationParser.getImage("image/number/number.png");
        BufferedImage keySources = ConfigurationParser.getImage("image/doorandkeys/keys.png");
        int width = keySources.getWidth();
        int height = keySources.getHeight();
        graphics.drawImage(keySources, x, y,x+40,y+40, 0, 0, width/4, height, null );
        paintNumber(graphics, number, keys.getYellow(), x+60, y+9);
        graphics.drawImage(keySources, x, y+space,x+40,y+space+40, width/4, 0, width/2, height, null );
        paintNumber(graphics, number, keys.getBlue(), x+60, y+space+9);
        graphics.drawImage(keySources, x, y+space*2,x+40,y+space*2+40, width/2, 0, width/4*3, height, null );
        paintNumber(graphics, number, keys.getRed(), x+60, y+space*2+9);
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
