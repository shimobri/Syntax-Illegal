import greenfoot.*;

public class Welt extends World
{
    Animation animation; // the animation object for this world
    boolean keyDown_D; // tracks the state of the 'd' key
    
    public Welt()
    {
        super(600, 600, 1);
        // initialize image array for world background animation
        GreenfootImage[] baseImgs = new GreenfootImage[10]; 
        // add images to array
        /** ***************    create frames    ******************** */
        for (int i=0; i<10; i++)
        {
            baseImgs[i] = new GreenfootImage(600, 600);
            baseImgs[i].setColor(new Color(240, 240, 240));
            baseImgs[i].fill();
        }
        for (int i=-120; i<299; i++)
        {
            baseImgs[(i+120)%10].setColor(Color.BLACK);
            baseImgs[(i+120)%10].fillOval(i, i, 600-2*i-1, 600-2*i-1);
            if (i > 296) continue;
            baseImgs[(i+120)%10].setColor(new Color(240, 240, 240));
            baseImgs[(i+120)%10].fillOval(i+3, i+3, 600-2*i-7, 600-2*i-7);
        }
        /** ******************************************************** */
        
        // set up animation object for animating the world background
        animation = new Animation(this, baseImgs);
        animation.setCycleActs(50);
        animation.setActiveState(true);
        // add animated actor to world
        addObject(new AActor(), getWidth()/2, getHeight()/4);
    }
    
    public void act()
    {
        // animate world background
        animation.run();
        
        /** *************    animation controls    ***************** */
        // animation speed control
        int cycleActs = animation.getCycleActs();
        int dca = 0;
        if (Greenfoot.isKeyDown("right") && cycleActs < 1000) dca++;
        if (Greenfoot.isKeyDown("left") && cycleActs > 0) dca--;
        animation.setCycleActs(cycleActs+dca);
        // animation direction control
        if (keyDown_D != Greenfoot.isKeyDown("d"))
        {
            keyDown_D = !keyDown_D;
            if (keyDown_D) animation.reverseImageOrder();
        }
        /** ******************************************************** */
    }
}