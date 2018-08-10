package packageIHM;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//      				 TRAJEC JUNIOR						   //
//						CNES/DLA/SDT/SPC				  	   //
//					classe calculs de trajectographie	 	   //
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

public class Traj {
	
	// ~~~~ Phénomènes physiques ~~~~
	private static double g0 = 9.80665;			//intensité de la pesanteur (m/s²)//atmosphère
	private static double rhoMoyen = 1.225;		//masse volumique de l'air (en kg/m3)
	private static double T = 298;				//température de l'atmosphère, 25°C (en K)
	// ~~~~ Caractéristiques moteurs ~~~~
	private static double iInst[] = new double [1000];	//impulsion totale instantanée (en N.s)
	private static double iTotale = 0;			//impulsion totale (en N.s)
	private static double isp = 0;				//impulsion spécifique (en s)
	// ~~~~ Descente parachutée	~~~~
	private static double tParaMin = 4;			//instant de déclenchement du premier parachute
	// ~~~~ Variables utilitaires ~~~~
	private static double pasDeCalcul = 0.01;	//pas de calcul de la trajecto (en s)
	private static double dtLongFeu = 100;		//durée à partir de laquelle on considère que la fusée ne décollera pas (10s)
	// ~~~~ tableaux de calcul ~~~~
	private static double mach[] = new double [100000];	//nombre de Mach
	private static double trajectoire[][] = new double [100000][20];//tableaux pour stocker les valeurs du calcul pas à pas

//~~~~~~~~~~~~~~~~	Méthode principale ~~~~~~~~~~~~~~~~//
	
public static void calculTraj(Fusee pFusee) {
	//initialisation du calcul pas à pas de la trajectoire
	//	pFusee.trajectoire[0][0] = 0;						//  0 : pas (en s)
	trajectoire[0][1] = pFusee.tIni;						//  1 : t (en s)
	trajectoire[0][2] = pFusee.accelX0;						//  2 : acc_x (en m/s²)
	trajectoire[0][3] = pFusee.accelZ0;						//  3 : acc_z (en m/s²)
	trajectoire[0][4] = pFusee.accelXZ0;					//  4 : acc_xz (en m/s²)
	trajectoire[0][5] = pFusee.vitesseX0;					//  5 : vit_x (en m/s)
	trajectoire[0][6] = pFusee.vitesseZ0;					//  6 : vit_z (en m/s)
	trajectoire[0][7] = pFusee.vitesseXZ0;					//  7 : vit_xz (en m/s)
	trajectoire[0][8] = pFusee.positionX0;					//  8 : pos_x (en m)
	trajectoire[0][9] = pFusee.positionZ0+pFusee.altRampe;	//  9 : pos_z (en m)
	trajectoire[0][10] = pFusee.positionXZ0;				// 10 : pos_xz (en m)
	trajectoire[0][11] = pFusee.betaRampe;					// 11 : Beta (en rad)
	trajectoire[0][12] = trajectoire[0][11];				// 12 : BetaD (en rad)
	trajectoire[0][13] = pFusee.poussee0;					// 13 : Poussee (en N)
	trajectoire[0][14] = pFusee.debit0;						// 14 : Debit (en kg/s)
	trajectoire[0][15] = pFusee.massePlein;					// 15 : masse (en kg)
	trajectoire[0][16] = trajectoire[0][15]*g0;				// 16 : Poids (en N)
	trajectoire[0][17] = reactionRampe(pFusee.lRampe, trajectoire[0][16], trajectoire[0][11], trajectoire[0][10]);	// 17 : R_rampe (en N)
	trajectoire[0][18] = rhoAtmosphere(pFusee, trajectoire[0][9]);	// 18 : Rho (en kg/m3)
	trajectoire[0][19] = traineeAerodynamique(pFusee, trajectoire[0][18], trajectoire[0][7]);	// 19 : Trainee (en N)
	
	pFusee.setSRefTrainee(sRef(pFusee));
	tParaMin = Math.min(pFusee.tPara1, pFusee.tPara2);
	
	//~~~~~~~~~~~~~~~~	Calculs	~~~~~~~~~~~~~~~~//
	//si l'altitude dépasse -0.0001m on arrête le calcul (impact balistique)
	for (int t=1; (trajectoire[t-1][9] >= 0) && (trajectoire[t][9] > -0.0001); t++) {
	//  0 : pas
//		trajectoire[t][0] = Pas_de_calcul;		
		//cette colonne du tableau ne sert à rien en l'état, mais pourrait servir à faire varier le pas de calcul
		//pour étudier certaines phases plus finement
	//  1 : temps
		trajectoire[t][1] = trajectoire[t-1][1]+pasDeCalcul;
		IHMPrincipale.fusee1.xTemps[t] = trajectoire[t][1];	//assignation dans le tableau permettant le tracé des courbes
	// 13 : Poussée
		trajectoire[t][13] = IHMPrincipale.XMLMoteur1.getLoiDePoussee(t);
//		System.out.println("Poussée à " + trajectoire[t][1] + "s " + trajectoire[t][13]);
	// 14 : Debit
		trajectoire[t][14] = trajectoire[t][13]/(g0*isp(pFusee));
		//System.out.println("Débit à " + trajectoire[t][1] + "s " + trajectoire[t][14]);
	// 15 : masse de la fusée
		trajectoire[t][15] = trajectoire[t-1][15]-trajectoire[t][14]*pasDeCalcul;
	// 16 : Poids
		trajectoire[t][16] = trajectoire[t][15]*g0;
		//System.out.println("Poids à " + trajectoire[t][1] + "s " + trajectoire[t][16]);
	// 2 : acc_x
		if ((trajectoire[t-1][10] < 0.001) && (trajectoire[t][13] <= (trajectoire[t][16]*Math.sin(trajectoire[0][11])))) {
			trajectoire[t][2] = 0;
		} else {
			trajectoire[t][2] = ((-trajectoire[t-1][19]+trajectoire[t][13])
					*Math.cos(trajectoire[t-1][11])-trajectoire[t-1][17]*Math.sin(trajectoire[t-1][11]))/trajectoire[t][15];
		}
	// 3 : acc_z
		if ((trajectoire[t-1][10] < 0.001) && (trajectoire[t][13] <= (trajectoire[t][16]*Math.sin(trajectoire[0][11])))) {
			trajectoire[t][3] = 0;
		} else {
			trajectoire[t][3] = ((-trajectoire[t-1][19]+trajectoire[t][13])*Math.sin(trajectoire[t-1][11])+trajectoire[t-1][17]
					*Math.cos(trajectoire[t-1][11])-trajectoire[t][16])/trajectoire[t][15];
		}
	// 4 : acc_xz
		trajectoire[t][4] = Math.pow(Math.pow(trajectoire[t][2], 2)+Math.pow(trajectoire[t][3], 2), 0.5);
	// 5 : vit_x
		trajectoire[t][5] = trajectoire[t-1][5]+trajectoire[t][2]*pasDeCalcul;
	// 6 : vit_z
		trajectoire[t][6] = trajectoire[t-1][6]+trajectoire[t][3]*pasDeCalcul;
	// 7 : vit_xz
		trajectoire[t][7] = Math.pow(Math.pow(trajectoire[t][5], 2)+Math.pow(trajectoire[t][6], 2), 0.5);
		mach[t] = trajectoire[t][7]/(Math.pow(T*401.8, 0.5));		//M = V/Racine(gamma*r*T) avec gamma*r = 1.4*287 = 401.8
	// 8 : pos_x
		trajectoire[t][8] = trajectoire[t-1][8]+0.5*(trajectoire[t-1][5]+trajectoire[t][5])*pasDeCalcul;
		pFusee.xPortee[t] = trajectoire[t][8]; //assignation dans le tableau permettant le tracé des courbes
	// 9 : pos_z
		trajectoire[t][9] = trajectoire[t-1][9]+0.5*(trajectoire[t-1][6]+trajectoire[t][6])*pasDeCalcul;
		pFusee.zAltitude[t] = trajectoire[t][9]; //assignation dans le tableau permettant le tracé des courbes
	// 10 : pos_xz
		trajectoire[t][10] = Math.pow(Math.pow(trajectoire[t][8], 2)+Math.pow(trajectoire[t][9], 2), 0.5);
		if ((t > dtLongFeu) && (trajectoire[t][10] == 0)) {	// si au bout de 10 secondes la fusée n'a pas bougé on arrête le calcul
			System.out.println("Un pétard n'effraie pas la montagne !");
			break;
		}
	// 11 : Beta
		if (trajectoire[t][10] > pFusee.lRampe && trajectoire[t][5] > 0) {
			trajectoire[t][11]= Math.atan(trajectoire[t][6]/trajectoire[t][5]);
		} else {
			trajectoire[t][11] = trajectoire[0][11];
		}
	// 12 : BetaD
		trajectoire[t][12] = trajectoire[t][11];
	// 17 : Réaction rampe
		trajectoire[t][17] = reactionRampe(pFusee.lRampe, trajectoire[t][16], trajectoire[t][11], trajectoire[t][10]);
	// 18 : Rho
		trajectoire[t][18] = rhoAtmosphere(pFusee, trajectoire[t][9]);
	// 19 : Trainee
		trajectoire[t][19] = traineeAerodynamique(pFusee, trajectoire[t][18], trajectoire[t][7]);
		
	//_____	EVENEMENTS DU VOL	_____//
		
		// FIN DE POUSSEE
		if ((trajectoire[t][13] < 0.01) && (trajectoire[t-1][13] > 0)) {
			pFusee.tempsFinProp = IHMPrincipale.round(trajectoire[t][1], 3);	//la date de fin de poussée est l'instant t
		}
		
		// CULMINATION
		if (trajectoire[t][9] >= trajectoire[t-1][9]) {
			pFusee.altitudeCulmination = trajectoire[t][9];		//alors l'altitude de culmination est à l'instant t,
			pFusee.tempsCulmination = IHMPrincipale.round(trajectoire[t][1], 3);//la date de culmination est l'instant t,
			pFusee.vitesseCulmination = trajectoire[t][7];		//la vitesse de culmination est celle de l'instant t
		}
		
		// VITESSE MAX
		if (trajectoire[t][7] >= pFusee.vitesseMax) {	//si la vitesse à l'instant t est > à la vitesse à l'instant t-1
			pFusee.vitesseMax = trajectoire[t][7];		//alors c'est la vitesse max
			pFusee.machMax = mach[t];
			pFusee.dateVitesseMaximale = IHMPrincipale.round(trajectoire[t][1], 3);
		}
		
		// ACCELERATION MAX
		if (trajectoire[t][4] >= pFusee.accelerationMaximale) {
			pFusee.accelerationMaximale = trajectoire[t][4];
			pFusee.dateAccelerationMaximale = IHMPrincipale.round(trajectoire[t][1], 3);
		}
		
		// SORTIE RAMPE
		if ((trajectoire[t-1][10] <= pFusee.lRampe) && (trajectoire[t][10] >= pFusee.lRampe)) {
			pFusee.vitesseSortieRampe = IHMPrincipale.round(0.5*(trajectoire[t][7]+trajectoire[t-1][7]), 3);
		}
		
		// PORTEE BALISTIQUE
//		if ((pFusee.trajectoire[t][9] <= 0) && (pFusee.trajectoire[t-1][9] >= 0)) {		//portée balistique
		pFusee.porteeBalistique = trajectoire[t][8];
		pFusee.tBalistique = IHMPrincipale.round(trajectoire[t][1], 3);
//		}
		
		// DECLENCHEMENT RECUPERATION
		if ((trajectoire[t][1] >= tParaMin-0.009) && (trajectoire[t][1] <= tParaMin+0.009)) {
			pFusee.altPara = trajectoire[t][9];		//altitude déclenchement récupération
			pFusee.x0Para = trajectoire[t][8];		//portée déclenchement récupération
			pFusee.massePara = trajectoire[t][15];	//masse de la fusée au déclenchement récupération
			pFusee.xPorteePara[0] = pFusee.x0Para;	//portée atteinte au déclenchement récupération
			pFusee.zAltitudePara[0] = pFusee.altPara;//altitude déclenchement récupération
			pFusee.xTempsPara[0] = tParaMin;		//instant de déclenchement récupération
			descenteParachutee(pFusee, trajectoire[t][15]);
		} else {
			pFusee.setDureeVol(pFusee.tBalistique);
		}
	}
}

private static double sRef(Fusee pFusee) {	//surface de référence pour le calcul de la traînée (en m²)
	
	double ep = 0;
	double dMaitreCouple = 0;
	double envMaitreCoupleAil = 0;
	double envMaitreCoupleCan = 0;
	double sFrontAil = 0;
	
	// choix envergure, épaisseur de l'aileron...
	if (pFusee.nombreTransitions == 0) {
		dMaitreCouple = pFusee.dOgive;
	} else if (pFusee.nombreTransitions == 1) {
		dMaitreCouple = Math.max(pFusee.dOgive, Math.max(pFusee.d1A, pFusee.d2A));
	} else {
		dMaitreCouple = Math.max(pFusee.dOgive, Math.max(pFusee.d1A, Math.max(pFusee.d2A, Math.max(pFusee.d1B, pFusee.d2B))));
	}
	
	switch (pFusee.nombreJeuxAil) {
	case 1:
		dMaitreCouple = Math.max(dMaitreCouple, pFusee.dAil);
		ep = pFusee.epAil;
		if (pFusee.dAil >= dMaitreCouple) {
			envMaitreCoupleAil = pFusee.envergureAil;
		} else {
			envMaitreCoupleAil = pFusee.envergureAil-(dMaitreCouple-pFusee.dAil);
		}
		sFrontAil = envMaitreCoupleAil*ep*pFusee.nombreAil;
	break;
	case 2:
		dMaitreCouple = Math.max(dMaitreCouple, Math.max(pFusee.dAil, pFusee.dCan));
		switch (pFusee.alignement) {
		case 1:
			if (pFusee.nombreCan != pFusee.nombreAil) {
				System.out.println("Les ailerons ne peuvent être superposés");
			}
			if (pFusee.nombreJeuxAil >= 1 && pFusee.dCan >= dMaitreCouple) {
				envMaitreCoupleCan = pFusee.envergureCan;
			} else {
				envMaitreCoupleCan = pFusee.envergureCan-(dMaitreCouple-pFusee.dCan);
			}
			if (pFusee.dAil >= dMaitreCouple) {
				envMaitreCoupleAil = pFusee.envergureAil;
			} else {
				envMaitreCoupleAil = pFusee.envergureAil-(dMaitreCouple-pFusee.dAil);
			}
			double S_front_emp_haut = envMaitreCoupleCan*pFusee.epCan*pFusee.nombreCan;
			double S_front_emp_bas = envMaitreCoupleAil*pFusee.epAil*pFusee.nombreAil;
			sFrontAil = Math.max(S_front_emp_haut, S_front_emp_bas);
		break;
		case 2:
			if (pFusee.dAil >= dMaitreCouple) {
				envMaitreCoupleAil = pFusee.envergureAil;
			} else {
				envMaitreCoupleAil = pFusee.envergureAil-(dMaitreCouple-pFusee.dAil);
			}
			if (pFusee.dCan >= dMaitreCouple) {
				envMaitreCoupleCan = pFusee.envergureCan;
			} else {
				envMaitreCoupleCan = pFusee.envergureCan-(dMaitreCouple-pFusee.dCan);
			}
			sFrontAil = (envMaitreCoupleAil*pFusee.epAil*pFusee.nombreAil) + (envMaitreCoupleCan*pFusee.epCan*pFusee.nombreCan);
		break;
		}
	}
	return ((Math.PI*Math.pow(dMaitreCouple,2)/4)+sFrontAil);
}

private static void calculSPara(Fusee pFusee) {	// surface du ou des parachute(s)
	if (pFusee.nombreParachutes == 1) {
		switch (pFusee.choixPara1) {
		case 0:
			pFusee.surfacePara1 = (pFusee.dim1Para1*pFusee.dim1Para1)+(4*pFusee.dim1Para1*pFusee.dim2Para1);
			break;
		case 1:
			pFusee.surfacePara1 = Math.PI*((pFusee.dim1Para1*pFusee.dim1Para1)-(pFusee.dim2Para1*pFusee.dim2Para1));
			//la dim2 permet de remplir le diamètre de la cheminée
			break;
		case 2:
			// utilise la surface para déclarée dans l'IHM
			pFusee.surfacePara1 = pFusee.surfacePara1;
		}
	} else if (pFusee.nombreParachutes == 2) {
		switch (pFusee.choixPara1) {
		case 0:
			pFusee.surfacePara1 = (pFusee.dim1Para1*pFusee.dim1Para1)+(4*pFusee.dim1Para1*pFusee.dim2Para1);
			break;
		case 1:
			pFusee.surfacePara1 = Math.PI*((pFusee.dim1Para1*pFusee.dim1Para1)-(pFusee.dim2Para1*pFusee.dim2Para1));
			//la dim2 permet de remplir le diamètre de la cheminée
			break;
		case 2:
			// utilise la surface para déclarée dans l'IHM
			pFusee.surfacePara1 = pFusee.surfacePara1;
		}
		switch (pFusee.choixPara2) {
		case 0:
			pFusee.surfacePara2 = (pFusee.dim1Para2*pFusee.dim1Para2)+(4*pFusee.dim1Para2*pFusee.dim2Para2);
			break;
		case 1:
			pFusee.surfacePara2 = Math.PI*((pFusee.dim1Para2*pFusee.dim1Para2)-(pFusee.dim2Para2*pFusee.dim2Para2));
			//la dim2 permet de remplir le diamètre de la cheminée
			break;
		case 2:
			// utilise la surface para déclarée dans l'IHM
			pFusee.surfacePara2 = pFusee.surfacePara2;
		}
	}
}

private static double calculVPara(double pMasseFusee, double pSurfacePara, double pCxPara) {
	return Math.pow(((2*g0*pMasseFusee)/(rhoMoyen*pSurfacePara*pCxPara)),0.5);
}

private static double calculVPara(double pMasseFusee, double pSurfacePara1, double pCxPara1, double pSurfacePara2, double pCxPara2) {
	return Math.pow(((2*g0*pMasseFusee)/(rhoMoyen*(pSurfacePara1*pCxPara1+pSurfacePara2*pCxPara2))),0.5);
}

private static double isp(Fusee pFusee) {	//impulsion spécifique (en s)
	for (int i = 1; i < 999; i++) {
		iInst[i] = ((IHMPrincipale.XMLMoteur1.getLoiDePoussee(i) + IHMPrincipale.XMLMoteur1.getLoiDePoussee(i-1))*pasDeCalcul)/2;
		iTotale = iTotale + iInst[i];
	}
	isp = (iTotale/g0)/(IHMPrincipale.XMLMoteur1.getMassePropergolMoteur());
	//réinitialisation
	for (int a = 0; a < 999; a++) {
		iInst[a] = 0;
	}
	iTotale = 0;
	return isp;
}

private static double reactionRampe(double pLRampe, double pPoidsFusee, double pInclinaisonRampe, double pPositionXZ) {
	double reactionRampe;
	if (pPositionXZ > pLRampe) {
		reactionRampe = 0;
	} else {
		reactionRampe = pPoidsFusee*Math.cos(pInclinaisonRampe);
	}
	return reactionRampe;
}

private static double rhoAtmosphere(Fusee pFusee, double pAltitude) {
	return rhoMoyen*(20000-pFusee.altRampe-pAltitude)/(20000+pFusee.altRampe+pAltitude);
}

private static double traineeAerodynamique(Fusee pFusee, double pRhoAtmosphere, double pVitesseXZ) {
	return 0.5*pRhoAtmosphere*sRef(pFusee)*pFusee.cx*Math.pow(pVitesseXZ,2);
}

private static void descenteParachutee(Fusee pFusee, double pMasseFusee) {
	calculSPara(pFusee);
	pFusee.vPara1 = calculVPara(pMasseFusee, pFusee.surfacePara1, pFusee.cxPara1);
	pFusee.vPara2 = calculVPara(pMasseFusee, pFusee.surfacePara2, pFusee.cxPara2);
	pFusee.vPara = calculVPara(pMasseFusee, pFusee.surfacePara1, pFusee.cxPara1, pFusee.surfacePara2, pFusee.cxPara2);
	//durées des phases sous parachutes
	pFusee.dVol1 = pFusee.tPara1 - pFusee.tPara2;
	pFusee.dVol2 = pFusee.tPara2 - pFusee.tPara1;
	if (pFusee.dVol1 < 0) {
		pFusee.dVol1 = 0;
	} else if (pFusee.dVol2 < 0) {
		pFusee.dVol2 = 0;
	}
	//altitude de déclenchement de l'autre parachute
	pFusee.altParaBis = pFusee.altPara-(pFusee.dVol2*pFusee.vPara2)-(pFusee.dVol1*pFusee.vPara1);
	//durée de la phase sous les deux parachutes déployés
	pFusee.dVolPara = pFusee.altParaBis/pFusee.vPara;
	//durée totale de la descente sous parachute(s)
	pFusee.dureeVolSousPara = pFusee.dVolPara+pFusee.dVol1+pFusee.dVol2;
	//durée totale du vol
	pFusee.dureeVol = pFusee.dureeVolSousPara+tParaMin;
	//déport latéral sous parachute (dû au vent)
	pFusee.deportLateral = pFusee.vitVentPara*pFusee.dureeVolSousPara;
	pFusee.xPorteePara[1] = pFusee.xPorteePara[0]+pFusee.deportLateral;//portée du point d'impact sous parachute
	pFusee.zAltitudePara[1] = 0;	//altitude du point d'impact sous parachute
	pFusee.xTempsPara[1] = pFusee.dureeVol;
}

public static void nettoyeur(Fusee pFusee) {
	/**
	 * Ré-initialise les tableaux
	 */
	int i = 0;
	for (i = 0; i < 100000; i++) {
		//pFusee.trajectoire[i][0] = 0;
		trajectoire[i][1] = 0;
		trajectoire[i][2] = 0;
		trajectoire[i][3] = 0;
		trajectoire[i][4] = 0;
		trajectoire[i][5] = 0;
		trajectoire[i][6] = 0;
		trajectoire[i][7] = 0;
		trajectoire[i][8] = 0;
		trajectoire[i][9] = 0;
		trajectoire[i][10] = 0;
		trajectoire[i][11] = 0;
		trajectoire[i][12] = 0;
		trajectoire[i][13] = 0;
		trajectoire[i][14] = 0;
		trajectoire[i][15] = 0;
		trajectoire[i][16] = 0;
		trajectoire[i][17] = 0;
		trajectoire[i][18] = 0;
		trajectoire[i][19] = 0;
		pFusee.xPortee[i] = 0;
		pFusee.zAltitude[i] = 0;
	}
	pFusee.xPorteePara[0] = 0;
	pFusee.zAltitudePara[0] = 0;
	pFusee.xPorteePara[1] = 0;
	pFusee.zAltitudePara[1] = 0;
}

//************* Mutateurs *************


//************* Accesseurs *************
public static double getPasDeCalcul()  {  
	return pasDeCalcul;	}

}