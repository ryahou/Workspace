package packageIHM;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//						TRAJEC JUNIOR						   //
//						CNES/DLA/SDT/SPC				  	   //
//					classe calculs de stabilité			 	   //
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

public class Stab {
	
	// ~~~~ Critères de stabilité ~~~~
	protected static double critElancementMin = 0;
	protected static double critElancementMax = 0;
	protected static double critCnMin = 0;
	protected static double critCnMax = 0;
	protected static double critMSMin = 0;
	protected static double critMSMax = 0;
	protected static double critCoupleMin = 0;
	protected static double critCoupleMax = 0;
	// Critères fusées à eau
	private static double critElancementMinFusEau = 1;
	private static double critElancementMaxFusEau = 100;
	private static double critCnMinFusEau = 15;
	private static double critCnMaxFusEau = 30;
	private static double critMSMinFusEau = 1;
	private static double critMSMaxFusEau = 3;
	private static double critCoupleMinFusEau = 15;
	private static double critCoupleMaxFusEau = 90;
	// Critères micro fusées
	private static double critElancementMinMicroFu = 10;
	private static double critElancementMaxMicroFu = 30;
	private static double critCnMinMicroFu = 15;
	private static double critCnMaxMicroFu = 30;
	private static double critMSMinMicroFu = 1;
	private static double critMSMaxMicroFu = 3;
	private static double critCoupleMinMicroFu = 15;
	private static double critCoupleMaxMicroFu = 100;
	// Critères mini fusées et Rocketry Challenge
	private static double critElancementMinMiniF = 10;
	private static double critElancementMaxMiniF = 20;
	public static double critCnMinMiniF = 15;
	public static double critCnMaxMiniF = 30;
	public static double critMSMinMiniF = 1.5;
	public static double critMSMaxMiniF = 6;
	private static double critCoupleMinMiniF = 30;
	private static double critCoupleMaxMiniF = 100;
	// Critères fusées expérimentales
	private static double critElancementMinFusEx = 10;
	private static double critElancementMaxFusEx = 35;
	private static double critCnMinFusEx = 15;
	private static double critCnMaxFusEx = 40;
	private static double critMSMinFusEx = 2;
	private static double critMSMaxFusEx = 6;
	private static double critCoupleMinFusEx = 40;
	private static double critCoupleMaxFusEx = 100;
	
//~~~~~~~~~~~~~~~~	Méthode principale ~~~~~~~~~~~~~~~~//
	
public static void calculStab(Fusee pFusee) {
	testNbreAilerons(pFusee);
	logiqueDiamètres(pFusee);
	gradientsDePortance(pFusee);
	centresDeGravité(pFusee);
	foyersDePortance(pFusee);
	criteresDeStabilite(pFusee);
}
	
//~~~~~~~~~~~~~~~~	Calculs	~~~~~~~~~~~~~~~~//

