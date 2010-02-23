package net.intensicode.galaxina.game.objects;

import net.intensicode.util.FixedMath;
import net.intensicode.util.Position;
import net.intensicode.galaxina.game.GameObject;

public final class Breather extends GameObject
    {
    public int breathPercent;

    public int breathOffsetFixedX;

    public int breathOffsetFixedY;

    public int breathPercentX;

    public int breathPercentY;



    public final Position getBreathPos( final Position aWorldPosition, final boolean aInSyncFlag )
        {
        if ( !aInSyncFlag ) return aWorldPosition;

        myTempPos.x = aWorldPosition.x * model.breather.breathPercentX / ONE_HUNDRED_PERCENT;
        myTempPos.y = aWorldPosition.y * model.breather.breathPercentY / ONE_HUNDRED_PERCENT;

        myTempPos.x += model.breather.breathOffsetFixedX;
        myTempPos.y += model.breather.breathOffsetFixedY;

        return myTempPos;
        }

    // From GameObject

    public final void onStartGame() throws Exception
        {
        }

    public final void onStartLevel() throws Exception
        {
        myState = STATE_WALKING;
        breathPercent = breathOffsetFixedX = breathOffsetFixedY = 0;
        breathPercentX = breathPercentY = ONE_HUNDRED_PERCENT;
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

        breathPercent = myBreathingTicks * ONE_HUNDRED_PERCENT / breathingTicks;

        if ( myReallyBreathFlag ) setExpansion();
        else setMovement();
        }

    // Implementation

    private void setExpansion()
        {
        if ( breathPercent >= 0 & breathPercent < 50 )
            {
            breathPercentX = ONE_HUNDRED_PERCENT + breathPercent / 2;
            breathOffsetFixedY = breathPercent / 5;
            }
        else if ( breathPercent >= 50 & breathPercent <= ONE_HUNDRED_PERCENT )
            {
            breathPercentX = ONE_HUNDRED_PERCENT + ( ONE_HUNDRED_PERCENT - breathPercent ) / 2;
            breathOffsetFixedY = 20 - breathPercent / 5;
            }
        breathPercentY = ONE_HUNDRED_PERCENT;

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
        else if ( breathPercent >= 75 & breathPercent <= ONE_HUNDRED_PERCENT )
            breathOffsetFixedX = FixedMath.toFixed( ( ONE_HUNDRED_PERCENT - breathPercent ) * -15 / 25 );

        breathOffsetFixedY = 0;
        breathPercentX = ONE_HUNDRED_PERCENT;
        breathPercentY = ONE_HUNDRED_PERCENT;
        }



    private int myState;

    private int myBreathingTicks;

    private boolean myReallyBreathFlag;

    private final Position myTempPos = new Position();

    private static final int STATE_WALKING = 0;

    private static final int STATE_CENTERING = 1;

    private static final int STATE_BREATHING = 2;

    private static final int ONE_HUNDRED_PERCENT = 100;
    }
