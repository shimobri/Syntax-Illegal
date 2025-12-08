import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Main Menu
 * 
 * @author Tiger Zhao
 * @version January 5, 2015
 */
public class Menu extends World
{
    GifImage backgroundGif = new GifImage("LETRIS.gif");
    
    private Button startButton;
    private Button controlsButton;
    
    private static GreenfootSound introMusic = new GreenfootSound("koro.mp3");
    
    /**
     * Constructor for objects of class Menu.
     */
    public Menu()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1152, 620, 1); 
        
        GreenfootImage start = new GreenfootImage("startButton.png");
        start.scale(450, 410);
        startButton = new Button("startButton", start);
        
        GreenfootImage controller = new GreenfootImage("controller.png");
        controller.scale(250, 180);
        controlsButton = new Button("controlsButton", controller);
        
        addObject(startButton, 265, 250);
        addObject(controlsButton, 265, 420);
    }
    
    /**
     * act method for the class Menu
     * This method is continuously run.
     */
    public void act(){
        Animation();
        introMusic.playLoop();

        if(startButton.isPressed()){ //if user presses the start button
            Greenfoot.setWorld(new Game()); //start the game
            introMusic.stop();
        }else if (controlsButton.isPressed()){ // if the user presses the controls button
            Greenfoot.setWorld(new Controls()); //show the controls
        }
    } 
    
    public void Animation(){        
        setBackground(backgroundGif.getCurrentImage());
    }
}
