package net.intensicode.galaxina.ui.logic;

import net.intensicode.galaxina.domain.PositionEx;

import java.awt.*;

public interface CoordinateTransformer
    {
    double scaled( double aMarkerSize );

    PositionEx toGame( Point aPoint );
    }
