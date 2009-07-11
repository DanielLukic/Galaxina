package net.intensicode.galaxina.domain;

public class SelectedMarker extends Object
    {
    public final PositionEx marker;



    public SelectedMarker( final PositionEx aMarker )
        {
        marker = aMarker;
        }

    public boolean isValid()
        {
        return marker != null;
        }

    // From Object

    public String toString()
        {
        return marker.toString();
        }
    }
