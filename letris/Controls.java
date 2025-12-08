import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Display controls
 * 
 * @author Tiger Zhao
 * @version January 5, 2015
 */
public class Controls extends World
{
    GifImage backgroundGif = new GifImage("controls.gif");
    private Button backButton;
    
    /**
     * Constructor for objects of class Controls.
     */
    public Controls(){    
        super(1152, 620, 1); 
        
        showControls();
        backButton = new Button("backButton" ,new GreenfootImage("backButton.png"));
        addObject(backButton, 580, 600);
    }
    
    /**
     * act method for the class Controls
     * This method is continuously run.
     */
    public void act(){
        Animation();
        if(backButton.isPressed()){
            Greenfoot.setWorld(new Menu());
        }
    }
    
    /**
     * Displays the controls in text on the screen.
     */
    public void showControls(){
        int size = 30;//the font size to be used
        
        //display the text
        addObject(new Text("Welcome to Letris. Clear as many horizontal lines as possible.",size), 580, 100);
        addObject(new Text("Use the \"a\" and \"d\" keys to move the block left/right.",size), 580, 150);
        addObject(new Text("Use the \"s\" arrow key to quickly slide the block down.",size), 580, 200);
        addObject(new Text("Use the space bar to instantly drop the block.",size), 580, 250);
        addObject(new Text("Use the shift key to store and take out blocks.",size), 580, 300);
        addObject(new Text("Use the \"w\" key rotate the block clockwise.",size), 580, 350);
        addObject(new Text("Use \"z\" to rotate the block counter clockwise.",size), 580, 400);
        addObject(new Text("Press the \"p\" to pause/unpause the game and access the menu.",size), 580, 450);
    }
    
    public void Animation(){        
        setBackground(backgroundGif.getCurrentImage());
    }
}
