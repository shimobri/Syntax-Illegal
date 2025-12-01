import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Use to display text
 * 
 * @author 
 * @version
 */
public class Text extends Actor
{
    /**
     * A constructor for the Text class
     * @param text the text to be displayed
     */
    public Text(String text){
        setImage(new GreenfootImage(text, 36, Color.WHITE,null));//display the text
    }
    
    /**
     * Another constructor for the Text class
     * @param text the text to be displayed
     * @param size the font size to be used
     */
    public Text(String text, int size){
        setImage(new GreenfootImage(text, size, Color.WHITE,null));//display the text
    }
}
