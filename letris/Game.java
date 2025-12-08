import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * This world class is the class that represents the game level. Most the of game
 * logic is contained here.
 * 
 * @author Tiger Zhao
 * @version January 5, 2015
 */
public class Game extends World
{
    private static GreenfootSound backgroundMusic = new GreenfootSound("main.mp3"); //the background music
    private GreenfootImage gridImage = new GreenfootImage("grids.png");
    private Block[][] grid = new Block[20][10];

    //these are the arrays that represent each block, a 1 represents a filled space, 0 is an empty space
    private int[][] blockT = new int[][]{{1,1,1},
            {0,1,0}};
    private int[][] blockL = new int[][]{{1,1,1},
            {0,0,1}};
    private int[][] blockJ = new int[][]{{1,1,1},
            {1,0,0}};
    private int[][] blockI = new int[][]{{1,1,1,1}};
    private int[][] blockZ = new int[][]{{0,1,1},
            {1,1,0}};
    private int[][] blockS = new int[][]{{1,1,0},
            {0,1,1}};
    private int[][] blockO = new int[][]{{1,1},
            {1,1}}; 
    private int[][] tileB = new int[][] {{1}};
            
    private Block[][] curBlock; //store the current block in terms of Block objects
    private String curBlockType =""; //stores the current block type
    private int y,x; //stores x and y positions of the current block relative to the grid system

    /*
     * Note: The delays below are used in order to control the speed at which the user can press a button.
     * If these delays were not here, a single press could register as 5 due to the speed at which the 
     * program runs.
     */
    private int maxFallDelay = 25;//delay to control moving down
    private int curFallDelay = 25;

    private int maxSlideDelay = 7;//the delay to control moving left/right
    private int curSlideDelay = 7;

    private int maxRotateDelay = 10;//the delay to control the rotations
    private int curRotateDelay = 10;

    private int maxDropDelay = 20; //delay to control the space bar
    private int curDropDelay = 20;
    
    private boolean isPaused = false; //whether or not the game is currently paused
    private int maxPauseDelay = 20; //delay to control pausing
    private int curPauseDelay = 20;
    //variables to store the buttons shown when the game is paused
    private Button exitButton;
    private Button restartButton;

    private Block[][] shiftBlock; //stores the shifted block in terms of Block objects
    private String shiftBlockType = ""; //stores the type of the shifted block
    private boolean shifted = false; //whether or not the player has used shift
    
    //below are the variables to store the gameplay stats
    private int score = 0;
    private int totalLinesCleared = 0;
    private int curLinesCleared = 0;
    private int level = 0;
    
    private ArrayList<Block[][]> nextBlocks = new ArrayList<Block[][]>(); //stores the future blocks
    private ArrayList<String> nextBlockTypes = new ArrayList<String>(); //stores the types of the furture blocks
    private int maxNext = 2;//the max number of blocks that the user can see into the future
    
