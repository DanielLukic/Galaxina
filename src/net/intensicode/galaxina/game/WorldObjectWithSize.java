package net.intensicode.galaxina.game;

import net.intensicode.util.*;

public abstract class WorldObjectWithSize extends WorldObject
    {
    public final SizeF sizeInWorld = new SizeF();

    public final RectangleF boundingBox = new RectangleF();

    public final void updateBoundingBox()
        {
        final float outerWidth = sizeInWorld.width * 90 / 100;
        final float outerHeight = sizeInWorld.height * 90 / 100;
        boundingBox.setCenterAndSize( worldPos, outerWidth, outerHeight );
        }
    }
