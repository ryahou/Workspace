package packageIHM;

import java.awt.Color;

import fr.cnes.genius.chart.GFreeChartXY;
import fr.cnes.genius.constraints.GConstraint;
import fr.cnes.genius.exception.GEntryRealException;
import fr.cnes.genius.exception.GException;
import fr.cnes.genius.exception.GFreeChartException;
import fr.cnes.genius.highLevel.GChoice;
import fr.cnes.genius.highLevel.GComboBoxWithLabel;
import fr.cnes.genius.highLevel.GEntryReal;
import fr.cnes.genius.lowLevel.GLabel;
import fr.cnes.genius.lowLevel.GPanel;
import fr.cnes.genius.lowLevel.GTextArea;
import fr.cnes.genius.main.GEvent;
import fr.cnes.genius.main.GListener;
import fr.cnes.genius.main.GReadWrite;

//se servir de cet objet pour tracer des courbes "à la carte" dans la partie trajecto
	// GPlotPanel plot1 = new GPlotPanel("1er titre", "2ème titre", "3ème titre", 3);

public class WidTraj extends GPanel implements GReadWrite, GListener {
	
	//déclaration des composants
	private GLabel labelFusee = new GLabel("Fusée");
	private GEntryReal boxMasse = new GEntryReal("Masse totale", IHMPrincipale.fusee1.massePlein, IHMPrincipale.unitMasse);
	//menu fusée et menu moteur tbd ?? //
	private GEntryReal boxSurfRef = new GEntryReal("Surface Réf.", IHMPrincipale.round(IHMPrincipale.fusee1.getSRefTrainee(), 6), 
			IHMPrincipale.unitSurface);
	private GEntryReal boxCx = new GEntryReal("Cx", IHMPrincipale.fusee1.cx, null);
	private GLabel labelRampe = new GLabel("Rampe");
	private GEntryReal boxLongueurRampe = new GEntryReal("Longueur", IHMPrincipale.fusee1.lRampe, IHMPrincipale.unitLongueur);
	private GEntryReal boxInclinaisonRampe = new GEntryReal("Inclinaison", IHMPrincipale.fusee1.betaRampe, IHMPrincipale.unitAngle);
	private GEntryReal boxAltitudeRampe = new GEntryReal("Altitude", IHMPrincipale.fusee1.altRampe, IHMPrincipale.unitLongueur);
	//descente sous parachute(s) de la fusée
	private GLabel labelPara = new GLabel("Descente sous parachute");
	private GEntryReal boxMassePara = new GEntryReal("Masse", IHMPrincipale.fusee1.massePara, IHMPrincipale.unitMasse);
	private int[] valueListPara = { 1, 2 };
	private GChoice choiceNombrePara = new GChoice("Nombre de parachute(s)", IHMPrincipale.fusee1.choixPara1, valueListPara, "1", "2");
	private GLabel labPara1 = new GLabel("Parachute 1");
	private GLabel labPara2 = new GLabel("Parachute 2");
	private GEntryReal boxDatePara1 = new GEntryReal("Ouverture para", IHMPrincipale.fusee1.tPara1, IHMPrincipale.unitTemps);
	private GEntryReal boxDatePara2 = new GEntryReal("", IHMPrincipale.fusee1.tPara2, IHMPrincipale.unitTemps);
	private GComboBoxWithLabel menuPara1 = new GComboBoxWithLabel("Type de parachute", IHMPrincipale.fusee1.choixPara1, "Croix", "Disque", 
			"A la carte");
	private GEntryReal boxDim1Para1 = new GEntryReal("Dimension 1", IHMPrincipale.fusee1.dim1Para1, IHMPrincipale.unitLongueur);
	private GEntryReal boxDim2Para1 = new GEntryReal("Dimension 2", IHMPrincipale.fusee1.dim2Para1, IHMPrincipale.unitLongueur);
	private GEntryReal boxDim1Para2 = new GEntryReal("", IHMPrincipale.fusee1.dim1Para2, IHMPrincipale.unitLongueur);
	private GEntryReal boxDim2Para2 = new GEntryReal("", IHMPrincipale.fusee1.dim2Para2, IHMPrincipale.unitLongueur);
	private GLabel dummyLabel1 = new GLabel("Dimension 1");
	private GLabel dummyLabel2 = new GLabel("Dimension 2");
	private GLabel dummyLabel3 = new GLabel("");
	private GLabel dummyLabel4 = new GLabel("");
	private GLabel dummyLabel5 = new GLabel("");
	private GLabel dummyLabel6 = new GLabel("");
	private GComboBoxWithLabel menuPara2 = new GComboBoxWithLabel("", IHMPrincipale.fusee1.choixPara2, "Croix", "Disque", "A la carte");
	private GEntryReal boxSurfPara1 = new GEntryReal("Surface para", IHMPrincipale.round(IHMPrincipale.fusee1.surfacePara1, 6), 
			IHMPrincipale.unitSurface);
	private GEntryReal boxSurfPara2 = new GEntryReal("", IHMPrincipale.round(IHMPrincipale.fusee1.surfacePara2, 6), 
			IHMPrincipale.unitSurface);
	private GEntryReal boxCxPara1 = new GEntryReal("Cx parachute", IHMPrincipale.fusee1.cxPara1);
	private GEntryReal boxCxPara2 = new GEntryReal("", IHMPrincipale.fusee1.cxPara2);
	private GEntryReal boxVitVent = new GEntryReal("Vitesse vent", IHMPrincipale.fusee1.vitVentPara, IHMPrincipale.unitVitesse);
	private GEntryReal boxVitDesc1 = new GEntryReal("Vitesse sous para", IHMPrincipale.fusee1.vPara1, IHMPrincipale.unitVitesse);
	private GEntryReal boxVitDesc2 = new GEntryReal("", IHMPrincipale.fusee1.vPara2, IHMPrincipale.unitVitesse);
	private GEntryReal boxVitDesc = new GEntryReal("Vitesse de descente", IHMPrincipale.fusee1.vPara, IHMPrincipale.unitVitesse);
	
