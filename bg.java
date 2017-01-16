import greenfoot.*; 
import java.io.*;
import java.util.*;

/**
 * @Pop Alexandru Radu & @Petric Ovidiu Vasiliu
 * @2.2.0
 */
public class bg extends World
{
    public bg()throws Exception
    {    
        super(900, 600, 1); 
        addObject(new Ambulance(), Greenfoot.getRandomNumber(getWidth()-10), Greenfoot.getRandomNumber(getHeight()-10));
        int people=Greenfoot.getRandomNumber(10)+20;
        int player_level;
        String username = Greenfoot.ask("Enter your name:"); //latest Greenfoot version is recommended
        showText(username,100,220);
        String people_string=Integer.toString(people);
        for(int i=0;i<people;i++)
            addObject(new Boy(), Greenfoot.getRandomNumber(getWidth()-10), Greenfoot.getRandomNumber(getHeight()-10));
        File res_file = new File("res.txt"); //it can be changed to another file name
        try{
            FileInputStream in = new FileInputStream(res_file);
        }
        //search for res.txt, if not found to create a default one
        catch(IOException ioe){
            res_file.createNewFile();
            FileInputStream in = new FileInputStream(res_file);
            Properties prop = new Properties();
            prop.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(res_file);
            prop.setProperty("username", "Player");
            prop.setProperty("level", "0");
            prop.setProperty("time_power", "0");
            prop.setProperty("lives", "0");
            prop.store(out, null);
            out.close();
        }
        //load the available res.txt
        FileInputStream in = new FileInputStream(res_file);
        Properties prop = new Properties();
        prop.load(in);
        in.close();

        FileOutputStream out = new FileOutputStream(res_file);
        prop.setProperty("people", people_string); //optimization to get this value between classes (bg.java and Ambulance.java)
        player_level=Integer.parseInt(prop.getProperty("level"));
        if(player_level>=25){
            for(int i=0;i<5;i++){
                int obstacle_x=Greenfoot.getRandomNumber(getWidth()-10);
                int obstacle_y=Greenfoot.getRandomNumber(getHeight()-10);
                //now checking if there is something underneath
                if(getObjectsAt(obstacle_x,obstacle_y,Boy.class).isEmpty() && 
                    getObjectsAt(obstacle_x+40,obstacle_y,Boy.class).isEmpty() && 
                    getObjectsAt(obstacle_x,obstacle_y+40,Boy.class).isEmpty() && 
                    getObjectsAt(obstacle_x-40,obstacle_y,Boy.class).isEmpty() && 
                    getObjectsAt(obstacle_x,obstacle_y-40,Boy.class).isEmpty() &&
                    getObjectsAt(obstacle_x+40,obstacle_y+40,Boy.class).isEmpty() && 
                    getObjectsAt(obstacle_x-40,obstacle_y-40,Boy.class).isEmpty() &&
                    getObjectsAt(obstacle_x+40,obstacle_y-40,Boy.class).isEmpty() &&
                    getObjectsAt(obstacle_x-40,obstacle_y+40,Boy.class).isEmpty() &&
                    /*and now beggins the ambulance search*/ 
                    getObjectsAt(obstacle_x,obstacle_y,Ambulance.class).isEmpty() && 
                    getObjectsAt(obstacle_x+50,obstacle_y,Ambulance.class).isEmpty() && 
                    getObjectsAt(obstacle_x,obstacle_y+50,Ambulance.class).isEmpty() && 
                    getObjectsAt(obstacle_x-50,obstacle_y,Ambulance.class).isEmpty() && 
                    getObjectsAt(obstacle_x,obstacle_y-50,Ambulance.class).isEmpty() )
                        addObject(new Hospital(), obstacle_x, obstacle_y);
                else
                        i--;
            }
        }
        try{
            prop.setProperty("username", username); //comment this if it doesn't compile (NullPointerException) in case of deleted res.txt (location is where README.TXT is) then re-enable after a successful compilation
        }
        catch(NullPointerException npe){
            username="Player";
            prop.setProperty("username", username); //this too
        }
        prop.store(out, null);
        out.close();
    }
}
