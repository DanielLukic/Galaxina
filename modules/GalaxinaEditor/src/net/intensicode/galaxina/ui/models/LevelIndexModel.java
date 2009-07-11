package net.intensicode.galaxina.ui.models;

import net.intensicode.galaxina.domain.Level;
import net.intensicode.galaxina.domain.Levels;

import javax.swing.*;

public final class LevelIndexModel extends AbstractSpinnerModel
    {
    public LevelIndexModel( final Levels aLevels )
        {
        myLevels = aLevels;
        reset();
        }

    public final void reset()
        {
        myLevelIndex = 0;
        }

    public final void switchTo( final Level aLevel )
        {
        setLevel( myLevels.findLevelIndexOf( aLevel ) );
        }

    // From AbstractSpinnerModel

    public final Object getValue()
        {
        if ( myCurrentValue == null ) myCurrentValue = myLevels.at( myLevelIndex );
        return myCurrentValue;
        }

    public final void setValue( final Object aNewValue )
        {
        if ( aNewValue instanceof String ) return;
        if ( getValue() == aNewValue ) return;

        myCurrentValue = (Level) aNewValue;
        myLevelIndex = myCurrentValue != null ? myCurrentValue.levelIndex() : 0;
        fireStateChanged();
        }

    public final Object getNextValue()
        {
        return setLevel( myLevelIndex + 1 );
        }

    public final Object getPreviousValue()
        {
        if ( myLevelIndex == 0 ) return getValue();
        return setLevel( myLevelIndex - 1 );
        }

    // Implementation

    private final Object setLevel( final int aLevelIndex )
        {
        myLevelIndex = aLevelIndex;
        myCurrentValue = myLevels.at( aLevelIndex );
        fireStateChanged();
        return getValue();
        }



    private int myLevelIndex;

    private Level myCurrentValue;

    private final Levels myLevels;
    }
