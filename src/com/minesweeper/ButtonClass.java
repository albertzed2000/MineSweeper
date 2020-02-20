//name this file ButtonClass
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minesweeper;
import java.awt.Dimension;
import javax.swing.JButton;
import static com.minesweeper.MineSweeperPart1.theGame;

/**
 *
 * @author albert.zheng
 */
public class ButtonClass extends JButton {
    int value;
    private int[] location;
    private boolean exposed;
    private boolean isFlagged;

    public ButtonClass()

    {
        setPreferredSize(new Dimension(30, 30));
        exposed = false;
        isFlagged = false;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return value;
    }
    public void setLocation(int row, int col) // sets the location of the tile.
    {
        location = new int[2];
        location[0] = row;
        location[1] = col;
    }
    public int[] getLocat() // returns location as [row, column]
    {
        return location;
    }
    public void expose() // exposes the current tile
    {
        exposed = true;
        theGame.nonMineTiles--;
        if (value==0)
        {
            setIcon(theGame.MINE_ICON);
        }
        else if (value == 1)
        {
            setIcon(theGame.TILE_ONE);
        }
        else if (value == 2)
        {
            setIcon(theGame.TILE_TWO);
        }
        else if (value == 3)
        {
            setIcon(theGame.TILE_THREE);
        }
        else if (value == 4)
        {
            setIcon(theGame.TILE_FOUR);
        }
        else if (value == 5)
        {
            setIcon(theGame.TILE_FIVE);
        }
        else if (value == 6)
        {
            setIcon(theGame.TILE_SIX);
        }
        else if (value == 7)
        {
            setIcon(theGame.TILE_SEVEN);
        }
        else if (value == 8)
        {
            setIcon(theGame.TILE_EIGHT);
        }
        else if (value == -1)
        { //if a mine is exposed, end the game
            if (isFlagged)
            {
                setIcon(theGame.RED_MINE_ICON);
                theGame.nonMineTiles++;
                return;
            }
            else
            {
                setIcon(theGame.MINE_ICON);
                theGame.nonMineTiles++;
            }
        }

        if (theGame.nonMineTiles == 0)
        {
            theGame.winGame();
        }
    }
    public boolean isExposed() // returns whether the current tile is exposed
    {
        return exposed;
    }

    public void flag() // sets flagged status to opposite status of current
    {
        if (isFlagged == false)
        {
            isFlagged = true;
        }
        else
        {
            isFlagged = false;
        }
    }
    public boolean isFlagged() // returns whether flag is flagged
    {
        return isFlagged;
    }
}