	public static void gradientsDePortance(Fusee pFusee)	{
	
	//_____	CALCULS DES GRADIENTS DE PORTANCE	_____
		
	//Calcul de la portance des ailerons
		pFusee.dRef = pFusee.dOgive; // le plus souvent
	if (pFusee.nombreJeuxAil == 0) {
		pFusee.cnAil = 0;
		pFusee.cnCan = 0;
	} else if (pFusee.nombreJeuxAil == 1) {
		pFusee.cnAil = diederich(pFusee.flecheAil, pFusee.saumonAil,pFusee.emplantureAil, pFusee.envergureAil, pFusee.nombreAil, pFusee.dOgive, pFusee.dAil);
		pFusee.cnCan = 0;
		pFusee.cnMasq = 0;
	} else if (pFusee.nombreJeuxAil == 2) {
		pFusee.cnAil = diederich(pFusee.flecheAil, pFusee.saumonAil, pFusee.emplantureAil, pFusee.envergureAil, pFusee.nombreAil, pFusee.dOgive, pFusee.dAil);
		pFusee.cnCan = diederich(pFusee.flecheCan, pFusee.saumonCan, pFusee.emplantureCan, pFusee.envergureCan, pFusee.nombreCan, pFusee.dOgive, pFusee.dCan);
	} else {
		pFusee.cnAil = diederich(pFusee.flecheAil, pFusee.saumonAil, pFusee.emplantureAil, pFusee.envergureAil, pFusee.nombreAil, pFusee.dOgive, pFusee.dAil);
		pFusee.cnCan = diederich(pFusee.flecheCan, pFusee.saumonCan, pFusee.emplantureCan, pFusee.envergureCan, pFusee.nombreCan, pFusee.dOgive, pFusee.dCan);
		//Calcul de la portance des ailerons du bas en 1/2 masqué
		if ((pFusee.dCan/2)+pFusee.envergureCan <= pFusee.dAil/2) {
			pFusee.envergureMasq = 0;
		} else if ((pFusee.dCan/2)+pFusee.envergureCan >= (pFusee.dAil/2)+pFusee.envergureAil) {
			pFusee.envergureMasq = pFusee.envergureAil;
		} else {
			pFusee.envergureMasq = (pFusee.dCan/2)+pFusee.envergureCan-(pFusee.dAil/2);
		}
		pFusee.saumonMasq = pFusee.saumonAil+(pFusee.emplantureAil-pFusee.saumonAil)*(1-(pFusee.envergureMasq/pFusee.envergureAil));
		pFusee.flecheMasq = pFusee.flecheAil*pFusee.envergureMasq/pFusee.envergureAil;
		//il faut vérifier qu'il y ait bien le même nombre d'ailerons
		if (pFusee.nombreCan != pFusee.nombreAil){
			System.out.println("Les deux jeux d'ailerons ne sont pas superposés");
			pFusee.nombreMasq = 0;			//ceci annulera l'effet des ailerons du haut sur ceux du bas
		} else {
			pFusee.nombreMasq = pFusee.nombreAil;
		}
		pFusee.xMasq = pFusee.xAil;
		//l'emplanture masquée est forcément celle des ailerons du bas
		pFusee.cnMasq = diederich(pFusee.flecheMasq, pFusee.saumonMasq, pFusee.emplantureAil, pFusee.envergureMasq, pFusee.nombreMasq, pFusee.dOgive, pFusee.dAil);
	}

	pFusee.cnAi = pFusee.cnAil-pFusee.cnMasq/2;
		
	//Calcul de la portance de l'ogive
	if (pFusee.typeOgive == 0) {	//Pas d'ogive
		pFusee.cnO = 0;
	} else {
		pFusee.cnO = 2*Math.pow(pFusee.dOgive/pFusee.dRef,2);
	}
	
	//Calcul de la portance des transitions
	switch (pFusee.nombreTransitions) {
	case 0:
		pFusee.cnA = 0;
		pFusee.cnB = 0;
	break;
	case 1:
		pFusee.cnA = 2*(Math.pow(pFusee.d2A/pFusee.dOgive,2)-Math.pow(pFusee.d1A/pFusee.dOgive,2));
		pFusee.cnB = 0;
	break;
	case 2:
		pFusee.cnA = 2*(Math.pow(pFusee.d2A/pFusee.dOgive,2)-Math.pow(pFusee.d1A/pFusee.dOgive,2));
		pFusee.cnB = 2*(Math.pow(pFusee.d2B/pFusee.dOgive,2)-Math.pow(pFusee.d1B/pFusee.dOgive,2));
	break;
	}
	 
	//Calcul de la portance globale de la fusée
	if(pFusee.nombreJeuxAil != 3){
		pFusee.cn = pFusee.cnAil+pFusee.cnCan+pFusee.cnO+pFusee.cnA+pFusee.cnB;
	} else {
		pFusee.cn = pFusee.cnAi+pFusee.cnCan+pFusee.cnO+pFusee.cnA+pFusee.cnB;
	}

	}

