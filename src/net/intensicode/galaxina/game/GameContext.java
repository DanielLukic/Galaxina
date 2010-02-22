package net.intensicode.galaxina.game;

import net.intensicode.galaxina.game.objects.GameModel;
import net.intensicode.galaxina.VisualContext;
import net.intensicode.screens.*;

public interface GameContext
    {
    Camera camera();

    Hiscore hiscore();

    GameModel gameModel();

    VisualContext visualContext();

    ScreenBase sharedGameBackground();

    ScreenBase sharedGameDrawers();

    VerticalSoftkeysScreen sharedSoftkeys();

    void startGame() throws Exception;
    }
