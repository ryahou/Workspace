package packageIHM;

import java.awt.Color;

import fr.cnes.genius.chart.GFreeChartXY;
import fr.cnes.genius.constraints.GConstraint;
import fr.cnes.genius.exception.GException;
import fr.cnes.genius.exception.GFreeChartException;
import fr.cnes.genius.highLevel.GComboBoxWithLabel;
import fr.cnes.genius.highLevel.GEntryInt;
import fr.cnes.genius.highLevel.GEntryReal;
import fr.cnes.genius.lowLevel.GCheckBox;
import fr.cnes.genius.lowLevel.GComboBox;
import fr.cnes.genius.lowLevel.GLabel;
import fr.cnes.genius.lowLevel.GPanel;
import fr.cnes.genius.lowLevel.GTextArea;
import fr.cnes.genius.main.GEvent;
import fr.cnes.genius.main.GListener;
import fr.cnes.genius.main.GReadWrite;

public class WidStab extends GPanel implements GReadWrite, GListener {

	/**
	 * Les variables sont stockées en système SI (kg, m...); les unités par
	 * défaut pour l'affichage sont les g et mm. L'unité d'un champ peut être
	 * changée par l'utilisateur.
	 */

	/// private int numColTop = 6;
	/// private GLabel[] colTop = new GLabel[numColTop];

	private GComboBoxWithLabel menuFusees = new GComboBoxWithLabel("Type de fusée", IHMPrincipale.fusee1.typeFusee, "Fusée à eau", 
			"Microfusée", "Rocketry Challenge", "Minifusée", "Fusée expérimentale", "Bi-étage");
	
	private GLabel lab1etage = new GLabel("1er étage");
	private GLabel lab2etage = new GLabel("2ème étage");

	private GEntryReal boxMasse = new GEntryReal("Masse", IHMPrincipale.fusee1.masseSansMoteur, IHMPrincipale.unitMasse);
	private GComboBox menuMasses = new GComboBox(IHMPrincipale.fusee1.etatMoteur, "sans propu", "avec propu vide", "avec propu plein");
	private GEntryReal boxCentreDeMasse = new GEntryReal("Centre de masse", IHMPrincipale.fusee1.xCG, IHMPrincipale.unitLongueur);
	private GComboBox menuCentreDeMasses = new GComboBox(IHMPrincipale.fusee1.etatMoteur, "sans propu", "avec propu vide",
			"avec propu plein");
	private GEntryReal boxLongTot = new GEntryReal("Longueur totale", IHMPrincipale.fusee1.longTot, IHMPrincipale.unitLongueur);

	private GEntryReal boxMasse2Etage = new GEntryReal("", 0, IHMPrincipale.unitMasse);
	private GComboBox menuMasses2Etage = new GComboBox(0, "sans propu", "avec propu vide", "avec propu plein");
	private GEntryReal boxCentreDeMasse2Etage = new GEntryReal("", 0, IHMPrincipale.unitLongueur);
	private GComboBox menuCentreDeMasses2Etage = new GComboBox(0, "sans propu", "avec propu vide", "avec propu plein");
	private GEntryReal boxLong2Etage = new GEntryReal("", 0, IHMPrincipale.unitLongueur);

	private GComboBoxWithLabel menuMoteurs = new GComboBoxWithLabel("Moteur", 1, "pas de moteur", "Cariacou", 
			"Pro24-1G_25E75_Rufina_C'Space", "Pro29-1G_56F120_C'Space", "Pro29-2G_110G250_C'Space");
	private GEntryReal boxPositionDuBas = new GEntryReal("Position", IHMPrincipale.fusee1.xPropuRef, IHMPrincipale.unitLongueur);

	private GComboBox menuMoteurs2Etage = new GComboBox("", 1, "H2O 2.0L 1000g 6bar", "pas de moteur", "µ-propu C6-3",
			"Cariacou", "P24-3G_75F51", "Pro54 Barasinga");
	private GEntryReal boxPositionDuBas2Etage = new GEntryReal("", 0, IHMPrincipale.unitLongueur);

	private GComboBoxWithLabel menuOgive = new GComboBoxWithLabel("Ogive", IHMPrincipale.fusee1.typeOgive, "Pas d'ogive",
			"Parabolique (arrondie)", "Ogivale (pointue)", "Conique (droite)");
	private GEntryReal boxHauteurOgive = new GEntryReal("Hauteur", IHMPrincipale.fusee1.longOgive, IHMPrincipale.unitLongueur);
	private GEntryReal boxDiametreOgive = new GEntryReal("Diamètre", IHMPrincipale.fusee1.dOgive, IHMPrincipale.unitLongueur);

	private GComboBox menuOgive2Etage = new GComboBox("", 0, "Pas d'ogive", "Parabolique (arrondie)",
			"Ogivale (pointue)", "Conique (droite)");
	private GEntryReal boxHauteurOgive2Etage = new GEntryReal("", 0, IHMPrincipale.unitLongueur);
	private GEntryReal boxDiametreOgive2Etage = new GEntryReal("", 0, IHMPrincipale.unitLongueur);

	private GComboBoxWithLabel menuAilerons = new GComboBoxWithLabel("Ailerons", IHMPrincipale.fusee1.nombreJeuxAil, "",
			"Mono-empennage", "Bi-empennage", "Bi-empennage 1/2 masqué");
	private GLabel labEmpennage1 = new GLabel("Empennage bas");
	private GLabel labEmpennage2 = new GLabel("Empennage haut");
	private GEntryReal boxEmplanture1 = new GEntryReal("Emplanture", IHMPrincipale.fusee1.emplantureAil, IHMPrincipale.unitLongueur);
	/// à mettre quand la souris passe sur la case : 'm'
	private GEntryReal boxEmplanture2 = new GEntryReal("", IHMPrincipale.fusee1.emplantureCan, IHMPrincipale.unitLongueur);
	private GEntryReal boxSaumon1 = new GEntryReal("Saumon", IHMPrincipale.fusee1.saumonAil, IHMPrincipale.unitLongueur); /// 'n'
	private GEntryReal boxSaumon2 = new GEntryReal("", IHMPrincipale.fusee1.saumonCan, IHMPrincipale.unitLongueur);
	private GEntryReal boxFleche1 = new GEntryReal("Flèche", IHMPrincipale.fusee1.flecheAil, IHMPrincipale.unitLongueur); /// 'p'
	private GEntryReal boxFleche2 = new GEntryReal("", IHMPrincipale.fusee1.flecheCan, IHMPrincipale.unitLongueur);
	private GEntryReal boxEnvergure1 = new GEntryReal("Semi-envergure", IHMPrincipale.fusee1.envergureAil, IHMPrincipale.unitLongueur);///'E'
	private GEntryReal boxEnvergure2 = new GEntryReal("", IHMPrincipale.fusee1.envergureCan, IHMPrincipale.unitLongueur);
	private GEntryReal boxEpaisseur1 = new GEntryReal("Epaisseur", IHMPrincipale.fusee1.epAil, IHMPrincipale.unitLongueur);
	private GEntryReal boxEpaisseur2 = new GEntryReal("", IHMPrincipale.fusee1.epCan, IHMPrincipale.unitLongueur);
	private GEntryInt boxNombre1 = new GEntryInt("Nombre", IHMPrincipale.fusee1.nombreAil);
	private GEntryInt boxNombre2 = new GEntryInt("", IHMPrincipale.fusee1.nombreCan);
	private GEntryReal boxPositionDuBas1 = new GEntryReal("Position", IHMPrincipale.fusee1.xAil, IHMPrincipale.unitLongueur);
	private GEntryReal boxPositionDuBas2 = new GEntryReal("", IHMPrincipale.fusee1.xCan, IHMPrincipale.unitLongueur);
	private GEntryReal boxDiametre1 = new GEntryReal("Diamètre", IHMPrincipale.fusee1.dAil, IHMPrincipale.unitLongueur);
	private GEntryReal boxDiametre2 = new GEntryReal("", IHMPrincipale.fusee1.dCan, IHMPrincipale.unitLongueur);

	private GComboBoxWithLabel menuTransition = new GComboBoxWithLabel("Transitions", IHMPrincipale.fusee1.nombreTransitions,
			"Pas de transition", "Une transition", "Deux transitions");
	private GLabel labTransitionA = new GLabel("Transition A");
	private GLabel labTransitionB = new GLabel("Transition B");
	private GEntryReal boxLongueurA = new GEntryReal("Longueur 'L'", IHMPrincipale.fusee1.lA, IHMPrincipale.unitLongueur);
	private GEntryReal boxLongueurB = new GEntryReal("", IHMPrincipale.fusee1.lB, IHMPrincipale.unitLongueur);
	private GEntryReal boxDiametreD1A = new GEntryReal("Diamètre 'D1'", IHMPrincipale.fusee1.d1A, IHMPrincipale.unitLongueur);
	private GEntryReal boxDiametreD1B = new GEntryReal("", IHMPrincipale.fusee1.d1B, IHMPrincipale.unitLongueur);
	private GEntryReal boxDiametreD2A = new GEntryReal("Diamètre 'D2'", IHMPrincipale.fusee1.d2A, IHMPrincipale.unitLongueur);
	private GEntryReal boxDiametreD2B = new GEntryReal("", IHMPrincipale.fusee1.d2B, IHMPrincipale.unitLongueur);
	private GEntryReal boxImplantationXA = new GEntryReal("Implantation 'x'", IHMPrincipale.fusee1.xA, IHMPrincipale.unitLongueur);
	private GEntryReal boxImplantationXB = new GEntryReal("", IHMPrincipale.fusee1.xB, IHMPrincipale.unitLongueur);

	/// sert également à afficher les résultats de l'ensemble étage 1 + 2 en bi-étage
///	private GLabel sousTitreResultatEtage1 = new GLabel("Résultats étage 1");
///	private GLabel sousTitreResultatEtage2 = new GLabel("Résultats étage 2");

	private GCheckBox niveauDetail = new GCheckBox("Résultats intermédiaires");

	private GLabel dummyLabel1 = new GLabel("");
	private GLabel dummyLabel2 = new GLabel("Position");
	private GLabel dummyLabel3 = new GLabel("");
	private GLabel dummyLabel4 = new GLabel("");
	private GLabel dummyLabel5 = new GLabel("Hauteur");
	private GLabel dummyLabel6 = new GLabel("");
	private GLabel dummyLabel7 = new GLabel("Diamètre");
	private GLabel dummyLabel8 = new GLabel("");

	GFreeChartXY diagrammeStabilite = new GFreeChartXY("", "Marge statique MS", "Portance Cnalpha", null, false);

