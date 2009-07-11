package net.intensicode.galaxina;

import net.intensicode.galaxina.domain.Project;

import java.io.File;

public final class EditorCore implements EditorCoreAPI
    {
    public EditorCore() throws Exception
        {
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



    private final Project myProject;

    private final EditorUiAPI myUiApi;

    private final EditorGlobalAPI myGlobalApi;

    private final EditorConfiguration myConfiguration;

    private final EditorState myState = new EditorState();
    }
