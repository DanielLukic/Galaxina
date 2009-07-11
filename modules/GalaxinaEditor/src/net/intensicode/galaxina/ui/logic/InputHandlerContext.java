package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.EditorCoreAPI;

public interface InputHandlerContext
    {
    void updateUI();

    EditorCoreAPI getCoreApi();

    CoordinateTransformer getTransformer();
    }
