package net.intensicode.galaxina.game.objects;

import net.intensicode.util.*;

public abstract class WorldObjectWithSize extends WorldObject
    {
    public final Size sizeInWorldFixed = new Size();

    public final Rectangle boundingBox = new Rectangle();

    public final void updateBoundingBox()
        {
        final int outerWidth = sizeInWorldFixed.width * 90 / 100;
        final int outerHeight = sizeInWorldFixed.height * 90 / 100;
        boundingBox.setCenterAndSize( worldPosFixed, outerWidth, outerHeight );
        }
    }
