package configuration;

import map.GameMap;
import model.hero.Hero;
import windows.CombatWindowPanel;
import windows.HeroInfoWindowPanel;
import windows.MainWindowPanel;
import windows.StartGame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-14 15:57
 * @description 游戏数据容器，包含地图信息，英雄信息，画板信息等相关信息
 */
public class GameContent {

    /**
     * 游戏窗口
     */
    private StartGame startGame;
    
    /**
     * 主画板信息
     */
    private MainWindowPanel mainWindowPanel;

    /**
     * 英雄信息画板
     */
    private HeroInfoWindowPanel heroInfoWindowPanel;

    /**
     * 战斗中面板
     */
    private CombatWindowPanel combatWindowPanel;

    /**
     * 地图资源map储存
     */
    private Map<String, GameMap> gameMaps;

    /**
     * 地图资源list按顺序存储
     */
    private List<GameMap> gameMapList;

    /**
     * 上一张地图
     */
    private GameMap previousGameMap;

    /**
     * 当前地图
     */
    private GameMap currentGameMap;

    /**
     * 下一张地图
     */
    private GameMap nextGameMap;

    /**
     * 英雄信息
     */
    private Hero hero;

    /**
     * 容器
     */
    private static final GameContent GAMECONTENT = new GameContent();

    /**
     * 私有构造保证全局唯一
     */
    private GameContent(){
        //初始化地图信息
        initGameMap();

        // 获取初始化英雄
        this.hero = ConfigurationParser.getHero();
    }

    /**
     * 初始化容器地图资源信息
     */
    private void initGameMap(){
        // 获取地图资源
        this.gameMaps = ConfigurationParser.getGameMaps();
        // 地图资源排序
        this.gameMapList = new ArrayList<>(this.gameMaps.values());
        this.gameMapList.sort(Comparator.comparingInt(GameMap::getFloor));
        // 当前地图为初始化地图
        this.currentGameMap = this.gameMapList.get(0);
        this.nextGameMap = this.gameMapList.get(currentGameMap.getFloor()+1);

    }

    /**
     * 获取容器
     * @return GameContent
     */
    public static GameContent gameContent(){
        return GAMECONTENT;
    }

    public HeroInfoWindowPanel getHeroInfoWindowPanel() {
        return heroInfoWindowPanel;
    }

    public void setHeroInfoWindowPanel(HeroInfoWindowPanel heroInfoWindowPanel) {
        this.heroInfoWindowPanel = heroInfoWindowPanel;
    }

    public StartGame getStartGame() {
        return startGame;
    }

    public void setStartGame(StartGame startGame) {
        this.startGame = startGame;
    }

    public List<GameMap> getGameMapList() {
        return gameMapList;
    }

    public void setGameMapList(List<GameMap> gameMapList) {
        this.gameMapList = gameMapList;
    }

    public GameMap getPreviousGameMap() {
        return previousGameMap;
    }

    public void setPreviousGameMap(GameMap previousGameMap) {
        this.previousGameMap = previousGameMap;
    }

    public GameMap getNextGameMap() {
        return nextGameMap;
    }

    public void setNextGameMap(GameMap nextGameMap) {
        this.nextGameMap = nextGameMap;
    }

    public Hero getHero() {
        return hero;
    }

    public MainWindowPanel getMainWindowJPanel() {
        return mainWindowPanel;
    }

    public void setMainWindowJPanel(MainWindowPanel mainWindowPanel) {
        this.mainWindowPanel = mainWindowPanel;
        this.getStartGame().setTitle("魔塔（第" + this.currentGameMap.getFloor() + "层）");
    }

    public Map<String, GameMap> getGameMaps() {
        return gameMaps;
    }

    public GameMap getCurrentGameMap() {
        return currentGameMap;
    }

    public void setCurrentGameMap(GameMap currentGameMap) {
        this.currentGameMap = currentGameMap;
    }

    public CombatWindowPanel getCombatWindowPanel() {
        return combatWindowPanel;
    }

    public void setCombatWindowPanel(CombatWindowPanel combatWindowPanel) {
        this.combatWindowPanel = combatWindowPanel;
    }
}
