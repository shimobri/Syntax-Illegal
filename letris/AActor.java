import greenfoot.*;

public class AActor extends Actor
{
    static GreenfootImage[][] imageSet = new GreenfootImage[4][10]; // to hold the image sets
    
    /** ********************  creates images for animations  ***************** */
    static
    {
        GreenfootImage[] baseImgs = new GreenfootImage[10];
        GreenfootImage base = new GreenfootImage(100, 100);
        base.setColor(Color.CYAN);
        base.fill();
        base.setColor(Color.BLACK);
        for (int x=0; x<100; x+=10) base.fillRect(x, 0, 3, 100);
        for (int i=0; i<10; i++)
        {
            baseImgs[i] = new GreenfootImage(60, 60);
            baseImgs[i].drawImage(base, i-10, 0);
            baseImgs[i].drawRect(0, 0, 59, 59);
        }
        imageSet[0] = baseImgs;
        for (int i=0; i<10; i++)
        {
            imageSet[1][i] = new GreenfootImage(baseImgs[i]);
            imageSet[1][i].rotate(90);
        }
        for (int i=0; i<10; i++)
        {
            GreenfootImage diag = new GreenfootImage(100, 100);
            diag.drawImage(base, i-10, 0);
            diag.rotate(45);
            imageSet[2][i] = new GreenfootImage(60, 60);
            imageSet[2][i].drawImage(diag, -20, -20);
            imageSet[2][i].drawRect(0, 0, 59, 59);
        }
        for (int i=0; i<10; i++)
        {
            GreenfootImage diag = new GreenfootImage(100, 100);
            diag.drawImage(base, i-10, 0);
            diag.rotate(135);
            imageSet[3][i] = new GreenfootImage(60, 60);
            imageSet[3][i].drawImage(diag, -20, -20);
            imageSet[3][i].drawRect(0, 0, 59, 59);
        }
    }
    /** ********************************************************************** */
    
    private Animation animation; // the animation object for this actor
    // fields added to control changes in animation
    private int frameSet; // tracks which set is currently being used
    private int cycleCount; // counts complete animation cycles executed
    
    
    public AActor()
    {
        // create and set up animation object to animate this actor
        animation = new Animation(this, imageSet[0]);
        animation.setCycleActs(50);
        animation.setActiveState(true);
    }

    public void act()
    {
        // animate this actor; sets a flag when an animation cycle completes
        boolean done = animation.run();
        
        /** *********************   controls animation   ********************* */
        // changing speed regulation
        int cycleActs = animation.getCycleActs(); // gets currently set value of acts per animation cycle
        int dca = 0; // for change amount
        if (Greenfoot.isKeyDown("up") && cycleActs < 1000) dca++;
        if (Greenfoot.isKeyDown("down") && cycleActs > 0) dca--;
        animation.setCycleActs(cycleActs+dca);
        // changing animation image set
        if (frameSet != 3 && Greenfoot.isKeyDown("3"))
        {
            frameSet = 3;
            animation.setFrames(imageSet[3]);
        }
        if (frameSet != 2 && Greenfoot.isKeyDown("2"))
        {
            frameSet = 2;
            animation.setFrames(imageSet[2]);
        }
        if (frameSet != 1 && Greenfoot.isKeyDown("1"))
        {
            frameSet = 1;
            cycleCount = 0;
            animation.setFrames(imageSet[1]);
        }
        if ((frameSet != 0 && Greenfoot.isKeyDown("0")) || (frameSet == 1 && done && (++cycleCount == 6)))
        {
            frameSet = 0;
            animation.setFrames(imageSet[0]);
        }
        // removing on click
        if (Greenfoot.mouseClicked(this)) getWorld().removeObject(this);
        /** ****************************************************************** */
    }
}