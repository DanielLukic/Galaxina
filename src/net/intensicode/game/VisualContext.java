package net.intensicode.game;

import net.intensicode.core.AbstractScreen;
import net.intensicode.core.Skin;
import net.intensicode.util.BitmapFontGen;
import net.intensicode.screens.AutoHideSoftkeysScreen;

import java.io.IOException;



/**
 * TODO: Describe this!
 */
public interface VisualContext
{
    Skin skin();

    AbstractScreen sharedStars();

    AbstractScreen sharedBackground();

    AbstractScreen sharedGameBackground();

    AbstractScreen sharedGameDrawers();

    AutoHideSoftkeysScreen sharedSoftkeys();

    BitmapFontGen titleFont() throws IOException;

    BitmapFontGen menuFont() throws IOException;

    BitmapFontGen textFont() throws IOException;

    BitmapFontGen softkeysFont() throws IOException;
}
