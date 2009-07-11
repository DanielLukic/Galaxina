package net.intensicode.game.extras;

import net.intensicode.game.objects.GameModel;
import net.intensicode.util.Random;

public final class RandomExtra extends ExtraType
    {
    public RandomExtra( final int aID )
        {
        super( aID, "RANDOM EXTRA" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final int randomID = Random.INSTANCE.nextInt( aModel.extraTypes.all.length );
        final ExtraType randomType = aModel.extraTypes.all[ randomID ];
        name = randomType.name;
        return randomType.apply( aModel );
        }
    }
