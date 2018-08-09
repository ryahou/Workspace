package packageIHM;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//      				 TRAJEC JUNIOR						   //
//						CNES/DLA/SDT/SPC				  	   //
//					classe conversion .txt -> .xml		 	   //
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

import java.io.*;

import org.xml.sax.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*;

public class XMLWriterEngine {
	
	public static boolean detente = IHMPrincipale.getAjouterMoteur(); // variable pour commander l'activité ou l'inactivité de la méthode
	
//	String nomFichierMoteurAAjouter = "Wapiti";
		
    static BufferedReader in;
    static StreamResult out;
    static TransformerHandler th;
    String xmlRootName;

    /**
     * Object converting .txt files into .xml files
     * @param nomFichierAAjouter : name of the .txt file to be converted
     * @param xmlRootName : name of the XML root of the file ("moteur" for the engines files)
     */
    
    public static void beginTxtToXml(String nomFichierAAjouter, String xmlRootName) {
    	
    	if (detente == true) {
    		
    		try {
    			in = new BufferedReader(new FileReader(nomFichierAAjouter + ".txt"));
    			out = new StreamResult(nomFichierAAjouter + ".xml");
    			openXml(xmlRootName);
    			// prépare la lecture ligne par ligne
    			String str;
    			while ((str = in.readLine()) != null) {
    				process_line(str);
    			}
                in.close();
                closeXml(xmlRootName);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
            System.out.println("fichier ajouté");
    	} else {
    		System.out.println("aucun fichier ajouté");
    	}
    }

    public static void openXml(String xmlRootName) throws ParserConfigurationException, TransformerConfigurationException, SAXException {

        SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        th = tf.newTransformerHandler();

        th.setResult(out);
        th.startDocument();
        th.startElement(null, null, xmlRootName, null);
    }

    public static void process_line(String s) throws SAXException {
    	th.startElement(null, null, "titre", null);
    	//début string
    	th.characters(s.toCharArray(), 0, s.length());
    	//fin string
    	th.endElement(null, null, "titre");
    }

    public static void closeXml(String xmlRootName) throws SAXException {
        th.endElement(null, null, xmlRootName);
        th.endDocument();
    }
	
}