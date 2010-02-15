package net.intensicode.galaxina.game;

import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.screens.ScreenBase;


public interface GameContext
    {
    Camera camera();

    Hiscore hiscore();

    GameModel gameModel();

    VisualContext visualContext();

    ScreenBase sharedGameBackground();

    ScreenBase sharedGameDrawers();

    void showMainMenu() throws Exception;

    void showHelp() throws Exception;

    void showHiscore() throws Exception;

    void showOptions() throws Exception;

    void startGame() throws Exception;

    void pauseGame() throws Exception;

    void exit() throws Exception;
    }
