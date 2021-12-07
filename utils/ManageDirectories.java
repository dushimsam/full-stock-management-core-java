package utils;
import java.util.*;

public class ManageDirectories {
    public void printDirectory(String currDir,String section)
  {
if(currDir.equals("home"))
{

  System.out.println("\n\n\t\t\t\t\tHOME >> "+section+" MANAGEMENT SYSTEM\n\n");
  System.out.println("\t\t\t1.Add\n");
  System.out.println("\t\t\t2.View\n");
  System.out.println("\t\t\t3.Back\n");

}else if(currDir.equals("view"))
{
  System.out.println("\n\n\t\t\t\t\tHOME >> "+section+" MANAGEMENT SYSTEM >> VIEW\n\n");
  System.out.println("\t\t\t1.View All\n");
  System.out.println("\t\t\t2.View One\n");
  System.out.println("\t\t\t3.Back\n");
}else if(currDir.equals("viewAll")){
  System.out.println("\n\n\t\t\t\t\tHOME >> "+section+" MANAGEMENT SYSTEM >> VIEW >> VIEW ALL\n\n");
}else if(currDir.equals("viewOne")){
  System.out.println("\n\n\t\t\t\t\tHOME >> "+section+" MANAGEMENT SYSTEM >> VIEW >> VIEW ONE\n\n");
}else if(currDir.equals("add")){
  System.out.println("\n\n\t\t\t\t\tHOME >> "+section+" MANAGEMENT SYSTEM >> ADD\n\n");
}else{
  System.out.println("\n\n\t\t\t\t\tHOME >> "+section+" MANAGEMENT SYSTEM >> VIEW >> VIEW ONE >> *** "+currDir+"*");
}
  } 
}
