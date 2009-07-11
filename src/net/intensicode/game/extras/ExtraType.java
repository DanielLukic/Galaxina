package net.intensicode.game.extras;

import net.intensicode.game.objects.GameModel;

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
