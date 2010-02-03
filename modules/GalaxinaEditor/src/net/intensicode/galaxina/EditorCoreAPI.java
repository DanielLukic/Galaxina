package net.intensicode.galaxina;

import net.intensicode.core.*;
import net.intensicode.galaxina.domain.Project;

import java.io.File;

public interface EditorCoreAPI
    {
    void open( final File aFile ) throws Exception;

    // Shortcuts

    GameEngine engine();

    GameTiming timing();

    int gameScreenWidth();

    int gameScreenHeight();

    void toggleGameEnginePause();

    void doGameEngineSingleStep();

    void clearGameEnginePauseAndSingleStep();

    // Core Objects

    Project project();

    // Sub APIs

    EditorUiAPI ui();

    EditorGlobalAPI global();

    EditorConfiguration configuration();

    EditorState state();
    }
