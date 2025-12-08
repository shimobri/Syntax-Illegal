import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A short popup for everytime the player levels up
 * 
 * @author Ian Choi
 * @version 12/6/2025
 */
public class LevelUpAnim extends Actor {
    private int timer = 120; // how long the animation stays
    private static GreenfootSound soundEffect = new GreenfootSound("levelup.mp3");

    public LevelUpAnim() {
        setImage("levelup.png");
    }

    public void act() {
        soundEffect.play();
        GreenfootImage img = getImage();
        img.scale(300, 250);
        img.setTransparency((int) ((timer / 120.0) * 255));
        
        timer--;
        if (timer <= 0) {
            getWorld().removeObject(this);
        }
    }
}