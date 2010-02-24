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

    ScreensBuilder screens();

    void startNewGame() throws Exception;
    }
