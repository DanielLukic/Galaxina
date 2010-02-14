package net.intensicode.galaxina.game.extras;

import net.intensicode.galaxina.game.objects.GameModel;

public final class ScoreBonus extends ExtraType
    {
    public ScoreBonus( final int aID )
        {
        super( aID, "SCORE BONUS" );
        }

    // From ExtraType

    public final boolean apply( final GameModel aModel )
        {
        final int scoreBonus = aModel.level.numberStartingAt1 * SCORE_BONUS_MULTIPLIER;
        aModel.player.score += scoreBonus;
        return true;
        }

    private static final int SCORE_BONUS_MULTIPLIER = 250;
    }
