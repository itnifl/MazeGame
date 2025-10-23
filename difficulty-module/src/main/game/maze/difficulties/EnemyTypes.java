/**
 */
package main.game.maze.difficulties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Enemy Types</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see main.game.maze.difficulties.DifficultiesPackage#getEnemyTypes()
 * @model
 * @generated
 */
public enum EnemyTypes implements Enumerator {
	/**
	 * The '<em><b>ZOMBIE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ZOMBIE_VALUE
	 * @generated
	 * @ordered
	 */
	ZOMBIE(0, "ZOMBIE", "ZOMBIE"),

	/**
	 * The '<em><b>GHOST</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GHOST_VALUE
	 * @generated
	 * @ordered
	 */
	GHOST(1, "GHOST", "GHOST");

	/**
	 * The '<em><b>ZOMBIE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ZOMBIE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ZOMBIE_VALUE = 0;

	/**
	 * The '<em><b>GHOST</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GHOST
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GHOST_VALUE = 1;

	/**
	 * An array of all the '<em><b>Enemy Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EnemyTypes[] VALUES_ARRAY =
		new EnemyTypes[] {
			ZOMBIE,
			GHOST,
		};

	/**
	 * A public read-only list of all the '<em><b>Enemy Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EnemyTypes> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Enemy Types</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EnemyTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EnemyTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enemy Types</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EnemyTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EnemyTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enemy Types</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static EnemyTypes get(int value) {
		switch (value) {
			case ZOMBIE_VALUE: return ZOMBIE;
			case GHOST_VALUE: return GHOST;
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
	private EnemyTypes(int value, String name, String literal) {
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
	
} //EnemyTypes
