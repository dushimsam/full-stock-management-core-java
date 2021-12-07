import java.io.*;
public class lastId{
public static void main(String[] args)
{


    int id=0;
    try{
         File file = new File("files/product.csv");

        BufferedReader input = new BufferedReader(new FileReader(file));
        String last="", line="";
        int counter=0;
        while ((line = input.readLine()) != null) {
          if(counter > 0)
          {
            last = line;
          }
            counter++;
        }
    
            String[] list= last.split("\\s+");
    
            if(list.length == 1)
            {
                id = 0;
            }else{
                id = Integer.parseInt(list[1]);
            }
           System.out.println(id);
    }catch(IOException e)
    {

    }

}



}