import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * This class represents an individual block. Each tetris piece is made up of
 * multiple blocks.
 * 
 * @author Tiger Zhao
 * @version January 5, 2015
 */
public class Block extends Actor
{
    private String type;//variable to store the type of tetris piece that this block is a part of, can be: I,J,L,O,S,T or Z
    private GreenfootImage image; //the image of the block, will change depending on what tetris piece this block is a part of
    
    /**
     * The constructor of the Block class
     * @param type the type of the tetris piece that this block is a part of
     */
    public Block(String type){
        this.type = type;
        if(type.equals("L")){
            image = new GreenfootImage("blockL.png");
        }else if(type.equals("S")){
            image = new GreenfootImage("blockS.png");
        }else if(type.equals("O")){
            image = new GreenfootImage("blockO.png");
        }else if(type.equals("J")){
            image = new GreenfootImage("blockJ.png");
        }else if(type.equals("Z")){
            image = new GreenfootImage("blockZ.png");
        }else if(type.equals("I")){
            image = new GreenfootImage("blockI.png");
        }else if(type.equals("T")){
            image = new GreenfootImage("blockT.png");
        }
        setImage(image);//set the image
    }
}
