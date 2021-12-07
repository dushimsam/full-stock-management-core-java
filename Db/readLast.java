import java.io.*;
public class readLast{

public static void main(String args[])
{
    try{
        File file = new File("files/employees.csv");
        BufferedReader input = new BufferedReader(new FileReader(file));
        String last="", line="";
    
        while ((line = input.readLine()) != null) { 
            last = line;
        }
        String[] list= last.split("\\s");
    System.out.println(list[1]);

    }catch(IOException e)
    {

    }
    


}


}