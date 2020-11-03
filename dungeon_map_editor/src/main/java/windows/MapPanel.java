package windows;

import map.EditorContent;
import map.GameMap;
import node.Location;
import node.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 14:01
 * @description 窗口
 */
public class MapPanel extends JPanel{

    private int x = 0;
    private int y = 0;

    private Location startLocation;

    public MapPanel(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addNode(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                startLocation =  getPosition(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Location position = getPosition(e.getX(), e.getY());
            }
        });
        //添加鼠标点击事件
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int x1 = x/50;
                int x2 = x1%13+1;
                int y = e.getY();
                int y1 = y/50;
                int y2 = y1%13+1;
                paintBorder(x2, y2);
            }
        });

    }

    @Override
    public void update(Graphics g) {
        Image imageBuffer = createImage(this.getWidth(), this.getHeight());
        Graphics graImage = imageBuffer.getGraphics();
        paint(graImage);
        graImage.dispose();
        g.drawImage(imageBuffer, 0, 0, this);
    }

    public void paintBorder(int x, int y) {
        this.x = x-1;
        this.y = y-1;
        this.update(this.getGraphics());
    }

    @Override
    public void paint(Graphics graphics) {
        try {
            super.paint(graphics);
            BufferedImage read = ImageIO.read(MapPanel.class.getClassLoader().getResourceAsStream("image/background1.png"));
            for(int x = 1; x<= 13; x++){
                for(int y=1;y<= 13;y++){
                    graphics.drawImage(read, (x-1)*50, (y-1)*50,50,50,null);
                }
            }

            Map<Location, Node> nodes = EditorContent.getEditorContent().getGameMap().getNodes();
            nodes.forEach((location, node) ->{
                int x = (int) location.getX();
                int y = (int) location.getY();
                graphics.drawImage(node.getBack(), (x-1)*50, (y-1)*50,50,50,null);
            });

            Graphics2D g2d = (Graphics2D)graphics;
            g2d.setStroke(new BasicStroke(3.0f));
            g2d.setColor(Color.GREEN);
            int x = this.x;
            int y = this.y;
            graphics.drawLine(50*x,50*y,50*(x+1),50*y);
            graphics.drawLine(50*x,50*y,50*x,50*(y+1));
            graphics.drawLine(50*(x+1),50*(y+1),50*(x+1),50*y);
            graphics.drawLine(50*(x+1),50*(y+1),50*x,50*(y+1));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addNode(MouseEvent e){
        Location location = getPosition(e.getX(), e.getY());
        System.out.println(location);
        GameMap gameMap = EditorContent.getEditorContent().getGameMap();
        Map<String, Object> currentInfo = EditorContent.getEditorContent().getCurrentInfo();
        Node node = new Node();
        Node source = (Node) currentInfo.get("node");
        String name = (String) currentInfo.get("name");
        node.setName(name);
        node.setImage(source.getImage());
        node.setBack(source.getBack());
        node.setLocation(location);
        gameMap.putNote(location, node);
    }

    private Location getPosition(int x, int y){
        int x1 = x/50;
        int x2 = x1%13+1;
        int y1 = y/50;
        int y2 = y1%13+1;
        return new Location(x2,y2);
    }
}
