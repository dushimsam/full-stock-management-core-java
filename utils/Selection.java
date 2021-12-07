package utils;
import java.util.*;


public class Selection{
 
    private boolean choice = true;
   
    public void setChoice(boolean choice)
    {
        this.choice = choice;
    }

    public boolean getChoice(){
        return this.choice;
    }
    public void selectChoice(String action,String subject)
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("\t\t\tDo You really wish to "+ action +" "+subject+" y/n ");
        String selection = scan.next();
        System.out.println("\n");
        if(selection.equals("yes") || selection.equals("YES") || selection.equals("y") || selection.equals("Y")) {
        setChoice(true);
        }else{
        setChoice(false);
        }
    }
    
}
