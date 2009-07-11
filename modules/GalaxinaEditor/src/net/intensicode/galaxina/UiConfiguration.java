package net.intensicode.galaxina;

import java.awt.*;

public class UiConfiguration
    {
    public int gridSizeX = 12;

    public int gridSizeY = 8;

    public Color gridColor = Color.GRAY;

    public Stroke gridStroke = new BasicStroke( 1, 0, 0, 1, new float[]{ 4, 2 }, 0 );

    public Color guideColor = Color.GRAY;

    public Stroke guideStroke = new BasicStroke( 1.0f );

    public Color borderColor = Color.BLACK;

    public Stroke borderStroke = new BasicStroke( 3.5f );

    public Stroke selectedSwarmStroke = new BasicStroke( 2.0f );

    public Stroke selectedPathStroke = new BasicStroke( 2.0f );

    public Color selectedPathColor = Color.YELLOW;

    public Color selectedSmoothPathColor = Color.ORANGE;

    public Color selectedMarkerColor = Color.RED;

    public Stroke swarmStroke = new BasicStroke( 1f );

    public Color swarmColor = Color.GREEN;

    public Stroke pathStroke = new BasicStroke( 0.5f );

    public Color pathColor = Color.GREEN;

    public Color smoothPathColor = Color.DARK_GRAY;

    public Color markerColor = Color.LIGHT_GRAY;

    public Stroke activeMarkerStroke = new BasicStroke( 1.0f );

    public Color activeMarkerColor = Color.GREEN;

    public int markerSize = 5;

    public int selectedMarkerSize = 8;

    public int swarmMarkerSize = 10;
    }
