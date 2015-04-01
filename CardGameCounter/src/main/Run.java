package main;


import controler.Controler;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import model.Model;
import view.View;

/**
 * Main class : Run and exit the application
 * 
 * @author kawa
 */
public class Run extends MIDlet
{
    /**
     * The main view
     */
    private View view;
    
    /**
     * The model
     */
    private Model model;
    
    /**
     * The controler
     */
    private Controler ctrl;

    /**
     * Constructor
     */
    public Run () 
    {
        model = new Model(Model.MODE_2PL);
        ctrl = new Controler(this);
        view = new View();
        
        ctrl.setModel(model);
        view.setControler(ctrl);
        model.setListener(view);
        
    }
    
    /**
     * Start the application
     * @throws MIDletStateChangeException 
     */
    protected void startApp() throws MIDletStateChangeException
    {
        switchDisplayable(null,view);
    }
    
    /**
     * Switch to another UI
     * @param alert 
     * @param nextDisplayable New UI
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {
        Display display = Display.getDisplay(this);
        if (alert == null) 
        {
            display.setCurrent(nextDisplayable);
        } 
        else 
        {
            display.setCurrent(alert, nextDisplayable);
        }
    }

    /**
     * Pause application (empty)
     */
    protected void pauseApp()
    {
    }
    
    /**
     * Stop the app
     */
    public void exitMIDlet()
    {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Destroy the application
     * @param unconditional 
     */
    protected void destroyApp(boolean unconditional)
    {
    }

}