    /**
     * Constructor for objects of class Map.
     */
    public Game()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 700, 1); 
        getBackground().drawImage(gridImage,36,36);
        getNextBlock();
        setPaintOrder(Text.class,Button.class,Block.class); //makes it so that blocks are always displayed on top
    } 

    /**
     * act method for the class Game
     * This method is continuously run.
     */
    public void act(){
        if (!isPaused){ //only run if the game is not paused
            removeObjects(getObjects(Text.class)); //clear text
            backgroundMusic.playLoop(); //play the music
            drawScreen(); //draws the blocks on screen
            checkLose(); //check if the player has lost
            drawShadow(); //draw the shadwos
            controlMovement(); //allow the player to input controls
        }else{
            addObject(new Text("Paused"), 185, 200);//show text
            if(exitButton.isPressed()){ //if the exit button is pressed
                Greenfoot.setWorld(new Menu()); //exit to menu
            }else if (restartButton.isPressed()){//if the restart button is pressed
                Greenfoot.setWorld(new Game()); //restart the game
            }
        }
        
        if(Greenfoot.isKeyDown("p") && curPauseDelay==0){ //if the player presses the pause key
            curPauseDelay=maxPauseDelay; // a delay for the speed at which the pause button is pressed
            if (isPaused){
                isPaused =false;
                removeObjects(getObjects(Button.class)); //remove the buttons shown at the pause screen
            }else {
                isPaused=true;
                //show buttons
                exitButton = new Button("exitButton",new GreenfootImage("exitButton.png")); 
                restartButton = new Button("restartButton", new GreenfootImage("restartButton.png"));
                addObject(exitButton, 185,300);
                addObject(restartButton, 185,375);
            }
        }
        if(curPauseDelay > 0){ //control the pause delay
            curPauseDelay--;
        }
    }
    
    /**
     * This method allows for the player to provide input.
     */
    public void controlMovement(){
        //controls the falling movement of block
        if(curFallDelay == 0){
            curFallDelay = maxFallDelay;
            if (canMoveDown(y)){
                y++;
            }else{
                setInBlock(); //merge the curblock with grid;
                getNextBlock(); //get a new curBlock, replacing the old curBlock
            }
        }else{
            curFallDelay--;
        }
        
        //controls the left/right movement of block
        if (curSlideDelay == 0){
            if(Greenfoot.isKeyDown("a") && canMoveLeft() ){
                curSlideDelay=maxSlideDelay;
                x--;
                if(!canMoveDown(y)){
                    curFallDelay = 20;
                }
            }else if (Greenfoot.isKeyDown("d") && canMoveRight()){
                curSlideDelay=maxSlideDelay;
                x++;
                if(!canMoveDown(y)){ //make is so that the block can still move left/right even though it can't go down
                    curFallDelay = 20;
                }
            }
        }else{
            curSlideDelay--;
        }

        //controls the rotation movement for the block
        if (curRotateDelay==0) {
            if (Greenfoot.isKeyDown("w") && canRotateRight() ){
                rotateRight();
                curRotateDelay=maxRotateDelay;
                if (!canMoveDown(y)) { //make is so that the block can still move left/right even though it can't go down
                    curFallDelay=20;
                }
            }
            else if (Greenfoot.isKeyDown("z") && canRotateLeft()) {
                rotateLeft();
                curRotateDelay=maxRotateDelay;
                if (!canMoveDown(y)) { //make is so that the block can still move left/right even though it can't go down
                    curFallDelay=20;
                }
            }
        }
        else {
            curRotateDelay--;
        }

        //allow the user to use the down arrow key to move the block down faster
        if (Greenfoot.isKeyDown("s") && canMoveDown(y)){
            y++;
            if(!canMoveDown(y)){
                curFallDelay=20;
            }
        }

        //allow the user to instantly drop the block
        if (curDropDelay==0){
            if(Greenfoot.isKeyDown("space") ){
                moveAllDown();
                curDropDelay =maxDropDelay;
            }
        }else{
            curDropDelay--;
        }

        //shift, store the block for later
        if ((Greenfoot.isKeyDown("shift") && !shifted)) {
            shifted=true;
            if(shiftBlock ==null){//user has not ever shifted this game
                shiftBlock = curBlock;
                shiftBlockType = curBlockType;
                getNextBlock();
            }else{//user has shifted before
                Block[][]tmp = shiftBlock;
                shiftBlock = curBlock;
                curBlock = tmp;
                String tmp1 = curBlockType;
                curBlockType = shiftBlockType;
                shiftBlockType = tmp1;
                y=curBlock.length-1;
                x=4;
            }
        }
    }
    
    /**
     * This method checks if the player has lost
     */
    public void checkLose(){
        if(grid[1][4] != null || grid[1][5] != null || grid[1][6] != null) {
            //showText("GAMEOVER",185,200);
            addObject(new Text("GAME OVER"),185,200);
            Greenfoot.delay(100);
            Greenfoot.setWorld(new LoseScreen(score,level,totalLinesCleared));
        }
    }
    
    /**
     * This method checks if the player can move the current block down.
     * @param y the height of the block to check from
     * @return whether or not the current block can move downwards
     */
    public boolean canMoveDown(int y){
        //check to see if there is a block below any of the blocks
        if (y >= 19){
            return false;
        }else{ //check to see if there is a block under any one of the current block
            for (int i=0; i < curBlock.length;i++){
                for (int j =0; j < curBlock[0].length;j++){
                    if(curBlock[i][j]!=null){ //x+j, y+i
                        if (grid[(y-i)+1][x+j] != null){ //see if there is a block under
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
    
    /**
     * This method checks whether or not the block can move left
     * @return whether or not the current block can move left
     */
    public boolean canMoveLeft(){
        boolean canMove=true;
        if (x <=0){
            return false;
        }else{ //check if there is a block to the left
            for (int i=0; i < curBlock.length;i++){
                for (int j =0; j < curBlock[0].length;j++){
                    if(curBlock[i][j]!=null){ //x+j, y+i
                        if (grid[y-i][x+j-1] != null){ //see if there is a block left
                            return false;
                        }
                    }
                }
            }
        }
        return canMove;
    }

    /**
     * This method checks whether or not the block can move right
     * @return whether or not the block can move right
     */
    public boolean canMoveRight(){
        boolean canMove=true;
        if (x +curBlock[0].length>9){
            return false;
        }else{ //check to see if there is a block to the right
            for (int i=0; i < curBlock.length;i++){
                for (int j =0; j < curBlock[0].length;j++){
                    if(curBlock[i][j]!=null){ //x+j, y+i
                        if (grid[y-i][x+j+1] != null){ //see if there is a block right
                            return false;
                        }
                    }
                }
            }
        }
        return canMove;
    }
    
    /**
     * This method checks whether or not the block can rotate right.
     * @return whether or not the block can rotate right
     */
    public boolean canRotateRight(){
        //rotate the block first to get where the block would end up
        Block[][] newBlock = new Block[curBlock[0].length][curBlock.length];
        for (int i =0; i < curBlock.length;i++){
            for (int j=0; j < curBlock[0].length;j++){
                int newI = j;
                int newJ=i;
                if (newI ==0){
                    newI = newBlock.length-1;
                }else if (newI ==newBlock.length-1){
                    newI=0;
                }
                newBlock[newI][newJ] = curBlock[i][j];
            }
        }
        //check to see that the new position of the rotated block is valid, eg. doesn't collide with any other blocks
        int newX =x;
        int newY=y;
        while(newY - newBlock.length<-1){ //if the block is off screen, allow the block to slide until it is on screen
            newY++;
        }

        while(newX+newBlock[0].length >10){//if the block is off screen, allow the block to slide until it is on screen
            newX--;
        }
        
        //actually checking to see if the block collides with any other blocks
        for (int i =0; i < newBlock.length;i++){
            for (int j=0; j < newBlock[0].length;j++){
                if (newBlock[i][j] !=null){
                    if (grid[newY-i][newX+j] !=null){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * This method checks whether or not the block can rotate left.
     * @return whether or not the block can move rotate left
     */
    public boolean canRotateLeft(){
        //rotate the block first to get where the block would end up
        Block[][] newBlock = new Block[curBlock[0].length][curBlock.length];
        for (int i =0; i < curBlock.length;i++){
            for (int j=0; j < curBlock[0].length;j++){
                int newI = j;
                int newJ=i;
                if (newJ ==0){
                    newJ = newBlock[0].length-1;
                }else if (newJ ==newBlock[0].length-1){
                    newJ=0;
                }
                newBlock[newI][newJ] = curBlock[i][j];
            }
        }
        //check to see that the new position of the rotated block is valid, eg. doesn't collide with any other blocks
        int newX =x;
        int newY=y;
        while(newY - newBlock.length<-1){//if the block is off screen, allow the block to slide until it is on screen
            newY++;
        }

        while(newX+newBlock[0].length >10){//if the block is off screen, allow the block to slide until it is on screen
            newX--;
        }
        
        //actually checking to see if the block collides with any other blocks
        for (int i =0; i < newBlock.length;i++){
            for (int j=0; j < newBlock[0].length;j++){
                if (newBlock[i][j] !=null){
                    if (grid[newY-i][newX+j] !=null){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method checks to see if any lines can be cleared and then clears them.
     * Score is added accordingly.
     * This method is only run every time the player places a block.
     */
    public void clearLines(){
        int cnt =0; //count the total number of lines cleared;
        ArrayList<List<Block>> cur = new ArrayList<List<Block>>();
        for(Block[] b :grid){
            cur.add(Arrays.asList(Arrays.copyOf(b,grid[0].length)));
        }
        for (int i =0; i < cur.size(); i++){
            if (!cur.get(i).contains(null)){
                cnt++;
                cur.remove(i);
                Block[] b = new Block[grid[0].length];
                cur.add(0,Arrays.asList(b));
            }
        }
        if (cnt>0){
            for (int i =0; i < grid.length;i++){
                grid[i] = cur.get(i).toArray(grid[i]);
            }
        }
        //add score according to how many lines cleared at once
        totalLinesCleared +=cnt;
        curLinesCleared +=cnt;
        if(cnt==4){
            score+=cnt*20;
        }else if(cnt==3){
            score+=cnt*15;
        }else if (cnt ==2){
            score+=cnt*10;
        }else{
            score+=cnt*5;
        }
    }
    
    /**
     * This method takes the current block and "sets it in". 
     * It addes the current block to the main array that holds all the tetris block.
     * Onces set in, the player can no longer control this block
     */
    public void setInBlock(){
        shifted=false;
        for (int i=0; i < curBlock.length;i++){
            for (int j =0; j < curBlock[0].length;j++){
                if(curBlock[i][j]!=null){ 
                    grid[y-i][x+j] = curBlock[i][j];
                }
            }
        }
        if (curBlockType.equals("B")) {
            explodeBomb();
        }
        
        clearLines();
    }
    
    /**
     * This method rotates the current block right.
     */
    public void rotateRight(){
        //actual rotating done here
        Block[][] newBlock = new Block[curBlock[0].length][curBlock.length];
        for (int i =0; i < curBlock.length;i++){
            for (int j=0; j < curBlock[0].length;j++){
                int newI = j;
                int newJ=i;
                if (newI ==0){
                    newI = newBlock.length-1;
                }else if (newI ==newBlock.length-1){
                    newI=0;
                }
                newBlock[newI][newJ] = curBlock[i][j];
            }
        }
        
        //makes it so that the block can move so that it is not off screen
        while(y - newBlock.length<-1){
            y++;
        }

        while(x+newBlock[0].length >10){
            x--;
        }

        curBlock = newBlock; //save rotation changes
    }

    /**
     * This method rotates the current block left.
     */
    public void rotateLeft(){
        //actual rotating done here
        Block[][] newBlock = new Block[curBlock[0].length][curBlock.length];
        for (int i =0; i < curBlock.length;i++){
            for (int j=0; j < curBlock[0].length;j++){
                int newI = j;
                int newJ=i;
                if (newJ ==0){
                    newJ = newBlock[0].length-1;
                }else if (newJ ==newBlock[0].length-1){
                    newJ=0;
                }
                newBlock[newI][newJ] = curBlock[i][j];
            }
        }
        
        //makes it so that the block can move so that it is not off screen
        while(y - newBlock.length<-1){
            y++;
        }

        while(x+newBlock[0].length >10){
            x--;
        }

        curBlock = newBlock;//save rotation changes
    }
    
    /**
     * This method instantly drops the current block to the bottom and sets it in place.
     * This is called when the user presses space.
     */
    public void moveAllDown(){
        while(canMoveDown(y)){
            y++;
        }
        setInBlock();
        getNextBlock(); //generate a new curBlock, replacing the old curBlock
    }

    /**
     * This method updates the game screen. 
     * It refreshes the blocks so that the game is playable.
     */
    public void drawScreen(){
        removeObjects(getObjects(Block.class)); //clear the blocks
        //add new blocks in their new positions if applicable
        for(int y =0; y < grid.length;y++){
            for(int x =0; x < grid[0].length; x++){
                if (grid[y][x] !=null){
                    addObject(grid[y][x], (x * 30) + 50, (y * 30) + 50);
                }
            }
        }
        
        //adds the block that the user is currently controlling
        for (int i=0; i < curBlock.length;i++){
            for (int j =0; j < curBlock[0].length;j++){
                if(curBlock[i][j]!=null){
                    addObject(curBlock[i][j], ((x + j) * 30) + 50, ((y - i) * 30) + 50);
                }
            }
        }

        //show text
        addObject(new Text("Score: " + score), 500, 50);
        addObject(new Text("Lines: " + totalLinesCleared), 500, 75);
        addObject(new Text("Level: " + level), 500, 100);

        
        //handle the leveling system
        if(curLinesCleared >= 10 && level != 5){
            level = Math.min(level + 1, 5);
            curLinesCleared = 10 - curLinesCleared;
            score += level * 100;
            
            addObject(new LevelUpAnim(), 300, 200);
            
            maxFallDelay -= 8;
            if (maxFallDelay < 3){
                maxFallDelay = 3;
            }
            
            curFallDelay -= 3;
            if (curFallDelay < 3) curFallDelay = 3;
        }   

        getBackground().drawImage(new GreenfootImage("tile"+shiftBlockType+".png"),350,550); //displays the currently shifted block
        
        //displays the blocks coming up in gameplay.
        int i=40;//variable used to help position the images
        for (String b : nextBlockTypes){
            getBackground().drawImage(new GreenfootImage("tile"+b+".png"),350,i);
            i+=58;
        }

    }
    
    /**
     * This method draws the shadow of the current block
     */
    public void drawShadow(){
        int tmpY = y; //temporary variable

        //determine how far down the current block could theoretically go
        while(canMoveDown(tmpY)){
            tmpY++;
        }
        
        removeObjects(getObjects(Shadow.class));//clear the current shadow so it can be updated

        //draw the new shadow
        for (int i =0; i < curBlock.length;i++){
            for(int j=0; j < curBlock[0].length;j++){
                if(curBlock[i][j] !=null){
                    Shadow s = new Shadow();
                    addObject(s, (x+j)*30 +50, (tmpY-i)*30+50);
                }
            }
        }
    }
    
    /**
     * This method generates blocks. 
     * It randomly chooses between the 7 possible tetris pieces and adds 
     * it to the blocks coming up.
     */
    public void generateBlocks(){
        Random random = new Random();
        int r = random.nextInt(8); //random number genration
        int[][] b; //variable to store the array representation of the block
        String t =""; //variable to store type
        if(r==0){//I
            b=blockI;
            t="I";
        }else if (r==1){ //L
            b=blockL;
            t="L";
        }else if (r==2){//S
            b=blockS;
            t="S";
        }else if (r==3){//Z
            b=blockZ;
            t="Z";
        }else if (r==4){//J
            b=blockJ;
            t="J";
        }else if (r==5){//T
            b=blockT;
            t="T";
        }
        else if (r == 6 && level >= 3) {
            b = tileB;
            t = "B";
        } 
        else if (r == 6 && level < 3) {
            r = random.nextInt(6);
            
            if(r==0){
                b=blockI; t="I";
            }else if(r==1){
                b=blockL; t="L";
            }else if(r==2){
                b=blockS; t="S";
            }else if(r==3){
                b=blockZ; t="Z";
            }else if(r==4){
                b=blockJ; t="J";
            }else{ // r == 5
                b=blockT; t="T";
            }
        }

        else  {//O
            b=blockO;
            t="O";
        }
        
        //this creates an array of blocks instead of 1s and 0s
        Block[][] block = new Block[b.length][b[0].length];
        for (int y =0; y < block.length;y++){
            for(int x =0; x < block[0].length; x++){
                if(b[y][x] ==1){
                    block[y][x] = new Block(t);
                }
            }
        }
        
        //add the neccessary data
        nextBlocks.add(block);
        nextBlockTypes.add(t);
    }
    
    /**
     * This method gets the next block in line and keeps a constant number of blocks coming up.
     */
    public void getNextBlock(){
        //if the next blocks is not maxed, add more blocks
        while(nextBlocks.size()<maxNext){
            generateBlocks();
        }
        //get and remove data
        curBlock = nextBlocks.get(0);
        curBlockType = nextBlockTypes.get(0);
        nextBlocks.remove(0);
        nextBlockTypes.remove(0);
        //set postions
        x=4;
        y=1;
        if(curBlockType.equals("I")){//special case of the long stick block
            y=0;
        }
    }
    public void explodeBomb() {
        int bombY = y;
        int bombX = x;
    
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int ny = bombY - dy;
                int nx = bombX + dx;
    
                // bounds check
                if (ny >= 0 && ny < 20 && nx >= 0 && nx < 10) {
                    grid[ny][nx] = null;
                }
            }
        }
    }
}
