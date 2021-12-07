package Db.transaction.supply;
import java.io.*;
import Db.CrudMethods;
import Model.supplies.Supplies;


public class SuppliesCrud{


    private File file = new File("Db/transaction/files/supply.csv");
    private File file2 = new File("Db/transaction/files/supplied_product.csv");
 

    public void setFile(File filename)
{
    this.file = filename;
}

public  File getFile()
{
    return this.file;
}



public int lastId()
{
    int id=0;
    try{
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
            id = Integer.parseInt(list[0]);
        }
    }catch(IOException e)
    {

    }
return id;
}


public String checkExistence(String supplier_id,String date){
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

    if(listeMots.length != 1)
    {
        String[] fileDate =listeMots[2].split("_+");

        if(listeMots[1].equals(supplier_id) && fileDate[0].equals(date))
        {
            response = listeMots[0];
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

public int createData(Supplies model){

    int response = 0;
    try {
        int returnedId= this.lastId()+1;
        response=returnedId;
        String id= Integer.toString(returnedId);
            FileWriter writer = new FileWriter(file,true);
            writer.write(System.getProperty("line.separator"));
            writer.write(id+"\t"+model.getSupplierId()+"\t"+model.getSupDate());
            writer.close();
} catch (IOException e) {
    System.out.println("\n\n\t\t\tCan't read File\n\n");
}
return response;
}

public void readData(){
    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        String[] lists;
        int check =0;
        int counter =0;
while((currentLn = objReader.readLine()) != null)
{  
    lists = currentLn.split("\\s+");

    if(counter == 0 && check == 0)
    {
        System.out.println("\n\t\tNUMBERED\t\tSUPPLY_CODE\t\t\tSUPPLIER_CODE\t\t\tDATE-TIME\t\t\n");
        check=1;
    }else{
   
    if(lists.length != 1){
 
        System.out.println("\n\t\t\t"+counter+"\t\t\t"+lists[0]+"\t\t\t"+lists[1]+"\t\t\t"+lists[2]+"\t\t\t");     
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
    };

   
}

public StringBuffer dateSupplies(String supDate)
{
    StringBuffer holdData = new StringBuffer();

    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        int counter = 0;
        int check = 0;
    while( (currentLn = objReader.readLine()) != null)
    {
        if(counter > 0)
        {

        String supId;
        String[] listeMots = currentLn.split("\\s+");
        if(listeMots.length != 1){
    String[] fileDate =listeMots[2].split("_+");
    if(fileDate[0].equals(supDate))
    {
        holdData.append("\n\t\t\t\t\t"+listeMots[0]+"\t\t\t"+listeMots[1]+"\t\t\t"+fileDate[1]+"\n");
        check =1;
        }
    }
}
counter++;
    }

    }catch(IOException e)
{System.out.println(e.getMessage());}
return holdData;
}
public Supplies readByOne(String id){
    Supplies supply = new Supplies();
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
            supply.setSupId(listeMots[0]);
            supply.setSupplierId(listeMots[1]);
            supply.setSupDate(listeMots[2]);
        
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
return supply;
}

public void updateData(String sup_id,String newData){
 
    try{
    BufferedReader file = new BufferedReader(new FileReader(this.file));
    StringBuffer inputBuffer = new StringBuffer();
    String line;
int counter = 1;
int check =0;
while((line = file.readLine()) != null)
{
String[] listeMots = line.split("\\s+");

if(counter > 0)
{
    if(listeMots.length != 1){
if(listeMots[0].equals(sup_id))
{
String oldData = listeMots[1];
String replaceLn= new String(line);
replaceLn = replaceLn.replace(oldData,newData);
line = replaceLn;
check = 1;
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
System.out.println("\n\n\t\t\tTry again error occurred\n");
}
}catch(IOException e)
{
        System.out.println(e.getMessage());
}

}
public int deleteData(String sup_id){

    try{
        BufferedReader objReader = new BufferedReader(new FileReader(this.file));
        BufferedReader objReader2 = new BufferedReader(new FileReader(this.file2));

        StringBuffer inputBuffer = new StringBuffer();
        StringBuffer inputBuffer2 = new StringBuffer();

        String line;
int counter = 1;
int check =0;
int found = 0;
while((line = objReader.readLine()) != null)
{
    String[] listeMots = line.split("\\s+");

if(counter > 0)
{
    if(listeMots.length != 1){
    
    if(listeMots[0].equals(sup_id))
    {
   found = 1;
   check = 1;
int counter2=0;
String line2;
int found2=0;
while((line2 = objReader2.readLine()) != null)
{
    String[] listeMots2 = line2.split("\\s+");
    if(counter2 > 0){
if(listeMots2.length != 1)
{
    if(listeMots2[0].equals(sup_id))
    {
     found2=1;
    }else{
     found2=0;
    }
}
    }
    if(found2 == 0)
{
    inputBuffer2.append(line2);
    inputBuffer2.append("\n");
}
    counter2++;
}
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

objReader.close();
objReader2.close();

        // write the new string with the replaced line OVER the same file

        FileOutputStream fileOut= new FileOutputStream(this.file);
fileOut.write(inputBuffer.toString().getBytes());
fileOut.close();
FileOutputStream fileOut2= new FileOutputStream(this.file2);
fileOut2.write(inputBuffer2.toString().getBytes());
fileOut2.close();

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
public void supplierSupplies(String supplierId)
{
    int check = 0;

    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        int counter = 0;
        int checkCount=0;
        // int check =0;
        while( (currentLn = objReader.readLine()) != null)
        {  
            String[] listeMots = currentLn.split("\\s+");
            if(counter == 0 && checkCount == 0)
            {
     System.out.println("\n\t\t\t\t\tNUMBERED\t\t\tSUPPLY_CODE\t\t\t\tDATE-TIME\t\t\n");
     checkCount=1;     
    }else{
              
          if(listeMots.length != 1)
          {
                if(listeMots[1].equals(supplierId))
{
    check = 1;
    System.out.println("\n\t\t\t\t\t"+counter+"\t\t\t\t\t"+listeMots[0]+"\t\t\t\t"+listeMots[2]+"\t\t");     

    
}
          }else{
              counter--;
          }
         
            }
          
            counter++;
          
        }

    }catch(IOException e)
    {}
if(check == 0)
{
    System.out.println("\t\t\tSorry no supply found for this Supplier");
}
}

}
