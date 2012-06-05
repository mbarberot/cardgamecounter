package model;

import java.util.Random;
import java.util.Vector;
import utils.ModelListener;

/**
 * Model
 *
 * @author kawa
 */
public class Model
{
    /**
     * Mode 2 player
     */
    public static int MODE_2PL = 2;
    /**
     * Mode 3 player
     */
    public static int MODE_3PL = 3;
    /**
     * Mode 4 player
     */
    public static int MODE_4PL = 4;
    /**
     * Constant value for "No selected player"
     */
    public static int PL_NONE = 99;
    /**
     * List of players
     */
    private Vector players;
    /**
     * List of views listening the Model
     */
    private ModelListener listener;
    /**
     * Current player
     */
    private int selectedPlayer;
    /**
     * Current mode
     */
    private int selectedMode;
    /**
     * Initial amount of health points
     */
    private int startHP;
    /**
     * Maximum poison marks allowed
     */
    private int maxPSN;
    /**
     * Random number generator (to flip a coin and roll a dice)
     */
    private Random ranGen;

    /**
     * Constructor.<br/> Don't forget to add views
     *
     * @param mode The mode
     */
    public Model(int mode)
    {
        this.players = null;
        this.listener = null;
        this.selectedMode = mode;
        this.selectedPlayer = PL_NONE;
        this.startHP = 20;
        this.maxPSN = 10;
        this.ranGen = new Random();
        makePlayers();
    }
    
    /**
     * Create players
     */
    private void makePlayers()
    {
        players = new Vector(selectedMode);
        for (int i = 0; i < selectedMode; i++)
        {
            Player p = new Player(i, "Player " + (i+1), startHP, maxPSN);
            players.addElement(p);
        }
        updateViews();
    }

    /**
     * Increment the selected player (if any)
     *
     * @param editHP true for health points, false for poison
     */
    public void incr(boolean editHP)
    {
        if (selectedPlayer != PL_NONE)
        {
            Player p = (Player) players.elementAt(selectedPlayer);
            p.incr(editHP);
            updateViews();
        }
    }

    /**
     * Decrement the selected player (if any)
     *
     * @param editHP true for health points, false for poison
     */
    public void decr(boolean editHP)
    {
        if (selectedPlayer != PL_NONE)
        {
            Player p = (Player) players.elementAt(selectedPlayer);
            p.decr(editHP);
            updateViews();
        }
    }
    
    /**
     * Set the selected player name
     * @param name New name
     */
    public void setName(String name)
    {
        if (selectedPlayer != PL_NONE)
        {
            Player p = (Player) players.elementAt(selectedPlayer);
            p.setName(name);
            updateViews();
        }
    }

    /**
     * Update every view.
     */
    public void updateViews()
    {
        if(listener != null)
        {
            listener.updatePlayers(players, selectedPlayer, winner());
        }
    }
    
    /**
     * Check if only one player is still alive
     * @return winner id or -1 if there is no winner
     */
    public int winner()
    {
        int winner = -1;
        int cptAlive = 0;
        int len = players.size();
        int i = 0;
        
        while(cptAlive <= 1 && i < len)
        {
            if(((Player)players.elementAt(i)).isAlive())
            {
                cptAlive++;
                winner = i;
            }
            i++;
        }
        
        if(cptAlive > 1) 
        {
            winner = -1;
        }
        
        return winner;
         
    }   
    
    /**
     * Flip a coin
     * @return 0(heads) or 1(tails)
     */
    public int flipCoin ()
    {
        int alea = ranGen.nextInt()%2;
        return (alea < 0 ? alea * -1 : alea);
    }
    
    /**
     * Roll a dice
     * @return 0 to 5
     */
    public int rollDice ()
    {
        int alea = ranGen.nextInt()%6;
        return (alea < 0 ? alea * -1 : alea);
    }

    //
    // Getters & Setters
    //
    
    public ModelListener getListener()
    {
        return listener;
    }

    public void setListener(ModelListener listener)
    {
        this.listener = listener;
        updateViews();
    }

    public Vector getPlayers()
    {
        return players;
    }

    public void setPlayers(Vector players)
    {
        this.players = players;
    }

    public int getSelectedMode()
    {
        return selectedMode;
    }

    public void setSelectedMode(int selectedMode)
    {
        this.selectedMode = selectedMode;
        makePlayers();
    }

    public int getSelectedPlayer()
    {
        return selectedPlayer;
    }

    public void setSelectedPlayer(int selectedPlayer)
    {
        this.selectedPlayer = selectedPlayer;
        updateViews();
    }

    public int getMaxPSN()
    {
        return maxPSN;
    }

    public void setMaxPSN(int maxPSN)
    {
        this.maxPSN = maxPSN;
        makePlayers();
    }

    public int getStartHP()
    {
        return startHP;
    }

    public void setStartHP(int startHP)
    {
        this.startHP = startHP;
        makePlayers();
    }
    
    
}
