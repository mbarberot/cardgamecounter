package view;

import controler.Controler;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Font;
import model.Model;
import model.Player;
import utils.ModelListener;

/**
 * Graphical User Interface
 *
 * @author kawa
 */
public class View extends Canvas implements ModelListener, CommandListener
{
    
    private static final int S_MENUBAR = 15;
    private static final int S_SEPARATOR = 2;
    
    private int[] xAreas;
    private int[] yAreas;
    
    /**
     * Controler
     */
    private Controler ctrl;
    /**
     * Players (for painting purpose)
     */
    private Vector players;
    /**
     * Selected Player's id (for painting purpose)
     */
    private int selectedPlayer;
    /**
     * Winner Player's id (for painting purpose) Negative if there is no winner
     */
    private int winner;
    private Command pl2;
    private Command pl3;
    private Command pl4;
    private Command flipCoin;
    private Command rollDice;
    private Command settings;
    private Command exit;

    /**
     * Constructor
     */
    public View()
    {
        this.setTitle("MTG counter");               

        this.pl2 = new Command("2 players", Command.ITEM, 0);
        this.pl3 = new Command("3 players", Command.ITEM, 0);
        this.pl4 = new Command("4 players", Command.ITEM, 0);
        this.flipCoin = new Command("Flip a coin", Command.ITEM, 0);
        this.rollDice = new Command("Roll a dice", Command.ITEM, 0);
        this.settings = new Command("Settings", Command.ITEM, 0);
        this.exit = new Command("Exit", Command.EXIT, 0);

        this.addCommand(settings);
        this.addCommand(pl2);
        this.addCommand(pl3);
        this.addCommand(pl4);
        this.addCommand(flipCoin);
        this.addCommand(rollDice);
        this.addCommand(exit);

        this.setCommandListener(this);
        
    }

    /**
     * Event treatment
     *
     * @param c Command fired
     * @param d Display wich had an event
     */
    public void commandAction(Command c, Displayable d)
    {
        if (d == this)
        {
            if (c == exit)
            {
                ctrl.exit();
            } else if (c == pl2)
            {
                ctrl.setMode(Model.MODE_2PL);
            } else if (c == pl3)
            {
                ctrl.setMode(Model.MODE_3PL);
            } else if (c == pl4)
            {
                ctrl.setMode(Model.MODE_4PL);
            } else if (c == settings)
            {
                ctrl.switchUI(new SettingsUI(this, ctrl));
            } else if (c == flipCoin)
            {
                ctrl.switchUI(new RandomUI(this, ctrl, RandomUI.FLIP_COIN));
            } else if (c == rollDice)
            {
                ctrl.switchUI(new RandomUI(this, ctrl, RandomUI.ROLL_DICE));
            }
        }
    }

