package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;

public abstract class SimpleObject
    {
    public boolean active;

    public final Position worldPosFixed = new Position();

    public final Size sizeInWorldFixed = new Size();

    public final Rectangle outerBBox = new Rectangle();

    public final Rectangle innerBBox = new Rectangle();

    public int animTicks;

    public int tickCount;


    public final void tickAnimation()
        {
        if ( tickCount < animTicks ) tickCount++;
        else tickCount = 0;
        }

    public final void updateBBoxes()
        {
        final int outerWidth = sizeInWorldFixed.width * 90 / 100;
        final int outerHeight = sizeInWorldFixed.height * 90 / 100;
        outerBBox.setCenterAndSize( worldPosFixed, outerWidth, outerHeight );

        final int innerWidth = sizeInWorldFixed.width * 50 / 100;
        final int innerHeight = sizeInWorldFixed.height * 50 / 100;
        innerBBox.setCenterAndSize( worldPosFixed, innerWidth, innerHeight );
        }
    }
