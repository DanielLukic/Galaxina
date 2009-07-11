package net.intensicode.game;

import net.intensicode.game.objects.GameModel;


/**
 * TODO: Describe this!
 */
public interface GameContext
    {
    Camera camera();

    Hiscore hiscore();

    GameModel gameModel();

    VisualContext visualContext();

    void showMainMenu() throws Exception;

    void showHelp() throws Exception;

    void showHiscore() throws Exception;

    void showOptions() throws Exception;

    void startGame() throws Exception;

    void pauseGame() throws Exception;

    void exit() throws Exception;
    }
