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
    
    /**
     * Constructor for objects of class Menu.
     */
    public Menu()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 675, 1); 
            
        GifImage teto = new GifImage("LETRIS.gif");
        setBackground(teto.getCurrentImage());
        
        GreenfootImage start = new GreenfootImage("startButton.png");
        startButton = new Button("startButton", start);
        
        GreenfootImage controller = new GreenfootImage("controls.png");
        controller.scale(300, 200);
        controlsButton = new Button("controlsButton", controller);
        
        addObject(startButton, 200, 480);
        addObject(controlsButton, 1000, 480);
    }
    
    /**
     * act method for the class Menu
     * This method is continuously run.
     */
    public void act(){
        Animation();
        if(startButton.isPressed()){ //if user presses the start button
            Greenfoot.setWorld(new Game()); //start the game
        }else if (controlsButton.isPressed()){ // if the user presses the controls button
            Greenfoot.setWorld(new Controls()); //show the controls
        }
    } 
    
    public void Animation(){        
        setBackground(backgroundGif.getCurrentImage());
    }
}
