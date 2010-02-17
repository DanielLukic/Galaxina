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

    void startGame() throws Exception;
    }
