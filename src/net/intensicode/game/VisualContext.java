package net.intensicode.game;

import net.intensicode.core.SkinManager;
import net.intensicode.graphics.BitmapFontGenerator;
import net.intensicode.screens.*;

import java.io.IOException;

public interface VisualContext
    {
    SkinManager skinManager();

    ScreenBase sharedStars();

    ScreenBase sharedBackground();

    ScreenBase sharedGameBackground();

    ScreenBase sharedGameDrawers();

    AutohideSoftkeysScreen sharedSoftkeys();

    BitmapFontGenerator titleFont() throws IOException;

    BitmapFontGenerator menuFont() throws IOException;

    BitmapFontGenerator textFont() throws IOException;

    BitmapFontGenerator softkeysFont() throws IOException;
    }
