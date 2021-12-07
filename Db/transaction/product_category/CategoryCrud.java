package Db.transaction.product_category;

import java.io.*;
import Db.CrudMethods;
import Model.product_category.ProductCategory;


public class CategoryCrud implements CrudMethods{


    private File file = new File("Db/transaction/files/product_category.csv");

    public void setFile(File filename)
{
    this.file = filename;
}

public  File getFile()
{
    return this.file;
}

public boolean exists(String value,String subject){
   
    boolean exist = false;
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
    
    if(subject.equals("id")){
    if(listeMots[0].equals(value))
    {
        exist = true;
  
    }
}else{
    if(listeMots[1].equals(value))
    {
        exist = true;
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
    return exist;
    }

public int createData(ProductCategory model){
 int response = 0;
    try {
      
        String id = this.generateRandomString().toUpperCase();
        FileWriter writer = new FileWriter(file,true);
         
        writer.write("\n"+id+"\t"+model.getCatName()+"\t"+model.getDescription());
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
        int counter =0;
        int check =0;
while( (currentLn = objReader.readLine()) != null)
{  
    String[] lists = currentLn.split("\\s+");

    if(counter == 0 && check == 0)
    {
        System.out.println("\n\t--------------------------------------------------------------------------------------------------------");
    System.out.println("\n\t\tNUMBERED\t\tCODE\t\tNAME\t\t\t DESCRIPTION\t\t\n");
    System.out.println("\n\t------------------------------------------------------------------------------------------------------------");
     check = 1;
}else{
    if(lists.length != 1){
    System.out.println("\n\t\t"+counter+"\t\t "+lists[0]+"\t\t "+lists[1]+"\t\t\t "+lists[2]+"\t\t");
    // System.out.println("\n\t\t----------------------------------------------------------------------------\t\t");
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

public ProductCategory readByOne(String id){
    ProductCategory category = new ProductCategory();
    try{
    BufferedReader objReader = new BufferedReader(new FileReader(file));
    String currentLn = "";
    int counter = 0;
    int check = 0;
while((currentLn = objReader.readLine()) != null)
{
if(counter > 0)
{
  
    String[] listeMots = currentLn.split("\\s+");
if(listeMots.length != 1)
{
    if(listeMots[0].equals(id))
        {
            check = 1;
            category.setCatId(listeMots[0]);
            category.setCatName(listeMots[1]);
            category.setDescription(listeMots[2]);
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
return category;
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

if(listeMots[0].equals(id))
{
String oldData = listeMots[index];
String replaceLn= new String(line);
replaceLn = replaceLn.replace(oldData,newData);
line = replaceLn;
check = 1;
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
System.out.println("\n\n\t\t\tTry again error occured\n");
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
    

if(counter > 0)
{

    String[] listeMots = line.split("\\s+");
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
