package net.intensicode.galaxina;

import net.intensicode.core.GameSystem;
import net.intensicode.galaxina.game.*;

public interface MainContext
    {
    GameSystem gameSystem();

    GameContext gameContext();

    VisualContext visualContext();

    MusicController musicController();

    void showMainMenu() throws Exception;

    void showHelp() throws Exception;

    void showHiscore() throws Exception;

    void showOptions() throws Exception;

    void startNewGame() throws Exception;
    }
