/**
 */
package main.game.maze.walls.impl;

import main.game.maze.walls.WallMaterial;
import main.game.maze.walls.WallMaterialBaseType;
import main.game.maze.walls.WallsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Wall Material</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.walls.impl.WallMaterialImpl#getWallBaseType <em>Wall Base Type</em>}</li>
 *   <li>{@link main.game.maze.walls.impl.WallMaterialImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link main.game.maze.walls.impl.WallMaterialImpl#isBreakable <em>Breakable</em>}</li>
 *   <li>{@link main.game.maze.walls.impl.WallMaterialImpl#getHitPoints <em>Hit Points</em>}</li>
 *   <li>{@link main.game.maze.walls.impl.WallMaterialImpl#getId <em>Id</em>}</li>
 *   <li>{@link main.game.maze.walls.impl.WallMaterialImpl#getBaseImage <em>Base Image</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WallMaterialImpl extends MinimalEObjectImpl.Container implements WallMaterial {
	/**
	 * The default value of the '{@link #getWallBaseType() <em>Wall Base Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWallBaseType()
	 * @generated
	 * @ordered
	 */
	protected static final WallMaterialBaseType WALL_BASE_TYPE_EDEFAULT = WallMaterialBaseType.STEEL;

	/**
	 * The cached value of the '{@link #getWallBaseType() <em>Wall Base Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWallBaseType()
	 * @generated
	 * @ordered
	 */
	protected WallMaterialBaseType wallBaseType = WALL_BASE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isBreakable() <em>Breakable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBreakable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BREAKABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBreakable() <em>Breakable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBreakable()
	 * @generated
	 * @ordered
	 */
	protected boolean breakable = BREAKABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getHitPoints() <em>Hit Points</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHitPoints()
	 * @generated
	 * @ordered
	 */
	protected static final int HIT_POINTS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHitPoints() <em>Hit Points</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHitPoints()
	 * @generated
	 * @ordered
	 */
	protected int hitPoints = HIT_POINTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getBaseImage() <em>Base Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseImage()
	 * @generated
	 * @ordered
	 */
	protected static final String BASE_IMAGE_EDEFAULT = "/main/game/maze/baseWall.png";

	/**
	 * The cached value of the '{@link #getBaseImage() <em>Base Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseImage()
	 * @generated
	 * @ordered
	 */
	protected String baseImage = BASE_IMAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WallMaterialImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WallsPackage.Literals.WALL_MATERIAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WallMaterialBaseType getWallBaseType() {
		return wallBaseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWallBaseType(WallMaterialBaseType newWallBaseType) {
		WallMaterialBaseType oldWallBaseType = wallBaseType;
		wallBaseType = newWallBaseType == null ? WALL_BASE_TYPE_EDEFAULT : newWallBaseType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WallsPackage.WALL_MATERIAL__WALL_BASE_TYPE,
					oldWallBaseType, wallBaseType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WallsPackage.WALL_MATERIAL__DISPLAY_NAME,
					oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBreakable() {
		return breakable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBreakable(boolean newBreakable) {
		boolean oldBreakable = breakable;
		breakable = newBreakable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WallsPackage.WALL_MATERIAL__BREAKABLE, oldBreakable,
					breakable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHitPoints(int newHitPoints) {
		int oldHitPoints = hitPoints;
		hitPoints = newHitPoints;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WallsPackage.WALL_MATERIAL__HIT_POINTS, oldHitPoints,
					hitPoints));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WallsPackage.WALL_MATERIAL__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBaseImage() {
		return baseImage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBaseImage(String newBaseImage) {
		String oldBaseImage = baseImage;
		baseImage = newBaseImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WallsPackage.WALL_MATERIAL__BASE_IMAGE, oldBaseImage,
					baseImage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case WallsPackage.WALL_MATERIAL__WALL_BASE_TYPE:
			return getWallBaseType();
		case WallsPackage.WALL_MATERIAL__DISPLAY_NAME:
			return getDisplayName();
		case WallsPackage.WALL_MATERIAL__BREAKABLE:
			return isBreakable();
		case WallsPackage.WALL_MATERIAL__HIT_POINTS:
			return getHitPoints();
		case WallsPackage.WALL_MATERIAL__ID:
			return getId();
		case WallsPackage.WALL_MATERIAL__BASE_IMAGE:
			return getBaseImage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case WallsPackage.WALL_MATERIAL__WALL_BASE_TYPE:
			setWallBaseType((WallMaterialBaseType)newValue);
			return;
		case WallsPackage.WALL_MATERIAL__DISPLAY_NAME:
			setDisplayName((String) newValue);
			return;
		case WallsPackage.WALL_MATERIAL__BREAKABLE:
			setBreakable((Boolean) newValue);
			return;
		case WallsPackage.WALL_MATERIAL__HIT_POINTS:
			setHitPoints((Integer) newValue);
			return;
		case WallsPackage.WALL_MATERIAL__ID:
			setId((String) newValue);
			return;
		case WallsPackage.WALL_MATERIAL__BASE_IMAGE:
			setBaseImage((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case WallsPackage.WALL_MATERIAL__WALL_BASE_TYPE:
			setWallBaseType(WALL_BASE_TYPE_EDEFAULT);
			return;
		case WallsPackage.WALL_MATERIAL__DISPLAY_NAME:
			setDisplayName(DISPLAY_NAME_EDEFAULT);
			return;
		case WallsPackage.WALL_MATERIAL__BREAKABLE:
			setBreakable(BREAKABLE_EDEFAULT);
			return;
		case WallsPackage.WALL_MATERIAL__HIT_POINTS:
			setHitPoints(HIT_POINTS_EDEFAULT);
			return;
		case WallsPackage.WALL_MATERIAL__ID:
			setId(ID_EDEFAULT);
			return;
		case WallsPackage.WALL_MATERIAL__BASE_IMAGE:
			setBaseImage(BASE_IMAGE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case WallsPackage.WALL_MATERIAL__WALL_BASE_TYPE:
			return wallBaseType != WALL_BASE_TYPE_EDEFAULT;
		case WallsPackage.WALL_MATERIAL__DISPLAY_NAME:
			return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
		case WallsPackage.WALL_MATERIAL__BREAKABLE:
			return breakable != BREAKABLE_EDEFAULT;
		case WallsPackage.WALL_MATERIAL__HIT_POINTS:
			return hitPoints != HIT_POINTS_EDEFAULT;
		case WallsPackage.WALL_MATERIAL__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case WallsPackage.WALL_MATERIAL__BASE_IMAGE:
			return BASE_IMAGE_EDEFAULT == null ? baseImage != null : !BASE_IMAGE_EDEFAULT.equals(baseImage);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (wallBaseType: ");
		result.append(wallBaseType);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", breakable: ");
		result.append(breakable);
		result.append(", hitPoints: ");
		result.append(hitPoints);
		result.append(", id: ");
		result.append(id);
		result.append(", baseImage: ");
		result.append(baseImage);
		result.append(')');
		return result.toString();
	}

} //WallMaterialImpl


