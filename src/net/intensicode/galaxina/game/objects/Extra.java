package net.intensicode.galaxina.game.objects;

import net.intensicode.galaxina.game.extras.ExtraType;
import net.intensicode.util.Position;
import net.intensicode.util.Rectangle;
import net.intensicode.core.GameTiming;

public final class Extra extends WorldObject
    {
    public final Position speedFixed = new Position();

    public static GameTiming timing;

    public ExtraType type;



    public Extra()
        {
        }

    public final void init( final Position aWorldPosFixed, final int aSpeedX, final int aSpeedY )
        {
        active = true;
        tickCount = 0;
        animTicks = timing.ticksPerSecond / 2;

        speedFixed.x = aSpeedX;
        speedFixed.y = aSpeedY;
        worldPosFixed.setTo( aWorldPosFixed );
        }

    public final void init( final Position aWorldPosFixed, final int aDropSpeedFixed )
        {
        init( aWorldPosFixed, 0, aDropSpeedFixed );
        }

    public final void onControlTick( final GameModel aGameModel )
        {
        if ( !active ) return;

        tickAnimation();

        worldPosFixed.translate( speedFixed );
        myRect.setCenterAndSize( worldPosFixed, aGameModel.extras.sizeInWorldFixed );

        final World world = aGameModel.world;
        active = world.isInside( worldPosFixed );

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
                aGameModel.explosions.addSpecial( worldPosFixed );
                }
            else
                {
                myMessage.setLength( 0 );
                myMessage.append( type.name );
                myMessage.append( " REJECTED" );
                aGameModel.infoFlash.show( myMessage.toString() );
                aGameModel.smokes.add( worldPosFixed );
                }
            active = false;
            }
        }



    private final Rectangle myRect = new Rectangle();

    private final StringBuffer myMessage = new StringBuffer();
    }
