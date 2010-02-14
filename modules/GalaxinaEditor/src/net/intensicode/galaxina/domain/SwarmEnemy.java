package net.intensicode.galaxina.domain;

import net.intensicode.galaxina.game.data.EnemyConfiguration;

public interface SwarmEnemy extends GroupEntryEx
    {
    String ENEMY = "ENEMY";

    String SYNC_MODE = "SYNC_MODE";

    String ZERO_DELAY = "ZERO_DELAY";

    SyncMode INDEPENDANT_MODE = new SyncMode( EnemyConfiguration.SYNC_INDEPENDANT, "Independant" );

    SyncMode SYNC_BY_PATH = new SyncMode( EnemyConfiguration.SYNC_BY_PATH, "Sync By Path" );

    SyncMode FOLLOW_LEFT_SIDE = new SyncMode( EnemyConfiguration.SYNC_FOLLOW_LEFT_SIDE, "Follow Left Side" );

    SyncMode FOLLOW_RIGHT_SIDE = new SyncMode( EnemyConfiguration.SYNC_FOLLOW_RIGHT_SIDE, "Follow Right Side" );

    SyncMode SYNC_SPEED = new SyncMode( EnemyConfiguration.SYNC_SPEED, "Sync Speed" );

    Enemy getEnemy();

    void setEnemey( Enemy aEnemy );

    SyncMode getSyncMode();

    void setSyncMode( SyncMode aSyncMode );

    Boolean getZeroDelay();

    void setZeroDelay( Boolean aZeroDelay );

    SwarmEnemy clone();
    }
