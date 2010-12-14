package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.extras.ExtraType;
import net.intensicode.galaxina.game.*;
import net.intensicode.util.*;
import net.intensicode.core.GameTiming;

public final class Extra extends WorldObject
    {
    public final PositionF speed = new PositionF();

    public static GameTiming timing;

    public ExtraType type;



    public Extra()
        {
        }

    public final void init( final PositionF aWorldPos, final float aSpeedX, final float aSpeedY )
        {
        active = true;
        tickCount = 0;
        animTicks = timing.ticksPerSecond / 2;

        speed.x = aSpeedX;
        speed.y = aSpeedY;
        worldPos.setTo( aWorldPos );
        }

    public final void init( final PositionF aWorldPos, final float aDropSpeed )
        {
        init( aWorldPos, 0, aDropSpeed );
        }

    public final void onControlTick( final GameModel aGameModel )
        {
        if ( !active ) return;

        tickAnimation();

        worldPos.translate( speed );
        myRect.setCenterAndSize( worldPos, aGameModel.extras.sizeInWorld );

        final World world = aGameModel.world;
        active = world.isInside( worldPos );

        checkIfCaught( aGameModel );
        }

    // Implementation

    private void checkIfCaught( final GameModel aGameModel )
        {
        final Player player = aGameModel.player;
        if ( player.outerBBox.intersectsWith( myRect ) )
            {
            final boolean applied = type.apply( aGameModel );
            if ( applied )
                {
                aGameModel.infoFlash.show( type.name );
                aGameModel.explosions.addSpecial( worldPos );
                }
            else
                {
                myMessage.setLength( 0 );
                myMessage.append( type.name );
                myMessage.append( " REJECTED" );
                aGameModel.infoFlash.show( myMessage.toString() );
                aGameModel.smokes.add( worldPos );
                }
            active = false;
            }
        }



    private final RectangleF myRect = new RectangleF();

    private final StringBuffer myMessage = new StringBuffer();
    }