	public static void centresDeGravité(Fusee pFusee)	{
			
		//_____	CALCULS DES CENTRES DE GRAVITE	_____//
		
		pFusee.masseVide = pFusee.masseSansMoteur+IHMPrincipale.XMLMoteur1.getMasseaVideMoteur();
		pFusee.massePlein = pFusee.masseSansMoteur+IHMPrincipale.XMLMoteur1.getMasseTotaleMoteur();
		
		if (pFusee.moteur.equals("Cariacou")) {
			pFusee.xPropuVide = 55/1000;
			pFusee.xPropuPlein = 50/1000;
		} else if (pFusee.moteur.equals("Barasinga")) {
			pFusee.xPropuVide = 240/1000;
			pFusee.xPropuPlein = 250/1000;
		} else {
			pFusee.xPropuVide = IHMPrincipale.XMLMoteur1.getLongueurMoteur()/2.0;
			pFusee.xPropuPlein = pFusee.xPropuVide;
		}
		
		if (pFusee.etatMoteur == 0) { // cas sans moteur
			pFusee.xCGVide = (pFusee.xCG*pFusee.masseSansMoteur+((pFusee.xPropuRef-IHMPrincipale.XMLMoteur1.getLongueurMoteur()+pFusee.xPropuVide)*IHMPrincipale.XMLMoteur1.getMasseaVideMoteur()))/pFusee.masseVide;
			pFusee.xCGPlein = (pFusee.xCG*pFusee.masseSansMoteur+((pFusee.xPropuRef-IHMPrincipale.XMLMoteur1.getLongueurMoteur()+pFusee.xPropuPlein)*IHMPrincipale.XMLMoteur1.getMasseTotaleMoteur()))/pFusee.massePlein;
		} else if (pFusee.etatMoteur == 1) { // cas avec moteur vide
			// idéalement il faudrait pouvoir disposer du centre de gravité du pain de poudre
			// arbitrairement on a pris la valeur moyenne des cdg du moteur plein et du moteur vide
			pFusee.xCGVide = pFusee.xCG;
			pFusee.xCGPlein = (pFusee.xCGVide*pFusee.masseVide+((pFusee.xPropuRef-IHMPrincipale.XMLMoteur1.getLongueurMoteur()+((pFusee.xPropuVide+pFusee.xPropuPlein)/2)*IHMPrincipale.XMLMoteur1.getMassePropergolMoteur())))/pFusee.massePlein;
		} else if (pFusee.etatMoteur == 2) { // cas avec moteur plein
			// idéalement il faudrait pouvoir disposer du centre de gravité du pain de poudre
			// arbitrairement on a pris la valeur moyenne des cdg du moteur plein et du moteur vide
			pFusee.xCGPlein = pFusee.xCG;
			pFusee.xCGVide = (pFusee.xCGPlein*pFusee.massePlein-((pFusee.xPropuRef-IHMPrincipale.XMLMoteur1.getLongueurMoteur()+((pFusee.xPropuVide+pFusee.xPropuPlein)/2)*IHMPrincipale.XMLMoteur1.getMassePropergolMoteur())))/pFusee.masseVide;
		}

	}
	
