package net.intensicode.galaxina;

import net.intensicode.core.GameSystem;
import net.intensicode.galaxina.game.VisualContext;

public interface MainContext
    {
    GameSystem gameSystem();

    VisualContext visualContext();

    void showMainMenu() throws Exception;
    }
