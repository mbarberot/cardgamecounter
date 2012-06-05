package view;

import controler.Controler;
import java.io.IOException;
import javax.microedition.lcdui.*;

/**
 * UI for displaying result of a draw.
 * This class manage its own events
 * 
 * @author kawa
 */
public class RandomUI extends Form implements CommandListener
{
    public static int FLIP_COIN = 0;
    public static int ROLL_DICE = 1;
    
    private View parent;
    private Controler ctrl;
    
    private Command back ;
    
    public RandomUI (View parent, Controler ctrl, int action)
    {
        super("");
        this.parent = parent;
        this.ctrl = ctrl;
        
        // Info
        boolean flip = (action == FLIP_COIN);
        
        // New title
        this.setTitle(flip ? "Flip a coin" : "Roll a dice");
        
        // Items
        String path = "/" 
                + (flip ? "coin" : "dice") 
                + (flip ? ctrl.flipCoin()+1 : ctrl.rollDice()+1) 
                + ".jpg";
        
        try
        {
            ImageItem picture = new ImageItem(
                    "",
                    Image.createImage(path),
                    ImageItem.LAYOUT_CENTER, 
                    path
                     );
            this.append(picture);
            
        } catch (IOException ex) { ex.printStackTrace(); }
        
        
        // Menu
        back = new Command("Back", Command.BACK, 0);
        this.addCommand(back);
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
        }
    }
    
}
