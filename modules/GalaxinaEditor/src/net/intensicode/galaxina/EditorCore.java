package net.intensicode.galaxina;

import net.intensicode.galaxina.domain.Project;
import net.intensicode.core.*;

import java.io.File;
import java.awt.*;

public final class EditorCore implements EditorCoreAPI
    {
    public EditorCore( final Dimension aGameScreenSize ) throws Exception
        {
        myGameScreenSize = aGameScreenSize;

        myConfiguration = new EditorConfiguration( this );
        myGlobalApi = new EditorGlobal( this );
        myUiApi = new EditorUi( this );

        myProject = new Project( this );
        }

    // From EditorCoreAPI

    public final void open( final File aFile ) throws Exception
        {
        myProject.open( aFile );
        }

    public final GameEngine engine()
        {
        return getGameSystem().engine;
        }

    public final GameTiming timing()
        {
        return getGameSystem().timing;
        }

    public final int gameScreenWidth()
        {
        return myGameScreenSize.width;
        }

    public final int gameScreenHeight()
        {
        return myGameScreenSize.height;
        }

    public final void toggleGameEnginePause()
        {
        engine().paused = !engine().paused;
        }

    public final void doGameEngineSingleStep()
        {
        engine().paused = false;
        engine().singleStep = true;
        }

    public final void clearGameEnginePauseAndSingleStep()
        {
        engine().paused = false;
        engine().singleStep = false;
        }

    public final Project project()
        {
        return myProject;
        }

    public final EditorUiAPI ui()
        {
        return myUiApi;
        }

    public final EditorGlobalAPI global()
        {
        return myGlobalApi;
        }

    public final EditorConfiguration configuration()
        {
        return myConfiguration;
        }

    public final EditorState state()
        {
        return myState;
        }

    // Implementation

    private GameSystem getGameSystem()
        {
        return myProject.galaxina().getGameSystem();
        }



    private final Project myProject;

    private final EditorUiAPI myUiApi;

    private final Dimension myGameScreenSize;

    private final EditorGlobalAPI myGlobalApi;

    private final EditorConfiguration myConfiguration;

    private final EditorState myState = new EditorState();
    }