	/// séries de valeurs pour le diagramme de stabilité
	/// à remplacer par des méthodes plus propres, notamment le couple
	/// gradient de portance
	private double[] xCnAlpha = { 0, 1, 2, 3, 4, 5, 6, 7 };
	private double[] yCnAlphaMin = { Stab.critCnMinMiniF, Stab.critCnMinMiniF, Stab.critCnMinMiniF, 
			Stab.critCnMinMiniF, Stab.critCnMinMiniF, Stab.critCnMinMiniF, Stab.critCnMinMiniF, Stab.critCnMinMiniF };
	private double[] yCnAlphaMax = { Stab.critCnMaxMiniF, Stab.critCnMaxMiniF, Stab.critCnMaxMiniF, 
			Stab.critCnMaxMiniF, Stab.critCnMaxMiniF, Stab.critCnMaxMiniF, Stab.critCnMaxMiniF, Stab.critCnMaxMiniF };
	/// marge statique
	private double[] xMSMin = { Stab.critMSMinMiniF, Stab.critMSMinMiniF, Stab.critMSMinMiniF, Stab.critMSMinMiniF, 
			Stab.critMSMinMiniF, Stab.critMSMinMiniF, Stab.critMSMinMiniF, Stab.critMSMinMiniF };
	private double[] xMSMax = { Stab.critMSMaxMiniF, Stab.critMSMaxMiniF, Stab.critMSMaxMiniF, Stab.critMSMaxMiniF, 
			Stab.critMSMaxMiniF, Stab.critMSMaxMiniF, Stab.critMSMaxMiniF, Stab.critMSMaxMiniF };
	private double[] yMS = { 0, 10, 20, 30, 40, 50, 60, 70 };
	/// couple MS*Cnalpha
	private double[] xCoupleMin = { 0, 0.5, 1, 2, 3, 4, 5, 6, 7 };
	private double[] yCoupleMin = { 60, 40, 15, 7.5, 5, 3.75, 3, 2.5, 2.143 };
	private double[] xCoupleMax = { 2, 3, 4, 5, 6, 7, 8, 9 };
	private double[] yCoupleMax = { 50, 33.33, 25, 20, 16.67, 14.29, 12.5, 11.11 };
	/// accesseurs des valeurs de l'objet fusee (les deux tableaux doivent avoir le même nombre de points)
	private double[] xFusee = { IHMPrincipale.fusee1.getMSMin(), IHMPrincipale.fusee1.getMSMax() };
	private double[] yFusee = { IHMPrincipale.fusee1.getPortanceFusee(), IHMPrincipale.fusee1.getPortanceFusee() };
	
	/// Objet schéma de la fusée et séries de valeurs nécessaires au tracé de la fusée
	GFreeChartXY schemaFusee = new GFreeChartXY("", "", "", null, false);
	/// Séries de valeurs pour l'ogive
	/// N.B. pour l'instant quelle que soit le type d'ogive sélectionnée,
	/// elles ont toutes le même profil :-(
	private double[] xOgive1 = { -IHMPrincipale.fusee1.dOgive/2, 0, IHMPrincipale.fusee1.dOgive/2 };
	private double[] yOgive1 = { -IHMPrincipale.fusee1.longOgive, 0, -IHMPrincipale.fusee1.longOgive };
	private double repereYOgive = 0;	///données fantôme utilisée pour recaler le tracé lorsqu'il n'y a pas d'ogive
	/// Séries de valeurs pour les ailerons
	/// rayon au niveau des ailerons
	private double[] Ail_gauche_x_emplanture = { -IHMPrincipale.fusee1.dAil/2, -IHMPrincipale.fusee1.dAil/2 };
	/// position du bas - emplanture, position du bas
	private double[] Ail_gauche_y_emplanture = { -IHMPrincipale.fusee1.xAil+IHMPrincipale.fusee1.emplantureAil, 
			-IHMPrincipale.fusee1.xAil };
	/// rayon au niveau des ailerons + semi-envergure
	private double[] Ail_gauche_x_saumon = { -IHMPrincipale.fusee1.dAil/2-IHMPrincipale.fusee1.envergureAil, 
			-IHMPrincipale.fusee1.dAil/2-IHMPrincipale.fusee1.envergureAil };
	/// position du bas - emplanture, position du bas
	private double[] Ail_gauche_y_saumon = { -IHMPrincipale.fusee1.xAil+IHMPrincipale.fusee1.emplantureAil, -IHMPrincipale.fusee1.xAil };
	/// rayon au niveau des ailerons + semi-envergure, rayon au niveau des ailerons
	private double[] Ail_gauche_x_BA = { -IHMPrincipale.fusee1.dAil/2-IHMPrincipale.fusee1.envergureAil, -IHMPrincipale.fusee1.dAil/2 };
	/// position du bas - emplanture
	private double[] Ail_gauche_y_BA = { -IHMPrincipale.fusee1.xAil+IHMPrincipale.fusee1.emplantureAil, 
			-IHMPrincipale.fusee1.xAil+IHMPrincipale.fusee1.emplantureAil };
	/// rayon au niveau des ailerons + semi-envergure, rayon au niveau des ailerons
	private double[] Ail_gauche_x_BF = { -IHMPrincipale.fusee1.dAil/2-IHMPrincipale.fusee1.envergureAil, -IHMPrincipale.fusee1.dAil/2 };
	/// position du bas
	private double[] Ail_gauche_y_BF = { -IHMPrincipale.fusee1.xAil, -IHMPrincipale.fusee1.xAil };
	/// rayon au niveau des ailerons
	private double[] Ail_droite_x_emplanture = { IHMPrincipale.fusee1.dAil/2, IHMPrincipale.fusee1.dAil/2 };
	/// position du bas - emplanture, position du bas
	private double[] Ail_droite_y_emplanture = { -IHMPrincipale.fusee1.xAil+IHMPrincipale.fusee1.emplantureAil, 
			-IHMPrincipale.fusee1.xAil };
	/// rayon au niveau des ailerons + semi-envergure
	private double[] Ail_droite_x_saumon = { IHMPrincipale.fusee1.dAil/2+IHMPrincipale.fusee1.envergureAil, 
			IHMPrincipale.fusee1.dAil/2+IHMPrincipale.fusee1.envergureAil };
	/// position du bas - emplanture, position du bas
	private double[] Ail_droite_y_saumon = { -IHMPrincipale.fusee1.xAil+IHMPrincipale.fusee1.emplantureAil, -IHMPrincipale.fusee1.xAil };
	/// rayon au niveau des ailerons + semi-envergure, rayon au niveau des ailerons
	private double[] Ail_droite_x_BA = { IHMPrincipale.fusee1.dAil/2+IHMPrincipale.fusee1.envergureAil, IHMPrincipale.fusee1.dAil/2 };
	/// position du bas - emplanture
	private double[] Ail_droite_y_BA = { -IHMPrincipale.fusee1.xAil+IHMPrincipale.fusee1.emplantureAil, 
			-IHMPrincipale.fusee1.xAil+IHMPrincipale.fusee1.emplantureAil };
	/// rayon au niveau des ailerons + semi-envergure, rayon au niveau des ailerons
	private double[] Ail_droite_x_BF = { IHMPrincipale.fusee1.dAil/2+IHMPrincipale.fusee1.envergureAil, IHMPrincipale.fusee1.dAil/2 };
	/// position du bas
	private double[] Ail_droite_y_BF = { -IHMPrincipale.fusee1.xAil, -IHMPrincipale.fusee1.xAil };
	
	/// Séries de valeurs pour les canards
	/// rayon au niveau des canards
	private double[] Can_gauche_x_emplanture = { -IHMPrincipale.fusee1.dCan/2, -IHMPrincipale.fusee1.dCan/2 };
	/// position du bas - emplanture, position du bas
	private double[] Can_gauche_y_emplanture = { -IHMPrincipale.fusee1.xCan+IHMPrincipale.fusee1.emplantureCan, 
			-IHMPrincipale.fusee1.xCan };
	/// rayon au niveau des canards + semi-envergure
	private double[] Can_gauche_x_saumon = { -IHMPrincipale.fusee1.dCan/2-IHMPrincipale.fusee1.envergureCan, 
			-IHMPrincipale.fusee1.dCan/2-IHMPrincipale.fusee1.envergureCan };
	/// position du bas - emplanture, position du bas
	private double[] Can_gauche_y_saumon = { -IHMPrincipale.fusee1.xCan+IHMPrincipale.fusee1.emplantureCan, -IHMPrincipale.fusee1.xCan };
	/// rayon au niveau des canards + semi-envergure, rayon au niveau des canards
	private double[] Can_gauche_x_BA = { -IHMPrincipale.fusee1.dCan/2-IHMPrincipale.fusee1.envergureCan, -IHMPrincipale.fusee1.dCan/2 };
	/// position du bas - emplanture
	private double[] Can_gauche_y_BA = { -IHMPrincipale.fusee1.xCan+IHMPrincipale.fusee1.emplantureCan, 
			-IHMPrincipale.fusee1.xCan+IHMPrincipale.fusee1.emplantureCan };
	/// rayon au niveau des canards + semi-envergure, rayon au niveau des canards
	private double[] Can_gauche_x_BF = { -IHMPrincipale.fusee1.dCan/2-IHMPrincipale.fusee1.envergureCan, -IHMPrincipale.fusee1.dCan/2 };
	/// position du bas
	private double[] Can_gauche_y_BF = { -IHMPrincipale.fusee1.xCan, -IHMPrincipale.fusee1.xCan };
	/// rayon au niveau des canards
	private double[] Can_droite_x_emplanture = { IHMPrincipale.fusee1.dCan/2, IHMPrincipale.fusee1.dCan/2 };
	/// position du bas - emplanture, position du bas
	private double[] Can_droite_y_emplanture = { -IHMPrincipale.fusee1.xCan+IHMPrincipale.fusee1.emplantureCan, 
			-IHMPrincipale.fusee1.xCan };
	/// rayon au niveau des canards + semi-envergure
	private double[] Can_droite_x_saumon = { IHMPrincipale.fusee1.dCan/2+IHMPrincipale.fusee1.envergureCan, 
			IHMPrincipale.fusee1.dCan/2+IHMPrincipale.fusee1.envergureCan };
	/// position du bas - emplanture, position du bas
	private double[] Can_droite_y_saumon = { -IHMPrincipale.fusee1.xCan+IHMPrincipale.fusee1.emplantureCan, -IHMPrincipale.fusee1.xCan };
	/// rayon au niveau des canards + semi-envergure, rayon au niveau des canards
	private double[] Can_droite_x_BA = { IHMPrincipale.fusee1.dCan/2+IHMPrincipale.fusee1.envergureCan, IHMPrincipale.fusee1.dCan/2 };
	/// position du bas - emplanture
	private double[] Can_droite_y_BA = { -IHMPrincipale.fusee1.xCan+IHMPrincipale.fusee1.emplantureCan, 
			-IHMPrincipale.fusee1.xCan+IHMPrincipale.fusee1.emplantureCan };
	/// rayon au niveau des canards + semi-envergure, rayon au niveau des canards
	private double[] Can_droite_x_BF = { IHMPrincipale.fusee1.dCan/2+IHMPrincipale.fusee1.envergureCan, IHMPrincipale.fusee1.dCan/2 };
	/// position du bas
	private double[] Can_droite_y_BF = { -IHMPrincipale.fusee1.xCan, -IHMPrincipale.fusee1.xCan };
	
