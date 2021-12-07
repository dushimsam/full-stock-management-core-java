package Db.transaction.ordered_product;
import java.io.*;
import Db.CrudMethods;
import Db.transaction.order.OrderCrud;
import Model.ordered_product.OrderedProduct;
import Db.transaction.product.*;
import Model.product.*;
public class OrderedCrud{


    private File file = new File("Db/transaction/files/ordered_product.csv");
    private File orderFile = new File("Db/transaction/files/order.csv");

    public void setFile(File filename)
{
    this.file = filename;
}

public  File getFile()
{
    return this.file;
}

public boolean checkExistence(String order_id,String product_id){
    boolean exists = false;
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
    if(listeMots[0].equals(order_id) && listeMots[1].equals(product_id))
         exists = true;
    }
    }
    counter++;  
    }
    
    objReader.close();
    
    } catch (IOException e) {
    System.out.println("\n\n\t\t\tCan't read File\n\n");
    }
    return exists;
    }



    public int createData(OrderedProduct model){

        int response = 0;
        try {
            if(this.checkExistence(model.getOrderId(),model.getProdId())){
                FileWriter writer = new FileWriter(file,true);
                writer.write("\n"+model.getOrderId()+"\t"+model.getProdId()+"\t"+model.getQuantity()+"\t"+model.getUnitPrice());
                writer.close();
                response = 1;
            }else{
                
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
        String[] lists;
while( (currentLn = objReader.readLine()) != null)
{    if(counter == 0)
    {
        System.out.println("\n\t\tNUMBERED\t\tORDER_ID\t\t\tPRODUCT_ID\t\t\tQUANTITY\t\t\tUNIT-PRICE\t\t\n");
    }else{
        lists = currentLn.split("\\s+");
        if(lists.length != 1){
            System.out.println("\n\t\t\t"+counter+"\t\t\t"+lists[0]+"\t\t\t"+lists[1]+"\t\t\t\t"+lists[2]+"\t\t\t\t"+lists[3]+"\t\t");     
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

public OrderedProduct readByOne(String order_id,String prod_id){
    OrderedProduct product = new OrderedProduct();
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

if(listeMots[0].equals(order_id) && listeMots[1].equals(prod_id))
{
    check = 1;
    product.setOrderId(listeMots[0]);
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


public void updateData(String order_id,String prod_id,int index,String newData,String status){
 
    try{
    BufferedReader file = new BufferedReader(new FileReader(this.file));
    StringBuffer inputBuffer = new StringBuffer();
    String line;
int counter = 0;
int check =0;
while((line = file.readLine()) != null)
{

if(counter > 0)
{
String[] listeMots = line.split("\\s+");

if(listeMots.length != 1)
{
    if(listeMots[0].equals(order_id) && listeMots[1].equals(prod_id))
{
String oldData = listeMots[index];
String replaceLn= new String(line);

if(status.equals("product_id"))
{

    replaceLn = replaceLn.replace(oldData,newData);
    replaceLn="\n"+listeMots[0]+"\t"+newData+"\t"+listeMots[2]+"\t"+listeMots[3]+"\t";
    check = 1;

}else{
    replaceLn = replaceLn.replace(oldData,newData);
    check = 1;
}

line = replaceLn;

}
}

}
inputBuffer.append(line);
inputBuffer.append("\n");
counter++;
}

file.close();
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

public int deleteData(String order_id,String prod_id){
    int check =0;

    try{
        BufferedReader objReader = new BufferedReader(new FileReader(this.file));
        StringBuffer inputBuffer = new StringBuffer();
        StockCrud stock = new StockCrud();
        String line;
int counter = 1;
//count number of products having the same order_id
int orderExistence=0;
while((line = objReader.readLine()) != null)
{
    if(counter > 0)
{
    String[] listeMots = line.split("\\s+");

if(listeMots.length != 1)
{
if(listeMots[0].equals(order_id))
{
    orderExistence++;
}
}

}
counter++;
}

if(orderExistence == 1)
{
OrderCrud order = new OrderCrud();
order.deleteData(order_id);
check = 1;
}else{
    counter =0;
    int found = 0;

    while((line = objReader.readLine()) != null)
    {
        String[] listeMots = line.split("\\s+");
    
    if(counter > 0)
    {
        if(listeMots.length != 1){
        if(listeMots[0].equals(order_id) && listeMots[1].equals(prod_id))
        {
     stock.updateData(prod_id,Float.parseFloat(listeMots[2]),"addition");
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
}
objReader.close();


        FileOutputStream fileOut= new FileOutputStream(this.file);
fileOut.write(inputBuffer.toString().getBytes());
fileOut.close();

    }catch(IOException e)
    {
            System.out.println(e.getMessage());
    }

    return check;
}





public void orderedProductsByDate(String orderDate)
{
    int check = 0;
    try{
        BufferedReader objReader = new BufferedReader(new FileReader(orderFile));
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
        String orderId;
        String[] listeMots = currentLn.split("\\s+");
        if(listeMots.length != 1){
     data = listeMots[2].split("_+");
  
    if(data[0].equals(orderDate))
    {
        orderId = listeMots[0];
        int counter2=0;
        while( (currLn2 = objReader2.readLine()) != null)
        {
            String[] listeMots2 = currLn2.split("\\s+");
            if(counter2 >0){
                if(listeMots2.length != 1)
                {
            if(listeMots2[0].equals(orderId))
            {      
               lists = currLn2.split("\\s+");
               if(lists.length != 1){
 
                    System.out.println("\n\t\t\t"+counter2+"\t\t\t"+lists[0]+"\t\t\t"+lists[1]+"\t\t\t\t"+lists[2]+"\t\t\t\t"+lists[3]+"\t\t");     
                  
               }
       
               check =1;
            }
        }
        }else{
       
                System.out.println("\n\t\tNUMBERED\t\tORDER_ID\t\t\tPRODUCT_ID\t\t\tQUANTITY\t\t\tUNIT-PRICE\t\t\n");
            
         
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
    System.out.println("\t\t\tSorry we don't find any order on this date");
}

}


public void productsUnderOrder(String orderId){
    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        int counter =1;
        String[] lists;
        int countLoop=0;
while( (currentLn = objReader.readLine()) != null)
{    if(countLoop == 0)
    {
         System.out.println("\n\t--------------------------------------------------------------------------------------------------------------------");
          System.out.println("\n\t\t\tNUMBERED\t\t\tPRODUCT-CODE\t\t\tQUANTITY\t\t\tUNIT-PRICE\t\t");
            System.out.println("\n\t-----------------------------------------------------------------------------------------------------------------");
                 check = 1;
    }else{
        lists = currentLn.split("\\s+");
        if(lists.length != 1){
            if(lists[0].equals(orderId))
            {      
    System.out.println("\n\t\t\t\t"+counter+"\t\t\t"+lists[1]+"\t\t\t"+lists[2]+"\t\t\t"+lists[3]+"\t\t");           
     }
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
}