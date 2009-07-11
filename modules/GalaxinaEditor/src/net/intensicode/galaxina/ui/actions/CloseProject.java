package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;
import net.intensicode.galaxina.domain.Project;
import net.intensicode.galaxina.domain.ProjectListener;

public final class CloseProject extends RunnableAction implements ProjectListener
    {
    public CloseProject( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );

        final Project project = myCoreAPI.project();
        setEnabled( project.isOpen() );

        project.add( this );
        }

    // From Runnable

    public final void run()
        {
        myCoreAPI.project().close();
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