	public static void foyersDePortance(Fusee pFusee) {
			
		//_____	CALCULS DES FOYERS DE PORTANCE	_____			
		
		//foyer de portance des ailerons du haut
		pFusee.xCPCan = pFusee.xCan-pFusee.emplantureCan+pFusee.flecheCan*(pFusee.emplantureCan+2*pFusee.saumonCan)/(3*(pFusee.emplantureCan+pFusee.saumonCan))+(pFusee.emplantureCan+pFusee.saumonCan-pFusee.emplantureCan*pFusee.saumonCan/(pFusee.emplantureCan+pFusee.saumonCan))/6;
		
		//foyer de portance de la partie masquée des ailerons du bas
		if(pFusee.nombreJeuxAil != 3){
			pFusee.xCPMasq = 0;
		} else {
			// l'emplanture masquée est forcément égale à celle des ailerons du bas
			pFusee.xCPMasq = pFusee.xMasq-pFusee.emplantureAil+pFusee.flecheMasq*(pFusee.emplantureAil+2*pFusee.saumonMasq)/(3*(pFusee.emplantureAil+pFusee.saumonMasq))+(pFusee.emplantureAil+pFusee.saumonMasq-pFusee.emplantureAil*pFusee.saumonMasq/(pFusee.emplantureAil+pFusee.saumonMasq))/6;
		}
			
		//foyer de portance des ailerons du bas
		pFusee.xCPAil = pFusee.xAil-pFusee.emplantureAil+pFusee.flecheAil*(pFusee.emplantureAil+2*pFusee.saumonAil)/(3*(pFusee.emplantureAil+pFusee.saumonAil))+(pFusee.emplantureAil+pFusee.saumonAil-pFusee.emplantureAil*pFusee.saumonAil/(pFusee.emplantureAil+pFusee.saumonAil))/6;
		
		//foyer de portance global des ailerons masqués
		pFusee.xCPTot = (pFusee.xCPAil*pFusee.cnAil-0.5*pFusee.xCPMasq*pFusee.cnMasq)/pFusee.cnAi;
			
		//foyer de portance de la jupe
		if (pFusee.d1A == pFusee.d2A || pFusee.d2A == 0 || pFusee.nombreTransitions == 0) {
			pFusee.xCPTransitionA = 0;
		} else {
			pFusee.xCPTransitionA = pFusee.xA+pFusee.lA/3*(1+1/(1+pFusee.d1A/pFusee.d2A));
		}
			
		//foyer de portance du rétreint
		if (pFusee.d1B == pFusee.d2B || pFusee.d2B == 0 || pFusee.nombreTransitions != 2) {
			pFusee.xCPTransitionB=0;
		} else {
			pFusee.xCPTransitionB = pFusee.xB+pFusee.lB/3*(1+1/(1+pFusee.d1B/pFusee.d2B));
		}
			
		//foyer de portance de l'ogive
		if (pFusee.typeOgive == 0) {		//Pas d'ogive
			pFusee.xCPOgive = 0;
		} else if (pFusee.typeOgive == 1) {	//Parabolique
			pFusee.xCPOgive = 1.0/2.0*pFusee.longOgive;
		} else if (pFusee.typeOgive == 2) {	//Ogivale
			pFusee.xCPOgive = 7.0/15.0*pFusee.longOgive;
		} else if (pFusee.typeOgive == 3) {	//Conique
			pFusee.xCPOgive = 2.0/3.0*pFusee.longOgive;
		}
			
		//foyer de portance global de la fusée
		if (pFusee.nombreJeuxAil != 3) {
			pFusee.xCPFusee = (pFusee.cnAil*pFusee.xCPAil+pFusee.cnCan*pFusee.xCPCan+pFusee.cnA*pFusee.xCPTransitionA+pFusee.cnB*pFusee.xCPTransitionB+pFusee.cnO*pFusee.xCPOgive)/pFusee.cn;
		} else {
			pFusee.xCPFusee = (pFusee.cnAi*pFusee.xCPTot+pFusee.cnCan*pFusee.xCPCan+pFusee.cnA*pFusee.xCPTransitionA+pFusee.cnB*pFusee.xCPTransitionB+pFusee.cnO*pFusee.xCPOgive)/pFusee.cn;
		}
			
	}
	
	public static void criteresDeStabilite (Fusee pFusee) {
		//_____	CALCULS DES CRITERES DE STABILITE	_____
		pFusee.msMin = (pFusee.xCPFusee-pFusee.xCGPlein)/pFusee.dRef;
		pFusee.msMax = (pFusee.xCPFusee-pFusee.xCGVide)/pFusee.dRef;
		pFusee.elancement = pFusee.longTot/pFusee.dRef;
		pFusee.coupleMin = pFusee.msMin*pFusee.cn;
		pFusee.coupleMax = pFusee.msMax*pFusee.cn;
	}
		
