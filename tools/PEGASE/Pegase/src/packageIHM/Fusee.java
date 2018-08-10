package packageIHM;

public class Fusee {
	
//~~~~~~~~~~~~~~~~	Données diverses	~~~~~~~~~~~~~~~~//
	protected int typeFusee = 3;

//~~~~~~~~~~~~~~~~	Données d'entrée pour la stabilité	~~~~~~~~~~~~~~~~//
	//fusée
	protected double longTot = 1.062;		//Longueur totale de la fusée (m)
	protected double xPropuRef = 1.08;		//Position du bas du propulseur (m)
	//ogive;
	protected double longOgive = 0.27;		//Hauteur de l'ogive (m)
	protected double dOgive = 0.065;		//Diamètre de l'ogive (m)
	protected int typeOgive = 3;			//0 s'il n'y a pas d'ogive, 1 pour parabolique, 2 pour ogivale et 3 pour conique
	//jeu d'ailerons du bas;
	protected double emplantureAil = 0.135;	//Emplanture d'un aileron (m)
	protected double envergureAil = 0.090;	//Semi-envergure d'un aileron (m)
	protected double saumonAil = 0.135;		//Saumon d'un aileron (m)
	protected double flecheAil = 0;			//Flèche d'un aileron (m)
	protected int nombreAil = 4;			//Nombre d'ailerons
	protected double xAil = 1.06;			//Position du bas des ailerons (m)
	//jeu d'ailerons du haut (plans canard)
	protected double emplantureCan = 0.070;	//Emplanture d'un aileron (m)
	protected double envergureCan = 0.032;	//Semi-envergure d'un aileron (m)
	protected double saumonCan = 0.070;		//Saumon d'un aileron (m)
	protected double flecheCan = 0;			//Flèche d'un aileron (m)
	protected int nombreCan = 3;			//Nombre d'ailerons du bas
	protected double xCan = 0.8;			//Position du bas des ailerons (m)
	//partie masquée des ailerons du bas
	protected double emplantureMasq = 0.135;//Emplanture d'un aileron (m) elle sera forcément égale à m_ail
	protected double envergureMasq = 0.032;	//Envergure d'un aileron (m)
	protected double saumonMasq = 0.135;	//Saumon d'un aileron (m)
	protected double flecheMasq = 0;		//Flèche d'un aileron (m)
	protected int nombreMasq = 0;			//Nombre d'ailerons du bas
	protected double xMasq = 1.06;			//Position du bas des ailerons (m)
	//transition A
	protected double lA = 0.021;			//Longueur de la transition A (m)
	protected double d1A = 0.065;			//Diamètre avant la transition A (m)
	protected double d2A = 0.052;			//Diamètre après la transition A (m)
	protected double xA = 0.4105;			//Position de la transition A (m)
	//transition B
	protected double lB = 0.02;				//Longueur de la transition B (m)
	protected double d1B = 0.052;			//Diamètre avant la transition B (m)
	protected double d2B = 0.065;			//Diamètre après la transition B (m)
	protected double xB = 0.6815;			//Position de la transition B (m)
	//position du CDG
	protected double xCG = 0.69;			//Position du CdG de la fusée sans moteur (m)
	protected double masseSansMoteur = 2.5;	//Masse de la fusée sans moteur (kg)
	protected int etatMoteur = 0;			//0 si absent, 1 si vide et 2 si plein
	//transitions et bi-empennages
	protected int nombreTransitions = 2;	//Nombre de transitions (0, 1 ou 2)
	protected int nombreJeuxAil = 1;		//Nombre de jeux d'ailerons : 0 par défaut, 1 pour mono-empennage, 2 pour bi-empennage 
	//et 3 pour bi-empennage à demi-masqué
	
//~~~~~~~~~~~~~~~~	Calculs intermédiaires de stabilité	~~~~~~~~~~~~~~~~//
	protected double cnO;				//portance de l'ogive (/rad)
	protected double cnA;				//portance de la jupe (/rad)
	protected double cnB;				//portance du rétreint (/rad)
	protected double fAil;				//ligne de mi-corde des ailerons du bas (mm)
	protected double cnAil;				//portance des ailerons du bas seuls (/rad)
	protected double dRef;				//diamètre de référence (m)
	protected double dAil = 0.065;		//diamètre de référence aux ailerons du bas (m)
	protected double cnCan;				//portance des ailerons du haut seuls (/rad)
	protected double dCan = 0;			//diamètre de référence aux ailerons du haut (m)
	protected double fCan;				//ligne de mi-corde des ailerons du haut (mm)
	protected double fMasq;				//ligne de mi-corde de la partie masquée des ailerons du bas (mm)
	protected double cnMasq;			//portance de la partie masquée des ailerons du bas (/rad)
	protected double cnAi;				//portance des ailerons du bas en 1/2 masqué (/rad)
	protected double xCPAil;			//foyer de portance des ailerons du bas (mm)
	protected double xCPMasq;			//foyer de portance de la partie masquée des ailerons du bas (mm)
	protected double xCPCan;			//foyer de portance des ailerons du haut (mm)
	protected double xCPTot;			//foyer de portance global des ailerons (mm)
	protected double xCPTransitionA;	//foyer de portance de la jupe (mm)
	protected double xCPTransitionB;	//foyer de portance du rétreint (mm)
	protected double xCPOgive;			//foyer de portance de l'ogive (mm)
	protected double xCPFusee = 0.679;	//foyer de portance global de la fusée (m)

//~~~~~~~~~~~~~~~~	Sorties de calculs de stabilité ~~~~~~~~~~~~~~~~//
	protected double cn = 0;			//Gradient de portance de la fusée (/rad)
	protected double msMin = 0;			//Marge statique minimum (calibre)
	protected double msMax = 0;			//Marge statique maximum (calibre)
	protected double elancement = 0;	//Elancement de la fusée
	protected double coupleMin = 0;		//Couple minimum
	protected double coupleMax = 0;		//Couple maximum
	
