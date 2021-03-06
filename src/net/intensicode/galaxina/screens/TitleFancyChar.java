package net.intensicode.galaxina.screens;

public final class TitleFancyChar
    {
    public char charCode;

    public int waitTicks;

    public int incomingTicks;

    public int stayTicks;

    public int outgoingTicks;

    public int startOffsetX;

    public int startOffsetY;

    public int endOffsetX;

    public int endOffsetY;

    public int drawPosX;

    public int drawPosY;

    public int basePosX;

    public int basePosY;

    public int alpha;


    public final void reset()
        {
        charCode = 0;
        waitTicks = incomingTicks = stayTicks = outgoingTicks = 0;
        startOffsetX = startOffsetY = endOffsetX = endOffsetY = 0;

        drawPosX = drawPosY = alpha = 0;

        myState = STATE_WAITING;
        myStateTicks = 0;
        }

    public final boolean isDead()
        {
        return myState == STATE_DEAD || charCode == 0;
        }

    public final boolean isVisible()
        {
        return myState != STATE_DEAD && myState != STATE_WAITING;
        }

    public final void tick()
        {
        switch ( myState )
            {
            case STATE_WAITING:
                if ( myStateTicks < waitTicks ) onWaitingTick();
                else nextState();
                break;
            case STATE_INCOMING:
                if ( myStateTicks < incomingTicks ) onIncomingTick();
                else nextState();
                break;
            case STATE_STAYING:
                if ( myStateTicks < stayTicks ) onStayingTick();
                else nextState();
                break;
            case STATE_OUTGOING:
                if ( myStateTicks < incomingTicks ) onOutgoingTick();
                else nextState();
                break;
            case STATE_DEAD:
                break;
            }
        }

    private void onWaitingTick()
        {
        if ( myStateTicks == 0 )
            {
            drawPosX = basePosX + startOffsetX;
            drawPosY = basePosY + startOffsetY;
            alpha = 0;
            }
        myStateTicks++;
        }

    private void nextState()
        {
        myState++;
        myStateTicks = 0;
        }

    private void onIncomingTick()
        {
        drawPosX = basePosX + startOffsetX * ( incomingTicks - myStateTicks ) / incomingTicks;
        drawPosY = basePosY + startOffsetY * ( incomingTicks - myStateTicks ) / incomingTicks;
        alpha = myStateTicks * 255 / incomingTicks;
        myStateTicks++;
        }

    private void onStayingTick()
        {
        drawPosX = basePosX;
        drawPosY = basePosY;
        myStateTicks++;
        }

    private void onOutgoingTick()
        {
        drawPosX = basePosX + endOffsetX * myStateTicks / outgoingTicks;
        drawPosY = basePosY + endOffsetY * myStateTicks / outgoingTicks;
        alpha = ( outgoingTicks - myStateTicks ) * 255 / outgoingTicks;
        myStateTicks++;
        }


    private int myState;

    private int myStateTicks;

    private static final int STATE_WAITING = 0;

    private static final int STATE_INCOMING = 1;

    private static final int STATE_STAYING = 2;

    private static final int STATE_OUTGOING = 3;

    private static final int STATE_DEAD = 4;
    }