	/// Séries de valeurs pour la transition A
	/// D2A/2, D1A/2
	private double[] TransitionA_x_gauche = { -IHMPrincipale.fusee1.d2A/2, -IHMPrincipale.fusee1.d1A/2 };
	/// implantation + longueur de la transition, implantation
	private double[] TransitionA_y_gauche = { -IHMPrincipale.fusee1.xA-IHMPrincipale.fusee1.lA, -IHMPrincipale.fusee1.xA };
	/// D2A/2, D1A/2
	private double[] TransitionA_x_droite = { IHMPrincipale.fusee1.d2A/2, IHMPrincipale.fusee1.d1A/2 };
	/// implantation + longueur de la transition, implantation
	private double[] TransitionA_y_droite = { -IHMPrincipale.fusee1.xA-IHMPrincipale.fusee1.lA, -IHMPrincipale.fusee1.xA };
	/// Séries de valeurs pour la transition B
	/// D2B/2, D1B/2
	private double[] TransitionB_x_gauche = { -IHMPrincipale.fusee1.d2B/2, -IHMPrincipale.fusee1.d1B/2 };
	/// implantation + longueur de la transition, implantation
	private double[] TransitionB_y_gauche = { -IHMPrincipale.fusee1.xB-IHMPrincipale.fusee1.lB, -IHMPrincipale.fusee1.xB };
	/// D2B/2, D1B/2
	private double[] TransitionB_x_droite = { IHMPrincipale.fusee1.d2B/2, IHMPrincipale.fusee1.d1B/2 };
	/// implantation + longueur de la transition, implantation
	private double[] TransitionB_y_droite = { -IHMPrincipale.fusee1.xB-IHMPrincipale.fusee1.lB, -IHMPrincipale.fusee1.xB };
	/// Séries de valeurs pour le premier tube
	/// rayon ogive, rayon ogive
	private double[] xTube1_gauche = { -IHMPrincipale.fusee1.dOgive/2, -IHMPrincipale.fusee1.dOgive/2 };
	/// longueur ogive, longueur ogive + implantation transition A - longueur transition A
	private double[] yTube1_gauche = { -IHMPrincipale.fusee1.longOgive, -IHMPrincipale.fusee1.xA };
	/// rayon ogive, rayon ogive
	private double[] xTube1_droite = { IHMPrincipale.fusee1.dOgive/2, IHMPrincipale.fusee1.dOgive/2 };
	/// longueur ogive, longueur ogive + implantation transition A - longueur transition A
	private double[] yTube1_droite = { -IHMPrincipale.fusee1.longOgive, -IHMPrincipale.fusee1.xA };
	/// Séries de valeurs pour le deuxième tube
	/// d2A/2, d2A/2
	private double[] xTube2_gauche = { -IHMPrincipale.fusee1.d2A/2, -IHMPrincipale.fusee1.d2A/2 };
	/// implantation transition A + longueur transition, implantation transition B
	private double[] yTube2_gauche = { -IHMPrincipale.fusee1.xA-IHMPrincipale.fusee1.lA, -IHMPrincipale.fusee1.xB };
	/// d2A/2, d2A/2
	private double[] xTube2_droite = { IHMPrincipale.fusee1.d2A/2, IHMPrincipale.fusee1.d2A/2 };
	/// implantation transition A + longueur transition, implantation transition B
	private double[] yTube2_droite = { -IHMPrincipale.fusee1.xA-IHMPrincipale.fusee1.lA, -IHMPrincipale.fusee1.xB };
	/// Séries de valeurs pour le troisième tube
	/// d2B/2, d2B/2
	private double[] xTube3_gauche = { -IHMPrincipale.fusee1.d2B/2, -IHMPrincipale.fusee1.d2B/2 };
	/// implantation transition B + longueur transition, longueur totale
	private double[] yTube3_gauche = { -IHMPrincipale.fusee1.xB-IHMPrincipale.fusee1.lB, -IHMPrincipale.fusee1.longTot };
	/// d2B/2, d2B/2
	private double[] xTube3_droite = { IHMPrincipale.fusee1.d2B/2, IHMPrincipale.fusee1.d2B/2 };
	/// implantation transition B + longueur transition, longueur totale
	private double[] yTube3_droite = { -IHMPrincipale.fusee1.xB-IHMPrincipale.fusee1.lB, -IHMPrincipale.fusee1.longTot };
	/// Séries de valeurs pour le moteur
	/// rayon moteur, rayon moteur
	private double[] xMoteur_gauche = { -IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2, -IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2 };
	/// position du bas du moteur - longueur du moteur, position du bas du moteur
	private double[] yMoteur_gauche = { -IHMPrincipale.fusee1.xPropuRef+IHMPrincipale.XMLMoteur1.getLongueurMoteur(), 
			-IHMPrincipale.fusee1.xPropuRef };
	/// rayon moteur, rayon moteur
	private double[] xMoteur_droite = { IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2, IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2 };
	/// position du bas du moteur - longueur du moteur, position du bas du moteur
	private double[] yMoteur_droite = { -IHMPrincipale.fusee1.xPropuRef+IHMPrincipale.XMLMoteur1.getLongueurMoteur(), 
			-IHMPrincipale.fusee1.xPropuRef };
	/// rayon moteur, rayon moteur
	private double[] xMoteur_haut = { -IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2, IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2 };
	/// position du bas du moteur - longueur du moteur, position du bas du moteur - longueur du moteur
	private double[] yMoteur_haut = { -IHMPrincipale.fusee1.xPropuRef+IHMPrincipale.XMLMoteur1.getLongueurMoteur(), 
			-IHMPrincipale.fusee1.xPropuRef+IHMPrincipale.XMLMoteur1.getLongueurMoteur() };
	/// rayon moteur, rayon moteur
	private double[] xMoteur_bas = { -IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2, IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2 };
	/// position du bas du moteur, position du bas du moteur
	private double[] yMoteur_bas = { -IHMPrincipale.fusee1.xPropuRef, -IHMPrincipale.fusee1.xPropuRef };
	/// Séries de valeurs pour le culot
	/// d2B/2, d2B/2
	private double[] xCulot = { -IHMPrincipale.fusee1.d2B/2, IHMPrincipale.fusee1.d2B/2 };
	/// longueur totale, longueur totale
	private double[] yCulot = { -IHMPrincipale.fusee1.longTot, -IHMPrincipale.fusee1.longTot };
	/// Séries de valeurs liées à la stabilité
	private double[] xCdG = { -0.0, -0.0 };		/// on suppose le centre de gravité sur l'axe longitudinal de la fusée
	/// centres de gravité moteur vide puis avec moteur plein
	private double[] yCdG = { -IHMPrincipale.fusee1.xCGVide, -IHMPrincipale.fusee1.xCGPlein };
	private double[] xFdP = { -0.0, -0.0 };		/// on suppose le foyer de portance sur l'axe longitudinal de la fusée
	/// foyer de portance
	private double[] yFdP = { -IHMPrincipale.fusee1.xCPFusee, -IHMPrincipale.fusee1.xCPFusee };
	/// Valeurs fantômes pour tracer un repère orthonormé
	private double LAMax;
	private double LBMax;
	private double LtotFleche;
	private double XM;
	private double XMin;
	private double XMax;
	private double YMin;
	
	protected static String resultatDiagnostic = "\n	INSTABLE	\n";
	protected static String boiteAuxLettres = "";
	
	///affichage des résultats
	private GTextArea boxMessage = new GTextArea(boiteAuxLettres);

	public WidStab() {

		/// for (int i = 0; i < numColTop; i++) {
		/// colTop[i] = new GLabel("Col "+(i+1));
		/// colTop[i].setConstraint(new GConstraint());
		/// }

		menuFusees.setInnerDescendantConstraint(new GConstraint(GConstraint.spanx(2), GConstraint.growx()), 1);
		
		boxMessage.setConstraint(new GConstraint(GConstraint.skip(3), GConstraint.height(130), GConstraint.width(330), 
				GConstraint.newline(true, 0), GConstraint.span(3, 6)));
		boxMessage.setEditable(false);

		lab1etage.setConstraint(new GConstraint(GConstraint.newline(true, 0), GConstraint.skip(1)));
		lab2etage.setConstraint(null);

		boxMasse.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(3)), 1);
		menuMasses.setConstraint(null);
		boxCentreDeMasse.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(3)), 1);
		menuCentreDeMasses.setConstraint(null);
		boxLongTot.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);

		boxMasse2Etage.setInnerDescendantConstraint(null, 0, 0);
		boxMasse2Etage.setInnerDescendantConstraint(new GConstraint(GConstraint.split(5)), 0, 0);
		boxMasse2Etage.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		menuMasses2Etage.setConstraint(null);
		boxCentreDeMasse2Etage.setInnerDescendantConstraint(null, 0, 0);
		boxCentreDeMasse2Etage.setInnerDescendantConstraint(new GConstraint(GConstraint.split(5)), 0, 0);
		boxCentreDeMasse2Etage
				.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		menuCentreDeMasses2Etage.setConstraint(null);
		boxLong2Etage.setInnerDescendantConstraint(null, 0, 0);
		boxLong2Etage.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxLong2Etage.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);

		menuMoteurs.setInnerDescendantConstraint(new GConstraint(GConstraint.newline(true, 20), GConstraint.split(2)),
				0, 0);
		menuMoteurs.setInnerDescendantConstraint(new GConstraint(GConstraint.growx()), 1);
		boxPositionDuBas.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);

		menuMoteurs2Etage.setConstraint(new GConstraint(GConstraint.growx()));
		boxPositionDuBas2Etage.setInnerDescendantConstraint(null, 0, 0);
		boxPositionDuBas2Etage.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxPositionDuBas2Etage
				.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);

		menuOgive.setInnerDescendantConstraint(new GConstraint(GConstraint.newline(true, 20), GConstraint.split(2)), 0,
				0);
		menuOgive.setInnerDescendantConstraint(new GConstraint(GConstraint.growx()), 1);
		boxHauteurOgive.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxDiametreOgive.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);

		dummyLabel1.setConstraint(new GConstraint(GConstraint.newline(true, 0), GConstraint.skip(1)));
		dummyLabel3.setConstraint(null);

		dummyLabel6.setConstraint(null);
		dummyLabel8.setConstraint(null);

		menuOgive2Etage.setConstraint(new GConstraint(GConstraint.growx()));
		boxHauteurOgive2Etage.setInnerDescendantConstraint(null, 0, 0);
		boxHauteurOgive2Etage.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxHauteurOgive2Etage.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)),
				1);
		boxDiametreOgive2Etage.setInnerDescendantConstraint(null, 0, 0);
		boxDiametreOgive2Etage.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxDiametreOgive2Etage
				.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);

		menuAilerons.setInnerDescendantConstraint(new GConstraint(GConstraint.newline(true, 20), GConstraint.split(2)),
				0, 0);
		menuAilerons.setInnerDescendantConstraint(new GConstraint(GConstraint.spanx(2), GConstraint.growx()), 1);

		labEmpennage1.setConstraint(new GConstraint(GConstraint.newline(true, 0), GConstraint.skip(1)));
		labEmpennage2.setConstraint(null);

		boxEmplanture1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxSaumon1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxFleche1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxEnvergure1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxEpaisseur1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxNombre1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxPositionDuBas1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxDiametre1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);

		boxEmplanture2.setInnerDescendantConstraint(null, 0, 0);
		boxEmplanture2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxEmplanture2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxSaumon2.setInnerDescendantConstraint(null, 0, 0);
		boxSaumon2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxSaumon2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxFleche2.setInnerDescendantConstraint(null, 0, 0);
		boxFleche2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxFleche2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxEnvergure2.setInnerDescendantConstraint(null, 0, 0);
		boxEnvergure2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxEnvergure2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxEpaisseur2.setInnerDescendantConstraint(null, 0, 0);
		boxEpaisseur2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxEpaisseur2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxNombre2.setInnerDescendantConstraint(null, 0, 0);
		boxNombre2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(2)), 0, 1);
		boxNombre2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxPositionDuBas2.setInnerDescendantConstraint(null, 0, 0);
		boxPositionDuBas2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxPositionDuBas2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxDiametre2.setInnerDescendantConstraint(null, 0, 0);
		boxDiametre2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxDiametre2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);

		menuTransition.setInnerDescendantConstraint(
				new GConstraint(GConstraint.newline(true, 20), GConstraint.split(2)), 0, 0);
		menuTransition.setInnerDescendantConstraint(new GConstraint(GConstraint.spanx(2), GConstraint.growx()), 1);