	protected double massePlein = 2.72;	//Masse de la fusée propulseur plein (kg)
	protected double masseVide = 2.65;	//Masse de la fusée propulseur vide (kg)
	protected double xCGVide = 0.69;	//CdG de la fusée propulseur vide (m)
	protected double xCGPlein = 0.7154;	//CdG de la fusée propulseur plein (m)
		
//~~~~~~~~~~~~~~~~	Caractéristiques moteurs ~~~~~~~~~~~~~~~~//
	protected double xPropuVide = 0;		//CdG du moteur vide (en mm)
	protected double xPropuPlein = 0;		//CdG du moteur plein (en mm)
	protected String moteur = "Cariacou";	//nom du moteur

//~~~~~~~~~~	Données d'entrée pour la trajectographie	~~~~~~~~~~
	//fusée
	protected double cx = 0.5;				//coefficient de traînée de la fusée (sans unité)
	protected double epAil = 0.002;			//Epaisseur d'un aileron (en m)
	protected int alignement = 1;			//1 si les deux jeux d'ailerons sont alignés et 2 sinon
	protected double epCan = 0;				//Epaisseur d'un aileron (en m)
	//parachute(s)
	protected double massePara = 2.65;		//masse sous parachute (en kg)
	protected double nombreParachutes = 1;	//Nombre de parachute(s)
	protected double vitVentPara = 10;		//vitesse du vent dans le plan horizontal (en m/s)
	protected int choixDepotage = 4;		//choix du retard de dépotage (en s)
//	protected double surfaceParaTotale = 0.2;//surface totale para 1 + 2 (en m²)
	//parachute 1
	protected double tPara1 = 7;			//instant de déclenchement du parachute (en s)
	protected double cxPara1 = 1;			//coefficient de traînée du parachute (sans unité)
	protected int choixPara1 = 1;			//0 pour un parachute croix, 1 pour un parachute circulaire, 
											//2 pour utiliser la variable issue de l'IHM
	protected double dim1Para1 = 0.225;		//dimension 1 du parachute 1 (en m)
	protected double dim2Para1 = 0.050;		//dimension 2 du parachute 1 (en m)
	protected double surfacePara1 = 0.1;	//surface du parachute 1 (en m²)
	protected double vPara1 = 1;			//vitesse de desente sous le parachute 1 seul (en m/s)
	//parachute 2
	protected double tPara2 = 7;			//instant de déclenchement du parachute (en s)
	protected double cxPara2 = 1;			//coefficient de traînée du parachute (sans unité)
	protected int choixPara2 = 1;			//idem ci-dessus mais pour le second parachute
	protected double dim1Para2 = 0.225;		//dimension 1 du parachute 2 (en m)
	protected double dim2Para2 = 0.050;		//dimension 2 du parachute 2 (en m)
	protected double surfacePara2 = 0.1;	//surface du parachute 2 (en m²)
	protected double vPara2 = 1;			//vitesse de desente sous le parachute 2 seul (en m/s)
	//rampe
	protected double betaRampe = 80*Math.PI/180;//assiette de la rampe (en rad)
	protected double altRampe = 0;			//altitude de la rampe (en m)
	protected double lRampe = 3;			//longueur de la rampe (en m)
	//Date d'allumage du moteur
	protected double tIni = 0;				//temps (en s)
	//autres variables d'initialisation
	protected double accelX0 = 0;			//accélération initiale selon x (en m/s²)
	protected double accelZ0 = 0;			//accélération initiale selon z (en m/s²)
	protected double accelXZ0 = 0;			//accélération initiale selon XZ (en m/s²)
	protected double vitesseX0 = 0;			//vitesse initiale selon X (en m/s)
	protected double vitesseZ0 = 0;			//vitesse initiale selon Z (en m/s)
	protected double vitesseXZ0 = 0;		//vitesse initiale selon XZ (en m/s)
	protected double positionX0 = 0;		//position initiale selon X (en m)
	protected double positionZ0 = 0;		//position initiale selon Z (en m)
	protected double positionXZ0 = 0;		//position initiale selon XZ (en m)
	protected double poussee0 = 0;			//poussée initiale (en N)
	protected double debit0 = 0;			//débit initial (en kg/s)
	
//~~~~~~~~~~	Résultats intermédiaires pour la trajectographie	~~~~~~~~~~
	protected double sRefTrainee;			//surface de référence pour le calcul de la traînée (en m²)
	protected double vPara = 1;				//vitesse de desente sous parachute (en m/s)
	// Séries de valeurs pour tracer les trajectoires
	public double[] xPortee = new double [100000];
	public double[] zAltitude = new double [100000];
	public double[] xPorteePara = new double [2];
	public double[] zAltitudePara = new double [2];
	public double[] xTemps = new double [100000];
	public double[] xTempsPara = new double [2];
	
//~~~~~~~~~~	Sorties de calculs de la trajectographie	~~~~~~~~~~
	//Attributs des événements du vol
	//accélération max
	protected double accelerationMaximale = 0;	//accélération maximale (en m/s²)
	protected double dateAccelerationMaximale = 0;//instant de l'accélération max (en s)
	//sortie de rampe
	protected double vitesseSortieRampe = 0;	//vitesse en sortie de rampe (en m)
	//vitesse max
	protected double vitesseMax = 0;			//vitesse maximale (en m/s)
	protected double dateVitesseMaximale = 0;	//instant de la vitesse max (en s)
	protected double machMax = 0;				//nombre de Mach maximum (sans unités)
	//fin de poussée
	protected double tempsFinProp = 0;			//date de la fin de propu (en s)
	//culmination
	protected double altitudeCulmination = 0;	//altitude de la culmination (en m)
	protected double tempsCulmination = 0;		//date de la culmination (en s)
	protected double vitesseCulmination = 0;	//vitesse de la culmination (en m/s)
	//impact balistique
	protected double porteeBalistique = 0;		//portée balistique (en m)
	protected double tBalistique = 0;			//durée totale du vol en balistique (en s)
	//descente sous parachute
	protected double dureeVolSousPara = 0;		//durée de la descente sous parachute (en s)
	protected double altPara = 0;				//altitude de déclenchement du parachute (en m)
	protected double x0Para = 0;				//portée de déclenchement du parachute (en m)
	protected double dVol1 = 0;					//durée du vol sous le seul parachute 1 (en s)
	protected double dVol2 = 0;					//durée du vol sous le seul parachute 2 (en s)
	protected double dVolPara = 0;				//durée du vol sous les deux parachutes (en s)
	protected double altParaBis = 0;			//altitude de déclenchement de l'autre parachute (en m)
	protected double deportLateral = 0;			//déport latéral sous parachute dû au vent (en m)
	//fin du vol
	protected double dureeVol = 0;				//durée totale du vol (en s)
		
