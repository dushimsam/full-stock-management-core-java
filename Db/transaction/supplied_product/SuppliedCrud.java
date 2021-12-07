package Db.transaction.supplied_product;
import Db.transaction.supply.SuppliesCrud;
import java.io.*;
import Db.CrudMethods;
import Model.supplied_product.SuppliedProduct;


public class SuppliedCrud{

    private File file = new File("Db/transaction/files/supplied_product.csv");
    private File supplyFile = new File("Db/transaction/files/supply.csv");

    public void setFile(File filename)
{
    this.file = filename;
}

public  File getFile()
{
    return this.file;
}

public String checkExistence(String supply_id,String product_id){
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
        if(listeMots[0].equals(supply_id) && listeMots[1].equals(product_id))
        {
            response = product_id;
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



    public String createData(SuppliedProduct model){

        String response = null;
        try {
            if(this.checkExistence(model.getSupId(),model.getProdId()) == null){
                FileWriter writer = new FileWriter(file,true);
                writer.write("\n"+model.getSupId()+"\t"+model.getProdId()+"\t"+model.getQuantity()+"\t"+model.getUnitPrice());
                writer.close();
            }else{
                 response =this.checkExistence(model.getSupId(),model.getProdId());
            }
    
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
        String[] lists;
while( (currentLn = objReader.readLine()) != null)
{    if(counter == 0 && check == 0)
    {
        System.out.println("\n\t\tNUMBERED\t\tSUPPLY-CODE\t\t\tPRODUCT-CODE\t\t\tQUANTITY\t\t\tUNIT-PRICE\t\t\n");
        check =1;
    }else{
        lists = currentLn.split("\\s+");
        if(lists.length != 1){
            System.out.println("\n\t\t\t"+counter+"\t\t\t"+lists[0]+"\t\t\t"+lists[1]+"\t\t\t\t"+lists[2]+"\t\t\t\t"+lists[3]+"\t\t");     
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

public SuppliedProduct readByOne(String supply_id,String prod_id){
    SuppliedProduct product = new SuppliedProduct();
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

if(listeMots[0].equals(supply_id) && listeMots[1].equals(prod_id))
{
    check = 1;
    product.setSupId(listeMots[0]);
    product.setProdId(listeMots[1]);
    product.setQuantity(Float.parseFloat(listeMots[2]));
    product.setUnitPrice(Float.parseFloat(listeMots[3]));

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


public void updateData(String supply_id,String prod_id,int index,String newData,String status){
 
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

if(listeMots[0].equals(supply_id) && listeMots[1].equals(prod_id))
{
String oldData = listeMots[index];
String replaceLn= new String(line);

if(status =="product_id")
{
if(this.checkExistence(supply_id,newData) == null)
{
    replaceLn = replaceLn.replace(oldData,newData);
    replaceLn="\n"+listeMots[0]+"\t"+newData+"\t"+listeMots[2]+"\t"+listeMots[3]+"\t";
    check = 1;
}else{
System.out.println("\t\t\tSuch supply has been made");
check =0;
}
}else{
    replaceLn = replaceLn.replace(oldData,newData);
    check = 1;
}

line = replaceLn;

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
System.out.println("\n\n\t\t\tTRY AGAIN\n");
}
}catch(IOException e)
{
        System.out.println(e.getMessage());
}

}

public int deleteData(String supply_id,String prod_id){

    try{
        BufferedReader file = new BufferedReader(new FileReader(this.file));
        StringBuffer inputBuffer = new StringBuffer();
        String line;
int counter = 1;
int check =0;
int found = 0;
//count number of products having the same supply_id
int supplyExistence=0;
while((line = file.readLine()) != null)
{
    if(counter > 0)
{
    String[] listeMots = line.split("\\s+");

if(listeMots.length != 1)
{
if(listeMots[0].equals(supply_id))
{
    supplyExistence++;
}
}

}
counter++;
}

if(supplyExistence == 1)
{
    SuppliesCrud supply = new SuppliesCrud();
supply.deleteData(supply_id);
}
counter =0;
while((line = file.readLine()) != null)
{
    String[] listeMots = line.split("\\s+");

if(counter > 0)
{

    if(listeMots.length != 1){
    if(listeMots[0].equals(supply_id) && listeMots[1].equals(prod_id))
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





public void suppliedProductsByDate(String supplyDate)
{
    int check = 0;
    try{
        BufferedReader objReader = new BufferedReader(new FileReader(supplyFile));
        BufferedReader objReader2 = new BufferedReader(new FileReader(file));
         String[] lists;
        String currentLn = "";
        String currLn2 = "";
        int counter = 0;
       
        String[] data;
    while( (currentLn = objReader.readLine()) != null)
    {
    if(counter > 0)
    {
        String supId;
        String[] listeMots = currentLn.split("\\s+");
        if(listeMots.length != 1){
     data = listeMots[2].split("_+");
  
    if(data[0].equals(supplyDate))
    {
        supId = listeMots[0];
        int counter2=0;
        int checkCount=0;
        while( (currLn2 = objReader2.readLine()) != null)
        {
            String[] listeMots2 = currLn2.split("\\s+");
            if(counter2 >0 && checkCount != 0){
                if(listeMots2.length != 1)
                {
            if(listeMots2[0].equals(supId))
            {      
            
               lists = currLn2.split("\\s+");
               if(lists.length != 1){
                   System.out.println("\n\t\t\t"+counter2+"\t\t\t"+lists[0]+"\t\t\t"+lists[1]+"\t\t\t\t"+lists[2]+"\t\t\t\t"+lists[3]+"\t\t");     
               }else{
                counter2--;
               }
       
               check =1;
            }
        }
    
        }else{
checkCount =1;
 System.out.println("\n\t\tNUMBERED\t\tSUPPLY_ID\t\t\tPRODUCT_ID\t\t\tQUANTITY\t\t\tUNIT-PRICE\t\t\n");
         
        }
counter2++;
        }

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
if(check == 0)
{
    System.out.println("\t\t\tSorry we don't find any supply on this date");
}

}


}