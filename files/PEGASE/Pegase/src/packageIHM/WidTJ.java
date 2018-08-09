package packageIHM;

import java.io.File;

import fr.cnes.genius.exception.GException;
import fr.cnes.genius.exception.GFileManipulatorException;
import fr.cnes.genius.main.GFileManipulation;
import fr.cnes.genius.savefiles.GSaveResults;
import fr.cnes.genius.standardapplication.GAboutDialog;
import fr.cnes.genius.standardapplication.GMainFrameAbstract;
import fr.cnes.genius.utils.GContextFileManagement;
import fr.cnes.genius.utils.GFileFilter;

public class WidTJ extends GMainFrameAbstract<WidTJDataPanel> {
	
    // File Names
    /** Prefix for context file names */
    private static final String INI_FILE_PREFIX = "INI_";
    /** By default context file names */
    private static final String INI_FILE = "INIT.xml";
    /** Prefix for output file names */
    private static final String EPH_FILE_PREFIX = "EPH_";
    /** By default EPHEM file names */
    private static final String EPH_FILE = "EPHEM.txt";
 
    // SIZES
    /** Data panel height is determined through a method*/
    /** Error console height */
    private static final int ERRCONSOLE_HEIGHT = 80;
    /** Icon size */
    private static  final int ICON_SIZE = 12;
 
    /**
     * Constructor
     * @throws GException GENIUS exception
     * String name of the window
     * Instanciation of the displaying class
     * GContextFileManagement it says it all, go check the javadoc for further details
     * GAboutDialog displays a pop-up with some pieces of information
     * GSaveResults it says it all, check the javadoc for further details
     * int height of the window
     * int height of the console
     * int size of the icon
     * boolean to display (if true) or not (if false) a progress bar
     */
    public WidTJ() throws GException {
 
        super("T/J",
              new WidTJDataPanel(),
              new GContextFileManagement(".", "TJ", new GFileFilter(INI_FILE_PREFIX, ".xml", "T/J Files") ),
              new GAboutDialog("About T/J", "Example T/J ...", "CNES", "Vx.x ; xx/xx/2017", null),// à la place de null: "./images/logoCNES.jpg"
              new GSaveResults("Saving TJ results", new File("results/"), ".txt", ".xml"),
              GetScreenWorkingHeight(), ERRCONSOLE_HEIGHT, ICON_SIZE, false);
 
    }
 
    /**
     * Method managing the results (and context) files saving.
     */
    @Override
    protected void saveFilesManagement() throws GException {
 
        final File ini = new File("data/", INI_FILE);
        final File res = new File("results/", EPH_FILE);
 
        // The context file INIT.xml will be saved in INI_....xml
        this.getSaveResultsDialog().setContextFile("../data/"+ini.getName(), INI_FILE_PREFIX, true);
        // Result files consist in a single one named by default "EPHEM"
        this.getSaveResultsDialog().clearResultFileList();
        this.getSaveResultsDialog().addSingleResultFile(res.getName(), EPH_FILE_PREFIX, true);
 
        this.getSaveResultsDialog().show();
 
    }
 
    /**
     * Method for pre processing management just before running computation.
     */
    @Override
    protected void customPreProcessManagement() throws GFileManipulatorException {

    	// We write a context file with data coming from the data panel
    	GFileManipulation.writeConfig("data/"+INI_FILE, "TJ", this.getDataPanel(), true);
    	// We initialize the JavaCommandLauncher
    	final String classPath = System.getProperty("java.class.path");
    	this.getJavaCommandLauncher().setJavaCommand(classPath, new String[] {"BatchTJ"});//il faut désigner la méthode principale du package de calculs
       	// We display the console above the other tabbedpanes
//    	this.getDataPanel().selectConsoleTab();
 
    }
 
    /**
     * Method for post processing management.
     */
    @Override
    protected void customPostProcessManagement() {
     	// Nothing to do ...
    }
    
    /**
     * Method getting the screen height.
     */
	public static int GetScreenWorkingHeight() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}

	/**
     * Commented method getting the screen width (will throw an exception on a computer using more than one screen).
     */
//    public static int GetScreenWorkingWidth() {
//	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
//	}
 
}
