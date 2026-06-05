/**
 */
package main.game.maze.walls.impl;

import java.util.Collection;

import main.game.maze.walls.WallMaterial;
import main.game.maze.walls.WallModel;
import main.game.maze.walls.WallsPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Wall Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.walls.impl.WallModelImpl#getMaterials <em>Materials</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WallModelImpl extends MinimalEObjectImpl.Container implements WallModel {
	/**
	 * The cached value of the '{@link #getMaterials() <em>Materials</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaterials()
	 * @generated
	 * @ordered
	 */
	protected EList<WallMaterial> materials;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WallModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WallsPackage.Literals.WALL_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<WallMaterial> getMaterials() {
		if (materials == null) {
			materials = new EObjectContainmentEList<WallMaterial>(WallMaterial.class, this,
					WallsPackage.WALL_MODEL__MATERIALS);
		}
		return materials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case WallsPackage.WALL_MODEL__MATERIALS:
			return ((InternalEList<?>) getMaterials()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case WallsPackage.WALL_MODEL__MATERIALS:
			return getMaterials();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case WallsPackage.WALL_MODEL__MATERIALS:
			getMaterials().clear();
			getMaterials().addAll((Collection<? extends WallMaterial>) newValue);
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
		case WallsPackage.WALL_MODEL__MATERIALS:
			getMaterials().clear();
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
		case WallsPackage.WALL_MODEL__MATERIALS:
			return materials != null && !materials.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //WallModelImpl


