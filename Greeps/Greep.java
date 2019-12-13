import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A Greep is an alien creature that likes to collect tomatoes.
 * 
 * @author B4CKF1SH
 * @version 1.0
 */
public class Greep extends Creature
{
    // Remember: you cannot extend the Greep's memory. So:
    // no additional fields (other than final fields) allowed in this class!
    
    private final int TURN_VALUE = 69;
    private final int WATER_MEMORY = 50;
    
    /**
     * Default constructor for testing purposes.
     */
    public Greep()
    {
        this(null);
    }

    
    /**
     * Create a Greep with its home space ship.
     */
    public Greep(Ship ship)
    {
        super(ship);
    }
    

    /**
     * Do what a greep's gotta do.
     * 
     * @author B4CKF1SH
     */
    public void act() {
        super.act();   // do not delete! leave as first statement in act().
        
        if (carryingTomato()) {
            goBack();
        }
        else {
            search();
        }
    }
    
    /**
     * Brings Tomatoes to ship
     * 
     * @author B4CKF1SH
     */
    private void goBack() {
        if (atShip()) {
            dropTomato();
            turn(180);
            move();
        }
        else {
            //go back
            if (atWorldEdge()) {
                turn(TURN_VALUE);
                move();
            }
            else if (atWater()) {
                setFlag(1, true);
                turn(TURN_VALUE);
                setMemory(WATER_MEMORY);
                move();
            }
            else if (getMemory() <= 0 && getFlag(1)) {
                setFlag(1, false);
                turnHome();
                move();
            }
            else {
                turnHome();
                move();
            }

            spit("red");
        }
    }
    
    /**
     * Searches for tomatoes
     * 
     * @author B4CKF1SH
     */
    private void search() {
        
        if (atWorldEdge()) {
            turn(TURN_VALUE);
            move();

        }
        else if (atWater()) {
            turn(TURN_VALUE);
            move();
        }
        else if (atTomatoPile()) {
            // go to middle of pile
            int deltaX = getOneIntersectingObject(TomatoPile.class).getX() - getX();
            int deltaY = getOneIntersectingObject(TomatoPile.class).getY() - getY();
            setRotation((int) (180 * Math.atan2(deltaY, deltaX) /Math.PI));
            move();
        }
        else if (seePaint("red")) {
            turnToNextPaint();
            move();
        }
        else {
            move();
        }
            
        spit("orange");
        checkFood();
    }
    
    /**
     * Follow colored trail
     * 
     * @author B4CKF1SH
     */
    private void turnToNextPaint() {
         turnHome();
         turn(180);
    }

    /**
     * Is there any food here where we are? If so, try to load some!
     */
    public void checkFood()
    {
        // check whether there's a tomato pile here
        TomatoPile tomatoes = (TomatoPile) getOneIntersectingObject(TomatoPile.class);
        if(tomatoes != null) {
            loadTomato();
            // Note: this attempts to load a tomato onto *another* Greep. It won't
            // do anything if we are alone here.
        }
    }


    /**
     * This method specifies the name of the author (for display on the result board).
     */
    public static String getAuthorName()
    {
        return "B4CKF1SH";  // write your name here!
    }


    /**
     * This method specifies the image we want displayed at any time. (No need 
     * to change this for the competition.)
     */
    public String getCurrentImage()
    {
        if(carryingTomato())
            return "greep-with-food.png";
        else
            return "greep.png";
    }
}
