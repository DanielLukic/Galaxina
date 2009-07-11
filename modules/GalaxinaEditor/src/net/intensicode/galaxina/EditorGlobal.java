package net.intensicode.galaxina;

public final class EditorGlobal implements EditorGlobalAPI
    {
    public EditorGlobal( final EditorCoreAPI aCoreAPI )
        {
        myCoreAPI = aCoreAPI;
        }

    // From EditorGlobalAPI

    public final void exit()
        {
        System.exit( 10 );
        }

    public final void pause()
        {
        }

    public final void resume()
        {
        }


    
    private final EditorCoreAPI myCoreAPI;
    }
