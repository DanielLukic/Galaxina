package net.intensicode.galaxina.domain;

public final class SelectedPathMarker extends SelectedMarker
    {
    public final Path path;



    public SelectedPathMarker( final Path aPath, final PositionEx aMarker )
        {
        super( aMarker );
        path = aPath;
        }

    public final boolean isValid()
        {
        return path != null && super.isValid();
        }

    // From Object

    public final String toString()
        {
        return String.format( "%s/%s", path, marker );
        }
    }
