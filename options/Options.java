package options;
import java.util.*;

public interface Options extends DeleteAndUpdate{
    
    public HashMap gatherRecords();
    public void viewAll();
    public void viewOne(String id);

}