	/**
	 * Setters of the entry data for the stability considerations
	 */
	public void setTypeFusee(int pTypeFusee)	{
			this.typeFusee = pTypeFusee;	}
	public void setNomMoteur(String pMoteur)	{
		this.moteur = pMoteur;	}
	public void setLong(double pLong)	{
		this.longTot = pLong;	}
	public void setXPropuRef(double pXPropuRef)	{
		this.xPropuRef = pXPropuRef;	}
	public void setLongOgive(double pLongOg)	{
		this.longOgive = pLongOg;	}
	public void setDOgive(double pDOg)	{
		this.dOgive = pDOg;	}
	public void setTypeOgive(int pTypeOgive)	{
		this.typeOgive = pTypeOgive;	}
	public void setEmplantureAil(double pMAil)	{
		this.emplantureAil = pMAil;	}
	public void setEnvergureAil(double pEAil)	{
		this.envergureAil = pEAil;	}
	public void setSaumonAil(double pNAil)	{
		this.saumonAil = pNAil;	}
	public void setflecheAil(double pPAil)	{
		this.flecheAil = pPAil;	}
	public void setNombreAil(int pD)	{
		this.nombreAil = pD;	}
	public void setXAil(double pXAil)	{
		this.xAil = pXAil;	}
	public void setEmplantureCan(double pMCan)	{
		this.emplantureCan = pMCan;	}
	public void setEnvergureCan(double pECan)	{
		this.envergureCan = pECan;	}
	public void setSaumonCan(double pNCan)	{
		this.saumonCan = pNCan;	}
	public void setFlecheCan(double pPCan)	{
		this.flecheCan = pPCan;	}
	public void setNombreCan(int pDCan)	{
		this.nombreCan = pDCan;	}
	public void setXCan(double pXCan)	{
		this.xCan = pXCan;	}
	public void setEnvergureMasq(double pEnvergureMasq)	{
		this.envergureMasq = pEnvergureMasq;	}
	public void setLA(double pLA)	{
		this.lA = pLA;	}
	public void setD1A(double pD1A)	{
		this.d1A = pD1A;	}
	public void setD2A(double pD2A)	{
		this.d2A = pD2A;	}
	public void setXA(double pXA)	{
		this.xA = pXA;	}
	public void setLB(double pLB)	{
		this.lB = pLB;	}
	public void setD1B(double pD1B)	{
		this.d1B = pD1B;	}
	public void setD2B(double pD2B)	{
		this.d2B = pD2B;	}
	public void setXB(double pXB)	{
		this.xB = pXB;	}
	public void setXCGSansMoteur(double pXcg)	{
		this.xCG = pXcg;	}
	public void setMasseSansMoteur(double pMasse)	{
		this.masseSansMoteur = pMasse;	}
	public void setNombreTransitions(int pNbTransitions)	{
		this.nombreTransitions = pNbTransitions;	}
	public void setNombreJeuxAil(int pNbJeuxAil)	{
		this.nombreJeuxAil = pNbJeuxAil;	}
	public void setDRef(double pDRef)	{
		this.dRef = pDRef;	}
	public void setDAil(double pDAil)	{
		this.dAil = pDAil;	}
	public void setDCan(double pDCan)	{
		this.dCan = pDCan;	}
	public void setEtatMoteur(int pEtatMoteur)	{
		this.etatMoteur = pEtatMoteur;	}
	
