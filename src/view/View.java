package view;

import controler.Controler;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.Sprite;
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
            paintBackground(g);
            paintPlayers(g);
            paintButtons(g);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Paint the background
     *
     * @param g Painting tool
     */
    private void paintBackground(Graphics g) throws IOException
    {
        g.setColor(200, 200, 200);
        g.fillRect(0, 0, getWidth(), getHeight());

        Image bg = Image.createImage("/bg.png");
        g.drawImage(bg, 0, 0, Graphics.TOP | Graphics.LEFT);
        
        Image menubar = Image.createImage("/menubar.png");
        g.drawImage(menubar, 0, 0, Graphics.TOP | Graphics.LEFT);

    }

    /**
     * Paint the buttons
     *
     * @param g Painting tool
     */
    private void paintButtons(Graphics g) throws IOException
    {
        String path = (ctrl.getSelectedAction() == Controler.ACTION_HP) ? "/hp.png" : "/psn.png";


        Image action = Image.createImage(path);
        g.drawImage(action, getWidth() / 2, 0, Graphics.HCENTER | Graphics.TOP);


    }

    /**
     * Paint the players
     *
     * @param g Painting tool
     */
    private void paintPlayers(Graphics g) throws IOException
    {
        int npl = players.size();
        int dy = (getHeight() - 32) / npl;
        int y = 32;
        int sty = 0;
        Player p;

        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));

        for (int i = 0; i < npl; i++)
        {
            p = (Player) players.elementAt(i);

            // Highlighted player ?
            if (i == selectedPlayer)
            {
                //g.setColor(0, 125, 125);
                //g.fillRect(0, y, getWidth(), dy);
                Image defeat = Image.createImage(Image.createImage("/select.png"), 0, y, getWidth(), dy, Sprite.TRANS_NONE);
                g.drawImage(defeat, 0, y, Graphics.TOP | Graphics.LEFT);
            }

            // Winner player ?
            if (i == winner)
            {
                //g.setColor(255, 125, 0);
                //g.fillRect(0, y, getWidth(), dy);
                Image defeat = Image.createImage(Image.createImage("/victory.png"), 0, y, getWidth(), dy, Sprite.TRANS_NONE);
                g.drawImage(defeat, 0, y, Graphics.TOP | Graphics.LEFT);
            }

            // Defeated player ?
            if (!p.isAlive())
            {
                Image defeat = Image.createImage(Image.createImage("/defeat.png"), 0, y, getWidth(), dy, Sprite.TRANS_NONE);
                g.drawImage(defeat, 0, y, Graphics.TOP | Graphics.LEFT);
                //g.setColor(125, 0, 0);
                //g.fillRect(0, y, getWidth(), dy);

            }

            // Drawing separator line
            g.setColor(30,30,30);
            g.fillRect(0, y, getWidth(), 2);
            //g.drawLine(0, y, getWidth(), y);

            // Increment to the next line
            y += dy;

            // Drawing the player
            sty = y - (3 * dy / 5); // Find the baseline
            paintPlayer(sty, p, g);
        }

    }

    /**
     * Paint a player
     *
     * @param y Baseline for writing
     * @param p Player to draw
     * @param g Drawing tool
     */
    public void paintPlayer(int y, Player p, Graphics g)
    {
        
        String nom = p.getName();
        String hp = p.getHp() + "";
        String psn = p.getPsn() + "";

        int marginleft = getWidth() / 10;
        int xnom = 0 + marginleft;
        int xhp = getWidth() / 2 + marginleft;
        int xpsn = getWidth() * 3 / 4 + marginleft;

        g.setColor(200,200,200);
        
        g.drawString(nom, xnom, y, 0);
        g.drawString(hp, xhp, y, 0);
        g.drawString(psn, xpsn, y, 0);
    }

    /**
     * Events "onKeyPressed"-like
     *
     * @param keyCode
     */
    public void keyPressed(int keyCode)
    {
        if (winner < 0)
        {
            // Numpad
            switch (keyCode)
            {
                case KEY_NUM1:
                    ctrl.selectPlayer(0);
                    ctrl.edit(true);
                    break;
                case KEY_NUM2:
                    ctrl.selectPlayer(0);
                    ctrl.switchUI(new EditNameUI(this, ctrl));
                    break;
                case KEY_NUM3:
                    ctrl.selectPlayer(0);
                    ctrl.edit(false);
                    break;
                case KEY_NUM4:
                    ctrl.selectPlayer(1);
                    ctrl.edit(true);
                    break;
                case KEY_NUM5:
                    ctrl.selectPlayer(1);
                    ctrl.switchUI(new EditNameUI(this, ctrl));
                    break;
                case KEY_NUM6:
                    ctrl.selectPlayer(1);
                    ctrl.edit(false);
                    break;
                case KEY_NUM7:
                    ctrl.selectPlayer(2);
                    ctrl.edit(true);
                    break;
                case KEY_NUM8:
                    ctrl.selectPlayer(2);
                    ctrl.switchUI(new EditNameUI(this, ctrl));
                    break;
                case KEY_NUM9:
                    ctrl.selectPlayer(2);
                    ctrl.edit(false);
                    break;
                case KEY_STAR:
                    ctrl.selectPlayer(3);
                    ctrl.edit(true);
                    break;
                case KEY_NUM0:
                    ctrl.selectPlayer(3);
                    ctrl.switchUI(new EditNameUI(this, ctrl));
                    break;
                case KEY_POUND:
                    ctrl.selectPlayer(3);
                    ctrl.edit(false);
                    break;
            }

            // Arrow & Ok button
            int nextPl;
            int gameCode = getGameAction(keyCode);
            switch (gameCode)
            {
                case FIRE:
                    ctrl.switchAction();
                    break;
                case UP:
                    ctrl.edit(true);
                    break;
                case DOWN:
                    ctrl.edit(false);
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
