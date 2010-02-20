package net.intensicode.galaxina.screens;

import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.VisualContext;
import net.intensicode.screens.MultiScreen;

public abstract class GalaxinaScreen extends MultiScreen
    {
    public GalaxinaScreen( final MainContext aMainContext )
        {
        myMainContext = aMainContext;
        myVisualContext = aMainContext.visualContext();
        }

    // Protected API

    protected final MainContext context()
        {
        return myMainContext;
        }

    protected final VisualContext visuals()
        {
        return myVisualContext;
        }

    private final MainContext myMainContext;

    private final VisualContext myVisualContext;
    }
