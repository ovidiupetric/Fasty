import greenfoot.*;

public class Hospital extends Actor
{
    public void act() 
    {
        // TODO
    }   
    public void addedToWorld(World w)
    {
        //preventing from spawning on top of another object except for another building
        while(isTouching(Boy.class) || isTouching(Ambulance.class))
        {
            int x=Greenfoot.getRandomNumber(getWorld().getWidth()-10);
            int y=Greenfoot.getRandomNumber(getWorld().getHeight()-10);
            setLocation(x, y);
        }
    }
}