///		sousTitreResultatEtage2.setConstraint(new GConstraint(GConstraint.skip(1)));
///		sousTitreResultatEtage1.setConstraint(
///				new GConstraint(GConstraint.newline(true, 0), GConstraint.skip(4), GConstraint.spany(2)));
		labTransitionA.setConstraint(new GConstraint(GConstraint.newline(true, 0), GConstraint.skip(1)));
		boxLongueurA.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxDiametreD1A.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxDiametreD2A.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);
		boxImplantationXA.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80), GConstraint.split(2)), 1);

		labTransitionB.setConstraint(null);
		boxLongueurB.setInnerDescendantConstraint(null, 0, 0);
		boxLongueurB.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxLongueurB.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxDiametreD1B.setInnerDescendantConstraint(null, 0, 0);
		boxDiametreD1B.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxDiametreD1B.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxDiametreD2B.setInnerDescendantConstraint(null, 0, 0);
		boxDiametreD2B.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxDiametreD2B.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxImplantationXB.setInnerDescendantConstraint(null, 0, 0);
		boxImplantationXB.setInnerDescendantConstraint(new GConstraint(GConstraint.split(4)), 0, 0);
		boxImplantationXB.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);

		niveauDetail.setConstraint(new GConstraint(GConstraint.newline(true, 20), GConstraint.spanx(2)));

		 try {
		 /// appelle différentes séries en fonction de la catégorie (micro, fusex...) sélectionnée
			diagrammeStabilite.addSerie("CnAlphaMin", xCnAlpha, yCnAlphaMin, Color.RED, true);
		 	diagrammeStabilite.addSerie("CnAlphaMax", xCnAlpha, yCnAlphaMax, Color.RED, true);
		 	diagrammeStabilite.addSerie("MSMin", xMSMin, yMS, Color.RED, true);
		 	diagrammeStabilite.addSerie("MSMax", xMSMax, yMS, Color.RED, true);
		 	diagrammeStabilite.addSerie("CoupleMin", xCoupleMin, yCoupleMin, Color.RED, true);
		 	diagrammeStabilite.addSerie("CoupleMax", xCoupleMax, yCoupleMax, Color.RED, true);
		 	diagrammeStabilite.addSerie("Points Fusée Diagramme de stabilité", xFusee, yFusee, Color.BLUE, false);
		 	
		 	diagrammeStabilite.setXZoom(0.0, 8.0);
		 	diagrammeStabilite.setYZoom(false, 0.0, 60.0);
		 } catch (GFreeChartException e) {
			 /// TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 
		 try {
			 /// appel des différentes séries de valeurs pour le tracé de la fusée
			 schemaFusee.addSerie("Ogive 1", xOgive1, yOgive1, Color.MAGENTA, true);
			 schemaFusee.addSerie("Emplanture empennage arrière gauche", Ail_gauche_x_emplanture, Ail_gauche_y_emplanture, Color.GREEN, 
					 true);
			 schemaFusee.addSerie("Saumon empennage arrière gauche", Ail_gauche_x_saumon, Ail_gauche_y_saumon, Color.GREEN, true);
			 schemaFusee.addSerie("BA empennage arrière gauche", Ail_gauche_x_BA, Ail_gauche_y_BA, Color.GREEN, true);
			 schemaFusee.addSerie("BF empennage arrière gauche", Ail_gauche_x_BF, Ail_gauche_y_BF, Color.GREEN, true);
			 schemaFusee.addSerie("Emplanture empennage arrière droite", Ail_droite_x_emplanture, Ail_droite_y_emplanture, Color.GREEN, 
					 true);
			 schemaFusee.addSerie("Saumon empennage arrière droite", Ail_droite_x_saumon, Ail_droite_y_saumon, Color.GREEN, true);
			 schemaFusee.addSerie("BA empennage arrière droite", Ail_droite_x_BA, Ail_droite_y_BA, Color.GREEN, true);
			 schemaFusee.addSerie("BF empennage arrière droite", Ail_droite_x_BF, Ail_droite_y_BF, Color.GREEN, true);
			 
			 schemaFusee.addSerie("Transition 1 gauche", TransitionA_x_gauche, TransitionA_y_gauche, Color.CYAN, true);
			 schemaFusee.addSerie("Transition 1 droite", TransitionA_x_droite, TransitionA_y_droite, Color.CYAN, true);
			 schemaFusee.addSerie("Transition 2 gauche", TransitionB_x_gauche, TransitionB_y_gauche, Color.CYAN, true);
			 schemaFusee.addSerie("Transition 2 droite", TransitionB_x_droite, TransitionB_y_droite, Color.CYAN, true);
			 schemaFusee.addSerie("Tube 1 gauche", xTube1_gauche, yTube1_gauche, Color.BLUE, true);
			 schemaFusee.addSerie("Tube 1 droite", xTube1_droite, yTube1_droite, Color.BLUE, true);
			 schemaFusee.addSerie("Tube 2 gauche", xTube2_gauche, yTube2_gauche, Color.BLUE, true);
			 schemaFusee.addSerie("Tube 2 droite", xTube2_droite, yTube2_droite, Color.BLUE, true);
			 schemaFusee.addSerie("Tube 3 gauche", xTube3_gauche, yTube3_gauche, Color.BLUE, true);
			 schemaFusee.addSerie("Tube 3 droite", xTube3_droite, yTube3_droite, Color.BLUE, true);
			 schemaFusee.addSerie("Culot", xCulot, yCulot, Color.BLUE, true);
			 schemaFusee.addSerie("Centres de gravité", xCdG, yCdG, Color.BLUE, false);
			 schemaFusee.addSerie("Foyer de portance", xFdP, yFdP, Color.RED, false);
			 schemaFusee.addSerie("Moteur 1 gauche", xMoteur_gauche, yMoteur_gauche, Color.PINK, true);
			 schemaFusee.addSerie("Moteur 1 droite", xMoteur_droite, yMoteur_droite, Color.PINK, true);
			 schemaFusee.addSerie("Moteur 1 haut", xMoteur_haut, yMoteur_haut, Color.PINK, true);
			 schemaFusee.addSerie("Moteur 1 bas", xMoteur_bas, yMoteur_bas, Color.PINK, true);
			 
			 schemaFusee.setXZoom(-0.22, 0.22);
			 schemaFusee.setYZoom(false, -1.08, 0.0);
		} catch (GFreeChartException e) {
			/// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		schemaFusee.setConstraint(new GConstraint(GConstraint.width(300), GConstraint.height(600), GConstraint.spany(24)));
		diagrammeStabilite.setConstraint(new GConstraint(GConstraint.width(310), GConstraint.height(200), GConstraint.skip(1), 
				GConstraint.span(3, 16)));
	}

	public void setter() {
		
		IHMPrincipale.fusee1.setTypeFusee(menuFusees.getValue());

		IHMPrincipale.fusee1.setLong(boxLongTot.getValue());

		IHMPrincipale.fusee1.setXCGSansMoteur(boxCentreDeMasse.getValue());

		IHMPrincipale.XMLMoteur1.setFichierMoteur(menuMoteurs.getSelectedItem() + ".xml");
		IHMPrincipale.XMLMoteur1.xmlReaderActive(IHMPrincipale.fusee1);
		IHMPrincipale.fusee1.setNomMoteur(menuMoteurs.getSelectedItem());
		IHMPrincipale.fusee1.setXPropuRef(boxPositionDuBas.getValue());

		IHMPrincipale.fusee1.setLongOgive(boxHauteurOgive.getValue());
		IHMPrincipale.fusee1.setDOgive(boxDiametreOgive.getValue());
		IHMPrincipale.fusee1.setTypeOgive(menuOgive.getValue()); 	/// 0 s'il n'y a pas d'ogive, 
													/// 1 pour parabolique, 
													/// 2 pour ogivale et 
													/// 3 pour conique

		IHMPrincipale.fusee1.setNombreJeuxAil(menuAilerons.getValue()); 	/// 0 par défaut, 
															/// 1 pour mono-empennage, 
															/// 2 pour bi-empennage et 
															/// 3 pour bi-empennage à demi-masqué
		IHMPrincipale.fusee1.setEmplantureAil(boxEmplanture1.getValue());
		IHMPrincipale.fusee1.setEnvergureAil(boxEnvergure1.getValue());
		IHMPrincipale.fusee1.setSaumonAil(boxSaumon1.getValue());
		IHMPrincipale.fusee1.setflecheAil(boxFleche1.getValue());
		IHMPrincipale.fusee1.setNombreAil(boxNombre1.getValue());
		IHMPrincipale.fusee1.setEpAil(boxEpaisseur1.getValue());
		IHMPrincipale.fusee1.setXAil(boxPositionDuBas1.getValue());

		IHMPrincipale.fusee1.setEmplantureCan(boxEmplanture2.getValue());
		IHMPrincipale.fusee1.setEnvergureCan(boxEnvergure2.getValue());
		IHMPrincipale.fusee1.setSaumonCan(boxSaumon2.getValue());
		IHMPrincipale.fusee1.setFlecheCan(boxFleche2.getValue());
		IHMPrincipale.fusee1.setNombreCan(boxNombre2.getValue());
		IHMPrincipale.fusee1.setEpCan(boxEpaisseur2.getValue());
		IHMPrincipale.fusee1.setXCan(boxPositionDuBas2.getValue());

		IHMPrincipale.fusee1.setLA(boxLongueurA.getValue());
		IHMPrincipale.fusee1.setD1A(boxDiametreD1A.getValue());
		IHMPrincipale.fusee1.setD2A(boxDiametreD2A.getValue());
		IHMPrincipale.fusee1.setXA(boxImplantationXA.getValue());

		IHMPrincipale.fusee1.setLB(boxLongueurB.getValue());
		IHMPrincipale.fusee1.setD1B(boxDiametreD1B.getValue());
		IHMPrincipale.fusee1.setD2B(boxDiametreD2B.getValue());
		IHMPrincipale.fusee1.setXB(boxImplantationXB.getValue());

		IHMPrincipale.fusee1.setEtatMoteur(menuCentreDeMasses.getValue()); /// 0 si absent, 
															/// 1 si vide et 
															/// 2 si plein
		if (menuMasses.getSelectedItem().equals("sans propu")) {
			IHMPrincipale.fusee1.setMasseSansMoteur(boxMasse.getValue());
		} else if (menuMasses.getSelectedItem().equals("avec propu vide")) {
			IHMPrincipale.fusee1.setMasseSansMoteur(boxMasse.getValue() - (IHMPrincipale.XMLMoteur1.getMasseaVideMoteur()));
		} else { /// cas où le propulseur est plein
			IHMPrincipale.fusee1.setMasseSansMoteur(boxMasse.getValue() - (IHMPrincipale.XMLMoteur1.getMasseTotaleMoteur()));
		}

		IHMPrincipale.fusee1.setNombreTransitions(menuTransition.getValue()); /// 0 s'il n'y a pas de transition, 
																/// 1 s'il y en a une et
																/// 2 s'il y en a 2

		/// le diamètre de référence pour la stabilité est celui pris au pied de
		/// l'ogive
		IHMPrincipale.fusee1.setDRef(boxDiametreOgive.getValue());

		IHMPrincipale.fusee1.setDAil(boxDiametre1.getValue());
		IHMPrincipale.fusee1.setDCan(boxDiametre2.getValue());
	}

	@Override
	public void display() throws GException {
		setter();
		Stab.calculStab(IHMPrincipale.fusee1);
		affichage();
		generic();
	}

	@Override
	public void generic() throws GException {

		/// for (int i = 0; i < numColTop; i++) { put(colTop[i]); }
		
		put(menuFusees);
		put(schemaFusee);
		
		boxMessage.setText(boiteAuxLettres);
		
		put(boxMessage);

		if (menuFusees.getValue() == 5) {///menuFusees.getSelectedItem().equals("Bi-étage")

			put(lab1etage);
			put(lab2etage);

			put(boxMasse);
			put(menuMasses);
			put(boxMasse2Etage);
			put(menuMasses2Etage);
			put(boxCentreDeMasse);
			put(menuCentreDeMasses);
			put(boxCentreDeMasse2Etage);
			put(menuCentreDeMasses2Etage);
			put(boxLongTot);
			put(boxLong2Etage);

			put(menuMoteurs);
			put(menuMoteurs2Etage);
			if (menuMoteurs.getSelectedItem().equals("pas de moteur")) {
				if (menuMoteurs2Etage.getSelectedItem().equals("pas de moteur")) {
					/// afficher un message d'avertissement 1
					/// afficher un message d'avertissement 2
					put(dummyLabel1);
				} else {
					/// afficher un message d'avertissement 2
					put(dummyLabel2);
					put(dummyLabel3);
					put(boxPositionDuBas2Etage);
				}
			} else {
				if (menuMoteurs2Etage.getSelectedItem().equals("pas de moteur")) {
					/// afficher un message d'avertissement 1
					put(boxPositionDuBas);
				} else {
					put(boxPositionDuBas);
					put(boxPositionDuBas2Etage);
				}
			}

			put(menuOgive);
			put(menuOgive2Etage);
			put(diagrammeStabilite);
			if (menuOgive.getSelectedItem().equals("Pas d'ogive")) {
				if (menuOgive2Etage.getSelectedItem().equals("Pas d'ogive")) {
					/// afficher un message d'avertissement 1
					/// afficher un message d'avertissement 2
					put(dummyLabel4);
				} else {
					/// afficher un message d'avertissement 2
					put(dummyLabel5);
					put(dummyLabel6);
					put(boxHauteurOgive2Etage);
					put(dummyLabel7);
					put(dummyLabel8);
					put(boxDiametreOgive2Etage);
				}
			} else {
				if (menuOgive2Etage.getSelectedItem().equals("Pas d'ogive")) {
					/// afficher un message d'avertissement 1
					put(boxHauteurOgive);
					put(boxDiametreOgive);
				} else {
					put(boxHauteurOgive);
					put(boxHauteurOgive2Etage);
					put(boxDiametreOgive);
					put(boxDiametreOgive2Etage);
				}
			}

		} else { /// cas des fusées mono-étage

			put(boxMasse);
			put(menuMasses);
			put(boxCentreDeMasse);
			put(menuCentreDeMasses);
			put(boxLongTot);

			put(menuMoteurs);
			if (menuMoteurs.getSelectedItem().equals("pas de moteur")) {
				/// afficher un message d'avertissement
			} else {
				put(boxPositionDuBas);
			}

			put(menuOgive);
			put(diagrammeStabilite);
			if (menuOgive.getSelectedItem().equals("Pas d'ogive")) {
				/// afficher un message d'avertissement
			} else {
				put(boxHauteurOgive);
				put(boxDiametreOgive);
			}
		}

		put(menuAilerons);

		if (menuAilerons.getSelectedItem().equals("Mono-empennage")) {
			put(boxEmplanture1);
			put(boxSaumon1);
			put(boxFleche1);
			put(boxEnvergure1);
			put(boxEpaisseur1);
			put(boxNombre1);
			put(boxPositionDuBas1);
			put(boxDiametre1);
		} else if (menuAilerons.getSelectedItem().equals("Bi-empennage")
				|| menuAilerons.getSelectedItem().equals("Bi-empennage 1/2 masqué")) {
			put(labEmpennage1);
			put(labEmpennage2);
			put(boxEmplanture1);
			put(boxEmplanture2);
			put(boxSaumon1);
			put(boxSaumon2);
			put(boxFleche1);
			put(boxFleche2);
			put(boxEnvergure1);
			put(boxEnvergure2);
			put(boxEpaisseur1);
			put(boxEpaisseur2);
			put(boxNombre1);
			put(boxNombre2);
			put(boxPositionDuBas1);
			put(boxPositionDuBas2);
			put(boxDiametre1);
			put(boxDiametre2);
		}

///		put(diagrammeStabilite);
		put(menuTransition);
		if (menuFusees.getValue() == 5) {///menuFusees.getSelectedItem().equals("Bi-étage")
///			put(sousTitreResultatEtage2);
///			put(sousTitreResultatEtage1);
		}

		if (menuTransition.getSelectedItem().equals("Une transition")) {
			put(labTransitionA);
			put(boxLongueurA);
			put(boxDiametreD1A);
			put(boxDiametreD2A);
			put(boxImplantationXA);
		} else if (menuTransition.getSelectedItem().equals("Deux transitions")) {
			put(labTransitionA);
			put(labTransitionB);
			put(boxLongueurA);
			put(boxLongueurB);
			put(boxDiametreD1A);
			put(boxDiametreD1B);
			put(boxDiametreD2A);
			put(boxDiametreD2B);
			put(boxImplantationXA);
			put(boxImplantationXB);
		}

		put(niveauDetail); /// permettra avec des boucles if d'afficher plus de
							/// résultats si voulu par l'utilisateur
	}

	public void affichage() throws GException {
		WidStab.boiteAuxLettres = "";
		boiteAuxLettres = "	Min |	Résultats	| Max"
				+ "\nElancement	| " + Stab.critElancementMin + " |	" + IHMPrincipale.round(IHMPrincipale.fusee1.elancement, 1) + "	| " 
					+ Stab.critElancementMax
				+ "\nPortance	| " + Stab.critCnMin + " |	" + IHMPrincipale.round(IHMPrincipale.fusee1.cn, 1) + "	| " + Stab.critCnMax
				+ "\nMarge Stat.	| " + Stab.critMSMin + " |	" + IHMPrincipale.round(IHMPrincipale.fusee1.msMin, 1) + " | " 
					+ IHMPrincipale.round(IHMPrincipale.fusee1.msMax, 1) + "	| " + Stab.critMSMax
				+ "\nCouple	| " + Stab.critCoupleMin + " |	" + IHMPrincipale.round(IHMPrincipale.fusee1.coupleMin, 1) + " | " 
					+ IHMPrincipale.round(IHMPrincipale.fusee1.coupleMax, 1) + "	| " + Stab.critCoupleMax
				+ "\n \n";
		if (niveauDetail.isSelected()) {
			boiteAuxLettres = boiteAuxLettres + "Foyer de portance : " + IHMPrincipale.round(IHMPrincipale.fusee1.getXCPFusee(), 3) + "mm\n"
					+ "Centre de gravité : " + IHMPrincipale.round(IHMPrincipale.fusee1.getXCGPlein(), 3) + "mm\n";
		}
		Stab.diagnostic(menuFusees.getSelectedItem());
		IHMPrincipale.compute("results/EPHEM");
	}
	
	public void refresh() {
		/**
		 * Actualise l'affichage en fonction des paramètres modifiés dans l'onglet de trajecto
		 */
		if (menuMasses.getSelectedItem().equals("sans propu")) {
			boxMasse.setValue(IHMPrincipale.fusee1.getMasseSansMoteur());
		} else if (menuMasses.getSelectedItem().equals("avec propu vide")) {
			boxMasse.setValue(IHMPrincipale.fusee1.getMasseVide());
		} else {
			boxMasse.setValue(IHMPrincipale.fusee1.getMassePlein());
		}
	}

	@Override
	public void after(GEvent arg0) throws GException {
		/**
		 * Actualise l'affichage des listes de moteurs, des critères de stab, du diagramme de stab
		 * Cette méthode est déclenchée par les événements modifiant la forme de la fusée
		 */
		
		if (arg0.contains(menuFusees)) { /// il est nécessaire de distinguer la procédure ci-dessous sinon les objets GCombox
			 /// ne s'actualisent pas.

			/// Partie pour les menus des moteurs
			/// L'idéal serait de montrer la liste de moteurs dont on a les fichiers RASP
			if(menuFusees.getValue() == 5) {	/// il faudrait trier les moteurs de la même façon... ///menuFusees.getSelectedItem().equals("Bi-étage")
				menuMoteurs.setValueList(1, null, "pas de moteur", "H2O 2.0L 1000g 6bar", "µ-propu C6-3", "Cariacou", 
						"Pro24-3G_75F51", "Pro54 Barasinga");
			} else if (menuFusees.getValue() == 0) {///menuFusees.getSelectedItem().equals("Fusée à eau")
				menuMoteurs.setValueList(1, null, "pas de moteur", "H2O 1.5L 300g 6bar", "H2O 1.5L 450g 6bar", 
						"H2O 1.5L 600g 6bar", "H2O 1.5L 750g 6bar", "H2O 2.0L 400g 6bar", "H2O 2.0L 600g 6bar", 
						"H2O 2.0L 800g 6bar", "H2O 2.0L 1000g 6bar");
			} else if (menuFusees.getValue() == 1) {///menuFusees.getSelectedItem().equals("Microfusée")
				menuMoteurs.setValueList(1, null, "pas de moteur", "µ-propu A8-3", "µ-propu B4-4", "µ-propu C6-3",
						"µ-propu C6-3 x2", "µ-propu C6-3 x3");
			} else if (menuFusees.getValue() == 2) {///menuFusees.getSelectedItem().equals("Rocketry Challenge")
				menuMoteurs.setValueList(1, null, "pas de moteur", "Pro24-1G_24E22", "Pro24-1G_25E75_Rufina_RC", 
						"Pro24-1G_26E31", "Pro29-1G_41F36", "Pro29-1G_51F36", "Pro29-1G_55F29", "Pro29-1G_56F120_RC", 
						"Pro29-1G_57F59", "Pro24-3G_60F50", "Pro24-3G_68F79", "Pro24-3G_68F240", "Pro24-3G_73F30", 
						"Pro24-3G_74F85", "Pro24-3G_75F51");
			} else if (menuFusees.getValue() == 3) {///menuFusees.getSelectedItem().equals("Minifusée")
				menuMoteurs.setValueList(1, null, "pas de moteur", "Cariacou", "Pro24-1G_25E75_Rufina_C'Space", 
						"Pro29-1G_56F120_C'Space", "Pro29-2G_110G250_C'Space");
			} else if (menuFusees.getValue() == 4) {///menuFusees.getSelectedItem().equals("Fusée expérimentale")
				menuMoteurs.setValueList(1, null, "pas de moteur", "Pro54 Barasinga", "Pro75");
			}
		}
		
		if (arg0.contains(menuFusees, boxMasse, menuMasses, boxCentreDeMasse, menuCentreDeMasses, boxLongTot, menuMoteurs, 
				boxPositionDuBas, menuOgive, boxHauteurOgive, boxDiametreOgive, menuAilerons, boxEmplanture1, boxSaumon1,
				boxFleche1, boxEnvergure1, boxNombre1, boxPositionDuBas1, boxDiametre1, boxEmplanture2, boxSaumon2,
				boxFleche2, boxEnvergure2, boxNombre2, boxPositionDuBas2, boxDiametre2, menuTransition, boxLongueurA,
				boxDiametreD1A, boxDiametreD2A, boxImplantationXA, boxLongueurB, boxDiametreD1B, boxDiametreD2B, 
				boxImplantationXB, niveauDetail)) {
			/// il manque les évènements liés à la seconde ogive etc
			
			/// il faut ré-exécuter ces méthodes pour rafraîchir l'objet fusee1
			setter();
			Stab.calculStab(IHMPrincipale.fusee1);
			
			/// On actualise les séries de valeurs à tracer
			/// Ici pour le diagramme de stabilité
			int i = 0;
			while (i < 8) {
				yCnAlphaMax[i] = Stab.getCritCnMax();
				yCnAlphaMin[i] = Stab.getCritCnMin();
				xMSMax[i] = Stab.getCritMSMax();
				xMSMin[i] = Stab.getCritMSMin();
				yCoupleMin[i] = Stab.getCritCoupleMin()/xCoupleMin[i];
				yCoupleMax[i] = Stab.getCritCoupleMax()/xCoupleMax[i];
				i = i+1;	}
			i = 0;
			
			xFusee[0] = IHMPrincipale.fusee1.getMSMin();
			xFusee[1] = IHMPrincipale.fusee1.getMSMax();
			yFusee[0] = IHMPrincipale.fusee1.getPortanceFusee();
			yFusee[1] = IHMPrincipale.fusee1.getPortanceFusee();
			
			/// Et ici pour le tracé de la fusée
			/// ogive
			switch (menuOgive.getSelectedItem()) {
			case "Pas d'ogive":
				xOgive1[0] = -(boxDiametreOgive.getValue())/2;
				xOgive1[1] = 0;
				xOgive1[2] = (boxDiametreOgive.getValue())/2;
				yOgive1[0] = 0;
				yOgive1[1] = 0;
				yOgive1[2] = 0;
				repereYOgive = -boxHauteurOgive.getValue();
			break;
			case "Parabolique (arrondie)":
				///tbd
				xOgive1[0] = -(boxDiametreOgive.getValue())/2;
				xOgive1[1] = 0;
				xOgive1[2] = (boxDiametreOgive.getValue())/2;
				yOgive1[0] = -boxHauteurOgive.getValue();
				yOgive1[1] = 0;
				yOgive1[2] = -boxHauteurOgive.getValue();
				repereYOgive = 0;
			break;
			case "Ogivale (pointue)":
				///tbd
				xOgive1[0] = -(boxDiametreOgive.getValue())/2;
				xOgive1[1] = 0;
				xOgive1[2] = (boxDiametreOgive.getValue())/2;
				yOgive1[0] = -boxHauteurOgive.getValue();
				yOgive1[1] = 0;
				yOgive1[2] = -boxHauteurOgive.getValue();
				repereYOgive = 0;
			break;
			case "Conique (droite)":
				///tbd
				xOgive1[0] = -(boxDiametreOgive.getValue())/2;
				xOgive1[1] = 0;
				xOgive1[2] = (boxDiametreOgive.getValue())/2;
				yOgive1[0] = -boxHauteurOgive.getValue();
				yOgive1[1] = 0;
				yOgive1[2] = -boxHauteurOgive.getValue();
				repereYOgive = 0;
			break;
			}
			/// aileron gauche
			/// rayon au niveau des ailerons
			Ail_gauche_x_emplanture[0] = -boxDiametre1.getValue()/2;
			Ail_gauche_x_emplanture[1] = -boxDiametre1.getValue()/2;
			/// position du bas - emplanture, position du bas
			Ail_gauche_y_emplanture[0] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+repereYOgive);
			Ail_gauche_y_emplanture[1] = -(boxPositionDuBas1.getValue()+repereYOgive);
			/// rayon au niveau des ailerons + semi-envergure
			Ail_gauche_x_saumon[0] = -((boxDiametre1.getValue()/2)+boxEnvergure1.getValue());
			Ail_gauche_x_saumon[1] = -((boxDiametre1.getValue()/2)+boxEnvergure1.getValue());
			///position du bas à la flèche près - saumon, position du bas à la flèche près
			Ail_gauche_y_saumon[0] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+boxFleche1.getValue()+repereYOgive);
			Ail_gauche_y_saumon[1] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+boxFleche1.getValue()+boxSaumon1.getValue()
				+repereYOgive);
			///rayon au niveau des ailerons + semi-envergure, rayon au niveau des ailerons
			Ail_gauche_x_BA[0] = -((boxDiametre1.getValue()/2)+boxEnvergure1.getValue());
			Ail_gauche_x_BA[1] = -boxDiametre1.getValue()/2;
			/// position du bas - saumon à la flèche près
			Ail_gauche_y_BA[0] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+boxFleche1.getValue()+repereYOgive);
			Ail_gauche_y_BA[1] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+repereYOgive);
			///rayon au niveau des ailerons + semi-envergure, rayon au niveau des ailerons
			Ail_gauche_x_BF[0] = -((boxDiametre1.getValue()/2)+boxEnvergure1.getValue());
			Ail_gauche_x_BF[1] = -boxDiametre1.getValue()/2;
			/// position du bas à la flèche près, position du bas
			Ail_gauche_y_BF[0] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+boxFleche1.getValue()+boxSaumon1.getValue()
				+repereYOgive);
			Ail_gauche_y_BF[1] = -(boxPositionDuBas1.getValue()+repereYOgive);
			/// aileron droit
			/// rayon au niveau des ailerons
			Ail_droite_x_emplanture[0] = boxDiametre1.getValue()/2;
			Ail_droite_x_emplanture[1] = boxDiametre1.getValue()/2;
			/// position du bas - emplanture, position du bas
			Ail_droite_y_emplanture[0] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+repereYOgive);
			Ail_droite_y_emplanture[1] = -(boxPositionDuBas1.getValue()+repereYOgive);
			/// rayon au niveau des ailerons + semi-envergure
			Ail_droite_x_saumon[0] = (boxDiametre1.getValue()/2)+boxEnvergure1.getValue();
			Ail_droite_x_saumon[1] = (boxDiametre1.getValue()/2)+boxEnvergure1.getValue();
			/// position du bas - emplanture + flèche, position du bas - emplanture + flèche + saumon
			Ail_droite_y_saumon[0] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+boxFleche1.getValue()+repereYOgive);
			Ail_droite_y_saumon[1] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+boxFleche1.getValue()+boxSaumon1.getValue()
				+repereYOgive);
			/// rayon au niveau des ailerons + semi-envergure, rayon au niveau des ailerons
			Ail_droite_x_BA[0] = (boxDiametre1.getValue()/2)+boxEnvergure1.getValue();
			Ail_droite_x_BA[1] = boxDiametre1.getValue()/2;
			/// position du bas - emplanture + flèche, position du bas - emplanture
			Ail_droite_y_BA[0] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+boxFleche1.getValue()+repereYOgive);
			Ail_droite_y_BA[1] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+repereYOgive);
			/// rayon au niveau des ailerons + semi-envergure, rayon au niveau des ailerons
			Ail_droite_x_BF[0] = (boxDiametre1.getValue()/2)+boxEnvergure1.getValue();
			Ail_droite_x_BF[1] = boxDiametre1.getValue()/2;
			/// position du bas - emplanture + flèche + saumon, position du bas
			Ail_droite_y_BF[0] = -(boxPositionDuBas1.getValue()-boxEmplanture1.getValue()+boxFleche1.getValue()+boxSaumon1.getValue()
				+repereYOgive);
			Ail_droite_y_BF[1] = -(boxPositionDuBas1.getValue()+repereYOgive);
			/// canard gauche
			/// rayon au niveau des canards
			Can_gauche_x_emplanture[0] = -boxDiametre2.getValue()/2;
			Can_gauche_x_emplanture[1] = -boxDiametre2.getValue()/2;
			/// position du bas - emplanture, position du bas
			Can_gauche_y_emplanture[0] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+repereYOgive);
			Can_gauche_y_emplanture[1] = -(boxPositionDuBas2.getValue()+repereYOgive);
			/// rayon au niveau des canards + semi-envergure
			Can_gauche_x_saumon[0] = -((boxDiametre2.getValue()/2)+boxEnvergure2.getValue());
			Can_gauche_x_saumon[1] = -((boxDiametre2.getValue()/2)+boxEnvergure2.getValue());
			/// position du bas à la flèche près, position du bas à la flèche près - saumon
			Can_gauche_y_saumon[0] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+boxFleche2.getValue()+repereYOgive);
			Can_gauche_y_saumon[1] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+boxFleche2.getValue()+boxSaumon2.getValue()
				+repereYOgive);
			///rayon au niveau des canards + semi-envergure, rayon au niveau des canards
			Can_gauche_x_BA[0] = -((boxDiametre2.getValue()/2)+boxEnvergure2.getValue());
			Can_gauche_x_BA[1] = -boxDiametre2.getValue()/2;
			/// position du bas - emplanture + flèche, position du bas - emplanture
			Can_gauche_y_BA[0] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+boxFleche2.getValue()+repereYOgive);
			Can_gauche_y_BA[1] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+repereYOgive);
			///rayon au niveau des canards + semi-envergure, rayon au niveau des canards
			Can_gauche_x_BF[0] = -((boxDiametre2.getValue()/2)+boxEnvergure2.getValue());
			Can_gauche_x_BF[1] = -boxDiametre2.getValue()/2;
			/// position du bas à la flèche près, position du bas
			Can_gauche_y_BF[0] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+boxFleche2.getValue()+boxSaumon2.getValue()
				+repereYOgive);
			Can_gauche_y_BF[1] = -(boxPositionDuBas2.getValue()+repereYOgive);
			/// canard droit
			/// rayon au niveau des canards
			Can_droite_x_emplanture[0] = boxDiametre2.getValue()/2;
			Can_droite_x_emplanture[1] = boxDiametre2.getValue()/2;
			/// position du bas - emplanture, position du bas
			Can_droite_y_emplanture[0] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+repereYOgive);
			Can_droite_y_emplanture[1] = -(boxPositionDuBas2.getValue()+repereYOgive);
			/// rayon au niveau des canards + semi-envergure
			Can_droite_x_saumon[0] = (boxDiametre2.getValue()/2)+boxEnvergure2.getValue();
			Can_droite_x_saumon[1] = (boxDiametre2.getValue()/2)+boxEnvergure2.getValue();
			/// position du bas - emplanture + flèche, position du bas - emplanture + flèche + saumon
			Can_droite_y_saumon[0] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+boxFleche2.getValue()+repereYOgive);
			Can_droite_y_saumon[1] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+boxFleche2.getValue()+boxSaumon2.getValue()
				+repereYOgive);
			/// rayon au niveau des canards + semi-envergure, rayon au niveau des canards
			Can_droite_x_BA[0] = (boxDiametre2.getValue()/2)+boxEnvergure2.getValue();
			Can_droite_x_BA[1] = boxDiametre2.getValue()/2;
			/// position du bas - emplanture + flèche, position du bas - emplanture
			Can_droite_y_BA[0] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+boxFleche2.getValue()+repereYOgive);
			Can_droite_y_BA[1] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+repereYOgive);
			/// rayon au niveau des canards + semi-envergure, rayon au niveau des canards
			Can_droite_x_BF[0] = (boxDiametre2.getValue()/2)+boxEnvergure2.getValue();
			Can_droite_x_BF[1] = boxDiametre2.getValue()/2;
			/// position du bas - emplanture, + flèche + saumon, position du bas
			Can_droite_y_BF[0] = -(boxPositionDuBas2.getValue()-boxEmplanture2.getValue()+boxFleche2.getValue()+boxSaumon2.getValue()
				+repereYOgive);
			Can_droite_y_BF[1] = -(boxPositionDuBas2.getValue()+repereYOgive);
			
			/// transition A
			///D2A/2, D1A/2
			TransitionA_x_gauche[0] = -boxDiametreD2A.getValue()/2;
			TransitionA_x_gauche[1] = -boxDiametreD1A.getValue()/2;
			///implantation + longueur de la transition, implantation
			TransitionA_y_gauche[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
			TransitionA_y_gauche[1] = -(boxImplantationXA.getValue()+repereYOgive);
			/// D2A/2, D1A/2
			TransitionA_x_droite[0] = boxDiametreD2A.getValue()/2;
			TransitionA_x_droite[1] = boxDiametreD1A.getValue()/2;
			/// implantation + longueur de la transition, implantation
			TransitionA_y_droite[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
			TransitionA_y_droite[1] = -(boxImplantationXA.getValue()+repereYOgive);
			/// transition B
			///D2B/2, D1B/2
			TransitionB_x_gauche[0] = -boxDiametreD2B.getValue()/2;
			TransitionB_x_gauche[1] = -boxDiametreD1B.getValue()/2;
			///implantation + longueur de la transition, implantation
			TransitionB_y_gauche[0] = -(boxImplantationXB.getValue()+boxLongueurB.getValue()+repereYOgive);
			TransitionB_y_gauche[1] = -(boxImplantationXB.getValue()+repereYOgive);
			/// D2B/2, D1B/2
			TransitionB_x_droite[0] = boxDiametreD2B.getValue()/2;
			TransitionB_x_droite[1] = boxDiametreD1B.getValue()/2;
			/// implantation + longueur de la transition, implantation
			TransitionB_y_droite[0] = -(boxImplantationXB.getValue()+boxLongueurB.getValue()+repereYOgive);
			TransitionB_y_droite[1] = -(boxImplantationXB.getValue()+repereYOgive);
			
			/// Premier tube et culot
			/// N.B. : peu robuste à l'absence d'ogive (il faudrait prendre le diamètre de référence)
			switch (menuTransition.getSelectedItem()) {
			case "Pas de transition":
				xCulot[0] = -boxDiametreOgive.getValue()/2;
				xCulot[1] = boxDiametreOgive.getValue()/2;
				xTube1_gauche[0] = -(boxDiametreOgive.getValue())/2;
				xTube1_gauche[1] = -(boxDiametreOgive.getValue())/2;
				yTube1_gauche[0] = -(boxHauteurOgive.getValue()+repereYOgive);
				yTube1_gauche[1] = -(boxLongTot.getValue()+repereYOgive);
				xTube1_droite[0] = (boxDiametreOgive.getValue())/2;
				xTube1_droite[1] = (boxDiametreOgive.getValue())/2;
				yTube1_droite[0] = -(boxHauteurOgive.getValue()+repereYOgive);
				yTube1_droite[1] = -(boxLongTot.getValue()+repereYOgive);
			break;
			case "Une transition":
				xCulot[0] = -boxDiametreD2A.getValue()/2;
				xCulot[1] = boxDiametreD2A.getValue()/2;
				xTube1_gauche[0] = -(boxDiametreOgive.getValue())/2;
				xTube1_gauche[1] = -(boxDiametreOgive.getValue())/2;
				yTube1_gauche[0] = -(boxHauteurOgive.getValue()+repereYOgive);
				yTube1_gauche[1] = -(boxImplantationXA.getValue()+repereYOgive);
				xTube1_droite[0] = (boxDiametreOgive.getValue())/2;
				xTube1_droite[1] = (boxDiametreOgive.getValue())/2;
				yTube1_droite[0] = -(boxHauteurOgive.getValue()+repereYOgive);
				yTube1_droite[1] = -(boxImplantationXA.getValue()+repereYOgive);
				xTube2_gauche[0] = -(boxDiametreD2A.getValue())/2;
				xTube2_gauche[1] = -(boxDiametreD2A.getValue())/2;
				yTube2_gauche[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
				yTube2_gauche[1] = -(boxLongTot.getValue()+repereYOgive);
				xTube2_droite[0] = (boxDiametreD2A.getValue())/2;
				xTube2_droite[1] = (boxDiametreD2A.getValue())/2;
				yTube2_droite[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
				yTube2_droite[1] = -(boxLongTot.getValue()+repereYOgive);
			break;
			case "Deux transitions":
				if (boxImplantationXA.getValue() < boxImplantationXB.getValue()) {
					xCulot[0] = -boxDiametreD2B.getValue()/2;
					xCulot[1] = boxDiametreD2B.getValue()/2;
					xTube1_gauche[0] = -(boxDiametreOgive.getValue())/2;
					xTube1_gauche[1] = -(boxDiametreOgive.getValue())/2;
					yTube1_gauche[0] = -(boxHauteurOgive.getValue()+repereYOgive);
					yTube1_gauche[1] = -(boxImplantationXA.getValue()+repereYOgive);
					xTube1_droite[0] = (boxDiametreOgive.getValue())/2;
					xTube1_droite[1] = (boxDiametreOgive.getValue())/2;
					yTube1_droite[0] = -(boxHauteurOgive.getValue()+repereYOgive);
					yTube1_droite[1] = -(boxImplantationXA.getValue()+repereYOgive);
					xTube2_gauche[0] = -(boxDiametreD2A.getValue())/2;
					xTube2_gauche[1] = -(boxDiametreD2A.getValue())/2;
					yTube2_gauche[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
					yTube2_gauche[1] = -(boxImplantationXB.getValue()+repereYOgive);
					xTube2_droite[0] = (boxDiametreD2A.getValue())/2;
					xTube2_droite[1] = (boxDiametreD2A.getValue())/2;
					yTube2_droite[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
					yTube2_droite[1] = -(boxImplantationXB.getValue()+repereYOgive);
					xTube3_gauche[0] = -(boxDiametreD2B.getValue())/2;
					xTube3_gauche[1] = -(boxDiametreD2B.getValue())/2;
					yTube3_gauche[0] = -(boxImplantationXB.getValue()+boxLongueurB.getValue()+repereYOgive);
					yTube3_gauche[1] = -(boxLongTot.getValue()+repereYOgive);
					xTube3_droite[0] = (boxDiametreD2B.getValue())/2;
					xTube3_droite[1] = (boxDiametreD2B.getValue())/2;
					yTube3_droite[0] = -(boxImplantationXB.getValue()+boxLongueurB.getValue()+repereYOgive);
					yTube3_droite[1] = -(boxLongTot.getValue()+repereYOgive);
				} else if (boxImplantationXA.getValue() > boxImplantationXB.getValue()) {
					xCulot[0] = -boxDiametreD2A.getValue()/2;
					xCulot[1] = boxDiametreD2A.getValue()/2;
					xTube1_gauche[0] = -(boxDiametreOgive.getValue())/2;
					xTube1_gauche[1] = -(boxDiametreOgive.getValue())/2;
					yTube1_gauche[0] = -(boxHauteurOgive.getValue()+repereYOgive);
					yTube1_gauche[1] = -(boxImplantationXB.getValue()+repereYOgive);
					xTube1_droite[0] = (boxDiametreOgive.getValue())/2;
					xTube1_droite[1] = (boxDiametreOgive.getValue())/2;
					yTube1_droite[0] = -(boxHauteurOgive.getValue()+repereYOgive);
					yTube1_droite[1] = -(boxImplantationXB.getValue()+repereYOgive);
					xTube2_gauche[0] = -(boxDiametreD2B.getValue())/2;
					xTube2_gauche[1] = -(boxDiametreD2B.getValue())/2;
					yTube2_gauche[0] = -(boxImplantationXB.getValue()+boxLongueurB.getValue()+repereYOgive);
					yTube2_gauche[1] = -(boxImplantationXA.getValue()+repereYOgive);
					xTube2_droite[0] = (boxDiametreD2B.getValue())/2;
					xTube2_droite[1] = (boxDiametreD2B.getValue())/2;
					yTube2_droite[0] = -(boxImplantationXB.getValue()+boxLongueurB.getValue()+repereYOgive);
					yTube2_droite[1] = -(boxImplantationXA.getValue()+repereYOgive);
					xTube3_gauche[0] = -(boxDiametreD2A.getValue())/2;
					xTube3_gauche[1] = -(boxDiametreD2A.getValue())/2;
					yTube3_gauche[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
					yTube3_gauche[1] = -(boxLongTot.getValue()+repereYOgive);
					xTube3_droite[0] = (boxDiametreD2A.getValue())/2;
					xTube3_droite[1] = (boxDiametreD2A.getValue())/2;
					yTube3_droite[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
					yTube3_droite[1] = -(boxLongTot.getValue()+repereYOgive);
				} else {
					xCulot[0] = -boxDiametreD2A.getValue()/2;
					xCulot[1] = boxDiametreD2A.getValue()/2;
					xTube1_gauche[0] = -(boxDiametreOgive.getValue())/2;
					xTube1_gauche[1] = -(boxDiametreOgive.getValue())/2;
					yTube1_gauche[0] = -(boxHauteurOgive.getValue()+repereYOgive);
					yTube1_gauche[1] = -(boxImplantationXA.getValue()+repereYOgive);
					xTube1_droite[0] = (boxDiametreOgive.getValue())/2;
					xTube1_droite[1] = (boxDiametreOgive.getValue())/2;
					yTube1_droite[0] = -(boxHauteurOgive.getValue()+repereYOgive);
					yTube1_droite[1] = -(boxImplantationXA.getValue()+repereYOgive);
					xTube2_gauche[0] = -(boxDiametreD2A.getValue())/2;
					xTube2_gauche[1] = -(boxDiametreD2A.getValue())/2;
					yTube2_gauche[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
					yTube2_gauche[1] = -(boxLongTot.getValue()+repereYOgive);
					xTube2_droite[0] = (boxDiametreD2A.getValue())/2;
					xTube2_droite[1] = (boxDiametreD2A.getValue())/2;
					yTube2_droite[0] = -(boxImplantationXA.getValue()+boxLongueurA.getValue()+repereYOgive);
					yTube2_droite[1] = -(boxLongTot.getValue()+repereYOgive);
					System.out.println("Seule la transition A est prise en compte !");
				}
			break;
			}
			yCulot[0] = -(boxLongTot.getValue()+repereYOgive);
			yCulot[1] = -(boxLongTot.getValue()+repereYOgive);
			/// moteur
			xMoteur_gauche[0] = -IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2;
			xMoteur_gauche[1] = -IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2;
			yMoteur_gauche[0] = -boxPositionDuBas.getValue()+IHMPrincipale.XMLMoteur1.getLongueurMoteur()-repereYOgive;
			yMoteur_gauche[1] = -(boxPositionDuBas.getValue()+repereYOgive);
			xMoteur_droite[0] = IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2;
			xMoteur_droite[1] = IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2;
			yMoteur_droite[0] = -boxPositionDuBas.getValue()+IHMPrincipale.XMLMoteur1.getLongueurMoteur()-repereYOgive;
			yMoteur_droite[1] = -(boxPositionDuBas.getValue()+repereYOgive);
			xMoteur_haut[0] = -IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2;
			xMoteur_haut[1] = IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2;
			yMoteur_haut[0] = -boxPositionDuBas.getValue()+IHMPrincipale.XMLMoteur1.getLongueurMoteur()-repereYOgive;
			yMoteur_haut[1] = -boxPositionDuBas.getValue()+IHMPrincipale.XMLMoteur1.getLongueurMoteur()-repereYOgive;
			xMoteur_bas[0] = -IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2;
			xMoteur_bas[1] = IHMPrincipale.XMLMoteur1.getDiametreMoteur()/2;
			yMoteur_bas[0] = -boxPositionDuBas.getValue()-repereYOgive;
			yMoteur_bas[1] = -boxPositionDuBas.getValue()-repereYOgive;
			/// centres de masses
			yCdG[0] = -(IHMPrincipale.fusee1.getXCGVide()+repereYOgive);
			yCdG[1] = -(IHMPrincipale.fusee1.getXCGPlein()+repereYOgive);
			/// foyer de portance
			yFdP[0] = -(IHMPrincipale.fusee1.getXCPFusee()+repereYOgive);
			yFdP[1] = -(IHMPrincipale.fusee1.getXCPFusee()+repereYOgive);
			/// logique pour déterminer les plages de variation en x et y
			/// (il manque les aspects liés à un deuxième étage (deuxième ogive, etc))
			LAMax = boxImplantationXA.getValue() + boxLongueurA.getValue() + repereYOgive;
			LBMax = boxImplantationXB.getValue() + boxLongueurB.getValue() + repereYOgive;
			LtotFleche = Math.max(boxPositionDuBas1.getValue() + boxFleche1.getValue(), 
					boxPositionDuBas2.getValue() + boxFleche2.getValue()) + repereYOgive;
			YMin = -Math.max(boxLongTot.getValue(), Math.max(boxCentreDeMasse.getValue(), Math.max(boxPositionDuBas.getValue(), 
					Math.max(boxHauteurOgive.getValue(), Math.max(boxEmplanture1.getValue(), Math.max(boxPositionDuBas1.getValue(),
					Math.max(LAMax, Math.max(LBMax, Math.max(boxPositionDuBas2.getValue(), LtotFleche)))))))));
			XMax = Math.max(boxDiametreOgive.getValue()/2, Math.max(boxEnvergure1.getValue()+(boxDiametre1.getValue()/2), 
					Math.max(boxDiametreD1A.getValue()/2, Math.max(boxDiametreD2A.getValue()/2, Math.max(boxDiametreD1B.getValue()/2, 
					Math.max(boxDiametreD2B.getValue()/2, Math.max(boxDiametre1.getValue()/2, boxDiametre2.getValue())))))));
			XM = -YMin/4.91;
			if (XMax < XM) {
				XMax = XM;
			}
			XMin = -XMax;
				
			try {
				/// Il faut d'abord retirer toutes les séries des deux objets GFreeChart
				diagrammeStabilite.removeAllSeries();
				schemaFusee.removeAllSeries();
				
				/// Enfin on rajoute les séries actualisées dans les objets GFreeChart
				/// ici pour le diagramme de stabilité
				diagrammeStabilite.addSerie("CnAlphaMin", xCnAlpha, yCnAlphaMin, Color.RED, true);
			 	diagrammeStabilite.addSerie("CnAlphaMax", xCnAlpha, yCnAlphaMax, Color.RED, true);
			 	diagrammeStabilite.addSerie("MSMin", xMSMin, yMS, Color.RED, true);
			 	diagrammeStabilite.addSerie("MSMax", xMSMax, yMS, Color.RED, true);
			 	diagrammeStabilite.addSerie("CoupleMin", xCoupleMin, yCoupleMin, Color.RED, true);
			 	diagrammeStabilite.addSerie("CoupleMax", xCoupleMax, yCoupleMax, Color.RED, true);
				diagrammeStabilite.addSerie("Points Fusée Diagramme de stabilité", xFusee, yFusee, Color.BLUE, false);
				/// et ici pour le tracé de la fusée
				if (menuOgive.getSelectedItem().equals("Parabolique (arrondie)") || menuOgive.getSelectedItem().equals("Ogivale (pointue)")
						|| menuOgive.getSelectedItem().equals("Conique (droite)")) {
					/// N.B.: il manque la gestion des différents profils d'ogives
					schemaFusee.addSerie("Ogive 1", xOgive1, yOgive1, Color.MAGENTA, true);
				}
				if (menuAilerons.getSelectedItem().equals("Mono-empennage")) {
					schemaFusee.addSerie("Emplanture empennage arrière gauche", Ail_gauche_x_emplanture, Ail_gauche_y_emplanture, 
							Color.GREEN, true);
					schemaFusee.addSerie("Saumon empennage arrière gauche", Ail_gauche_x_saumon, Ail_gauche_y_saumon, Color.GREEN, true);
					schemaFusee.addSerie("BA empennage arrière gauche", Ail_gauche_x_BA, Ail_gauche_y_BA, Color.GREEN, true);
					schemaFusee.addSerie("BF empennage arrière gauche", Ail_gauche_x_BF, Ail_gauche_y_BF, Color.GREEN, true);
					schemaFusee.addSerie("Emplanture empennage arrière droite", Ail_droite_x_emplanture, Ail_droite_y_emplanture, 
							Color.GREEN, true);
					schemaFusee.addSerie("Saumon empennage arrière droite", Ail_droite_x_saumon, Ail_droite_y_saumon, Color.GREEN, true);
					schemaFusee.addSerie("BA empennage arrière droite", Ail_droite_x_BA, Ail_droite_y_BA, Color.GREEN, true);
					schemaFusee.addSerie("BF empennage arrière droite", Ail_droite_x_BF, Ail_droite_y_BF, Color.GREEN, true);
				} else if ((menuAilerons.getSelectedItem().equals("Bi-empennage")) 
						|| (menuAilerons.getSelectedItem().equals("Bi-empennage 1/2 masqué"))) {
					schemaFusee.addSerie("Emplanture empennage arrière gauche", Ail_gauche_x_emplanture, Ail_gauche_y_emplanture, 
							Color.GREEN, true);
					schemaFusee.addSerie("Saumon empennage arrière gauche", Ail_gauche_x_saumon, Ail_gauche_y_saumon, Color.GREEN, true);
					schemaFusee.addSerie("BA empennage arrière gauche", Ail_gauche_x_BA, Ail_gauche_y_BA, Color.GREEN, true);
					schemaFusee.addSerie("BF empennage arrière gauche", Ail_gauche_x_BF, Ail_gauche_y_BF, Color.GREEN, true);
					schemaFusee.addSerie("Emplanture empennage arrière droite", Ail_droite_x_emplanture, Ail_droite_y_emplanture, 
							Color.GREEN, true);
					schemaFusee.addSerie("Saumon empennage arrière droite", Ail_droite_x_saumon, Ail_droite_y_saumon, Color.GREEN, true);
					schemaFusee.addSerie("BA empennage arrière droite", Ail_droite_x_BA, Ail_droite_y_BA, Color.GREEN, true);
					schemaFusee.addSerie("BF empennage arrière droite", Ail_droite_x_BF, Ail_droite_y_BF, Color.GREEN, true);
					schemaFusee.addSerie("Canards, emplanture empennage arrière gauche", Can_gauche_x_emplanture, Can_gauche_y_emplanture, 
							 Color.GREEN, true);
					 schemaFusee.addSerie("Canard, saumon empennage arrière gauche", Can_gauche_x_saumon, Can_gauche_y_saumon, 
							 Color.GREEN, true);
					 schemaFusee.addSerie("Canard, BA empennage arrière gauche", Can_gauche_x_BA, Can_gauche_y_BA, Color.GREEN, true);
					 schemaFusee.addSerie("Canard, BF empennage arrière gauche", Can_gauche_x_BF, Can_gauche_y_BF, Color.GREEN, true);
					 schemaFusee.addSerie("Canard, emplanture empennage arrière droite", Can_droite_x_emplanture, 
							 Can_droite_y_emplanture, Color.GREEN, true);
					 schemaFusee.addSerie("Canard, saumon empennage arrière droite", Can_droite_x_saumon, Can_droite_y_saumon, 
							 Color.GREEN, true);
					 schemaFusee.addSerie("Canard, BA empennage arrière droite", Can_droite_x_BA, Can_droite_y_BA, Color.GREEN, true);
					 schemaFusee.addSerie("Canards, BF empennage arrière droite", Can_droite_x_BF, Can_droite_y_BF, Color.GREEN, true);
				}
				schemaFusee.addSerie("Tube 1 gauche", xTube1_gauche, yTube1_gauche, Color.BLUE, true);
				schemaFusee.addSerie("Tube 1 droite", xTube1_droite, yTube1_droite, Color.BLUE, true);
				if (menuTransition.getSelectedItem().equals("Une transition")) {
					schemaFusee.addSerie("Transition 1 gauche", TransitionA_x_gauche, TransitionA_y_gauche, Color.CYAN, true);
					schemaFusee.addSerie("Transition 1 droite", TransitionA_x_droite, TransitionA_y_droite, Color.CYAN, true);
					schemaFusee.addSerie("Tube 2 gauche", xTube2_gauche, yTube2_gauche, Color.BLUE, true);
					schemaFusee.addSerie("Tube 2 droite", xTube2_droite, yTube2_droite, Color.BLUE, true);
				} else if (menuTransition.getSelectedItem().equals("Deux transitions")) {
					schemaFusee.addSerie("Transition 1 gauche", TransitionA_x_gauche, TransitionA_y_gauche, Color.CYAN, true);
					schemaFusee.addSerie("Transition 1 droite", TransitionA_x_droite, TransitionA_y_droite, Color.CYAN, true);
					schemaFusee.addSerie("Transition 2 gauche", TransitionB_x_gauche, TransitionB_y_gauche, Color.CYAN, true);
					schemaFusee.addSerie("Transition 2 droite", TransitionB_x_droite, TransitionB_y_droite, Color.CYAN, true);
					schemaFusee.addSerie("Tube 2 gauche", xTube2_gauche, yTube2_gauche, Color.BLUE, true);
					schemaFusee.addSerie("Tube 2 droite", xTube2_droite, yTube2_droite, Color.BLUE, true);
					schemaFusee.addSerie("Tube 3 gauche", xTube3_gauche, yTube3_gauche, Color.BLUE, true);
					schemaFusee.addSerie("Tube 3 droite", xTube3_droite, yTube3_droite, Color.BLUE, true);
				}
				schemaFusee.addSerie("Culot", xCulot, yCulot, Color.BLUE, true);
				schemaFusee.addSerie("Centres de gravité", xCdG, yCdG, Color.BLUE, false);
				schemaFusee.addSerie("Foyer de portance", xFdP, yFdP, Color.RED, false);
				schemaFusee.addSerie("Moteur 1 gauche", xMoteur_gauche, yMoteur_gauche, Color.PINK, true);
				schemaFusee.addSerie("Moteur 1 droite", xMoteur_droite, yMoteur_droite, Color.PINK, true);
				schemaFusee.addSerie("Moteur 1 haut", xMoteur_haut, yMoteur_haut, Color.PINK, true);
				schemaFusee.addSerie("Moteur 1 bas", xMoteur_bas, yMoteur_bas, Color.PINK, true);
				
				schemaFusee.setXZoom(XMin, XMax);
				schemaFusee.setYZoom(false, YMin, 0.0);
			} catch (GFreeChartException e) {
				/// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void before(GEvent arg0) throws GException {
		/// Rien à faire ...
	}

	public void read() throws GException {
		generic();
	}

	public void write() throws GException {
		generic();
	}

}