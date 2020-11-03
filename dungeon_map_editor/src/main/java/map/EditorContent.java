package map;

import windows.EditorMapPanel;
import windows.MapPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-23 18:45
 * @description
 */
public class EditorContent {

    private GameMap gameMap;

    private List<GameMap> gameMapList = new ArrayList<>();

    private static EditorContent editorContent = new EditorContent();

    private MapPanel mapPanel;

    private EditorMapPanel editorMapPanel;

    private Map<String, Object> currentInfo;

    public Map<String, Object> getCurrentInfo() {
        return currentInfo;
    }

    public void setCurrentInfo(Map<String, Object> currentInfo) {
        this.currentInfo = currentInfo;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public List<GameMap> getGameMapList() {
        return gameMapList;
    }

    public void setGameMapList(List<GameMap> gameMapList) {
        this.gameMapList = gameMapList;
    }

    public static EditorContent getEditorContent() {
        return editorContent;
    }

    public static void setEditorContent(EditorContent editorContent) {
        EditorContent.editorContent = editorContent;
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    public EditorMapPanel getEditorMapPanel() {
        return editorMapPanel;
    }

    public void setEditorMapPanel(EditorMapPanel editorMapPanel) {
        this.editorMapPanel = editorMapPanel;
    }
}
