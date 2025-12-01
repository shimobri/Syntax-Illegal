import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * This world class is the screen that the player sees
 * when he/she loses.
 * 
 * @author Tiger Zhao
 * @version January 5, 2015
 */
public class LoseScreen extends World
{
    private Button restartButton;
    private Button exitButton;
    
    /**
     * Constructor for objects of class LoseScreen.
     * @param score the score that the user had when they lost the game
     * @param level the current level of the user when they lost the game
     * @param linesCleared the total number of lines cleared when they lost the game
     */
    public LoseScreen(int score, int level,int linesCleared)
    {    
        super(600, 700, 1); //initialize world
        
        //Displaying text below
        addObject(new Text("Your Score:" +score),300,200);
        addObject(new Text("Your Level:" +level),300,250);
        addObject(new Text("Total Lines Cleared:" +linesCleared),300,300);
        
        //Display the buttons for the user to press
        restartButton = new Button("restartButton", new GreenfootImage("restartButton.png"));
        exitButton = new Button("exitButton", new GreenfootImage("exitButton.png"));
        addObject(restartButton, 300,400);
        addObject(exitButton, 300,475);
    }
    
    /**
     * act method for the class LoseScreen
     * This method is continuously run.
     */
    public void act(){
        if(restartButton.isPressed()){
            Greenfoot.setWorld(new Game());//start a new game
        }else if(exitButton.isPressed()){
            Greenfoot.setWorld(new Menu()); //return to menu
        }
    }
}
