package configuration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-11 21:02
 * @description 资源加载器，用来加载地图配置文件
 */
public class ConfigurationLoader {

    /**
     * 使用 类加载器 加载 配置文件
     */
    private static ClassLoader loader = ConfigurationLoader.class.getClassLoader();

    /**
     * 关卡配置文件总数
     */
    private int floors;

    /**
     * 配置文件信息缓存
     */
    private Map<String, String> fileContentCache = new HashMap<>();

    /**
     * 构造方法，创建对象时加载资源文件
     */
    public ConfigurationLoader(){
        //获取文件信息
        scanMapConfigurationFiles();
    }

    /**
     * 获取配置文件
     * @return
     */
    public void scanMapConfigurationFiles() {

        //读取配置文件信息
        InputStream inputStream = loader.getResourceAsStream("maps/GameInfo.json");
        String config = readInputAsString(inputStream);
        JSONArray jsonArray = JSONObject.parseObject(config).getJSONArray("floors");
        List<String> list = jsonArray.toJavaList(String.class);

        //读取英雄信息配置文件
        String hero = JSONObject.parseObject(config).getString("hero");
        inputStream = loader.getResourceAsStream(hero);
        config = readInputAsString(inputStream);
        this.fileContentCache.put("hero", config);

        //读取地图配置文件
        for (String pathInfo : list) {
            String substring = pathInfo.substring(1, pathInfo.length() - 1);
            String[] split = substring.split(":");
            inputStream = loader.getResourceAsStream(split[1].substring(1, split[1].length()-1));
            String content = readInputAsString(inputStream);
            this.fileContentCache.put(split[0].substring(1, split[0].length()-1), content);
        }
        this.floors = list.size();

    }

    /**
     * 将json格式的输入流读取成字符串类型
     * @param inputStream
     * @return
     * */
    private String readInputAsString(InputStream inputStream) {
        if(null == inputStream){
           return "";
        }
        try(ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024*4];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();

            return new String(bytes);
        }catch (IOException e){
            System.out.println("文件读取异常");
            return "";
        }finally {
            try {
                inputStream.close();
            }catch (IOException e){
                System.out.println("流关闭失败");
            }
        }
    }

    /**
     * 获取地图层数
     * @return
     */
    public int getFloors() {
        return floors;
    }

    public Map<String, String> getFileContentCache() {
        return fileContentCache;
    }
}
