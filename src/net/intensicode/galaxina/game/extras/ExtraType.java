package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.objects.GameModel;

/**
 * TODO: Describe this!
 */
public abstract class ExtraType
    {
    public String name;

    public int id;



    public ExtraType( final int aID, final String aName )
        {
        id = aID;
        name = aName;
        }

    public abstract boolean apply( GameModel aGameModel );
    }
