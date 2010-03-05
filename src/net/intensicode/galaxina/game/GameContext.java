package net.intensicode.galaxina.game;

import net.intensicode.galaxina.VisualContext;
import net.intensicode.galaxina.screens.StarField;
import net.intensicode.screens.*;

public interface GameContext
    {
    Camera camera();

    Hiscore hiscore();

    GameModel gameModel();

    StarField starField();

    VisualContext visualContext();

    ScreenBase sharedGameBackground();

    ScreenBase sharedGameDrawers();

    VerticalSoftkeysScreen sharedSoftkeys();

    void startGame() throws Exception;
    }
