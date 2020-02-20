//name this file MineSweeperPart1
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minesweeper;
//peepeepoopoo
import static com.sun.webkit.graphics.WCImage.getImage;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.ImageIcon;


/**
 *
 * @author albert.zheng
 */
public class MineSweeperPart1
{
    static ArrayList<Integer> mineNumbers = new ArrayList<>();
    static int totalMines = 40;
    static int rows = 15;
    static int columns = 15;
    static Random rand = new Random();
    static int[][] grid = new int[rows][columns];
    static ButtonClass[][] buttons;
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel minePanel;
    static int nonMineTiles;
    static MineSweeperPart1 theGame;



    //images
    ImageIcon CLOSED_TILE = new ImageIcon("closedTileIcon.png");
    ImageIcon FLAG = new ImageIcon("flagIcon.png");
    ImageIcon TILE_ZERO = new ImageIcon("tileZero.png");
    ImageIcon TILE_ONE = new ImageIcon("tileOne.png");
    ImageIcon TILE_TWO = new ImageIcon("tileTwo.png");
    ImageIcon TILE_THREE = new ImageIcon("tileThree.png");
    ImageIcon TILE_FOUR = new ImageIcon("tileFour.png");
    ImageIcon TILE_FIVE = new ImageIcon("tileFive.png");
    ImageIcon TILE_SIX = new ImageIcon("tileSix.png");
    ImageIcon TILE_SEVEN = new ImageIcon("tileSeven.png");
    ImageIcon TILE_EIGHT = new ImageIcon("tileEight.png");
    ImageIcon MINE_ICON = new ImageIcon("mineIcon.png");
    ImageIcon RED_MINE_ICON = new ImageIcon("redMineIcon.png");
    ImageIcon SAD_MINE_ICON = new ImageIcon("sadMineIcon.png");


