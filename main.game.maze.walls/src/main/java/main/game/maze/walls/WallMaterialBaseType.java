/**
 */
package main.game.maze.walls;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Wall Material Base Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see main.game.maze.walls.WallsPackage#getWallMaterialBaseType()
 * @model instanceClass="main.game.maze.walls.WallMaterialBaseType"
 * @generated
 */
public enum WallMaterialBaseType implements Enumerator {
	/**
	 * The '<em><b>GLASS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GLASS_VALUE
	 * @generated
	 * @ordered
	 */
	GLASS(0, "GLASS", "GLASS"),

	/**
	 * The '<em><b>DIRT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIRT_VALUE
	 * @generated
	 * @ordered
	 */
	DIRT(1, "DIRT", "DIRT"),

	/**
	 * The '<em><b>WOOD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WOOD_VALUE
	 * @generated
	 * @ordered
	 */
	WOOD(2, "WOOD", "WOOD"),

	/**
	 * The '<em><b>STONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STONE_VALUE
	 * @generated
	 * @ordered
	 */
	STONE(3, "STONE", "STONE"),

	/**
	 * The '<em><b>STEEL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STEEL_VALUE
	 * @generated
	 * @ordered
	 */
	STEEL(4, "STEEL", "STEEL");

	/**
	 * The '<em><b>GLASS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GLASS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GLASS_VALUE = 0;

	/**
	 * The '<em><b>DIRT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIRT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DIRT_VALUE = 1;

	/**
	 * The '<em><b>WOOD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WOOD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WOOD_VALUE = 2;

	/**
	 * The '<em><b>STONE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STONE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int STONE_VALUE = 3;

	/**
	 * The '<em><b>STEEL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STEEL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int STEEL_VALUE = 4;

	/**
	 * An array of all the '<em><b>Wall Material Base Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final WallMaterialBaseType[] VALUES_ARRAY = new WallMaterialBaseType[] { GLASS, DIRT, WOOD, STONE,
			STEEL, };

	/**
	 * A public read-only list of all the '<em><b>Wall Material Base Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<WallMaterialBaseType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Wall Material Base Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static WallMaterialBaseType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			WallMaterialBaseType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Wall Material Base Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static WallMaterialBaseType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			WallMaterialBaseType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Wall Material Base Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static WallMaterialBaseType get(int value) {
		switch (value) {
		case GLASS_VALUE:
			return GLASS;
		case DIRT_VALUE:
			return DIRT;
		case WOOD_VALUE:
			return WOOD;
		case STONE_VALUE:
			return STONE;
		case STEEL_VALUE:
			return STEEL;
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
	private WallMaterialBaseType(int value, String name, String literal) {
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

} //WallMaterialBaseType
