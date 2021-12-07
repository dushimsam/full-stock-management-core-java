package options;
import java.util.*;

public interface DeleteAndUpdate {
    public void updateRecord(int index,String id);
    public  int deleteRecord(String id);  

    default public String promptExistingDetails(String value,String existingDetail){
        Scanner scan = new Scanner(System.in);
        System.out.print("\t\t\tExisting "+value+": " +existingDetail);
        System.out.print("\t\t\tEnter The New : ");
        String newValue = scan.nextLine();
       return  newValue;
      } 
}
