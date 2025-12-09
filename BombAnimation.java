import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Explosion here.
 * 
 * @author Ian Choi 
 * @version 12/8/2025
 */

public class BombAnimation extends Actor
{
    private GifImage gif = new GifImage("explosion.gif");;
    private int duration = 80; // frames to show GIF
    private int counter = 0;
    
    public BombAnimation(String type) {
        if (type == "N") duration = 300;
        
        setImage(gif.getCurrentImage());
    }
    
    public void act() {
        setImage(gif.getCurrentImage());
        counter++;
        if (counter >= duration) {
            getWorld().removeObject(this); // remove the actor
        }
    }
}
