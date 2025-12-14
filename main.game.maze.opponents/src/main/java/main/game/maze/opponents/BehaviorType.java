/**
 */
package main.game.maze.opponents;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Behavior Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see main.game.maze.opponents.OpponentsPackage#getBehaviorType()
 * @model
 * @generated
 */
public enum BehaviorType implements Enumerator {
	/**
	 * The '<em><b>PASSIVE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PASSIVE_VALUE
	 * @generated
	 * @ordered
	 */
	PASSIVE(0, "PASSIVE", "PASSIVE"),

	/**
	 * The '<em><b>WANDER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WANDER_VALUE
	 * @generated
	 * @ordered
	 */
	WANDER(1, "WANDER", "WANDER"),

	/**
	 * The '<em><b>AGGRESSIVE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AGGRESSIVE_VALUE
	 * @generated
	 * @ordered
	 */
	AGGRESSIVE(2, "AGGRESSIVE", "AGGRESSIVE"), /**
	 * The '<em><b>PATROL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PATROL_VALUE
	 * @generated
	 * @ordered
	 */
	PATROL(3, "PATROL", "PATROL");

	/**
	 * The '<em><b>PASSIVE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PASSIVE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PASSIVE_VALUE = 0;

	/**
	 * The '<em><b>WANDER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WANDER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WANDER_VALUE = 1;

	/**
	 * The '<em><b>AGGRESSIVE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AGGRESSIVE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int AGGRESSIVE_VALUE = 2;

	/**
	 * The '<em><b>PATROL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PATROL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PATROL_VALUE = 3;

	/**
	 * An array of all the '<em><b>Behavior Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final BehaviorType[] VALUES_ARRAY =
		new BehaviorType[] {
			PASSIVE,
			WANDER,
			AGGRESSIVE,
			PATROL,
		};

	/**
	 * A public read-only list of all the '<em><b>Behavior Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<BehaviorType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Behavior Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BehaviorType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BehaviorType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Behavior Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BehaviorType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BehaviorType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Behavior Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BehaviorType get(int value) {
		switch (value) {
			case PASSIVE_VALUE: return PASSIVE;
			case WANDER_VALUE: return WANDER;
			case AGGRESSIVE_VALUE: return AGGRESSIVE;
			case PATROL_VALUE: return PATROL;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private BehaviorType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //BehaviorType