	// METHODE D'ATTRIBUTION DES DIAMETRES
	public static void logiqueDiamètres(Fusee pFusee) {
		// si les ailerons sont sur l'ogive, une transition ou confondus avec les ailerons du bas, erreur de saisie
		switch (pFusee.nombreTransitions) {
		case 0:
			if (pFusee.xAil-pFusee.emplantureAil < pFusee.longOgive) {
				System.out.println("Erreur de saisie utilisateur, le jeu d'aileron du bas n'est pas bien placé");
			}
			if ((pFusee.xCan-pFusee.emplantureCan < pFusee.longOgive) && (pFusee.nombreJeuxAil == 2)) {
				System.out.println("Erreur de saisie utilisateur, le jeu d'aileron du haut n'est pas bien placé");
			}
			pFusee.dAil = pFusee.dOgive;
			pFusee.dCan = pFusee.dOgive;
		break;
		case 1:
			if ((pFusee.xAil-pFusee.emplantureAil < pFusee.longOgive) || ((pFusee.xAil < pFusee.xA+pFusee.lA) && (pFusee.xAil+pFusee.emplantureAil > pFusee.xA))) {
				System.out.println("Erreur de saisie utilisateur, le jeu d'aileron du bas n'est pas bien placé");
			} else if (pFusee.xAil < pFusee.xA) {
				pFusee.dAil = pFusee.dOgive;
			} else if (pFusee.xAil-pFusee.emplantureAil > pFusee.xA+pFusee.lA) {
				pFusee.dAil = pFusee.d2A;
			}
			if ((pFusee.xCan-pFusee.emplantureCan < pFusee.longOgive) || ((pFusee.xCan < pFusee.xA+pFusee.lA) && (pFusee.xCan+pFusee.emplantureCan > pFusee.xA)) && (pFusee.nombreJeuxAil == 2)) {
				System.out.println("Erreur de saisie utilisateur, le jeu d'aileron du bas n'est pas bien placé");
			} else if (pFusee.xCan < pFusee.xA) {
				pFusee.dCan = pFusee.dOgive;
			} else if (pFusee.xCan-pFusee.emplantureCan > pFusee.xA+pFusee.lA) {
				pFusee.dCan = pFusee.d2A;
			}
		break;
		case 2:
			if (pFusee.xAil-pFusee.emplantureAil <= pFusee.longOgive) {
				System.out.println("Erreur de saisie utilisateur, le jeu d'ailerons du bas n'est pas bien placé");
				pFusee.dAil = pFusee.dOgive;
			} else if (((pFusee.xAil <= pFusee.xA+pFusee.lA) && (pFusee.xAil >= pFusee.xA)) || ((pFusee.xAil <= pFusee.xB+pFusee.lB) && (pFusee.xAil >= pFusee.xB))) {
				System.out.println("Erreur de saisie utilisateur, le jeu d'ailerons du bas n'est pas bien placé");
				pFusee.dAil = pFusee.dOgive;
			} else if (pFusee.xAil <= pFusee.xA) {
				pFusee.dAil = pFusee.dOgive;
			} else if (pFusee.xAil <= pFusee.xB) {
				pFusee.dAil = pFusee.d2A;
			} else {
				pFusee.dAil = pFusee.d2B;
			}
			if ((pFusee.xCan-pFusee.emplantureCan <= pFusee.longOgive) && (pFusee.nombreJeuxAil == 2)) {
				System.out.println("Erreur de saisie utilisateur, le jeu d'ailerons du haut n'est pas bien placé");
				pFusee.dCan = pFusee.dOgive;
			} else if (((pFusee.xCan <= pFusee.xA+pFusee.lA) && (pFusee.xCan >= pFusee.xA)) || ((pFusee.xCan <= pFusee.xB+pFusee.lB) && (pFusee.xCan >= pFusee.xB)) && (pFusee.nombreJeuxAil == 2)) {
				System.out.println("Erreur de saisie utilisateur, le jeu d'ailerons du haut n'est pas bien placé");
				pFusee.dCan = pFusee.dOgive;
			} else if ((pFusee.xCan >= pFusee.xAil-pFusee.emplantureAil) && (pFusee.xCan <= pFusee.xAil) && (pFusee.nombreJeuxAil == 2)) {
				System.out.println("Erreur de saisie utilisateur, le jeu d'ailerons du haut n'est pas bien placé");
				pFusee.dCan = pFusee.dAil;
			} else if (pFusee.xCan <= pFusee.xA && (pFusee.nombreJeuxAil == 2)) {
				pFusee.dCan = pFusee.dOgive;
			} else if (pFusee.xCan <= pFusee.xB && (pFusee.nombreJeuxAil == 2)) {
				pFusee.dCan = pFusee.d2A;
			} else {
				pFusee.dCan = pFusee.d2B;
			}
		break;	
		}
	}
		
