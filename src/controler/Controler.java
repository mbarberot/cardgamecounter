package controler;

import javax.microedition.lcdui.Displayable;
import main.Run;
import model.Model;
import model.Player;

/**
 * Controler class for the MVC architecture.
 *
 * @author kawa
 */
public class Controler
{
    /**
     * Action related to Health Points
     *
    public static int ACTION_HP = 0;
    
    /**
     * Action related to Poison
     *
    public static int ACTION_PSN = 1;
     */
    
    public static int ACTION_INC = 0;
    public static int ACTION_DEC = 1;
    
    public static int TYPE_HP = 2;
    public static int TYPE_PSN = 3;
    
    /**
     * Model
     */
    private Model model;
    
    /**
     * Current action
     */
    private int selectedAction;
    private int selectedType;
    
    /**
     * Main class
     */
    private Run run ;

    /**
     * Constructor
     */
    public Controler(Run run)
    {
        this.run = run;
        this.selectedAction = ACTION_DEC;
        this.selectedType = TYPE_HP;
    }

    /**
     * Edit a player counter
     * @param inc True if increase, false if decrease
     *
    public void edit(boolean inc)
    {
        boolean hp = selectedAction == ACTION_HP ;
        
        if(inc)
        {
            model.incr(hp);
        }
        else
        {
            model.decr(hp);
        }
    }
     */
    
    public void edit()
    {
        boolean inc = (selectedAction == ACTION_INC) ;
        boolean hp = (selectedType == TYPE_HP) ;
        
        if(inc)
        {
            model.incr(hp);
        }
        else
        {
            model.decr(hp);
        }
    }

    /**
     * Switch between the two actions available
     */
    public void switchAction()
    {
        selectedAction = (selectedAction == ACTION_INC) ? ACTION_DEC : ACTION_INC;
        model.updateViews();
    }
    
    public void switchType()
    {
        selectedType = (selectedType == TYPE_HP) ? TYPE_PSN : TYPE_HP;
        model.updateViews();
    }
    
    

    /**
     * Select a player.
     * A defeated player can't be selected
     * 
     * @param idPlayer Selected player id
     */
    public void selectPlayer(int idPlayer)
    {
        if(model.getSelectedMode() > idPlayer)
        {
            Player p = (Player) model.getPlayers().elementAt(idPlayer);
            if (p.isAlive())
            {
                model.setSelectedPlayer(idPlayer);
            }
        }
    }
    
    /**
     * Quit the application
     */
    public void exit()
    {
        run.exitMIDlet();
    }
    
    /**
     * Display a new UI
     * @param disp The new UI
     */
    public void switchUI(Displayable disp)
    {
        run.switchDisplayable(null, disp);
    }
    
    /**
     * Set the mode (number of player)
     * @param mode New mode
     */
    public void setMode(int mode)
    {
        model.setSelectedMode(mode);
    }
    
    /**
     * Set the initial amount of health points
     * @param startHP New initial amount of health points
     */
    public void setStartHP(int startHP)
    {
        model.setStartHP(startHP);
    }
    
    /**
     * Get the initial amount of health points
     * @return Initial amount of health points
     */
    public int getStartHP()
    {
        return model.getStartHP();
    }
    
    /**
     * Set the maximum poison
     * @param maxPSN the new maximum
     */
    public void setMaxPSN(int maxPSN)
    {
        model.setMaxPSN(maxPSN);
    }
    
    /**
     * Get the maximum poison
     * @return the maximum poison
     */
    public int getMaxPSN()
    {
        return model.getMaxPSN();
    }
    
    /**
     * Set a player name.
     * The name will be set for the selected player
     * 
     * @param name New name
     */
    public void setName(String name)
    {
        model.setName(name);
    }
    
    /**
     * Flip a coin
     * @return heads(0) or tails(1) 
     */
    public int flipCoin()
    {
        return model.flipCoin();
    }
    
    /**
     * Roll six-faced dice
     * @return 0 to 5
     */
    public int rollDice()
    {
        return model.rollDice();
    }
    
    //
    // Getters and Setters
    //

    public int getSelectedAction()
    {
        return selectedAction;
    }

    public void setSelectedAction(int selectedAction)
    {
        this.selectedAction = selectedAction;
    }

    public int getSelectedType()
    {
        return selectedType;
    }

    public void setSelectedType(int selectedType)
    {
        this.selectedType = selectedType;
    }
    
    public void setModel(Model model)
    {
        this.model = model;
    }
}
