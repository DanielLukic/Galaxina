package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.GameModel;
import net.intensicode.util.Random;

public final class RandomExtra extends ExtraType
    {
    public RandomExtra( final int aID )
        {
        super( aID, "RANDOM EXTRA" );
        }

    // From ExtraType

    public int idForDrawer()
        {
        createNewNextRandomId();
        return myNextRandomId;
        }

    public final boolean apply( final GameModel aModel )
        {
        final int randomID = myNextRandomId;
        createNewNextRandomId();

        final ExtraType randomType = aModel.extraTypes.all[ randomID ];
        name = randomType.name;
        return randomType.apply( aModel );
        }

    // Implementation

    private void createNewNextRandomId()
        {
        do
            {
            myNextRandomId = Random.INSTANCE.nextInt( ExtraTypes.NUMBER_OF_EXTRA_TYPES );
            if ( id == ExtraTypes.RANDOM_WEAPON && !ExtraTypes.isWeaponId( myNextRandomId ) ) continue;
            }
        while ( myNextRandomId == ExtraTypes.NO_EXTRA );
        }

    private int myNextRandomId;
    }
