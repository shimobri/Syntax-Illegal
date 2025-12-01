import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * This class is used to represent a button that the user can press.
 * 
 * @author Tiger Zhao
 * @version January 5, 2015
 */
public class Button extends Actor
{
    private boolean pressed =false;
    private String name ="";
    
    /**
     * Constructor for objects of class Button.
     * @param name the name of the button
     * @param image the image to set the button as
     */
    public Button(String name, GreenfootImage image){
        this.name = name;
        setImage(image);//set the image
    }

    /**
     * act method for the class Button
     * This method is continuously run.
     */
    public void act(){
        //determine if the player has pressed this button.
        if(!pressed){
            MouseInfo mi = Greenfoot.getMouseInfo();
            if(mi !=null && mi.getActor() ==this &&mi.getButton()==1){
                pressed = true;
            }
        }
    }

    /**
     * @return whether or not the button has been pressed
     */
    public boolean isPressed(){
        return pressed;
    }
}
