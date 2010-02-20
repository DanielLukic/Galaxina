package net.intensicode.galaxina.screens;

import net.intensicode.galaxina.MainContext;
import net.intensicode.galaxina.game.GameContext;
import net.intensicode.galaxina.game.objects.GameModel;

public abstract class GalaxinaGameScreen extends GalaxinaScreen
    {
    public GalaxinaGameScreen( final MainContext aMainContext )
        {
        super( aMainContext );
        myGameContext = aMainContext.gameContext();
        myGameModel = myGameContext.gameModel();
        }

    // Protected API

    protected final GameContext game()
        {
        return myGameContext;
        }

    protected final GameModel model()
        {
        return myGameModel;
        }

    private final GameModel myGameModel;

    private final GameContext myGameContext;
    }
