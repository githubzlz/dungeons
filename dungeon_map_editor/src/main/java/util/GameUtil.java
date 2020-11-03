package util;

import node.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 20:30
 * @description 载入图片的工具类
 */
public class GameUtil {

    /**
     * 解析配置文件坐标点
     * @param locationConfigs
     * @return
     */
    public static List<Location> parseLocation(List<String> locationConfigs){
        try {
            // 创建节点位置信息集合
            List<Location> locations = new ArrayList<>();

            //解析位置信息
            for (String locationConfig : locationConfigs) {

                //若是"(1,1)-(1,13)"格式，则根据区域创建位置信息
                if(locationConfig.contains("-")){

                    //ls = ["(1,1)","(1,13)"]
                    String[] ls = locationConfig.split("-");
                    //检查解析出的信息是否正确
                    if(ls.length != 2 || null == ls[0] || "".equals(ls[0]) || null == ls[1] || "".equals(ls[1])){
                        throw new Exception("节点信息解析异常");
                    }

                    //去掉括号 =1,1  1,13
                    String substring = ls[0].substring(1, ls[0].length() - 1);
                    //解析起始点 x,y坐标
                    String[] xy = substring.split(",");
                    int startX = Integer.parseInt(xy[0]);
                    int startY = Integer.parseInt(xy[1]);
                    //结束点x,y坐标
                    substring = ls[1].substring(1, ls[1].length() - 1);
                    xy = substring.split(",");
                    int endX = Integer.parseInt(xy[0]);
                    int endY = Integer.parseInt(xy[1]);

                    //起始坐标必须位于结束坐标左侧或上方
                    if(startX > endX || startY > endY){
                        throw new Exception("文件解析失败");
                    }

                    // 坐标点都在y轴上的情况
                    if(startX == endX){
                        for(int i1 = startY; i1 <= endY; i1++){
                            locations.add(new Location(startX, i1));
                        }
                    // 坐标点都在x轴上的情况
                    }else if(startY == endY){
                        for(int i1 = startX; i1 <= endX; i1++){
                            locations.add(new Location(i1, startY));
                        }
                    }else {
                        throw new Exception("文件解析失败");
                    }

                //若是(1,1)格式，则直接添加坐标点
                }else {
                    String substring = locationConfig.substring(1, locationConfig.length() - 1);
                    String[] ls = substring.split(",");
                    //检查解析出的信息是否正确
                    if(ls.length != 2 || null == ls[0] || "".equals(ls[0]) || null == ls[1] || "".equals(ls[1])){
                        throw new Exception("节点信息解析异常");
                    }
                    locations.add(new Location(Integer.parseInt(ls[0]),Integer.parseInt(ls[1])));
                }
            };
            return locations;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