	/**
	 * Setters of the output data for the stability
	 */
	public void setCnAil(double pCnAil)	{
		this.cnAil = pCnAil;	}
	public void setCnCan(double pCnCan)	{
		this.cnCan = pCnCan;	}
	public void setCnMasq(double pCnMasq)	{
		this.cnMasq = pCnMasq;	}
	public void setMSMin(double pMSMin)	{
		this.msMin = pMSMin;	}
	
	/**
	 * Setters of the intput data for the trajectory
	 */
	public void setEpAil(double pEpAil)	{
		this.epAil = pEpAil;	}
	public void setEpCan(double pEpCan)	{
		this.epCan = pEpCan;	}
	public void setChoixDepotage(int pChoixDepotage) {
		this.choixDepotage = pChoixDepotage;	}
	public void setCx(double pCx)	{
		this.cx = pCx;	}
	public void setMasseMoteurPlein(double pMasse)	{
		this.massePlein = pMasse;	}
	public void setSRefTrainee(double pSRef)	{
		this.sRefTrainee = pSRef;	}
	public void setLRampe(double pLRampe)	{
		this.lRampe = pLRampe;	}
	public void setBetaRampe(double pBetaRampe)	{
		this.betaRampe = pBetaRampe;	}
	public void setAltRampe(double pAltRampe)	{
		this.altRampe = pAltRampe;	}
	public void setDim1Para1(double pDim1Para1)	{
		this.dim1Para1 = pDim1Para1;	}
	public void setDim2Para1(double pDim2Para1)	{
		this.dim2Para1 = pDim2Para1;	}
	public void setDim1Para2(double pDim1Para2)	{
		this.dim1Para2 = pDim1Para2;	}
	public void setDim2Para2(double pDim2Para2)	{
		this.dim2Para2 = pDim2Para2;	}
//	public void setSurfacePara(double pSurfPara)	{
//		this.surfaceParaTotale = pSurfPara;	}
	public void setSurfacePara1(double pSurfPara1)	{
		this.surfacePara1 = pSurfPara1;	}
	public void setSurfacePara2(double pSurfPara1)	{
		this.surfacePara2 = pSurfPara1;	}
	public void setMassePara(double pMassePara)	{
		this.massePara = pMassePara;	}
	public void setDatePara1(double pDatePara)	{
		this.tPara1 = pDatePara;	}
	public void setDatePara2(double pDatePara)	{
		this.tPara2 = pDatePara;	}
	public void setCxPara1(double pCxPara)	{
		this.cxPara1 = pCxPara;	}
	public void setCxPara2(double pCxPara)	{
		this.cxPara2 = pCxPara;	}
	public void setVitVentPara(double pVitVentPara)	{
		this.vitVentPara = pVitVentPara;	}
	public void setNombreParachutes(int pNombreParachutes) {
		this.nombreParachutes = pNombreParachutes;	}
	public void setChoixPara1(int pChoixPara1) {
		this.choixPara1 = pChoixPara1;	}
	
