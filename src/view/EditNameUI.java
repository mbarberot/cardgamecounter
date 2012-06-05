package view;

import controler.Controler;
import javax.microedition.lcdui.*;
import model.Player;

/**
 * Class for editing names.
 * This class manage its own events.
 * 
 * @author kawa
 */
public class EditNameUI extends Form implements CommandListener
{
    private View parent;
    private Controler ctrl;
    
    private TextField name;
    
    private Command back;
    private Command valid;
    
    public EditNameUI (View parent, Controler ctrl) 
    {
        super("Edit name");
        this.parent = parent;
        this.ctrl = ctrl;
        
        // Needed informations
        int idPlayer = parent.getSelectedPlayer();
        Player p = (Player)parent.getPlayers().elementAt(idPlayer);
        
        // Items
        this.name = new TextField("Name for player " + (idPlayer+1), p.getName(), 30, TextField.SENSITIVE);
        this.append(name);
        
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
                ctrl.setName(name.getString());
                ctrl.switchUI(parent);
            }
        }
    }
    
}
