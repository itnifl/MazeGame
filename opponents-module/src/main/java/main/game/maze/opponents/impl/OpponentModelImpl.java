/**
 */
package main.game.maze.opponents.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultyGameData;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentModel;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.OpponentsTables;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.pivot.evaluation.Executor;
import org.eclipse.ocl.pivot.ids.IdResolver;
import org.eclipse.ocl.pivot.ids.TypeId;
import org.eclipse.ocl.pivot.library.collection.CollectionSumOperation;
import org.eclipse.ocl.pivot.library.oclany.OclComparableLessThanEqualOperation;
import org.eclipse.ocl.pivot.library.string.CGStringGetSeverityOperation;
import org.eclipse.ocl.pivot.library.string.CGStringLogDiagnosticOperation;
import org.eclipse.ocl.pivot.utilities.PivotUtil;
import org.eclipse.ocl.pivot.utilities.ValueUtil;
import org.eclipse.ocl.pivot.values.IntegerValue;
import org.eclipse.ocl.pivot.values.InvalidValueException;
import org.eclipse.ocl.pivot.values.OrderedSetValue;
import org.eclipse.ocl.pivot.values.SequenceValue;
import org.eclipse.ocl.pivot.values.SequenceValue.Accumulator;

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
	protected DifficultyGameData selectedDifficulty;

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
	 * @generated
	 */
	@Override
	public int getMaxThreat() {
		/**
		 *
		 * let d : difficulties::Difficulty[?] = self.selectedDifficulty.currentDifficulty
		 * in if d.oclIsUndefined() then 0 else d.maxThreat endif
		 */
		/*@Caught*/ Object CAUGHT_d;
		try {
			final /*@NonInvalid*/ DifficultyGameData selectedDifficulty = this.getSelectedDifficulty();
			if (selectedDifficulty == null) {
				throw new InvalidValueException("Null source for \'\'http://main.game.maze/difficulty\'::DifficultyGameData::currentDifficulty\'");
			}
			final /*@Thrown*/ Difficulty d = selectedDifficulty.getCurrentDifficulty();
			CAUGHT_d = d;
		}
		catch (Exception e) {
			CAUGHT_d = ValueUtil.createInvalidValue(e);
		}
		final /*@NonInvalid*/ boolean oclIsUndefined = (CAUGHT_d == null) || (CAUGHT_d instanceof InvalidValueException);
		/*@Thrown*/ IntegerValue IF_oclIsUndefined;
		if (oclIsUndefined) {
			IF_oclIsUndefined = OpponentsTables.INT_0;
		}
		else {
			if (CAUGHT_d == null) {
				throw new InvalidValueException("Null source for \'\'http://main.game.maze/difficulty\'::Difficulty::maxThreat\'");
			}
			if (CAUGHT_d instanceof InvalidValueException) {
				throw (InvalidValueException)CAUGHT_d;
			}
			final /*@Thrown*/ int maxThreat = ((Difficulty)CAUGHT_d).getMaxThreat();
			final /*@Thrown*/ IntegerValue BOXED_maxThreat = ValueUtil.integerValueOf(maxThreat);
			IF_oclIsUndefined = BOXED_maxThreat;
		}
		final /*@Thrown*/ int ECORE_IF_oclIsUndefined = ValueUtil.intValueOf(IF_oclIsUndefined);
		return ECORE_IF_oclIsUndefined;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DifficultyGameData getSelectedDifficulty() {
		if (selectedDifficulty != null && selectedDifficulty.eIsProxy()) {
			InternalEObject oldSelectedDifficulty = (InternalEObject)selectedDifficulty;
			selectedDifficulty = (DifficultyGameData)eResolveProxy(oldSelectedDifficulty);
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
	public DifficultyGameData basicGetSelectedDifficulty() {
		return selectedDifficulty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSelectedDifficulty(DifficultyGameData newSelectedDifficulty) {
		DifficultyGameData oldSelectedDifficulty = selectedDifficulty;
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
	public boolean validateMaxThreat(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		final String constraintName = "OpponentModel::validateMaxThreat";
		try {
			/**
			 *
			 * inv validateMaxThreat:
			 *   let severity : Integer[1] = constraintName.getSeverity()
			 *   in
			 *     if severity <= 0
			 *     then true
			 *     else
			 *       let
			 *         result : Boolean[1] = self.characterTypes->collect(ct | ct.effectiveThreat)
			 *         ->sum() <= self.maxThreat
			 *       in
			 *         constraintName.logDiagnostic(self, null, diagnostics, context, null, severity, result, 0)
			 *     endif
			 */
			final /*@NonInvalid*/ Executor executor = PivotUtil.getExecutor(this);
			final /*@NonInvalid*/ IdResolver idResolver = executor.getIdResolver();
			final /*@NonInvalid*/ IntegerValue severity_0 = CGStringGetSeverityOperation.INSTANCE.evaluate(executor, OpponentsPackage.Literals.OPPONENT_MODEL___VALIDATE_MAX_THREAT__DIAGNOSTICCHAIN_MAP);
			final /*@NonInvalid*/ boolean le = OclComparableLessThanEqualOperation.INSTANCE.evaluate(executor, severity_0, OpponentsTables.INT_0).booleanValue();
			/*@NonInvalid*/ boolean IF_le;
			if (le) {
				IF_le = true;
			}
			else {
				final /*@NonInvalid*/ List<CharacterType> characterTypes = this.getCharacterTypes();
				final /*@NonInvalid*/ OrderedSetValue BOXED_characterTypes = idResolver.createOrderedSetOfAll(OpponentsTables.ORD_CLSSid_CharacterType, characterTypes);
				/*@Thrown*/ Accumulator accumulator = ValueUtil.createSequenceAccumulatorValue(OpponentsTables.SEQ_DATAid_EInt);
				Iterator<Object> ITERATOR_ct = BOXED_characterTypes.iterator();
				/*@NonInvalid*/ SequenceValue collect;
				while (true) {
					if (!ITERATOR_ct.hasNext()) {
						collect = accumulator;
						break;
					}
					/*@NonInvalid*/ CharacterType ct = (CharacterType)ITERATOR_ct.next();
					/**
					 * ct.effectiveThreat
					 */
					final /*@NonInvalid*/ int effectiveThreat = ct.getEffectiveThreat();
					final /*@NonInvalid*/ IntegerValue BOXED_effectiveThreat = ValueUtil.integerValueOf(effectiveThreat);
					//
					accumulator.add(BOXED_effectiveThreat);
				}
				final /*@NonInvalid*/ IntegerValue sum = (IntegerValue)CollectionSumOperation.INSTANCE.evaluate(executor, OpponentsTables.DATAid_EInt, collect);
				final /*@NonInvalid*/ int maxThreat = this.getMaxThreat();
				final /*@NonInvalid*/ IntegerValue BOXED_maxThreat = ValueUtil.integerValueOf(maxThreat);
				final /*@NonInvalid*/ boolean result = OclComparableLessThanEqualOperation.INSTANCE.evaluate(executor, sum, BOXED_maxThreat).booleanValue();
				final /*@NonInvalid*/ boolean logDiagnostic = CGStringLogDiagnosticOperation.INSTANCE.evaluate(executor, TypeId.BOOLEAN, constraintName, this, (Object)null, diagnostics, context, (Object)null, severity_0, result, OpponentsTables.INT_0).booleanValue();
				IF_le = logDiagnostic;
			}
			return IF_le;
		}
		catch (Throwable e) {
			return ValueUtil.validationFailedDiagnostic(constraintName, this, diagnostics, context, e);
		}
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
				setSelectedDifficulty((DifficultyGameData)newValue);
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
				setSelectedDifficulty((DifficultyGameData)null);
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
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case OpponentsPackage.OPPONENT_MODEL___VALIDATE_MAX_THREAT__DIAGNOSTICCHAIN_MAP:
				return validateMaxThreat((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(')');
		return result.toString();
	}

} //OpponentModelImpl
