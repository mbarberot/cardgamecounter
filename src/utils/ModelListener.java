package utils;

import java.util.Vector;

/**
 * Listener used for updating views for the MVC architecture.
 * 
 * 
 * @author kawa
 */
public interface ModelListener
{
    public void updatePlayers(Vector players, int selectedPlayer, int winner);
}
