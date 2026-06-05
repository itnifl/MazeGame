/**
 */
package main.game.maze.walls;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see main.game.maze.walls.WallsFactory
 * @model kind="package"
 * @generated
 */
public interface WallsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "walls";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://main.game.maze/walls";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "walls";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WallsPackage eINSTANCE = main.game.maze.walls.impl.WallsPackageImpl.init();

	/**
	 * The meta object id for the '{@link main.game.maze.walls.impl.WallMaterialImpl <em>Wall Material</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.walls.impl.WallMaterialImpl
	 * @see main.game.maze.walls.impl.WallsPackageImpl#getWallMaterial()
	 * @generated
	 */
	int WALL_MATERIAL = 0;

	/**
	 * The feature id for the '<em><b>Wall Base Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MATERIAL__WALL_BASE_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MATERIAL__DISPLAY_NAME = 1;

	/**
	 * The feature id for the '<em><b>Breakable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MATERIAL__BREAKABLE = 2;

	/**
	 * The feature id for the '<em><b>Hit Points</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MATERIAL__HIT_POINTS = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MATERIAL__ID = 4;

	/**
	 * The feature id for the '<em><b>Base Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MATERIAL__BASE_IMAGE = 5;

	/**
	 * The number of structural features of the '<em>Wall Material</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MATERIAL_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Wall Material</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MATERIAL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.walls.impl.WallModelImpl <em>Wall Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.walls.impl.WallModelImpl
	 * @see main.game.maze.walls.impl.WallsPackageImpl#getWallModel()
	 * @generated
	 */
	int WALL_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Materials</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MODEL__MATERIALS = 0;

	/**
	 * The number of structural features of the '<em>Wall Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MODEL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Wall Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link main.game.maze.walls.WallMaterialBaseType <em>Wall Material Base Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see main.game.maze.walls.WallMaterialBaseType
	 * @see main.game.maze.walls.impl.WallsPackageImpl#getWallMaterialBaseType()
	 * @generated
	 */
	int WALL_MATERIAL_BASE_TYPE = 2;

	/**
	 * Returns the meta object for class '{@link main.game.maze.walls.WallMaterial <em>Wall Material</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wall Material</em>'.
	 * @see main.game.maze.walls.WallMaterial
	 * @generated
	 */
	EClass getWallMaterial();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.walls.WallMaterial#getWallBaseType <em>Wall Base Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wall Base Type</em>'.
	 * @see main.game.maze.walls.WallMaterial#getWallBaseType()
	 * @see #getWallMaterial()
	 * @generated
	 */
	EAttribute getWallMaterial_WallBaseType();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.walls.WallMaterial#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see main.game.maze.walls.WallMaterial#getDisplayName()
	 * @see #getWallMaterial()
	 * @generated
	 */
	EAttribute getWallMaterial_DisplayName();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.walls.WallMaterial#isBreakable <em>Breakable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Breakable</em>'.
	 * @see main.game.maze.walls.WallMaterial#isBreakable()
	 * @see #getWallMaterial()
	 * @generated
	 */
	EAttribute getWallMaterial_Breakable();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.walls.WallMaterial#getHitPoints <em>Hit Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hit Points</em>'.
	 * @see main.game.maze.walls.WallMaterial#getHitPoints()
	 * @see #getWallMaterial()
	 * @generated
	 */
	EAttribute getWallMaterial_HitPoints();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.walls.WallMaterial#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see main.game.maze.walls.WallMaterial#getId()
	 * @see #getWallMaterial()
	 * @generated
	 */
	EAttribute getWallMaterial_Id();

	/**
	 * Returns the meta object for the attribute '{@link main.game.maze.walls.WallMaterial#getBaseImage <em>Base Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Image</em>'.
	 * @see main.game.maze.walls.WallMaterial#getBaseImage()
	 * @see #getWallMaterial()
	 * @generated
	 */
	EAttribute getWallMaterial_BaseImage();

	/**
	 * Returns the meta object for class '{@link main.game.maze.walls.WallModel <em>Wall Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wall Model</em>'.
	 * @see main.game.maze.walls.WallModel
	 * @generated
	 */
	EClass getWallModel();

	/**
	 * Returns the meta object for the containment reference list '{@link main.game.maze.walls.WallModel#getMaterials <em>Materials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Materials</em>'.
	 * @see main.game.maze.walls.WallModel#getMaterials()
	 * @see #getWallModel()
	 * @generated
	 */
	EReference getWallModel_Materials();

	/**
	 * Returns the meta object for enum '{@link main.game.maze.walls.WallMaterialBaseType <em>Wall Material Base Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Wall Material Base Type</em>'.
	 * @see main.game.maze.walls.WallMaterialBaseType
	 * @generated
	 */
	EEnum getWallMaterialBaseType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WallsFactory getWallsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link main.game.maze.walls.impl.WallMaterialImpl <em>Wall Material</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.walls.impl.WallMaterialImpl
		 * @see main.game.maze.walls.impl.WallsPackageImpl#getWallMaterial()
		 * @generated
		 */
		EClass WALL_MATERIAL = eINSTANCE.getWallMaterial();

		/**
		 * The meta object literal for the '<em><b>Wall Base Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WALL_MATERIAL__WALL_BASE_TYPE = eINSTANCE.getWallMaterial_WallBaseType();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WALL_MATERIAL__DISPLAY_NAME = eINSTANCE.getWallMaterial_DisplayName();

		/**
		 * The meta object literal for the '<em><b>Breakable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WALL_MATERIAL__BREAKABLE = eINSTANCE.getWallMaterial_Breakable();

		/**
		 * The meta object literal for the '<em><b>Hit Points</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WALL_MATERIAL__HIT_POINTS = eINSTANCE.getWallMaterial_HitPoints();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WALL_MATERIAL__ID = eINSTANCE.getWallMaterial_Id();

		/**
		 * The meta object literal for the '<em><b>Base Image</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WALL_MATERIAL__BASE_IMAGE = eINSTANCE.getWallMaterial_BaseImage();

		/**
		 * The meta object literal for the '{@link main.game.maze.walls.impl.WallModelImpl <em>Wall Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.walls.impl.WallModelImpl
		 * @see main.game.maze.walls.impl.WallsPackageImpl#getWallModel()
		 * @generated
		 */
		EClass WALL_MODEL = eINSTANCE.getWallModel();

		/**
		 * The meta object literal for the '<em><b>Materials</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WALL_MODEL__MATERIALS = eINSTANCE.getWallModel_Materials();

		/**
		 * The meta object literal for the '{@link main.game.maze.walls.WallMaterialBaseType <em>Wall Material Base Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see main.game.maze.walls.WallMaterialBaseType
		 * @see main.game.maze.walls.impl.WallsPackageImpl#getWallMaterialBaseType()
		 * @generated
		 */
		EEnum WALL_MATERIAL_BASE_TYPE = eINSTANCE.getWallMaterialBaseType();

	}

} //WallsPackage


