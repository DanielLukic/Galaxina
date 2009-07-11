package net.intensicode.galaxina.ui.actions;

import net.intensicode.galaxina.EditorCoreAPI;

public final class QuitApplication extends RunnableAction
    {
    public QuitApplication( final EditorCoreAPI aCoreAPI )
        {
        super( aCoreAPI );
        setEnabled( true );
        }

    // From Runnable

    public final void run()
        {
        myCoreAPI.global().exit();
        }
    }
