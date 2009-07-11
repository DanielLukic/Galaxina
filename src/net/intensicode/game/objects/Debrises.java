package net.intensicode.game.objects;

public final class Debrises extends GameObject
    {
    public final Debris[] debrises = new Debris[MAX_DEBRISES];



    public final Debris getInstance()
        {
        for ( int idx = 0; idx < debrises.length; idx++ )
            {
            final Debris debris = debrises[ idx ];
            if ( debris == null ) return debrises[ idx ] = new Debris();
            if ( !debris.active ) return debris;
            }

        //#if DEBUG
        throw new RuntimeException();
        //#else
        //# return new Debris();
        //#endif
        }

    // From GameObject

    public final void onStartGame() throws Exception
        {
        }

    public final void onStartLevel() throws Exception
        {
        for ( int idx = 0; idx < debrises.length; idx++ )
            {
            final Debris debris = debrises[ idx ];
            if ( debris != null ) debris.active = false;
            }
        }

    public final void onControlTick()
        {
        for ( int idx = 0; idx < debrises.length; idx++ )
            {
            final Debris debris = debrises[ idx ];
            if ( debris == null ) continue;
            if ( !debris.active ) continue;
            debris.onControlTick();
            }
        }



    private static final int MAX_DEBRISES = 64;
    }