	/**
	 * Setters of the output data of the trajectory
	 */
	public void setAltCulmination(double pAltCulmination)	{
		this.altitudeCulmination = pAltCulmination;	}
	public void setInstCulmination(double pInstCulmination)	{
		this.tempsCulmination = pInstCulmination;	}
	public void setVitCulmination(double pVitCulmination)	{
		this.vitesseCulmination = pVitCulmination;	}
	public void setAccMax(double pAccMax)	{
		this.accelerationMaximale = pAccMax;	}
	public void setMMax(double pMMax)	{
		this.machMax = pMMax;	}
	public void setVitSortRampe(double pVitSortRampe)	{
		this.vitesseSortieRampe = pVitSortRampe;	}
	public void setAltPara(double pAltPara)	{
		this.altPara = pAltPara;	}
	public void setTBalist(double pTBalist)	{
		this.tBalistique = pTBalist;	}
	public void setDVol(double pDVol)	{
		this.dureeVol = pDVol;	}
	public void setPortBalist(double pPortBalist)	{
		this.porteeBalistique = pPortBalist;	}
	public void setVitesseMax(double pVitesseMax) {
		this.vitesseMax = pVitesseMax;	}
	public void setMachMax(double pMachMax) {
		this.machMax = pMachMax;	}
	public void setDateVitesseMaximale(double pDateVitesseMaximale) {
		this.dateVitesseMaximale = pDateVitesseMaximale;	}
	public void setAccelerationMaximale(double pAccelerationMaximale) {
		this.accelerationMaximale = pAccelerationMaximale;	}
	public void setDateAccelerationMaximale(double pDateAccelerationMaximale) {
		this.dateAccelerationMaximale = pDateAccelerationMaximale;	}
	public void setTempsFinProp(double pTempsFinProp) {
		this.tempsFinProp = pTempsFinProp;	}
	public void setVitesseSortieRampe(double pVitesseSortieRampe) {
		this.vitesseSortieRampe = pVitesseSortieRampe;	}
	public void setAltitudeCulmination(double pAltitudeCulmination) {
		this.altitudeCulmination = pAltitudeCulmination;	}
	public void setTempsCulmination(double pTempsCulmination) {
		this.tempsCulmination = pTempsCulmination;	}
	public void setVitesseCulmination(double pVitesseCulmination) {
		this.vitesseCulmination = pVitesseCulmination;	}
	public void setPorteeBalistique(double pPorteeBalistique) {
		this.porteeBalistique = pPorteeBalistique;	}
	public void setDureeVol(double pDureeVol) {
		this.dureeVol = pDureeVol;	}
	
