package net.intensicode.game.weapons;

import junit.framework.TestCase;
import net.intensicode.game.objects.GameModel;
import net.intensicode.game.objects.GameObject;

public final class SimpleGunTest extends TestCase
    {
    protected final void setUp() throws Exception
        {
        GameObject.model = new GameModel( null );
        }

    protected final void tearDown() throws Exception
        {
        GameObject.model = null;
        }

    public final void testGetNumberOfSlots()
        {
        GameObject.model.player.weaponUpgrades = 0;
        assertEquals( 1, SimpleGun.getNumberOfSlots() );
        GameObject.model.player.weaponUpgrades = 1;
        assertEquals( 2, SimpleGun.getNumberOfSlots() );
        GameObject.model.player.weaponUpgrades = 2;
        assertEquals( 3, SimpleGun.getNumberOfSlots() );
        }

    public final void testGetNumberOfBullets()
        {
        GameObject.model.player.bulletUpgrades = 0;
        assertEquals( 1, SimpleGun.getNumberOfBullets() );
        GameObject.model.player.bulletUpgrades = 1;
        assertEquals( 2, SimpleGun.getNumberOfBullets() );
        GameObject.model.player.bulletUpgrades = 2;
        assertEquals( 3, SimpleGun.getNumberOfBullets() );
        }

    public final void testGetReloadTicks()
        {
        GameObject.model.player.reloadUpgrades = 0;
        assertEquals( 20, SimpleGun.getReloadTicks() );
        GameObject.model.player.reloadUpgrades = 2;
        assertEquals( 12, SimpleGun.getReloadTicks() );
        }
    }
