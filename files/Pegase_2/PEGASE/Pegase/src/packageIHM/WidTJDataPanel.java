package packageIHM;

import fr.cnes.genius.exception.GException;
import fr.cnes.genius.standardapplication.GDataPanelAbstract;

public class WidTJDataPanel extends GDataPanelAbstract {	//implements GReadWrite

    static WidStab widStab;
    final WidTraj widTraj;
    final WidSauv widSauv;
    final WidVol widVol;

//    private GEntryString boxClub = new GEntryString("Club", "Mon Club");
//    private GEntryString boxProjet = new GEntryString("Projet", "Mon projet");
    
//    boxClub.setInnerDescendantConstraint(new GConstraint(GConstraint.split(6)), 0, 0);
//	boxProjet.setInnerDescendantConstraint(new GConstraint(GConstraint.split(3)), 0, 0);
	
	public WidTJDataPanel() throws GException {
		// Calling for the super constructor
    	// "data" is the name of the root XML name for the context files
		super("Data");
		
		// Creating a stability widget (for both stages or mono-stage rocket)
        widStab = new WidStab();
        // Adding it in a tabbedpane
        this.addTab("Stabilité", widStab);
        
//        // Creating a stability widget for the second stage
//        widStab2 = new WidStab();
//        // Adding it in a tabbedpane
//        this.addTab("Stabilité", widStab2);
        
//        // Creating a stability widget for the first stage (if needed)
//        widStab1 = new WidStab();
//        // Adding it in a tabbedpane
//        this.addTab("Stabilité", widStab1);
        
        // Creating a trajectory widget
        widTraj = new WidTraj();
        // Adding it in a tabbedpane
        this.addTab("Trajectoire", widTraj);

        // Creating a safety widget
        widSauv = new WidSauv();
        // Adding it in a tabbedpane
        this.addTab("Sauvegarde", widSauv);
        
        // Creating a flight widget
        widVol = new WidVol();
        // Adding it in a tabbedpane
        this.addTab("Vol", widVol);

	}
 
    @Override
    public void display () throws GException { 
//    	super.put(boxClub);
//		super.put(boxProjet);
    	super.display();
    	
    }
 
    @Override
    public void clear() throws GException {}
    
//	public void read() throws GException {
//		display();
//	}
//
//	public void write() throws GException {
//		display();
//	}

}
