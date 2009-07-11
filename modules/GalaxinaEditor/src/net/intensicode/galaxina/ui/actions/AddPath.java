package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.domain.NormalPath;
import net.intensicode.galaxina.domain.Project;
import net.intensicode.galaxina.domain.ProjectListener;

public final class AddPath extends RunnableAction implements ProjectListener
    {
    public AddPath( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        myCoreAPI.project().add( this );
        }

    // From Runnable

    public final void run()
        {
        myCoreAPI.project().pathes().addEntry( new NormalPath( myCoreAPI ) );
        }

    // From ProjectListener

    public final void onProjectOpened( final Project aProject )
        {
        setEnabled( true );
        }

    public final void onProjectClosed()
        {
        setEnabled( false );
        }
    }
