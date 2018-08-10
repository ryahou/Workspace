package packageIHM;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;

import fr.cnes.genius.exception.GException;
import fr.cnes.genius.loggers.GConsoleLogger;
import fr.cnes.genius.unit.GMetricUnit;
import fr.cnes.genius.unit.GUnit;

public class IHMPrincipale {
	
	/**
	 * Pegase main class and main method
	 */

	public static Fusee fusee1 = new Fusee();
	public static XMLReader XMLMoteur1 = new XMLReader();
	
//	private int NumeroProjet=1;			//Numéro du projet
//	private String Nom="";				//Nom de la fusée
//	private String Club="";				//Nom du club
	private static boolean ajouterMoteur = false;	//permet de régler la classe XML sur création de fichier (true) ou passive (false)
	private static String ephemFileName;
	
	public static GUnit[] unitMasse = { new GMetricUnit("g"), new GMetricUnit("kg") };
	public static GUnit[] unitLongueur = { new GMetricUnit("mm"), new GMetricUnit("m") };
	public static GUnit[] unitSurface = { new GMetricUnit("mm^2"), new GMetricUnit("m^2") };
	public static GUnit[] unitTemps = { new GMetricUnit("s") };
	public static GUnit[] unitVitesse = { new GMetricUnit("m/s"), new GMetricUnit("km/h") };
	public static GUnit[] unitAngle = { new GMetricUnit("deg"), new GMetricUnit("rad") };
	
	public static void main(String[] args) throws GException {
		
//		// début code pour plusieurs écrans
////		Rectangle virtualBounds = new Rectangle();
////		  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
////		  GraphicsDevice[] gs = ge.getScreenDevices();
////		  for (int j = 0; j < gs.length; j++) { 
////		      GraphicsDevice gd = gs[j];
////		      GraphicsConfiguration[] gc = gd.getConfigurations();
////		      for (int i=0; i < gc.length; i++) {
////		          virtualBounds = virtualBounds.union(gc[i].getBounds());
////		      }
////		  } 
//		// fin code pour plusieurs écrans
		
		@SuppressWarnings("unused")
		WidTJDataPanel dataPan = new WidTJDataPanel();

	    final WidTJ pan = new WidTJ();
	    pan.displayMainFrame();

	}
	

    public static void compute (String ephemFileName) {
    	/**
         * Method "computing" (saves the results in an "EPHEM.txt" file).
         * @param ephemFileName
         * The "\n" are used in the translation from .txt to .xml in the XMLWriterEngine.
         */
        // Writing in the result EPHEM file
    	FileWriter ephemFile = null;
    	try {
            ephemFile = new FileWriter(ephemFileName + ".txt");
            ephemFile.write("Elancement : " + fusee1.getElancement() + " ");
            ephemFile.write("Gradient de portance : " + fusee1.getPortanceFusee() + "/rad ");
            ephemFile.write("MS Min : " + fusee1.getMSMin() + "calibres ");
            ephemFile.write("MS Max : " + fusee1.getMSMax() + "calibres ");
            ephemFile.write("Couple Min : " + fusee1.getCoupleMin() + "calibre/rad ");
            ephemFile.write("Couple Max : " + fusee1.getCoupleMax() + "calibre/rad ");
            ephemFile.write("Abscisse du foyer de portance : " + fusee1.getXCPFusee()*1000 + "mm ");
            ephemFile.write("Abscisse du centre de gravité moteur plein : " + fusee1.getXCGPlein()*1000 + "mm ");
            ephemFile.close();
        } catch (IOException err) {
            GConsoleLogger.getLogger().log(Level.SEVERE, err.getMessage());
        }
    	XMLWriterEngine.beginTxtToXml(ephemFileName, "fusée");
    	if (ajouterMoteur == true) {
    		XMLWriterEngine.beginTxtToXml(IHMPrincipale.fusee1.moteur, IHMPrincipale.fusee1.moteur);
    	}
    	
    }

public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
}
    
 // ************* Mutateurs *************
    public static void setEphemFileName(String pEphemFileName) {
		ephemFileName = pEphemFileName;	}

 // ************* Accesseurs *************
	public static String getEphemFileName() {
		return ephemFileName;	}
	public static boolean getAjouterMoteur()  {  
		return ajouterMoteur;	}
	public static Fusee getCalcObject() {
		return fusee1;	}
	
}