import greenfoot.*; 
import java.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class Ambulance extends Actor 
{
    int secret=0,saved_people=0,people=0,level=0,level2,time_power=0,time_power_used=0,lives=0,new_chance=0,won=0,ok=1,ok2=1;
    private int time = 720; //default is 720 for 12 seconds, for debug porposes change to 1200 for 20 seconds
    public void act()
    {
        if (time>0 && won==0)
        {
            time--;
            getWorld().showText(Integer.toString(time/60),100,120); //showing the time left
            if(time == 0)
                getWorld().showText("You lost! Press 'Reset' to try again.",400,300); //centered text
            if(Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")){
                setLocation(getX()-10, getY());
                setRotation(0);
            }
            if(Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")){
                setLocation(getX()+10, getY());
                setRotation(180);
            }
            if(Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")){
                setLocation(getX(), getY()-10);
                setRotation(90);
            }
            if(Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")){
                setLocation(getX(), getY()+10);
                setRotation(270);
            }
            if(Greenfoot.isKeyDown("left")&&Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("a")&&Greenfoot.isKeyDown("w"))
            {
                setLocation(getX()-1/2, getY());
                setLocation(getX(), getY()-1/2);
                setRotation(45);
            }
            if(Greenfoot.isKeyDown("left")&&Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("a")&&Greenfoot.isKeyDown("s"))
            {
                setLocation(getX()-1/2, getY());
                setLocation(getX(), getY()+1/2);
                setRotation(-45);
            }
            if(Greenfoot.isKeyDown("right")&&Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("d")&&Greenfoot.isKeyDown("w"))
            {
                setLocation(getX()+1/2, getY());
                setLocation(getX(), getY()-1/2);
                setRotation(180-45);
            }
            if(Greenfoot.isKeyDown("right")&&Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("d")&&Greenfoot.isKeyDown("s"))
            {
                setLocation(getX()+1/2, getY());
                setLocation(getX(), getY()+1/2);
                setRotation(45-180);
            }
            if(Greenfoot.isKeyDown("p"))
                Greenfoot.stop();
            if(Greenfoot.isKeyDown("q")){
                time_power_used=1;
            }
            if(Greenfoot.isKeyDown("e"))
            {
                new_chance=1;
            }
            if(Greenfoot.isKeyDown("l") && secret==1){
                if(ok==1){
                    saved_people++;
                    ok=0;
                }
            }
            if(isTouching(Hospital.class))
            {
                if(lives==0)
                {
                    getWorld().showText("You crashed in the building! Press 'Reset' and try again.",400,300); //centered text
                    time=0;
                }
                if(lives==1){
                    removeTouching(Hospital.class);
                    lives=0;
                }
            }
            if(isTouching(Boy.class)){
                removeTouching(Boy.class);
                score();
            }
        }
        else if(lives==0)
                Greenfoot.stop();
        else
            Greenfoot.stop();
    }  
    private void score() {
        saved_people++;
        //...just a delimiter
        if(level==11) //I'm a notification :)
        {
            System.out.println("I needed to stop you!!! Sorry!");
            Greenfoot.stop();
        }
        if(level==12) //Me too :D
        {
            System.out.println("I told you that I come back...to annoy you, muahahaha >:)");
            Greenfoot.stop();
        }
        if(level==13 && saved_people==9) //Don't forget me guys ;)
        {
            System.out.println("I'm out! I hate you.");
            Greenfoot.stop();
        }
        if(level==13 && saved_people==15) //You know, you're just a comment :/
        {
            System.out.println("You have a better score than me :'( Please stop!");
            Greenfoot.stop();
        }
        if(level==14 && saved_people==10) //I'm the compiler :) /*Actually not.*/
        {
            System.out.println("Oh sorry! I remeber now: I have level 3897256342658263. Good luck with that ;)");
            Greenfoot.stop();
        }
        if(level==14 && saved_people==5) //OK guys calm down, I'm the OS >:)
        {
            System.out.println("You know... I can make this game harder. What if you got fired? >:) This is your last day.");
            Greenfoot.stop();
        }
        if(level==26 && saved_people==5) //Shut up, I'm the energy B-)
        {
            if(ok2==1){
                System.out.println("This game is really hard so you will fail. :) I will leave you now. Only a letter can help you.");
                saved_people--;
                secret=1;
                ok2=0;
                Greenfoot.stop();
            }
        }
        //and another one, thanks for your patience
        File res_file = new File("res.txt");
        try{
            FileInputStream in = new FileInputStream(res_file);
            Properties prop = new Properties();
            prop.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(res_file);
            if(new_chance==1)
            {
                lives = Integer.parseInt(prop.getProperty("lives"));
                if(lives==1){
                    new_chance=0;
                    prop.setProperty("lives", "0");
                }
            }
            if(time_power_used==1){
                time_power = Integer.parseInt(prop.getProperty("time_power"));
                time_power_used=0;
                prop.setProperty("time_power", "0");
                time+=time_power;
                time_power=0;
            }
            level = Integer.parseInt(prop.getProperty("level"));
            people = Integer.parseInt(prop.getProperty("people")); //getting the value of people_string from res.txt (original value from bg.java)
            if(saved_people>=people){
                getWorld().showText("You won! Press 'Reset' for next level.",400,300); //centered text
                level++;
                String level_string = Integer.toString(level);
                prop.setProperty("level", level_string);
                if(level%5==0 && level!=0)
                {
                    time_power=Greenfoot.getRandomNumber(120)+60;
                    getWorld().showText("You got a bonus of " + time_power/60 + " seconds.",400,350); //below center
                    String time_power_string = Integer.toString(time_power);
                    prop.setProperty("time_power", time_power_string);
                }
                if(level%10==0 && level>=10)
                {
                    getWorld().showText("You got a new bonus chance.",400,375); //below center
                    prop.setProperty("lives", "1");
                }
                //The BEGINNING of notifications
                if(level==1)
                {
                    System.out.println("***Good! You did it! I come back at level 10. Close me now.");
                    Greenfoot.stop();
                }
                if(level==5)
                {
                    System.out.println("***Level 5! Wow! You got a gift :). Look at the game text (below 'You won!'). You get that bonus from 5 to 5 levels. Use it with 'Q' and wisely!");
                    Greenfoot.stop();
                }
                if(level==10)
                {
                    System.out.println("***Wow! I shall stop you because you're too good at this game. I put a lot of obstacles ;)");
                    System.out.println("You know what? This is really hard for everyone (except me :D), I'll give you a new chance from 10 to 10 levels. Use it wisely with 'E'.");
                    Greenfoot.stop();
                }
                if(level==15)
                {
                    System.out.println("Oh my God! You're still playing this game? I bet you can't reach level 25.");
                    Greenfoot.stop();
                }
                //The END of notifications
                won=1;
            }
            prop.store(out, null);
            out.close();
        }
        catch(IOException ioe2){
            System.out.println("Can't find 'res.txt'!");
        }
    }
    public void addedToWorld(World w)
    {
        File res_file = new File("res.txt");
        try{ //reading and parsing res.txt
            FileInputStream in = new FileInputStream(res_file);
            Properties prop = new Properties();
            prop.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(res_file);
            level = Integer.parseInt(prop.getProperty("level"));
            people = Integer.parseInt(prop.getProperty("people")); //getting the value of people_string from res.txt (original value from bg.java)
            prop.store(out, null);
            out.close();
        }
        catch(IOException ioe2){
            System.out.println("Can't find 'res.txt'!");
        }
        getWorld().showText("Level/saved/total " + level + "/" + saved_people + "/" + people,200,100);
    }
}
