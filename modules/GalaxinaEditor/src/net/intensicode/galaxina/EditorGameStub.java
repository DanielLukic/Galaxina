package net.intensicode.galaxina;

import net.intensicode.IntensiGame;
import net.intensicode.core.AbstractScreen;
import net.intensicode.core.Engine;

public final class EditorGameStub extends IntensiGame
    {
    public EditorGameStub() throws Exception
        {
        initConfiguration();
        initEngine();
        }

    public final Engine engine()
        {
        return myEngine;
        }

    // From SystemContext

    public final AbstractScreen initMainController() throws Exception
        {
        throw new RuntimeException( "nyi" );
        }
    }
