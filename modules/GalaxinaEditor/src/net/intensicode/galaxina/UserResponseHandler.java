package net.intensicode.galaxina;

import javax.swing.*;

public final class UserResponseHandler
    {
    public UserResponseHandler( final EditorUiAPI aUiAPI, final boolean aResponseIsYesFlag )
        {
        myUiAPI = aUiAPI;
        myResponseIsYes = aResponseIsYesFlag;
        }

    public final UserResponseHandler andRun( final String aEditorActionID )
        {
        return andRun( myUiAPI.action( aEditorActionID ) );
        }

    public final UserResponseHandler andRun( final Action aRunnableAction )
        {
        if ( myResponseIsYes && aRunnableAction != null ) myUiAPI.run( aRunnableAction );
        return this;
        }

    public final UserResponseHandler orRun( final String aEditorActionID )
        {
        return orRun( myUiAPI.action( aEditorActionID ) );
        }

    public final UserResponseHandler orRun( final Action aRunnableAction )
        {
        if ( !myResponseIsYes && aRunnableAction != null ) myUiAPI.run( aRunnableAction );
        return this;
        }


    private final EditorUiAPI myUiAPI;

    private final boolean myResponseIsYes;
    }
