/**
 */
package main.game.maze.opponents.impl;

import java.util.Collection;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;

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
 * An implementation of the model object '<em><b>Opponent Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link main.game.maze.opponents.impl.OpponentModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.OpponentModelImpl#getCharacterTypes <em>Character Types</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.OpponentModelImpl#getMaxThreat <em>Max Threat</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.OpponentModelImpl#getSelectedDifficulty <em>Selected Difficulty</em>}</li>
 *   <li>{@link main.game.maze.opponents.impl.OpponentModelImpl#getGameSetCurrentThreatLevel <em>Game Set Current Threat Level</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OpponentModelImpl extends MinimalEObjectImpl.Container implements OpponentModel {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCharacterTypes() <em>Character Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharacterTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<CharacterType> characterTypes;

	/**
	 * The default value of the '{@link #getMaxThreat() <em>Max Threat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxThreat()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_THREAT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSelectedDifficulty() <em>Selected Difficulty</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectedDifficulty()
	 * @generated
	 * @ordered
	 */
	protected Difficulty selectedDifficulty;

	/**
	 * The default value of the '{@link #getGameSetCurrentThreatLevel() <em>Game Set Current Threat Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGameSetCurrentThreatLevel()
	 * @generated
	 * @ordered
	 */
	protected static final double GAME_SET_CURRENT_THREAT_LEVEL_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getGameSetCurrentThreatLevel() <em>Game Set Current Threat Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGameSetCurrentThreatLevel()
	 * @generated
	 * @ordered
	 */
	protected double gameSetCurrentThreatLevel = GAME_SET_CURRENT_THREAT_LEVEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OpponentModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OpponentsPackage.Literals.OPPONENT_MODEL;
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
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.OPPONENT_MODEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CharacterType> getCharacterTypes() {
		if (characterTypes == null) {
			characterTypes = new EObjectContainmentEList<CharacterType>(CharacterType.class, this, OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES);
		}
		return characterTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public int getMaxThreat() {
		if (this.selectedDifficulty != null && this.getSelectedDifficulty() != null) {
			return this.getSelectedDifficulty().getMaxThreat();
		}
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Difficulty getSelectedDifficulty() {
		if (selectedDifficulty != null && selectedDifficulty.eIsProxy()) {
			InternalEObject oldSelectedDifficulty = (InternalEObject)selectedDifficulty;
			selectedDifficulty = (Difficulty)eResolveProxy(oldSelectedDifficulty);
			if (selectedDifficulty != oldSelectedDifficulty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, OpponentsPackage.OPPONENT_MODEL__SELECTED_DIFFICULTY, oldSelectedDifficulty, selectedDifficulty));
			}
		}
		return selectedDifficulty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Difficulty basicGetSelectedDifficulty() {
		return selectedDifficulty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSelectedDifficulty(Difficulty newSelectedDifficulty) {
		Difficulty oldSelectedDifficulty = selectedDifficulty;
		selectedDifficulty = newSelectedDifficulty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.OPPONENT_MODEL__SELECTED_DIFFICULTY, oldSelectedDifficulty, selectedDifficulty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getGameSetCurrentThreatLevel() {
		return gameSetCurrentThreatLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGameSetCurrentThreatLevel(double newGameSetCurrentThreatLevel) {
		double oldGameSetCurrentThreatLevel = gameSetCurrentThreatLevel;
		gameSetCurrentThreatLevel = newGameSetCurrentThreatLevel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OpponentsPackage.OPPONENT_MODEL__GAME_SET_CURRENT_THREAT_LEVEL, oldGameSetCurrentThreatLevel, gameSetCurrentThreatLevel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				return ((InternalEList<?>)getCharacterTypes()).basicRemove(otherEnd, msgs);
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
			case OpponentsPackage.OPPONENT_MODEL__NAME:
				return getName();
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				return getCharacterTypes();
			case OpponentsPackage.OPPONENT_MODEL__MAX_THREAT:
				return getMaxThreat();
			case OpponentsPackage.OPPONENT_MODEL__SELECTED_DIFFICULTY:
				if (resolve) return getSelectedDifficulty();
				return basicGetSelectedDifficulty();
			case OpponentsPackage.OPPONENT_MODEL__GAME_SET_CURRENT_THREAT_LEVEL:
				return getGameSetCurrentThreatLevel();
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
			case OpponentsPackage.OPPONENT_MODEL__NAME:
				setName((String)newValue);
				return;
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				getCharacterTypes().clear();
				getCharacterTypes().addAll((Collection<? extends CharacterType>)newValue);
				return;
			case OpponentsPackage.OPPONENT_MODEL__SELECTED_DIFFICULTY:
				setSelectedDifficulty((Difficulty)newValue);
				return;
			case OpponentsPackage.OPPONENT_MODEL__GAME_SET_CURRENT_THREAT_LEVEL:
				setGameSetCurrentThreatLevel((Double)newValue);
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
			case OpponentsPackage.OPPONENT_MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				getCharacterTypes().clear();
				return;
			case OpponentsPackage.OPPONENT_MODEL__SELECTED_DIFFICULTY:
				setSelectedDifficulty((Difficulty)null);
				return;
			case OpponentsPackage.OPPONENT_MODEL__GAME_SET_CURRENT_THREAT_LEVEL:
				setGameSetCurrentThreatLevel(GAME_SET_CURRENT_THREAT_LEVEL_EDEFAULT);
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
			case OpponentsPackage.OPPONENT_MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OpponentsPackage.OPPONENT_MODEL__CHARACTER_TYPES:
				return characterTypes != null && !characterTypes.isEmpty();
			case OpponentsPackage.OPPONENT_MODEL__MAX_THREAT:
				return getMaxThreat() != MAX_THREAT_EDEFAULT;
			case OpponentsPackage.OPPONENT_MODEL__SELECTED_DIFFICULTY:
				return selectedDifficulty != null;
			case OpponentsPackage.OPPONENT_MODEL__GAME_SET_CURRENT_THREAT_LEVEL:
				return gameSetCurrentThreatLevel != GAME_SET_CURRENT_THREAT_LEVEL_EDEFAULT;
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
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", GameSetCurrentThreatLevel: ");
		result.append(gameSetCurrentThreatLevel);
		result.append(')');
		return result.toString();
	}

} //OpponentModelImpl
