package windows;

import map.EditorContent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 14:01
 * @description 窗口
 */
public class EditorMapPanel extends JPanel{

    public EditorMapPanel() {
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
        try {
            super.paint(graphics);
            Map<String, Object> currentInfo = EditorContent.getEditorContent().getCurrentInfo();
            if(currentInfo != null){
                String image = (String) currentInfo.get("image");
                if(image != null){
                    BufferedImage read = ImageIO.read(Objects.requireNonNull(MapPanel.class.getClassLoader().getResourceAsStream(image)));
                    graphics.drawImage(read, 10, 10, 70, 70, null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
