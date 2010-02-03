package net.intensicode.game.objects;

import net.intensicode.util.FixedMath;
import net.intensicode.util.Position;

public final class Breather extends GameObject
    {
    public int breathPercent;

    public int breathOffsetFixedX;

    public int breathOffsetFixedY;

    public int breathPercentX;

    public int breathPercentY;



    public final Position getBreathPos( final Position aRelativePosition, final boolean aInSyncFlag )
        {
        if ( !aInSyncFlag )
            {
            return model.world.relativeToWorld( aRelativePosition );
            }

        myTempPos.setTo( aRelativePosition );
        myTempPos.x = myTempPos.x * model.breather.breathPercentX / 100;
        myTempPos.y = myTempPos.y * model.breather.breathPercentY / 100;

        myTempPos.x += FixedMath.toInt( model.breather.breathOffsetFixedX );
        myTempPos.y += FixedMath.toInt( model.breather.breathOffsetFixedY );

        return model.world.relativeToWorld( myTempPos );
        }

    // From GameObject

    public final void onStartGame() throws Exception
        {
        }

    public final void onStartLevel() throws Exception
        {
        myState = STATE_WALKING;
        breathPercent = breathOffsetFixedX = breathOffsetFixedY = 0;
        breathPercentX = breathPercentY = 100;
        myBreathingTicks = 0;
        myReallyBreathFlag = false;
        }

    public final void onControlTick()
        {
        final int breathingTicks = timing.ticksPerSecond * 4;

        switch ( myState )
            {
            case STATE_WALKING:
                if ( model.enemySpawner.done() ) myState = STATE_CENTERING;
                break;
            case STATE_CENTERING:
                if ( myBreathingTicks == 0 || myBreathingTicks == breathingTicks / 2 )
                    {
                    myState = STATE_BREATHING;
                    myBreathingTicks = 0;
                    }
                break;
            case STATE_BREATHING:
                myReallyBreathFlag = true;
                break;
            }

        if ( myBreathingTicks < breathingTicks ) myBreathingTicks++;
        else myBreathingTicks = 0;

        breathPercent = myBreathingTicks * 100 / breathingTicks;

        if ( myReallyBreathFlag ) setExpansion();
        else setMovement();
        }

    // Implementation

    private void setExpansion()
        {
        if ( breathPercent >= 0 & breathPercent < 50 )
            {
            breathPercentX = 100 + breathPercent / 2;
            breathOffsetFixedY = breathPercent / 5;
            }
        else if ( breathPercent >= 50 & breathPercent <= 100 )
            {
            breathPercentX = 100 + ( 100 - breathPercent ) / 2;
            breathOffsetFixedY = 20 - breathPercent / 5;
            }
        breathPercentY = 100;

        breathOffsetFixedX = 0;
        breathOffsetFixedY = FixedMath.toFixed( breathOffsetFixedY );
        }

    private void setMovement()
        {
        if ( breathPercent >= 0 & breathPercent < 25 )
            breathOffsetFixedX = FixedMath.toFixed( breathPercent * 15 / 25 );
        else if ( breathPercent >= 25 & breathPercent < 50 )
            breathOffsetFixedX = FixedMath.toFixed( ( 50 - breathPercent ) * 15 / 25 );
        else if ( breathPercent >= 50 & breathPercent < 75 )
            breathOffsetFixedX = FixedMath.toFixed( ( breathPercent - 50 ) * -15 / 25 );
        else if ( breathPercent >= 75 & breathPercent <= 100 )
            breathOffsetFixedX = FixedMath.toFixed( ( 100 - breathPercent ) * -15 / 25 );

        breathOffsetFixedY = 0;
        breathPercentX = 100;
        breathPercentY = 100;
        }



    private int myState;

    private int myBreathingTicks;

    private boolean myReallyBreathFlag;

    private final Position myTempPos = new Position();

    private static final int STATE_WALKING = 0;

    private static final int STATE_CENTERING = 1;

    private static final int STATE_BREATHING = 2;
    }
