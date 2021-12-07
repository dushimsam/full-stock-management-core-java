import java.io.*;
import java.util.*;
public class inputBufferUse{

    public static StringBuffer returnBuffer()
    {
        StringBuffer inputBuffer = new StringBuffer();

        try {
            File file = new File("transaction/files/customers.csv");
            BufferedReader objReader = new BufferedReader(new FileReader(file));
        
            String line;
            int counter = 0;
            int check =0;
         
            while((line = objReader.readLine()) != null)
            {
               
                    inputBuffer.append(line);
                    inputBuffer.append("\n");
               
                
                counter++;
            }

        } catch (Exception e) {
            //TODO: handle exception
        }
        return inputBuffer;
    }
public static void main(String[] args)

{
StringBuffer inputBuffer = new StringBuffer();
inputBuffer=returnBuffer();
String a[] = inputBuffer.toString().split("\n");
System.out.println(a[1]);
}
}