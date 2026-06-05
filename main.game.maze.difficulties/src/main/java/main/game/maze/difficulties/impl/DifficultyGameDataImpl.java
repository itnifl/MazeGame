/**
 */
package main.game.maze.difficulties.impl;

import java.util.Collection;

import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultyGameData;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Difficulty Game Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.difficulties.impl.DifficultyGameDataImpl#getDifficulties <em>Difficulties</em>}</li>
 *   <li>{@link main.game.maze.difficulties.impl.DifficultyGameDataImpl#getCurrentDifficulty <em>Current Difficulty</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DifficultyGameDataImpl extends MinimalEObjectImpl.Container implements DifficultyGameData {
	/**
	 * The cached value of the '{@link #getDifficulties() <em>Difficulties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDifficulties()
	 * @generated
	 * @ordered
	 */
	protected EList<Difficulty> difficulties;

	/**
	 * The cached value of the '{@link #getCurrentDifficulty() <em>Current Difficulty</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentDifficulty()
	 * @generated
	 * @ordered
	 */
	protected Difficulty currentDifficulty;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DifficultyGameDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DifficultiesPackage.Literals.DIFFICULTY_GAME_DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Difficulty> getDifficulties() {
		if (difficulties == null) {
			difficulties = new EObjectContainmentEList<Difficulty>(Difficulty.class, this, DifficultiesPackage.DIFFICULTY_GAME_DATA__DIFFICULTIES);
		}
		return difficulties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Difficulty getCurrentDifficulty() {
		if (currentDifficulty != null && currentDifficulty.eIsProxy()) {
			InternalEObject oldCurrentDifficulty = (InternalEObject)currentDifficulty;
			currentDifficulty = (Difficulty)eResolveProxy(oldCurrentDifficulty);
			if (currentDifficulty != oldCurrentDifficulty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DifficultiesPackage.DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY, oldCurrentDifficulty, currentDifficulty));
			}
		}
		return currentDifficulty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Difficulty basicGetCurrentDifficulty() {
		return currentDifficulty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCurrentDifficulty(Difficulty newCurrentDifficulty) {
		Difficulty oldCurrentDifficulty = currentDifficulty;
		currentDifficulty = newCurrentDifficulty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifficultiesPackage.DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY, oldCurrentDifficulty, currentDifficulty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DifficultiesPackage.DIFFICULTY_GAME_DATA__DIFFICULTIES:
				return ((InternalEList<?>)getDifficulties()).basicRemove(otherEnd, msgs);
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
			case DifficultiesPackage.DIFFICULTY_GAME_DATA__DIFFICULTIES:
				return getDifficulties();
			case DifficultiesPackage.DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY:
				if (resolve) return getCurrentDifficulty();
				return basicGetCurrentDifficulty();
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
			case DifficultiesPackage.DIFFICULTY_GAME_DATA__DIFFICULTIES:
				getDifficulties().clear();
				getDifficulties().addAll((Collection<? extends Difficulty>)newValue);
				return;
			case DifficultiesPackage.DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY:
				setCurrentDifficulty((Difficulty)newValue);
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
			case DifficultiesPackage.DIFFICULTY_GAME_DATA__DIFFICULTIES:
				getDifficulties().clear();
				return;
			case DifficultiesPackage.DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY:
				setCurrentDifficulty((Difficulty)null);
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
			case DifficultiesPackage.DIFFICULTY_GAME_DATA__DIFFICULTIES:
				return difficulties != null && !difficulties.isEmpty();
			case DifficultiesPackage.DIFFICULTY_GAME_DATA__CURRENT_DIFFICULTY:
				return currentDifficulty != null;
		}
		return super.eIsSet(featureID);
	}

} //DifficultyGameDataImpl


