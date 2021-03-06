package net.intensicode.galaxina.util;

import net.intensicode.util.*;

public final class UtilitiesEx
    {
    public static int getDirectionID( final float aDirectionInDegrees, final int aDirectionSteps )
        {
        final float directionStep = 360 / aDirectionSteps;
        return (int) ( ( aDirectionInDegrees + directionStep / 2 ) / directionStep );
        }

    public static float directionToDegrees( final PositionF aDirection )
        {
        final float xNormalized = aDirection.x / MathExtended.length( aDirection.x, aDirection.y );
        final float angleInDegrees = (float) MathExtended.toDegrees( MathExtended.acos( xNormalized ) );

        if ( aDirection.x < 0 && aDirection.y <= 0 )
            {
            return 360 + 90 - angleInDegrees;
            }
        if ( aDirection.x < 0 && aDirection.y >= 0 )
            {
            return 90 + angleInDegrees;
            }
        if ( aDirection.x >= 0 && aDirection.y <= 0 )
            {
            return 90 - angleInDegrees;
            }
        if ( aDirection.x >= 0 && aDirection.y >= 0 )
            {
            return 90 + angleInDegrees;
            }
        else
            {
            throw new IllegalArgumentException();
            }
        }
    }
