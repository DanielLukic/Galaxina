package net.intensicode.galaxina.ui;

import javax.swing.*;
import java.util.ArrayList;

public final class RunnableChain implements Runnable
    {
    public final void append( final Runnable aRunnable )
        {
        myRunnables.add( aRunnable );
        }

    public final void start()
        {
        if ( myRunnables.size() == 0 ) return;
        SwingUtilities.invokeLater( this );
        }

    // From Runnable

    public final void run()
        {
        if ( myRunnables.size() == 0 ) return;
        myRunnables.remove( 0 ).run();
        start();
        }

    private final ArrayList<Runnable> myRunnables = new ArrayList<Runnable>();
    }