	//gérer l'éjection de masses au point de vue de la trajecto
	//gérer les descentes des modules éjectés
	
	//courbe altitude / portée
	GFreeChartXY courbeAltitudePortee = new GFreeChartXY("Trajectoire (z x)", "Portée x (m)", "Altitude z (m)", null, true);
	//variable fantôme pour gérer la plage de portée
	private double porteeMax = 0;
	//courbe altitude / temps
	GFreeChartXY courbeAltitudeTemps = new GFreeChartXY("Trajectoire (z t)", "Temps t (s)", "Altitude z (m)", null, true);
	//variable fantôme pour gérer la plage de portée
	private double dureeMax = 0;
	
	private String messageResultat = "";
	private GTextArea tableauResultat = new GTextArea(messageResultat);
	
	//boîte pour afficher une valeur (débuggage)
//	private GEntryReal boxDebug = new GEntryReal("Sortie de débugage", IHMPrincipale.fusee1.surfacePara1, 
//			IHMPrincipale.unitSurface);
	
	public WidTraj() throws GEntryRealException {
		
		//contraintes des composants
		boxMasse.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxSurfRef.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxCx.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		labelRampe.setConstraint(new GConstraint(GConstraint.newline(true, 20)));
		boxLongueurRampe.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxLongueurRampe.setUnit("m");
		boxInclinaisonRampe.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxAltitudeRampe.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxAltitudeRampe.setUnit("m");
		labelPara.setConstraint(new GConstraint(GConstraint.newline(true, 20), GConstraint.spanx(2)));
		boxMassePara.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		labPara1.setConstraint(new GConstraint(GConstraint.newline(true, 0), GConstraint.skip(1)));
		labPara2.setConstraint(new GConstraint(GConstraint.wrap(true, 0), GConstraint.skip(1), GConstraint.spanx(2)));
		boxDatePara1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxDatePara2.setInnerDescendantConstraint(null, 0, 0);
		boxDatePara2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(2)), 0, 1);
		boxDatePara2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		menuPara2.setInnerDescendantConstraint(null, 0, 0);
		dummyLabel3.setConstraint(null);
		dummyLabel4.setConstraint(null);
		dummyLabel5.setConstraint(null);
		dummyLabel6.setConstraint(null);
		boxDim1Para1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxDim2Para1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxDim1Para2.setInnerDescendantConstraint(null, 0, 0);
		boxDim1Para2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(2)), 0, 1);
		boxDim1Para2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxDim2Para2.setInnerDescendantConstraint(null, 0, 0);
		boxDim2Para2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(2)), 0, 1);
		boxDim2Para2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxSurfPara1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxSurfPara1.setUnit("m^2");
		boxSurfPara1.setFracDigits(3);
		boxSurfPara2.setInnerDescendantConstraint(null, 0, 0);
		boxSurfPara2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(2)), 0, 1);
		boxSurfPara2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxSurfPara2.setUnit("m^2");
		boxSurfPara2.setFracDigits(3);
		boxCxPara1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxCxPara2.setInnerDescendantConstraint(null, 0, 0);
		boxCxPara2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxVitVent.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxVitDesc1.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxVitDesc2.setInnerDescendantConstraint(null, 0, 0);
		boxVitDesc2.setInnerDescendantConstraint(new GConstraint(GConstraint.split(2)), 0, 1);
		boxVitDesc2.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);
		boxVitDesc.setInnerDescendantConstraint(new GConstraint(GConstraint.width(80)), 1);//GConstraint.aligny(YAlign.Top)
		
		courbeAltitudePortee.setConstraint(new GConstraint(GConstraint.skip(6), 
				GConstraint.spany(15), GConstraint.width(300), GConstraint.height(300)));
		courbeAltitudeTemps.setConstraint(new GConstraint(GConstraint.skip(7), GConstraint.spany(15), 
				GConstraint.width(300), GConstraint.height(300)));
		
		tableauResultat.setConstraint(new GConstraint(GConstraint.skip(1), GConstraint.wrap(true, 0), GConstraint.span(6, 8), 
				GConstraint.height(200), GConstraint.width(300)));
		tableauResultat.setEditable(false);
		
		try {
			//profil Altitude / Portée
			courbeAltitudePortee.addSerie("Trajectoire balistique", IHMPrincipale.fusee1.xPortee, IHMPrincipale.fusee1.zAltitude, 
					Color.BLUE, true);
			courbeAltitudePortee.addSerie("Trajectoire sous parachute", IHMPrincipale.fusee1.xPorteePara, 
					IHMPrincipale.fusee1.zAltitudePara, Color.GREEN, true);
			courbeAltitudePortee.setXZoom(0.0, 140.0);
			courbeAltitudePortee.setYZoom(false, 0.0, 150.0);
			//profil Altitude / Temps
			courbeAltitudeTemps.addSerie("Trajectoire balistique", IHMPrincipale.fusee1.xTemps, IHMPrincipale.fusee1.zAltitude, 
					Color.BLUE, true);
			courbeAltitudeTemps.addSerie("Trajectoire sous parachute", IHMPrincipale.fusee1.xTempsPara, IHMPrincipale.fusee1.zAltitudePara, 
					Color.GREEN, true);
			courbeAltitudeTemps.setXZoom(0.0, 20.0);
			courbeAltitudeTemps.setYZoom(false, 0.0, 150.0);
		} catch (GFreeChartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void display() throws GException {
		setter();
		//rajouter une logique dans stab : si non bi étage fixer les param initiaux de la trajecto dans l'objet fusee
		Traj.calculTraj(IHMPrincipale.fusee1);
		affichage();
		generic();
	}

	@Override
	public void generic() throws GException {
		
		tableauResultat.setText(messageResultat);
		
		put(labelFusee);
		put(courbeAltitudePortee);
		put(courbeAltitudeTemps);
		boxMasse.setValue(IHMPrincipale.fusee1.massePlein);
		put(boxMasse);
		boxSurfRef.setValue(IHMPrincipale.round(IHMPrincipale.fusee1.getSRefTrainee(), 6));
		put(boxSurfRef);
		put(boxCx);
		
		put(labelRampe);
		put(boxLongueurRampe);
		put(boxInclinaisonRampe);
		put(boxAltitudeRampe);
		
		put(labelPara);
		boxMassePara.setValue(IHMPrincipale.fusee1.getMassePara());
		put(boxMassePara);
		put(boxVitVent);
		put(choiceNombrePara);
		if (choiceNombrePara.getValue() == 1) {
			put(boxDatePara1);
			put(menuPara1);
			put(tableauResultat);
			if (menuPara1.getValue() != 2) {
				put(boxDim1Para1);
				put(boxDim2Para1);
			}
			put(boxSurfPara1);
			put(boxCxPara1);
		} else {	// si l'utilisateur a sélectionné l'option "2 parachutes"
			put(labPara1);
			put(labPara2);
			put(boxDatePara1);
			put(boxDatePara2);
			put(menuPara1);
			put(menuPara2);
			put(tableauResultat);
			if (menuPara1.getValue() != 2) {
				put(boxDim1Para1);
				if (menuPara2.getValue() != 2) {
					put(boxDim1Para2);
				}
				put(boxDim2Para1);
				if (menuPara2.getValue() != 2) {
					put(boxDim2Para2);
				}
			} else {
				if (menuPara2.getValue() != 2) {
					put(dummyLabel1);
					put(dummyLabel3);
					put(dummyLabel5);
					put(boxDim1Para2);
					put(dummyLabel2);
					put(dummyLabel4);
					put(dummyLabel6);
					put(boxDim2Para2);
				}
			}
			put(boxSurfPara1);
			put(boxSurfPara2);
			put(boxCxPara1);
			put(boxCxPara2);
			boxVitDesc1.setValue(IHMPrincipale.round(IHMPrincipale.fusee1.getVPara1(), 3));
			put(boxVitDesc1);
			boxVitDesc2.setValue(IHMPrincipale.round(IHMPrincipale.fusee1.getVPara2(), 3));
			put(boxVitDesc2);
		}
		boxVitDesc.setValue(IHMPrincipale.round(IHMPrincipale.fusee1.getVPara(), 3));
		put(boxVitDesc);
		
//		put(boxDebug);
	}
	
	public void setter() {
		//fusée
		IHMPrincipale.fusee1.setMasseSansMoteur(boxMasse.getValue() - IHMPrincipale.XMLMoteur1.getMasseTotaleMoteur());
		IHMPrincipale.fusee1.setMasseMoteurPlein(boxMasse.getValue());
		IHMPrincipale.fusee1.setCx(boxCx.getValue());
		//rampe
		IHMPrincipale.fusee1.setLRampe(boxLongueurRampe.getValue());
		IHMPrincipale.fusee1.setBetaRampe(boxInclinaisonRampe.getValue());
		IHMPrincipale.fusee1.setAltRampe(boxAltitudeRampe.getValue());
		//parachute
		IHMPrincipale.fusee1.setDatePara1(boxDatePara1.getValue());
		IHMPrincipale.fusee1.setDatePara2(boxDatePara2.getValue());
		IHMPrincipale.fusee1.setNombreParachutes(choiceNombrePara.getValue());
		IHMPrincipale.fusee1.setChoixPara1(menuPara1.getValue());
		IHMPrincipale.fusee1.setChoixPara1(menuPara2.getValue());
		IHMPrincipale.fusee1.setDim1Para1(boxDim1Para1.getValue());
		IHMPrincipale.fusee1.setDim2Para1(boxDim2Para1.getValue());
		IHMPrincipale.fusee1.setDim1Para2(boxDim1Para2.getValue());
		IHMPrincipale.fusee1.setDim2Para2(boxDim2Para2.getValue());
		IHMPrincipale.fusee1.setCxPara1(boxCxPara1.getValue());
		IHMPrincipale.fusee1.setCxPara2(boxCxPara2.getValue());
		IHMPrincipale.fusee1.setVitVentPara(boxVitVent.getValue());
		//rafraîchissement de l'affichage des données sur l'onglet de stabilité
		WidTJDataPanel.widStab.refresh();
	}
	
	public void affichage() throws GException {
		messageResultat = "";
		messageResultat = "vitesse sortie rampe : " + IHMPrincipale.round(IHMPrincipale.fusee1.vitesseSortieRampe, 3)
				+ "\nvitesse max : " + IHMPrincipale.round(IHMPrincipale.fusee1.vitesseMax, 3)
				+ "\naccélération max : " + IHMPrincipale.round(IHMPrincipale.fusee1.accelerationMaximale, 3)
				+ "\naltitude apogée : " + IHMPrincipale.round(IHMPrincipale.fusee1.altitudeCulmination, 3)
				+ "\ninstant apogée : " + IHMPrincipale.round(IHMPrincipale.fusee1.tempsCulmination, 3)
				+ "\nvitesse apogée : " + IHMPrincipale.round(IHMPrincipale.fusee1.vitesseCulmination, 3)
				+ "\naltitude récup : " + IHMPrincipale.round(IHMPrincipale.fusee1.altPara, 3)
				+ "\ndurée vol : " + IHMPrincipale.round(IHMPrincipale.fusee1.dureeVol, 3)
				+ "\nportée balistique : " + IHMPrincipale.round(IHMPrincipale.fusee1.porteeBalistique, 3);
		
//		System.out.println("date de la vitesse max : " + IHMPrincipale.fusee1.dateVitesseMaximale);
//		System.out.println("mach max : " + IHMPrincipale.fusee1.machMax);
//		System.out.println("date de fin de propu : " + IHMPrincipale.fusee1.tempsFinProp);
	}
	
	public void read() throws GException {
		generic();
	}

	public void write() throws GException {
		generic();
	}
	
	public void after(GEvent arg0) throws GException {
		if (arg0.contains(menuPara1, menuPara1, boxSurfPara1, boxSurfPara2)) {
			if (menuPara1.getValue() == 2) {
				IHMPrincipale.fusee1.setSurfacePara1(boxSurfPara1.getValue());
				boxSurfPara1.setValue(IHMPrincipale.fusee1.surfacePara1);
			} else {
				boxSurfPara1.setValue(IHMPrincipale.fusee1.surfacePara1);
			}
			if (menuPara2.getValue() == 2) {
				IHMPrincipale.fusee1.setSurfacePara2(boxSurfPara2.getValue());
				boxSurfPara2.setValue(IHMPrincipale.fusee1.surfacePara2);
			} else {
				boxSurfPara2.setValue(IHMPrincipale.fusee1.surfacePara2);
			}
			// Il faut d'abord retirer toutes les séries
			courbeAltitudePortee.removeAllSeries();
			courbeAltitudeTemps.removeAllSeries();
			//on actualise ensuite les séries de valeurs
			Traj.nettoyeur(IHMPrincipale.fusee1);
			setter();
			Traj.calculTraj(IHMPrincipale.fusee1);
			porteeMax = Math.max(IHMPrincipale.fusee1.xPorteePara[1], IHMPrincipale.fusee1.porteeBalistique);
			dureeMax = Math.max(IHMPrincipale.fusee1.xTempsPara[1], IHMPrincipale.fusee1.tBalistique);
			try {
				// On rajoute ensuite les séries
				//profil Altitude / Portée
				courbeAltitudePortee.addSerie("Trajectoire balistique", IHMPrincipale.fusee1.xPortee, IHMPrincipale.fusee1.zAltitude, 
						Color.BLUE, true);
				courbeAltitudePortee.addSerie("Trajectoire sous parachute", IHMPrincipale.fusee1.xPorteePara, 
						IHMPrincipale.fusee1.zAltitudePara, Color.GREEN, true);
				courbeAltitudePortee.setXZoom(0.0, porteeMax+30);
				courbeAltitudePortee.setYZoom(false, 0.0, IHMPrincipale.fusee1.altitudeCulmination+30);
				//profil Altitude / Temps
				courbeAltitudeTemps.addSerie("Trajectoire balistique", IHMPrincipale.fusee1.xTemps, IHMPrincipale.fusee1.zAltitude, 
						Color.BLUE, true);
				courbeAltitudeTemps.addSerie("Trajectoire sous parachute", IHMPrincipale.fusee1.xTempsPara, 
						IHMPrincipale.fusee1.zAltitudePara, Color.GREEN, true);
				courbeAltitudeTemps.setXZoom(0.0, dureeMax+2);
				courbeAltitudeTemps.setYZoom(false, 0.0, IHMPrincipale.fusee1.altitudeCulmination+30);
			} catch (GFreeChartException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//méthode pour ré-initialiser les tableaux de trajecto
			Traj.nettoyeur(IHMPrincipale.fusee1);
		}
		if (arg0.contains(boxMasse, boxCx, boxAltitudeRampe, boxLongueurRampe, boxInclinaisonRampe, boxDatePara1, boxDatePara2, 
				choiceNombrePara, boxDim1Para1, boxDim2Para1, boxDim1Para2, boxDim2Para2, 
				boxCxPara1, boxCxPara2, boxVitVent)) {
			// Il faut d'abord retirer toutes les séries
			courbeAltitudePortee.removeAllSeries();
			courbeAltitudeTemps.removeAllSeries();
			// il faut ré-exécuter ces méthodes pour rafraîchir l'objet fusee1
			Traj.nettoyeur(IHMPrincipale.fusee1);
			setter();
			Traj.calculTraj(IHMPrincipale.fusee1);
			boxSurfPara1.setValue(IHMPrincipale.fusee1.surfacePara1);
			boxSurfPara2.setValue(IHMPrincipale.fusee1.surfacePara2);
			porteeMax = Math.max(IHMPrincipale.fusee1.xPorteePara[1], IHMPrincipale.fusee1.porteeBalistique);
			dureeMax = Math.max(IHMPrincipale.fusee1.xTempsPara[1], IHMPrincipale.fusee1.tBalistique);
			try {
				// On rajoute ensuite les séries
				//profil Altitude / Portée
				courbeAltitudePortee.addSerie("Trajectoire balistique", IHMPrincipale.fusee1.xPortee, IHMPrincipale.fusee1.zAltitude, 
						Color.BLUE, true);
				courbeAltitudePortee.addSerie("Trajectoire sous parachute", IHMPrincipale.fusee1.xPorteePara, 
						IHMPrincipale.fusee1.zAltitudePara, Color.GREEN, true);
				courbeAltitudePortee.setXZoom(0.0, porteeMax+30);
				courbeAltitudePortee.setYZoom(false, 0.0, IHMPrincipale.fusee1.altitudeCulmination+30);
				//profil Altitude / Temps
				courbeAltitudeTemps.addSerie("Trajectoire balistique", IHMPrincipale.fusee1.xTemps, IHMPrincipale.fusee1.zAltitude, 
						Color.BLUE, true);
				courbeAltitudeTemps.addSerie("Trajectoire sous parachute", IHMPrincipale.fusee1.xTempsPara, 
						IHMPrincipale.fusee1.zAltitudePara, Color.GREEN, true);
				courbeAltitudeTemps.setXZoom(0.0, dureeMax+2);
				courbeAltitudeTemps.setYZoom(false, 0.0, IHMPrincipale.fusee1.altitudeCulmination+30);
			} catch (GFreeChartException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//méthode pour ré-initialiser les tableaux de trajecto
			Traj.nettoyeur(IHMPrincipale.fusee1);
		}
	}
	
	public void before(GEvent arg0) throws GException {
		// Rien à faire ...
	}

}
