/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minesweeper;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static com.minesweeper.MineSweeperPart1.theGame;
import javax.swing.SwingUtilities;

/**
 *
 * @author albert.zheng
 */
public class MouseClickHandler implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me)
    {
        //checks which buttons have been clicked
        ButtonClass but;
        but = (ButtonClass) me.getSource(); //gets source of clicked button

        //check for right-click only condition
        if (me.isMetaDown())
        {
            if (but.isExposed() == false)
            {
                but.flag();
                if (but.isFlagged())
                {
                    but.setIcon(theGame.FLAG);
                }
                else
                {
                    but.setIcon(theGame.CLOSED_TILE);
                }
            }
        }
        if (SwingUtilities.isLeftMouseButton(me)) //check if left mouse button has been pressed 
        {
            if(SwingUtilities.isRightMouseButton(me))//check if right mouse button is also being pressed
            {   //if they're both pressed, then call a function that exposes surrounding unflagged and covered tiles
                theGame.exposeSurroundingUnflagged(but.getLocat());
            }
            if (but.isFlagged() == false) // if the leftclicked button isn't flagged, expose it
            {
                but.expose();
                if (but.value == -1)
                {
                    theGame.endGame();
                    but.setIcon(theGame.SAD_MINE_ICON);
                }
                else if (but.value == 0)
                {
                    theGame.exposeSurroundingZeros(but.getLocat());
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

}
//name this file MouseClickHandler