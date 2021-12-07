package Db;
import java.io.*;
import Db.CrudMethods;
import Model.Supplier;


public class SupplierCrud implements CrudMethods{


    private File file = new File("Db/files/suppliers.csv");

    public void setFile(File filename)
{
    this.file = filename;
}

public  File getFile()
{
    return this.file;
}


public String checkExistence(String data,String status){
    String response = null;
    try {
    BufferedReader objReader = new BufferedReader(new FileReader(file));
    String currentLn = "";

    int counter = 0;
    int check = 0;
while( (currentLn = objReader.readLine()) != null)
{
    if(counter > 0)
{
String[] listeMots = currentLn.split("\\s+");

if(status.equals("name"))
{
    if(listeMots.length != 1)
    {
        if(listeMots[1].equals(data))
        {
            response = "found";
        }
    }
}else{

    if(listeMots.length != 1)
    {
        
    if(listeMots[0].equals(data))
    {
        response = listeMots[0];
    }
}

}

}
counter++;  
}

objReader.close();

} catch (IOException e) {
System.out.println("\n\n\t\t\tCan't read File\n\n");
}
return response;
}

public boolean verifyEmail(String email){
    boolean found = false;
    try {
     
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        int counter = 0;
        while((currentLn = objReader.readLine()) != null){
              if(counter > 0)
              {
                String[] listeMots = currentLn.split("\\s+");
                if(listeMots.length != 1)
                {
                    if(listeMots[3].equals(email))
                            found = true;
                 }
              }

            counter++;  
        }
      }catch (IOException e) {
        System.out.println("\n\n\t\t\tCan't read File\n\n");
    }
    return found;
}

public void createData(Supplier model){

    try {
        String ident=this.generateRandomString().toUpperCase();
        ident = ident+this.generateRandomInteger();
            model.setSupId(ident);
            FileWriter writer = new FileWriter(file,true);
            writer.write("\n"+ident+"\t"+model.getSupName()+"\t"+model.getTel()+"\t"+model.getEmail()+"\t"+model.getLocation());
            writer.close();

}catch (IOException e) {
    System.out.println("\n\n\t\t\tCan't create File\n\n");
}
}


public void readData(){
    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        int counter =0;
        int check =0;
while( (currentLn = objReader.readLine()) != null)
{    
    String[] lists = currentLn.split("\\s+");

      if(counter == 0 && check == 0)
    {
       
    System.out.println("\n\t\tNUMBERED\t\tCODE\t\tSUPPLIER_NAME\t\tTELEPHONE\t\tEMAIL\t\tLOCATION\t\t\n");
     check = 1;
}else{
        if(lists.length != 1){
        
     System.out.println("\n\t\t"+counter+"\t\t"+lists[0]+"\t\t"+lists[1]+"\t\t"+lists[2]+"\t\t"+lists[3]+"\t\t"+lists[4]+"\t\t");     
        }else{
            counter--;
        }
     
    }
 
        counter++;
    
}
    
objReader.close();
    }catch(IOException e)
    {
        System.out.println(e.getMessage());
    }
   
}

public Supplier readByOne(String id){
    Supplier sup = new Supplier();
    try{
    BufferedReader objReader = new BufferedReader(new FileReader(file));
    String currentLn = "";
    int counter = 0;
    int check = 0;
while( (currentLn = objReader.readLine()) != null)
{
if(counter > 0)
{
    String[] listeMots = currentLn.split("\\s+");
if(listeMots.length != 1){
if(listeMots[0].equals(id))
{
    check = 1;
    sup.setSupId(listeMots[0]);
    sup.setSupName(listeMots[1]);
    sup.setTel(listeMots[2]);
    sup.setEmail(listeMots[3]);
    sup.setLocation(listeMots[4]);
}
    }
}
    
    counter++;  
}

}
catch(IOException e)
{
    System.out.println(e.getMessage());
}
return sup;
}

public void updateData(String id,int index,String newData){
 
    try{
    BufferedReader file = new BufferedReader(new FileReader(this.file));
    StringBuffer inputBuffer = new StringBuffer();
    String line;
int counter = 0;
int check =0;
while((line = file.readLine()) != null)
{
String[] listeMots = line.split("\\s+");

if(counter > 0)
{
    if(listeMots.length != 1){
if(listeMots[0].equals(id))
{
    if(index == 1)
    {
        if(this.checkExistence(newData,"name") == null)
        {
            String oldData = listeMots[index];
            String replaceLn= new String(line);
            replaceLn = replaceLn.replace(oldData,newData);
            line = replaceLn;
            check = 1;
        }else{
            check = 0;
        }
    }else{
        String oldData = listeMots[index];
        String replaceLn= new String(line);
        replaceLn = replaceLn.replace(oldData,newData);
        line = replaceLn;
        check = 1;
    }

}
    }
}
inputBuffer.append(line);
inputBuffer.append("\n");
counter++;
}

file.close();

    // write the new string with the replaced line OVER the same file

    FileOutputStream fileOut= new FileOutputStream(this.file);
fileOut.write(inputBuffer.toString().getBytes());
fileOut.close();
if(check == 1)
{
System.out.println("\n\n\t\t\tUpdated\n");
}else{
System.out.println("\n\n\t\t\tSorry Such name already Exists\n");
}
}catch(IOException e)
{
        System.out.println(e.getMessage());
}

}
public int deleteData(String id){
    
    try{
        BufferedReader file = new BufferedReader(new FileReader(this.file));
        StringBuffer inputBuffer = new StringBuffer();
        String line;
int counter = 0;
int check =0;
int found = 0;
while((line = file.readLine()) != null)
{
    String[] listeMots = line.split("\\s+");

if(counter > 0)
{
 if(listeMots.length != 1)
 {
    if(listeMots[0].equals(id))
    {
   found = 1;
   check = 1;
    }else {
        found = 0;
    }
 }
  

}
if(found == 0)
{
    inputBuffer.append(line);
    inputBuffer.append("\n");
}
counter++;
}

file.close();

        // write the new string with the replaced line OVER the same file

        FileOutputStream fileOut= new FileOutputStream(this.file);
fileOut.write(inputBuffer.toString().getBytes());
fileOut.close();
if(check == 1)
{
return 1;
}
    }catch(IOException e)
    {
            System.out.println(e.getMessage());
    }

    return 0;
}




}
