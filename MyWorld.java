import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private double gameTime = 0.0;
    private int score = 0;
    
    private boolean rotate;
    private boolean drop;
    private boolean store;
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 40x40 pixels
        super(12, 22, 40);
        prepare();

        // Set a gray background (WIP)
        /* GreenfootImage cell = new GreenfootImage(1, 1);
        cell.setColor(Color.DARK_GRAY);
        cell.fill();
        setBackground(cell); */
        
        GreenfootImage cell = getBackground();
        cell.setColor(Color.WHITE); // Set color for grid lines

        // Draw horizontal lines
        for (int y = 1; y < getHeight(); y++) {
            cell.drawLine(0, y * getCellSize(), getWidth() * getCellSize(), y * getCellSize());
        }

        // Draw vertical lines
        for (int x = 1; x < getWidth(); x++) {
            cell.drawLine(x * getCellSize(), 0, x * getCellSize(), getHeight() * getCellSize());
        }
    }
    
    private void prepare() {
        Tetromino mino = new Tetromino();
        addObject(mino, 6, 0);
        mino.setLocation(6, 0);
        mino.turn(90);
        
        if (mino.getY() == 23) {
            removeObject(mino);
        }
    }
}
