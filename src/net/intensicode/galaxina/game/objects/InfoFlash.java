package net.intensicode.galaxina.game.objects;

public final class InfoFlash extends GameObject
    {
    public boolean visible;

    public String message;



    public final void reset()
        {
        visible = false;
        message = null;
        myVisibleTicks = 0;
        }

    public final void show( final String aMessage )
        {
        message = aMessage;
        myVisibleTicks = timing.ticksPerSecond;
        }

    // From GameObject

    public final void onStartGame() throws Exception
        {
        }

    public final void onStartLevel() throws Exception
        {
        reset();
        }

    public final void onControlTick()
        {
        if ( myVisibleTicks > 0 )
            {
            final int interval = timing.ticksPerSecond / 4;
            final int flashInterval = interval * 2 / 3;
            visible = ( myVisibleTicks % interval ) < flashInterval;
            myVisibleTicks--;
            }
        else
            {
            reset();
            }
        }



    private int myVisibleTicks;
    }
