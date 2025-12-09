import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WinScreen here.
 * 
 * @author Ian Choi
 * @version 12/8/2025
 */
public class WinScreen extends World
{
    GifImage backgroundGif = new GifImage("win.gif");
    
    private Button restartButton;
    private Button exitButton;
    
    public WinScreen(int score, int level,int linesCleared)
    {    
        super(600, 700, 1); //initialize world
        
        //Displaying text below
        addObject(new Text("You Won!"),300,340);
        addObject(new Text("Your Score:" +score),300,390);
        addObject(new Text("Your Level:" +level),300,440);
        addObject(new Text("Total Lines Cleared:" +linesCleared),300,490);
        
        //Display the buttons for the user to press
        restartButton = new Button("restartButton", new GreenfootImage("restartButton.png"));
        exitButton = new Button("exitButton", new GreenfootImage("exitButton.png"));
        addObject(restartButton, 300,540);
        addObject(exitButton, 300,640);
    }
    
    /**
     * act method for the class LoseScreen
     * This method is continuously run.
     */
    public void act(){
        Animation();
        if(restartButton.isPressed()){
            Greenfoot.setWorld(new Game());//start a new game
        }else if(exitButton.isPressed()){
            Greenfoot.setWorld(new Menu()); //return to menu
        }
    }
    
    public void Animation(){        
        setBackground(backgroundGif.getCurrentImage());
    }
}