	// METHODE PROTEGEANT LE NOMBRE D'AILERONS
	public static void testNbreAilerons(Fusee pFusee) {
		if ((pFusee.nombreAil != 3) && (pFusee.nombreAil != 4) && (pFusee.nombreAil != 6)) {
			System.out.println("Le jeu d'ailerons du bas a un nombre d'ailerons incorrect !");
		}
		if ((pFusee.nombreJeuxAil == 2) && (pFusee.nombreCan != 3) && (pFusee.nombreCan != 4) && (pFusee.nombreCan != 6)) {
			System.out.println("Le jeu d'ailerons du haut a un nombre d'ailerons incorrect !");
		}
	}
	
	/**
	 * méthode estimant le gradient de portance (formule de Diederich)
	 * @param p : flèche
	 * @param n : saumon
	 * @param m : emplanture
	 * @param E : semi-envergure
	 * @param Q : nombre d'ailerons (3, 4 ou 6)
	 * @param Dref : diamètre de référence
	 * @param Dcalc : diamètre aux ailerons
	 * @return
	 */
	public static double diederich(double p, double n, double m, double E, double Q, double Dref, double Dcalc) {
		double f = Math.pow(Math.pow(p+n/2-m/2,2)+Math.pow(E,2),0.5);
		double Cn_alpha = 0;
		if(Q==6) {
			Cn_alpha = 4*Q*Math.pow((E/Dref),2)*(1+(0.5*Dcalc)/(2*E+Dcalc))/(1+Math.pow(1+Math.pow((2*f/(m+n)),2),0.5));
		} else {
			Cn_alpha = 4*Q*Math.pow((E/Dref),2)*(1+Dcalc/(2*E+Dcalc))/(1+Math.pow(1+Math.pow((2*f/(m+n)),2),0.5));
		}
		return Cn_alpha;
	}
	
