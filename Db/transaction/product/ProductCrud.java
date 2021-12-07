package Db.transaction.product;
import java.util.*;
import java.io.*;
import Db.CrudMethods;
import Model.product.Product;


public class ProductCrud implements CrudMethods{


    private File file = new File("Db/transaction/files/product.csv");

    public void setFile(File filename)
{
    this.file = filename;
}

public  File getFile()
{
    return this.file;
}

// public int lastId()
// {
//     int id=0;
//     try{
//         BufferedReader input = new BufferedReader(new FileReader(file));
//         String last="", line="";
//         int counter=0;
//         while ((line = input.readLine()) != null) {
//           if(counter > 0)
//           {
//             last = line;
//           }
//             counter++;
//         }
    
//         String[] list= last.split("\\s+");
//         if(list.length == 1)
//         {
//             id = 0;
//         }else{
//             id = Integer.parseInt(list[0]);
//         }
//     }catch(IOException e)
//     {

//     }
// return id;
// }


public boolean exists(String input,String status){

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
        if(status.equals("code"))
        {
if(listeMots[0].equals(input))
{
 exist = true;
}
        }else{
            if(listeMots[1].equals(input))
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

// public String giveId(String name)
// {

//  String id = null;
// try {
//     BufferedReader objReader = new BufferedReader(new FileReader(file));
//     String currentLn = "";

//     int counter = 0;
//     int check = 0;
// while( (currentLn = objReader.readLine()) != null)
// {
//     if(counter > 0)
// {
//  String[] listeMots = currentLn.split("\\s+");
//     if(listeMots.length != 1)
//     {
// if(listeMots[1].equals(name))
// {
//  id  =listeMots[0];
// }
//    }
// }
// counter++;  
// }

// objReader.close();

// } catch (IOException e) {
// System.out.println("\n\n\t\t\tCan't read File\n\n");
// }

// return id;
// }  

public void createData(Product model){

    try {
            String code = this.generateRandomString().toUpperCase();
            FileWriter writer = new FileWriter(file,true);
            writer.write("\n"+code+"\t"+model.getProdName()+"\t"+model.getCategId()+"\t"+model.getExpiration());
            writer.close();
} catch (IOException e) {
    System.out.println("\n\n\t\t\tCan't read File\n\n");
}

}


public void readData(){
    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        String[] lists;
         int counter =0;
        int check =0;
while( (currentLn = objReader.readLine()) != null)
{  
    lists = currentLn.split("\\s+");
    if(lists.length != 1){
    if(counter == 0)
    {
    System.out.println("\n\t----------------------------------------------------------------------------------------------------------------------------------------------");
     System.out.println("\n\t\tNUMBERED\t\t\tCODE\t\t\tPRODUCT NAME\t\t\tCATEGORY\t\tEXPIRATION_IN_MONTHS\n");
     System.out.println("\n\t---------------------------------------------------------------------------------------------------------------------------------------------");
 check = 1;
    }else{
         if(lists.length != 1){
        System.out.println("\n\t\t\t"+counter+"\t\t\t"+lists[0]+"\t\t\t"+lists[1]+"\t\t\t"+lists[2]+"\t\t\t"+lists[3]+"\n");     
         }else{
             counter--;
         }
    }
    counter++;  
   }
   
}
objReader.close();
    }catch(IOException e)
    {
        System.out.println(e.getMessage());
    }
   
}

public Product readByOne(String input){
    Product product = new Product();
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
    if(listeMots.length != 1)
    {
        
            if(listeMots[0].equals(input))
            {
                check = 1;
                product.setProdCode(listeMots[0]);
                product.setProdName(listeMots[1]);
                product.setCategId(listeMots[2]);
                product.setExpiration(Integer.parseInt(listeMots[3]));
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
return product;
}

public void updateData(String code,int index,String newData){
 
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
if(listeMots[0].equals(code))
{
String oldData = listeMots[index];
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

public int deleteData(String code){
    
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

    if(listeMots[0].equals(code))
    {
   found = 1;
   check = 1;
    }else {
        found = 0;
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