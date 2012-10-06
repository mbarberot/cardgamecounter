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
    
    private boolean flip ;
    
    private ImageItem picture ;
    
    private Command back ;
    private Command redo ;
    
    public RandomUI (View parent, Controler ctrl, int action)
    {
        super("");
        this.parent = parent;
        this.ctrl = ctrl;
        
        // Info
        this.flip = (action == FLIP_COIN);
        
        // New title
        this.setTitle(flip ? "Flip a coin" : "Roll a dice");
        
        this.random();
        this.append(picture);
        
        // Menu
        back = new Command("Back", Command.BACK, 0);
        redo = new Command((flip ? "Flip" : "Roll")+" again", Command.ITEM, 0);
        this.addCommand(back);
        this.addCommand(redo);
        this.setCommandListener(this);
        
    }
    
    private void random()
    {
        // Items
        String path = "/" 
                + (flip ? "coin" : "dice") 
                + (flip ? ctrl.flipCoin()+1 : ctrl.rollDice()+1) 
                + ".jpg";
        
        try
        {
            picture = new ImageItem(
                    "",
                    Image.createImage(path),
                    ImageItem.LAYOUT_CENTER, 
                    path
                     );
            
        } catch (IOException ex) { ex.printStackTrace(); }
    }
    
    public void commandAction(Command c, Displayable d)
    {
        if(d == this)
        {
            if(c == back)
            {
                ctrl.switchUI(parent);
            }
            else if(c == redo)
            {
                random();
                this.set(0, picture);
            }
        }
    }
    
}
