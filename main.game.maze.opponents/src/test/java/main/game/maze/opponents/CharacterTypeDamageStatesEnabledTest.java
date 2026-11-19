package main.game.maze.opponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CharacterTypeDamageStatesEnabledTest {

    @BeforeAll
    static void initPackage() {
        OpponentsPackage.eINSTANCE.eClass();
    }

    @Test
    void defaultDamageStatesEnabled_isFalse_onAllSubclasses() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        Ghost ghost = OpponentsFactory.eINSTANCE.createGhost();
        PumpkinBomber bomber = OpponentsFactory.eINSTANCE.createPumpkinBomber();

        assertFalse(zombie.isDamageStatesEnabled(), "Zombie default damageStatesEnabled must be false");
        assertFalse(ghost.isDamageStatesEnabled(), "Ghost default damageStatesEnabled must be false");
        assertFalse(bomber.isDamageStatesEnabled(), "PumpkinBomber default damageStatesEnabled must be false");
    }

    @Test
    void setDamageStatesEnabled_updatesValue() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();

        zombie.setDamageStatesEnabled(true);
        assertTrue(zombie.isDamageStatesEnabled());

        zombie.setDamageStatesEnabled(false);
        assertFalse(zombie.isDamageStatesEnabled());
    }

    @Test
    void reflectiveEGetAndESet_worksCorrectly() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        var feature = OpponentsPackage.Literals.CHARACTER_TYPE__DAMAGE_STATES_ENABLED;

        assertNotNull(feature, "CHARACTER_TYPE__DAMAGE_STATES_ENABLED literal must not be null");
        assertEquals(Boolean.FALSE, zombie.eGet(feature));
        assertFalse(zombie.eIsSet(feature));

        zombie.eSet(feature, Boolean.TRUE);
        assertTrue(zombie.isDamageStatesEnabled());
        assertEquals(Boolean.TRUE, zombie.eGet(feature));
        assertTrue(zombie.eIsSet(feature));

        zombie.eUnset(feature);
        assertFalse(zombie.isDamageStatesEnabled());
        assertEquals(Boolean.FALSE, zombie.eGet(feature));
        assertFalse(zombie.eIsSet(feature));
    }

    @Test
    void subclassFeatureIdsMatchCharacterType() {
        assertEquals(OpponentsPackage.CHARACTER_TYPE__DAMAGE_STATES_ENABLED, OpponentsPackage.ZOMBIE__DAMAGE_STATES_ENABLED);
        assertEquals(OpponentsPackage.CHARACTER_TYPE__DAMAGE_STATES_ENABLED, OpponentsPackage.GHOST__DAMAGE_STATES_ENABLED);
        assertEquals(OpponentsPackage.CHARACTER_TYPE__DAMAGE_STATES_ENABLED, OpponentsPackage.RANGED_ENEMY__DAMAGE_STATES_ENABLED);
        assertEquals(OpponentsPackage.CHARACTER_TYPE__DAMAGE_STATES_ENABLED, OpponentsPackage.PUMPKIN_BOMBER__DAMAGE_STATES_ENABLED);
    }

    @Test
    void eNotificationFires_whenDamageStatesEnabledChanges() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        AtomicBoolean notified = new AtomicBoolean(false);

        zombie.eAdapters().add(new AdapterImpl() {
            @Override
            public void notifyChanged(Notification msg) {
                if (msg.getFeatureID(CharacterType.class) == OpponentsPackage.CHARACTER_TYPE__DAMAGE_STATES_ENABLED) {
                    assertEquals(false, msg.getOldBooleanValue());
                    assertEquals(true, msg.getNewBooleanValue());
                    notified.set(true);
                }
            }
        });

        zombie.setDamageStatesEnabled(true);
        assertTrue(notified.get(), "EMF notification should fire on damageStatesEnabled change");
    }

    @Test
    void toString_containsDamageStatesEnabled() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        String str = zombie.toString();
        assertTrue(str.contains("damageStatesEnabled: false"), "toString must contain damageStatesEnabled: false, got: " + str);

        zombie.setDamageStatesEnabled(true);
        assertTrue(zombie.toString().contains("damageStatesEnabled: true"));
    }
}