    /**
     * Paint the canvas
     *
     * @param g Painting tool
     */
    public void paint(Graphics g)
    {
        try
        {
            defineAreas();
            paintBackground(g);
            paintPlayers(g);
            paintButtons(g);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * Instanciate both xAreas and yAreas arrays.
     */
    private void defineAreas()
    {
        defineXAreas();
        defineYAreas();
    }
    
    /**
     * Instanciate the xAreas array that defines horizontal areas in the canvas.
     */
    private void defineXAreas()
    {               
        int marginLeft = getWidth() * 5 / 100 ;
        int xPosHP = getWidth() * 50/100 + marginLeft ;
        int xPosPSN = getWidth() * 75/100 + marginLeft ;        
        
        this.xAreas = new int[]{0, xPosHP, xPosPSN, getWidth()} ;
    }
    
    /**
     * Instanciate the yAreas array that defines vertical areas in the canvas.
     */
    private void defineYAreas()
    {      
        int npl = players.size();
        int nAreas = npl + 2;
        int y = S_MENUBAR ;
        int dy = (getHeight() - S_MENUBAR) / npl ;
        
        this.yAreas = new int[nAreas] ;
        
        // Première case
        yAreas[0] = 0;  
        // Cases 1 -> n
        for(int i = 1; i < nAreas; i++)
        {
            yAreas[i] = y;
            y += dy ;            
        }                             
    }        

    /**
     * Paint the background
     *
     * @param g Painting tool
     */
    private void paintBackground(Graphics g) throws IOException
    {
        g.setColor(50, 50, 50);
        g.fillRect(0, S_MENUBAR, getWidth(), getHeight());
        
        g.setColor(0,125,0);
        g.fillRect(0, 0, getWidth(), S_MENUBAR);
    }

    /**
     * Paint the buttons
     *
     * @param g Painting tool
     */
    private void paintButtons(Graphics g) throws IOException
    {
        /*
        String path;
        if(hasPointerEvents()) { path = (ctrl.getSelectedAction() == Controler.ACTION_DEC) ? "/minus.png" : "/plus.png"; }
        else { path = (ctrl.getSelectedType() == Controler.TYPE_HP) ? "/hp.png" : "/psn.png"; }


        Image action = Image.createImage(path);
        g.drawImage(action, getWidth() / 2, 0, Graphics.HCENTER | Graphics.TOP);
        */
        
        String action;
        if(hasPointerEvents())
        {
            action = (ctrl.getSelectedAction() == Controler.ACTION_DEC) ? "-" : "+" ;
        }
        else
        {
            action = (ctrl.getSelectedType() == Controler.TYPE_HP) ? "HP" : "PSN" ;
        }
        
        g.setColor(200,200,200);
        g.drawString(action, getWidth()*45/100, 1, 0);
        
    }

    
    
    /**
     * Paint the players
     *
     * @param g Painting tool
     */
    private void paintPlayers(Graphics g) throws IOException
    {
        int npl = players.size();
        int dy = (getHeight() - S_MENUBAR - (npl * S_SEPARATOR)) / npl;
        int y = yAreas[1];
        int sty = 0;
        Player p;

        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

        for (int i = 0; i < npl; i++)
        {
            p = (Player) players.elementAt(i);

            // Drawing separator line
            g.setColor(30,30,30);
            g.fillRect(0, yAreas[i+1], getWidth(), S_SEPARATOR);                       
         
            // 
            // Have to draw anything spécial ?
            //
            if (i == selectedPlayer || i == winner || !p.isAlive()) 
            {                
                if (i == selectedPlayer)// Highlighted player ?
                {
                    g.setColor(0, 125, 125);
                } 
                
                if (i == winner) // Winner player ?
                {
                    g.setColor(255, 125, 0);
                } 
                
                if(!p.isAlive()) // Defated player ?
                {
                    g.setColor(125, 0, 0);
                }
                
                g.fillRect(0, yAreas[i+1], getWidth(), dy);
            }

            // Drawing the player           
            paintPlayer(p, g);
        }
    }

    /**
     * Paint a player
     *
     * @param p Player to draw
     * @param g Drawing tool
     */
    public void paintPlayer( Player p, Graphics g)
    {
        String nom = p.getName();
        String hp = p.getHp() + "";
        String psn = p.getPsn() + "";

        int marginleft = getWidth() * 5 / 100;
        int margintop = getHeight() * 5 / 100;
        int baseline = yAreas[p.getId()+1] + margintop;        
        int xnom = xAreas[0] + marginleft;
        int xhp = xAreas[1] + marginleft;
        int xpsn = xAreas[2] + marginleft;

        g.setColor(200,200,200);
        
        g.drawString(nom, xnom, baseline, 0);
        g.drawString(hp, xhp, baseline, 0);
        g.drawString(psn, xpsn, baseline, 0);
    }
    
    /**
     * Tactile events "on pressed"
     * 
     * @param x value on x-axis
     * @param y value on y-axis
     */
    protected void pointerPressed(int x, int y)
    {
        int xArea = getXArea(x);
        int yArea = getYArea(y);
               
        //
        // Is Menubar zone pressed ?
        //
        if(yArea == 0)
        {
            ctrl.switchAction();
        }
        else
        {
            // Important : conversion from yArea to player id !
            // yArea = 1 means player one's area but player one's id = 0 !
            ctrl.selectPlayer(yArea - 1);
                        
            switch(xArea)
            {
                case 0 : // Player's name area
                    ctrl.switchUI(new EditNameUI(this, ctrl));
                    break;
                case 1 : // Player's HP -> do action
                    ctrl.setSelectedType(Controler.TYPE_HP);
                    ctrl.edit();
                    break;
                case 2 :
                    ctrl.setSelectedType(Controler.TYPE_PSN);
                    ctrl.edit();
                    break;
            }
        }
    }
        
    /**
     * Return the horizontal area where the pointer was pressed
     * @param x - x coord from the pointer
     * @return -1 = Error, 0 = PlayerName, 1 = PlayerHP, 2 = PlayerPSN
     */
    private int getXArea (int x)
    {
        int i;
        int len;       
        
        i = 0;
        len = xAreas.length;       
        while(x > xAreas[i] && i < len)
        {
            i++;
        }
        
        return i-1;
    }
    
    /**
     * Return the vertical area where the pointer was pressed
     * @param y - y coord from the pointer
     * @return -1 = Error, 0 = Menubar, 1 = PlayerOne, 2 = PlayerTwo ...etc
     */
    private int getYArea (int y)
    {
        int i;
        int len;
        
        i = 0;
        len = yAreas.length;        
        while(y > yAreas[i] && i < len)
        {
            i++;
        }
        
        return i-1;
    }
    
  
    
   
    
    /**
     * Events "onKeyPressed"-like
     *
     * @param keyCode
     */
    protected void keyPressed(int keyCode)
    {
        if (winner < 0)
        {
            // Numpad
            switch (keyCode)
            {
                case KEY_NUM1:
                    ctrl.selectPlayer(0);
                    ctrl.setSelectedAction(Controler.ACTION_INC);
                    ctrl.edit();
                    break;
                case KEY_NUM2:
                    ctrl.selectPlayer(0);
                    ctrl.switchUI(new EditNameUI(this, ctrl));
                    break;
                case KEY_NUM3:
                    ctrl.selectPlayer(0);
                    ctrl.setSelectedAction(Controler.ACTION_DEC);
                    ctrl.edit();
                    break;
                case KEY_NUM4:
                    ctrl.selectPlayer(1);
                    ctrl.setSelectedAction(Controler.ACTION_INC);
                    ctrl.edit();
                    break;
                case KEY_NUM5:
                    ctrl.selectPlayer(1);
                    ctrl.switchUI(new EditNameUI(this, ctrl));
                    break;
                case KEY_NUM6:
                    ctrl.selectPlayer(1);
                    ctrl.setSelectedAction(Controler.ACTION_DEC);
                    ctrl.edit();
                    break;
                case KEY_NUM7:
                    ctrl.selectPlayer(2);
                    ctrl.setSelectedAction(Controler.ACTION_INC);
                    ctrl.edit();
                    break;
                case KEY_NUM8:
                    ctrl.selectPlayer(2);
                    ctrl.switchUI(new EditNameUI(this, ctrl));
                    break;
                case KEY_NUM9:
                    ctrl.selectPlayer(2);
                    ctrl.setSelectedAction(Controler.ACTION_DEC);
                    ctrl.edit();
                    break;
                case KEY_STAR:
                    ctrl.selectPlayer(3);
                    ctrl.setSelectedAction(Controler.ACTION_INC);
                    ctrl.edit();
                    break;
                case KEY_NUM0:
                    ctrl.selectPlayer(3);
                    ctrl.switchUI(new EditNameUI(this, ctrl));
                    break;
                case KEY_POUND:
                    ctrl.selectPlayer(3);
                    ctrl.setSelectedAction(Controler.ACTION_DEC);
                    ctrl.edit();
                    break;
                default:
                    // Arrow & Ok button
                    int nextPl;
                    int gameCode = getGameAction(keyCode);
                    switch (gameCode)
                    {
                        case FIRE:
                            ctrl.switchType();
                            break;
                        case UP:
                            ctrl.setSelectedAction(Controler.ACTION_INC);
                            ctrl.edit();
                            break;
                        case DOWN:
                            ctrl.setSelectedAction(Controler.ACTION_DEC);
                            ctrl.edit();
                            break;
                        case LEFT:
                            nextPl = nextPlayer(false);
                            ctrl.selectPlayer(nextPl);                          
                            break;
                        case RIGHT:
                            nextPl = nextPlayer(true);
                            ctrl.selectPlayer(nextPl);
                            break;
                    }
            }
        }
    }

    /**
     * Find the next player to highlight.
     *
     * @param inc True to look for the next, false to the previous
     * @return Player id
     */
    private int nextPlayer(boolean inc)
    {
        int i = (selectedPlayer == Model.PL_NONE ? (inc ? -1 : 1) : selectedPlayer);
        int len = players.size();
        int cpt = 0;
        boolean found = false;

        while (cpt < len && !found)
        {
            if (inc)
            {
                i = (i + 1 >= len ? 0 : i + 1);
            } else
            {
                i = (i - 1 < 0 ? len - 1 : i - 1);
            }

            if (((Player) players.elementAt(i)).isAlive())
            {
                found = true;
            }
            cpt++;
        }
        return i;
    }

    /**
     * Update fired from the model
     *
     * @param players Players
     * @param selectedPlayer Highglighted player
     */
    public void updatePlayers(Vector players, int selectedPlayer, int winner)
    {
        this.players = players;
        this.selectedPlayer = selectedPlayer;
        this.winner = winner;
        this.repaint();
    }

    //
    // Getters and Setters
    //
    public void setControler(Controler ctrl)
    {
        this.ctrl = ctrl;
    }

    public int getSelectedPlayer()
    {
        return selectedPlayer;
    }

    public Vector getPlayers()
    {
        return players;
    }
}
