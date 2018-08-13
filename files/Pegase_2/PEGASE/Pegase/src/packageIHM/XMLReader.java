package packageIHM;
///~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~///
///      				 TRAJEC JUNIOR						   ///
///						CNES/DLA/SDT/SPC				  	   ///
///					classe lecture de fichiers XML		 	   ///
///~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~///

import java.io.FileNotFoundException;
import java.io.FileReader;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLReader {

	///indices pour gérer les tableaux
	private static int i = 0;
	private static int a = 0;
	private static int t = 0;
	private static int f = 0;
	private static String j;											///stocke les mots-clés xml
	private static double valeur[] = new double [1000];					///stocke les valeurs traduites
	private static String tableauDeCaracteres[] = new String [1000];	///stocke les valeurs non traduites (chaînes de caractères)
	private static String nomMoteur;									///nom du moteur
	private static String fichierMoteur;								///nom du fichier moteur
	private String nomFabricant;										///nom du fabricant
	private String listeDepotages;										///liste des valeurs de dépotages au format chaînes de caractères
	private static double diametreMoteur;								///diamètre du moteur (en m)
	private static double longueurMoteur;								///longueur du moteur (en m)
	private static double masseAVideMoteur;								///masse du moteur sans propergol (en kg)
	private static double massePropergolMoteur;							///masse de propergol (en kg)
	private static double masseTotaleMoteur;							///masse totale du moteur (en kg)
	private static double loiDePousseePOUSSEE[] = new double [1000];	///stocke temporairement les valeurs de la poussée (en N)
	private static double loiDePousseeTEMPS[] = new double [1000];		///stocke temporairement les valeurs de temps (en s)
	private static double loiDePoussee[] = new double [100000];			///stocke définitevement la loi de poussée (en N, par rapport au pas de temps des calculs de trajecto)

	public void xmlReaderActive(Fusee pFusee) {
		
		/// ré-initialisation des compteurs et des tableaux
		i = 0;
		a = 0;
		f = 0;
		nettoyeur();
		///Création de notre factory
		XMLInputFactory factory = XMLInputFactory.newInstance();
///		///On utilise notre fichier
///		File file = new File(moteur);
		
	///ArrayList<Integer> listEvent = new ArrayList<>();

		try {
			///Obtention de notre reader
			XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(fichierMoteur));
			while (reader.hasNext()) {
				/// Récupération de l'événement
				int type = reader.next();

				///if(!listEvent.contains(type))
				///	listEvent.add(type);

/// ce bloc switch permet de stocker les chaînes de caractères dans un tableau
				switch (type) {
					case XMLStreamReader.START_ELEMENT:
						j = String.valueOf(reader.getName());
						if (j == "titre") {
							tableauDeCaracteres[i]=reader.getElementText();
							///cette boucle if permet de filtrer l'en-tête du fichier
							if (tableauDeCaracteres[i].startsWith(";")) {
								///System.out.println("Ligne non traitée");
							} else {
								splitter();
							}
						}
						break;  
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		arrangeur(pFusee);
		afficheur();
}

/**
 * method splitting the string from the table
 */
public void splitter() {
	String delims = "[ \t]+";								///la chaîne sera brisée à chaque espace ou tabulation
	String[] tokens = tableauDeCaracteres[i].split(delims);
	for (String t : tokens) {
		tableauDeCaracteres[i] = t;							///la chaîne est restockée dans une case du même tableau
		///System.out.println("tableau caract : " + Tableau_de_caracteres[i]);
		i=i+1;
	}
}

/**
 * method translating and saving the data under other variables or tables
 */
public void arrangeur (Fusee pFusee) {
	nomMoteur = tableauDeCaracteres[0];
	nomFabricant = tableauDeCaracteres[6];
	listeDepotages = tableauDeCaracteres[3];
	///identifie et traduit la ligne de paramètres
	for (i=1; i<3; i++){
		valeur[a] = Double.valueOf(tableauDeCaracteres[i]);
		a = a+1;
	}
	for (i=4; i<6; i++){
		valeur[a] = Double.valueOf(tableauDeCaracteres[i]);
		a = a+1;
	}
	diametreMoteur = valeur[0]/1000;
	longueurMoteur = valeur[1]/1000;
	massePropergolMoteur = valeur[2];
	masseTotaleMoteur = valeur[3];
	
	///traduit et stocke les autres valeurs dans des tableaux temporaires
	loiDePousseePOUSSEE[0] = 0;
	loiDePousseeTEMPS[0] = 0;
	t = 1;
	f = 1;
	for (i=7; tableauDeCaracteres[i]!=null; i++){
		if (i % 2 == 0) {	/// si i est pair c'est une poussée en Newton
			loiDePousseePOUSSEE[f] = Double.valueOf(tableauDeCaracteres[i]);
			///System.out.println("tableau newton " + Loi_de_poussee_POUSSEE[F]);
			f = f+1;
		} else {	/// si i est impair c'est un temps en secondes
			loiDePousseeTEMPS[t] = Double.valueOf(tableauDeCaracteres[i]);
			///System.out.println("tableau secondes " + Loi_de_poussee_TEMPS[t]);
			t = t+1;
		}
		///System.out.println(Tableau_de_caracteres[i]);
	}
	
	///stocke la loi de poussée dans le tableau qui sera appellé pour les calculs de trajecto
	///en créant les valeurs manquantes mais c'est peu précis
	loiDePoussee[0] = 0;
	t = 1;
	a = 1;
///	for (a=1; Loi_de_poussee_POUSSEE[t] > 0; a++) {	///(a*Traj.getPas_de_calcul() <= Loi_de_poussee_TEMPS[t+1]) && 
///		Loi_de_poussee[a] = Loi_de_poussee_POUSSEE[t];
///		if (Loi_de_poussee_TEMPS[t+1] < ((a+2)*Traj.getPas_de_calcul())) {
///			Loi_de_poussee[a] = Loi_de_poussee_POUSSEE[t];
///			t = t+1;
///		}
///		System.out.println(a + "-ième point de la loi de poussée : " + Loi_de_poussee[a]);
///	}
	
	///le traitement ci-après contient une interpolation plus précise
	while (t < 1000) {	///Loi_de_poussee_TEMPS[t] > 0 && Loi_de_poussee_POUSSEE[t] > 0
		if (loiDePousseeTEMPS[t] == a*Traj.getPasDeCalcul()) {	///(a*Traj.getPas_de_calcul() <= Loi_de_poussee_TEMPS[t+1]) && 
			loiDePoussee[a] = loiDePousseePOUSSEE[t];
			t = t+1;
			a = a+1;
		} else if (loiDePousseeTEMPS[t] > a*Traj.getPasDeCalcul()) {
			///remplacer par l'interpolation
			loiDePoussee[a] = loiDePousseePOUSSEE[t-1]+(((Traj.getPasDeCalcul()*(a-1/2))-pFusee.tIni-loiDePousseeTEMPS[t-1])*((loiDePousseePOUSSEE[t]-loiDePousseePOUSSEE[t-1])/(loiDePousseeTEMPS[t]-loiDePousseeTEMPS[t-1])));
///			Loi_de_poussee[a] = Loi_de_poussee_POUSSEE[t]+(((Traj.getPas_de_calcul()*(a-1/2))-Traj.getT_ini()-Loi_de_poussee_TEMPS[t])*((Loi_de_poussee_POUSSEE[t+1]-Loi_de_poussee_POUSSEE[t])/(Loi_de_poussee_TEMPS[t+1]-Loi_de_poussee_TEMPS[t])));
			a = a+1;
		} else if (loiDePousseeTEMPS[t] < a*Traj.getPasDeCalcul()) {
			t = t+1;
		}
	}

}

public void nettoyeur() {
	/// les tableaux sont réinitialisés :
	for (t=0; t < 999; t++) {
		loiDePousseePOUSSEE[t] = 0.0;
		loiDePousseeTEMPS[t] = 0.0;
		loiDePoussee[t] = 0.0;
		tableauDeCaracteres[t] = null;
		valeur[t] = 0.0;
	}
}

public void afficheur() {
	for (a = 0; a < 100; a ++) {
		///System.out.println(a + "-ième point de la loi de poussée : " + Loi_de_poussee[a]);
	}
}

///************* Mutateurs *************
public void setFichierMoteur(String pFichierMoteur)	{
	fichierMoteur = pFichierMoteur;
	}

///************* Accesseurs ************
public String getNomMoteur()  {  
    return nomMoteur;
 	}
public String getFichierMoteur()  {  
    return fichierMoteur;
 	}
public double getLongueurMoteur()  {  
    return longueurMoteur;
 	}
public double getMasseTotaleMoteur()  {  
    return masseTotaleMoteur;
 	}
public double getMasseaVideMoteur()  {
	masseAVideMoteur = masseTotaleMoteur - massePropergolMoteur;
    return masseAVideMoteur;
 	}
public double getMassePropergolMoteur()	{
	return massePropergolMoteur;
	}
public double getLoiDePoussee(int i)  {	///logiquement un paramètre entier pour attraper une valeur dans le tableau
    return loiDePoussee[i];
 	}
public static double getLoiDePousseePOUSSEE(int i)  {	///logiquement un paramètre entier pour attraper une valeur dans le tableau
    return loiDePousseePOUSSEE[i];
 	}
public static double getLoiDePousseeTEMPS(int i)  {	///logiquement un paramètre entier pour attraper une valeur dans le tableau
    return loiDePousseeTEMPS[i];
 	}
public double getDiametreMoteur() {
	return diametreMoteur;
}
public String getNomFabricant() {
	return nomFabricant;
}
public String getListeDepotages() {
	return listeDepotages;
}

}