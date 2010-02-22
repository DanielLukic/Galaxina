package net.intensicode.galaxina;

import net.intensicode.core.GameSystem;
import net.intensicode.galaxina.game.*;

public interface MainContext
    {
    GameSystem gameSystem();

    GameContext gameContext();

    VisualContext visualContext();

    MusicController musicController();

    Controls controls();

    Options options();

    Hiscore hiscore();

    void saveHiscore() throws Exception;

    //#if ONLINE_HISCORE
    void updateHiscore() throws Exception;
    //#endif

    void showMainMenu() throws Exception;

    void showHelp() throws Exception;

    void showHiscore() throws Exception;

    void showOptions() throws Exception;

    void showControls() throws Exception;

    void showReset() throws Exception;

    void startNewGame() throws Exception;
    }