	public static void diagnostic(String categorie) {
//		WidStab.boiteAuxLettres = "";
		switch (categorie) {
		case "Fusée à eau":
			critElancementMin = critElancementMinFusEau;
			critElancementMax = critElancementMaxFusEau;
			critCnMin = critCnMinFusEau;
			critCnMax = critCnMaxFusEau;
			critMSMin = critMSMinFusEau;
			critMSMax = critMSMaxFusEau;
			critCoupleMin = critCoupleMinFusEau;
			critCoupleMax = critCoupleMaxFusEau;
			break;
		case "Microfusée":
			critElancementMin = critElancementMinMicroFu;
			critElancementMax = critElancementMaxMicroFu;
			critCnMin = critCnMinMicroFu;
			critCnMax = critCnMaxMicroFu;
			critMSMin = critMSMinMicroFu;
			critMSMax = critMSMaxMicroFu;
			critCoupleMin = critCoupleMinMicroFu;
			critCoupleMax = critCoupleMaxMicroFu;
			break;
		case "Rocketry Challenge":
			critElancementMin = critElancementMinMiniF;
			critElancementMax = critElancementMaxMiniF;
			critCnMin = critCnMinMiniF;
			critCnMax = critCnMaxMiniF;
			critMSMin = critMSMinMiniF;
			critMSMax = critMSMaxMiniF;
			critCoupleMin = critCoupleMinMiniF;
			critCoupleMax = critCoupleMaxMiniF;
			break;
		case "Minifusée":
			critElancementMin = critElancementMinMiniF;
			critElancementMax = critElancementMaxMiniF;
			critCnMin = critCnMinMiniF;
			critCnMax = critCnMaxMiniF;
			critMSMin = critMSMinMiniF;
			critMSMax = critMSMaxMiniF;
			critCoupleMin = critCoupleMinMiniF;
			critCoupleMax = critCoupleMaxMiniF;
			break;
		case "Fusée expérimentale":
			critElancementMin = critElancementMinFusEx;
			critElancementMax = critElancementMaxFusEx;
			critCnMin = critCnMinFusEx;
			critCnMax = critCnMaxFusEx;
			critMSMin = critMSMinFusEx;
			critMSMax = critMSMaxFusEx;
			critCoupleMin = critCoupleMinFusEx;
			critCoupleMax = critCoupleMaxFusEx;
			break;
		case "Bi-étage":
			WidStab.resultatDiagnostic = "\nBi-étage non gérées dans cette version\n";
		}
		if ((IHMPrincipale.fusee1.cn < critCnMin) || (IHMPrincipale.fusee1.msMin < critMSMin)
				|| (IHMPrincipale.fusee1.coupleMin < critCoupleMin) || (IHMPrincipale.fusee1.msMax < critMSMin) 
				|| (IHMPrincipale.fusee1.coupleMax < critCoupleMin)) {
			WidStab.resultatDiagnostic = "\n	INSTABLE	\n";
			if (IHMPrincipale.fusee1.cn < critCnMin) {
				WidStab.boiteAuxLettres = WidStab.boiteAuxLettres+"Ailerons trop petits !\n";
			}
			if (IHMPrincipale.fusee1.msMin < critMSMin) {
				WidStab.boiteAuxLettres = WidStab.boiteAuxLettres+"Abaisser les ailerons ou réhausser le CdM !\n";
			}
			if (IHMPrincipale.fusee1.coupleMin < critCoupleMin) {
				WidStab.boiteAuxLettres = WidStab.boiteAuxLettres+"Ailerons trop petits ou trop hauts /CdM !\n";
			}
		} else if ((IHMPrincipale.fusee1.cn > critCnMax) || (IHMPrincipale.fusee1.msMax > critMSMax) 
				|| (IHMPrincipale.fusee1.coupleMax > critCoupleMax) || (IHMPrincipale.fusee1.msMin > critMSMax) 
				|| (IHMPrincipale.fusee1.coupleMin > critCoupleMax)) {
			WidStab.resultatDiagnostic = "\n	SURSTABLE	\n";
			if (IHMPrincipale.fusee1.cn > critCnMax) {
				WidStab.boiteAuxLettres = WidStab.boiteAuxLettres+"Ailerons trop grands !\n";
			}
			if (IHMPrincipale.fusee1.msMax > critMSMax) {
				WidStab.boiteAuxLettres = WidStab.boiteAuxLettres+"Réhausser les ailerons ou abaisser le CdM !\n";
			}
			if (IHMPrincipale.fusee1.coupleMax > critCoupleMax) {
				WidStab.boiteAuxLettres = WidStab.boiteAuxLettres+"Ailerons trop grands ou trop bas /CdM !\n";
			}
		} else {
			WidStab.resultatDiagnostic = "\n	STABLE	\n";
		}
		if (IHMPrincipale.fusee1.elancement < critElancementMin) {
			WidStab.boiteAuxLettres = WidStab.boiteAuxLettres+"La fusée est trop courte !\n";
			WidStab.resultatDiagnostic = "\n	INSTABLE	\n";
		} else if (IHMPrincipale.fusee1.elancement > critElancementMax) {
			WidStab.boiteAuxLettres = WidStab.boiteAuxLettres+"La fusée est trop longue !\n";
			WidStab.resultatDiagnostic = "\n	INSTABLE	\n";
		}
		WidStab.boiteAuxLettres = WidStab.boiteAuxLettres+WidStab.resultatDiagnostic;
	}
		
	
// ************* Mutateurs *************


// ************* Accesseurs *************
public static double getCritCnMax()  {  
	return critCnMax;	}
public static double getCritCnMin()  {  
	return critCnMin;	}
public static double getCritMSMax()  {  
	return critMSMax;	}
public static double getCritMSMin()  {  
	return critMSMin;	}
public static double getCritCoupleMax()  {  
	return critCoupleMax;	}
public static double getCritCoupleMin()  {  
	return critCoupleMin;	}
	
}
