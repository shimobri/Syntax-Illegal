import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tetromino here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tetromino extends Actor
{
    /**
     * Act - do whatever the Tetromino wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    boolean[][] boardOccupied = new boolean[100][200];
    
    public Tetromino() {
        GreenfootImage image = getImage();

        // Scale the image to a new width and height
        image.scale(40, 40);

        // Set the scaled image back to the actor
        setImage(image);
        
        int type = ((int) Math.random() * 3) + 1;
        
        /*if (type == 1) {
            
        }*/
    }
    
    public void act()
    {   
        int x = getX(), y = getY();
        
        move(1);
        if (Greenfoot.isKeyDown("a")) {
            setLocation(x - 1, y + 1);
        }
        else if (Greenfoot.isKeyDown("d")) {
            setLocation(x + 1, y + 1);
        }        
        Greenfoot.delay(5);
    }
}