	/**
	 * Getters of the input data of the stability
	 */

	
	/**
	 * Getters of the output data of the stability
	 */
	public double getPortanceFusee()  {  
		return cn;	}
	public double getMSMin()  {  
		return msMin;	}
	public double getMSMax()  {  
		return msMax;	}
	public double getElancement()  {  
		return elancement;	}
	public double getCoupleMin()  {  
		return coupleMin;	}
	public double getCoupleMax()  {  
		return coupleMax;	}
	public double getMasseVide()  {  
		return masseVide;	}
	public double getXCGVide()  {  
		return xCGVide;	}
	public double getXCGPlein()  {  
		return xCGPlein;	}
	public double getD2B()  {
		return d2B;	}
	public double getfAil() {
		return fAil;	}
	public double getfCan() {
		return fCan;	}
	public double getfMasq() {
		return fMasq;	}
	public double getLongTot() {
		return longTot;	}
	public double getXCPFusee() {
		return xCPFusee;	}
	
	/**
	 * Getters of the input data of the trajectory
	 */
	public double getMasseSansMoteur()  {  
		return masseSansMoteur;	}
	public double getMassePlein()  {  
		return massePlein;	}
	public int getChoixDepotage() {
		return choixDepotage;	}
	
	/**
	 * Getters of the output data of the trajectory
	 */
	public double getSRefTrainee() {
		return sRefTrainee;	}
	public double getAltitudeCulmination()  {  
		return altitudeCulmination;	}
	public double getMach_max() {
		return machMax;	}
	public double getDateVitesseMaximale() {
		return dateVitesseMaximale;	}
	public double getDateAccelerationMaximale() {
		return dateAccelerationMaximale;	}
	public double getTempsFinProp() {
		return tempsFinProp;	}
	public double getVitesseSortieRampe() {
		return vitesseSortieRampe;	}
	public double getTempsCulmination() {
		return tempsCulmination;	}
	public double getVitesseCulmination() {
		return vitesseCulmination;	}
	public double getPorteeBalistique() {
		return porteeBalistique;	}
	public double getDureeVol() {
		return dureeVol;	}
	public double getMassePara() {
		return massePara;	}
	public double getVPara() {
		return vPara;	}
	public double getVPara1() {
		return vPara1;	}
	public double getVPara2() {
		return vPara2;	}
}