    public MineSweeperPart1()
    {
        buttons = new ButtonClass[rows][columns];
        prepareGUI();
        //scale image icons down to button size
        CLOSED_TILE = scaleImageIcon(CLOSED_TILE);
        FLAG = scaleImageIcon(FLAG);
        TILE_ZERO = scaleImageIcon(TILE_ZERO);
        TILE_ONE = scaleImageIcon(TILE_ONE);
        TILE_TWO = scaleImageIcon(TILE_TWO);
        TILE_THREE = scaleImageIcon(TILE_THREE);
        TILE_FOUR = scaleImageIcon(TILE_FOUR);
        TILE_FIVE = scaleImageIcon(TILE_FIVE);
        TILE_SIX = scaleImageIcon(TILE_SIX);
        TILE_SEVEN = scaleImageIcon(TILE_SEVEN);
        TILE_EIGHT = scaleImageIcon(TILE_EIGHT);
        MINE_ICON = scaleImageIcon(MINE_ICON);
        RED_MINE_ICON = scaleImageIcon(RED_MINE_ICON);
        SAD_MINE_ICON = scaleImageIcon(SAD_MINE_ICON);
        nonMineTiles = rows*columns - totalMines;
        setGrid();




    }
    private void prepareGUI(){
        mainFrame = new JFrame("Minesweeper");
        mainFrame.setSize(400,400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        minePanel = new JPanel();
        minePanel.setLayout(new GridLayout(rows, columns));
        headerLabel = new JLabel("", JLabel.CENTER);
        mainFrame.add(headerLabel);
        mainFrame.setVisible(true);
    }
    public void setGrid()
    {
        //Initialize mine positions randomly, put in arraylist
        int x = 0;
        while (x < totalMines)
        {
            int minePosition = rand.nextInt(rows*columns);
            if (!mineNumbers.contains(minePosition))
            {
                mineNumbers.add(minePosition);
                x++;
            }
        }
        for (int g = 0; g < (rows*columns); g++)
        {//create a 2d array
            grid[g/rows][g%columns]=0;
        }
        for (int p = 0; p < totalMines; p++)
        {
            grid[mineNumbers.get(p)/rows][mineNumbers.get(p)%columns]=-1;
        }
        for (int a = 0; a < mineNumbers.size(); a++)
        {
            int mineX = mineNumbers.get(a)/rows;
            int mineY = mineNumbers.get(a)%columns;
            //nested for loop which checks if a position around a mine exists
            //and then adds 1 to the value if it exists and is not a mine
            for (int q = -1; q < 2; q++)
            {
                for (int c = -1; c < 2; c++)
                {
                    if (mineX + q >= 0 && mineY + c >= 0 && mineX + q < rows && mineY + c <columns)
                    {
                        if (grid[mineX + q][mineY+c] != -1)
                        {
                            grid[mineX + q][mineY + c] += 1;
                        }
                    }
                }
            }
        }
        //print the positions of the mines
        System.out.println("Positions of the mines: " + mineNumbers);
        System.out.println("Minesweeper grid");
        for (int w = 0; w < rows; w++)
        {
            for (int k = 0; k < columns; k++)
            {
                //print the minesweeper grid
                System.out.print("[" + grid[w][k] + "]," );
            }
            System.out.println("");
        }

        //loop that creates buttons, and assigns them minesweeper game values
        for (int k = 0; k < rows; k++)
        {
            for (int t = 0; t < columns; t++)
            {
                ButtonClass mineButton = new ButtonClass();
                mineButton.setValue(grid[k][t]);
                mineButton.setLocation(k, t);
                mineButton.setIcon(CLOSED_TILE);
                mineButton.addMouseListener(new MouseClickHandler());
                minePanel.add(mineButton);
                buttons[k][t] = mineButton;

            }
        }

        mainFrame.add(minePanel);
        mainFrame.pack();
    }
    public void endGame()
    {
        for (ButtonClass[] buttonRow : buttons)
        {
            for (ButtonClass singleButton : buttonRow)
            {
                singleButton.expose();
            }
        }
    }
    public void exposeSurroundingZeros(int[] location)
    {
        //check numbers above, below, toleft, and toright of number
        for (int e = -1; e < 2; e++)
        {//horizontal for loop
            for (int g = -1; g < 2; g++)
            {//vertical for loop
                if (location[0]+e >= 0 && location[0]+e<rows && location[1]+g >= 0 && location[1]+g < columns)
                {
                    if (buttons[location[0]+e][location[1]+g].isExposed()==false){
                        buttons[location[0]+e][location[1]+g].expose();
                        if (buttons[location[0]+e][location[1]+g].getValue()==0)
                        {
                            exposeSurroundingZeros(buttons[location[0]+e][location[1]+g].getLocat());
                        }
                    }
                }
            }
        }
    }
    public ImageIcon scaleImageIcon(ImageIcon img)
    {
        Image imageFromIcon = img.getImage();
        ImageIcon scaledImageIcon = new ImageIcon(imageFromIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        return scaledImageIcon;
    }
    public void winGame ()
    {
        System.out.println("you win!");
        for (int x = 0; x < rows; x++)
        {
            for (int y = 0; y < columns; y++)
            {
                if (buttons[x][y].isExposed() != true)
                {
                    buttons[x][y].setIcon(FLAG);
                }
            }
        }
    }

    public void exposeSurroundingUnflagged(int[] location)
    {
        //check numbers above, below, toleft, and toright of number
        for (int e = -1; e < 2; e++)
        {//horizontal for loop
            for (int g = -1; g < 2; g++)
            {//vertical for loop
                if (location[0]+e >= 0 && location[0]+e<rows && location[1]+g >= 0 && location[1]+g < columns)
                {
                    if (buttons[location[0]+e][location[1]+g].isExposed()==false)
                    {
                        if (buttons[location[0]+e][location[1]+g].isFlagged()==false)
                        {
                            buttons[location[0]+e][location[1]+g].expose();
                        }
                        if (buttons[location[0]+e][location[1]+g].value==-1)
                        {
                            theGame.endGame();
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args)
    {
        theGame = new MineSweeperPart1();
        System.out.println(nonMineTiles);
    }

}