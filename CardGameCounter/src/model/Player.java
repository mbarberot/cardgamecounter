package model;

/**
 * A player
 * 
 * @author kawa
 */
public class Player
{
    /**
     * Player's health points
     */
    private Counter hp ;
    
    /**
     * Player's poison marks
     */
    private Counter psn ;
    
    /**
     * Player's id
     */
    private int id ;
    
    /**
     * Player's name
     */
    private String name ;
    
    /**
     * Constructor for a MTG player
     * 
     * @param id Players' id
     * @param name Name of the player
     * @param startHP Initial amount of health points
     * @param maxPSN Maximum amount of poison counter
     */
    public Player(int id, String name, int startHP, int maxPSN)
    {
        this(id,name,startHP,0,0,Counter.NO_MAX,0,0,maxPSN,Counter.DEFAULT);
    }
    
    /**
     * Full constructor for a player
     * 
     * @param id Player's id
     * @param name Player's name
     * @param startHP Initial value for health points
     * @param minHP Minimum value for health points
     * @param maxHP Maximum value for health points
     * @param paramHP Parameters for the health points' counter
     * @param startPSN Initial value for poison marks
     * @param minPSN Minimum value for poison marks
     * @param maxPSN Maximum value for poison marks
     * @param paramPSN Parameters for poison marks' counter
     */
    public Player(int id, String name, int startHP, int minHP, int maxHP, int paramHP, int startPSN, int minPSN, int maxPSN, int paramPSN)
    {
        this.id = id;
        this.name = name;
        this.hp = new Counter(startHP, minHP, maxHP, paramHP);
        this.psn = new Counter(startPSN, minPSN, maxPSN, paramPSN);
    }

    /**
     * Increment a counter
     * 
     * @param editHP Wich counter to increment : true for health points, poison otherwise.
     */
    public void incr(boolean editHP)
    {
        if(isAlive())
        {
            if(editHP) { hp.incr(); }
            else { psn.incr(); }
        }
    }
    
    /**
     * Decrement a counter
     * 
     * @param editHP Wich counter to decrement : true for health points, poison otherwise.
     */
    public void decr(boolean editHP)
    {
        if(isAlive())
        {
            if(editHP) { hp.decr(); }
            else { psn.decr(); }
        }
    }
    
    /**
     * Check if the player is alive.
     * 
     * @return true is the player is alive, false if he lost the game.
     */
    public boolean isAlive()
    {
        if(hp.isMin())
        {   
            return false;
        }
        else if(psn.isMax())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    
    //
    // Getters and setters
    //
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Counter getHp()
    {
        return hp;
    }

    public void setHp(Counter hp)
    {
        this.hp = hp;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Counter getPsn()
    {
        return psn;
    }

    public void setPsn(Counter psn)
    {
        this.psn = psn;
    }
    
}
