package view;

import controler.Controler;
import javax.microedition.lcdui.*;

/**
 * Settings UI.
 * This class manage its own events.
 * 
 * @author kawa
 */
public class SettingsUI extends Form implements CommandListener
{
    private View parent;
    private Controler ctrl;

    private TextField startHP;
    private TextField maxPSN;
    
    private Command back;
    private Command valid;

    public SettingsUI(View parent, Controler ctrl)
    {
        super("Settings");
        this.parent = parent;
        this.ctrl = ctrl;
        
        // Items
        this.startHP = new TextField("Initial HP", ctrl.getStartHP()+"", 3, TextField.NUMERIC);
        this.maxPSN = new TextField("Max PSN", ctrl.getMaxPSN()+"", 3, TextField.NUMERIC);
        this.append(startHP);
        this.append(maxPSN);
        
        // Menu
        this.back = new Command("Back", Command.BACK, 0);
        this.valid = new Command("Ok", Command.OK, 0);
        this.addCommand(back);
        this.addCommand(valid);
        this.setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d)
    {
        if(d == this)
        {
            if(c == back)
            {
                ctrl.switchUI(parent);
            }
            
            if(c == valid)
            {
                ctrl.setStartHP(Integer.parseInt(startHP.getString()));
                ctrl.setMaxPSN(Integer.parseInt(maxPSN.getString()));
                ctrl.switchUI(parent);
            }
        }
    
    }
}
