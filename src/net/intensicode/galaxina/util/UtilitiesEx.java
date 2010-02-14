package net.intensicode.galaxina.util;

import net.intensicode.util.*;

public final class UtilitiesEx
    {
    public static int getDirectionID( final int aDirectionInDegrees, final int aDirectionSteps )
        {
        final int directionStep = 360 / aDirectionSteps;
        return ( aDirectionInDegrees + directionStep / 2 ) / directionStep;
        }

    public static int directionToMathFP( final int aFixedX, final int aFixedY )
        {
        final int lengthFixed = FixedMath.length( aFixedX, aFixedY );
        if ( lengthFixed == 0 ) return 0;

        final int xFixed = FixedMath.div( aFixedX * 100, lengthFixed );
        final int yFixed = FixedMath.div( -aFixedY * 100, lengthFixed );

        final int xInt = FixedMath.toInt( xFixed );
        final int yInt = FixedMath.toInt( yFixed );

        final int xFrac = FixedMath.fraction( xFixed ) * FP_FACTOR;
        final int yFrac = FixedMath.fraction( yFixed ) * FP_FACTOR;

        final int xFP = MathFP.toFP( xInt );
        final int yFP = MathFP.toFP( yInt );

        final int factorFP = MathFP.toFP( FP_FACTOR );
        final int xFracFP = MathFP.div( MathFP.toFP( xFrac ), factorFP );
        final int yFracFP = MathFP.div( MathFP.toFP( yFrac ), factorFP );

        final int x = xFP + xFracFP;
        final int y = yFP + yFracFP;

        final int lengthFP = MathFP.sqrt( MathFP.mul( x, x ) + MathFP.mul( y, y ) );
        return MathFP.div( x, lengthFP );
        }

    public static int directionToDegrees( final Position aFixedDirection )
        {
        final int xFP = directionToMathFP( aFixedDirection.x, aFixedDirection.y );
        final int angleFP = MathFP.rad2deg( MathFP.acos( xFP ) );

        if ( aFixedDirection.x < 0 && aFixedDirection.y <= 0 )
            {
            return 360 + 90 - MathFP.toInt( angleFP + MathFP.HALF );
            }
        if ( aFixedDirection.x < 0 && aFixedDirection.y >= 0 )
            {
            return 90 + MathFP.toInt( angleFP + MathFP.HALF );
            }
        if ( aFixedDirection.x >= 0 && aFixedDirection.y <= 0 )
            {
            return 90 - MathFP.toInt( angleFP + MathFP.HALF );
            }
        if ( aFixedDirection.x >= 0 && aFixedDirection.y >= 0 )
            {
            return 90 + MathFP.toInt( angleFP + MathFP.HALF );
            }
        else
            {
            throw new RuntimeException( "nyi" );
            }
        }



    private static final int FP_FACTOR = 0x8000;
    }
