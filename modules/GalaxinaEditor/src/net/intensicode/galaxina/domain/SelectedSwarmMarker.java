package net.intensicode.galaxina.domain;

public final class SelectedSwarmMarker extends SelectedMarker
    {
    public final Swarm swarm;



    public SelectedSwarmMarker( final Swarm aSwarm, final PositionEx aMarker )
        {
        super( aMarker );
        swarm = aSwarm;
        }

    public final boolean isValid()
        {
        return swarm != null && super.isValid();
        }

    // From Object

    public final String toString()
        {
        return String.format( "%s/%s", swarm, marker );
        }
    }
