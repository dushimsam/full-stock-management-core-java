package Db.transaction.order;

import Db.transaction.product.*;
import java.io.*;
import Db.RandomIds;
import Model.order.Order;
import Db.transaction.ordered_product.*;

public class OrderCrud implements RandomIds{


    private File file = new File("Db/transaction/files/order.csv");
    private File file2 = new File("Db/transaction/files/ordered_product.csv");
 

    public void setFile(File filename)
{
    this.file = filename;
}

public  File getFile()
{
    return this.file;
}






public Order createData(Order model){
    try {
            String orderId = "order_"+this.generateRandomString().toUpperCase();
            model.setOrderId(orderId);
            FileWriter writer = new FileWriter(file,true);
            writer.write("\n"+orderId+"\t"+model.getCustId()+"\t"+model.getOrderDate());
            writer.close();

} catch (IOException e) {
    System.out.println("\n\n\t\t\tCan't read File\n\n");
}
return model;
}

public void readData(){
    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        String[] lists;
        int counter =0,check=0;

while((currentLn = objReader.readLine()) != null)
{  
    if(counter == 0)
    {
        System.out.println("\n\t------------------------------------------------------------------------------------------------------------");
            System.out.println("\n\tNUMBERED |\t\tORDER_ID| \t\tCUSTOMER_ID |\t\tDATE-TIME|\n");
            System.out.println("\n\t--------------------------------------------------------------------------------------------------------");
check = 1;
    }else{
    lists = currentLn.split("\\s+");
    if(lists.length != 1){
        System.out.println("\n\t\t"+counter+"\t\t"+lists[0]+"\t\t"+lists[1]+"\t\t"+lists[2]+"\t\n");     
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

public StringBuffer dateOrders(String orderDate)
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

        String orderId;
        String[] listeMots = currentLn.split("\\s+");
        if(listeMots.length != 1){
    String[] fileDate =listeMots[2].split("_+");
    if(fileDate[0].equals(orderDate))
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
public Order readByOne(String id){
    Order order = new Order();
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
            order.setOrderId(listeMots[0]);
            order.setCustId(listeMots[1]);
            order.setOrderDate(listeMots[2]);
        
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
return order;
}

public void updateData(String order_id,String newData){
 
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
if(listeMots[0].equals(order_id))
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
System.out.println("\n\n\t\t\tTry again error occured\n");
}
}catch(IOException e)
{
        System.out.println(e.getMessage());
}

}
public int deleteData(String order_id){

    try{
        BufferedReader objReader = new BufferedReader(new FileReader(this.file));
        BufferedReader objReader2 = new BufferedReader(new FileReader(this.file2));

        StringBuffer inputBuffer = new StringBuffer();
        StringBuffer inputBuffer2 = new StringBuffer();
        StockCrud stock = new StockCrud();
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
    
    if(listeMots[0].equals(order_id))
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
    if(listeMots2[0].equals(order_id))
    {
found2=1;
stock.updateData(listeMots2[1],Float.parseFloat(listeMots2[2]),"addition");
    }else{
   found2 = 0;
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
public void customerOrders(String customerId,String id)
{
    int check = 0;

    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        int counter = 0;
        while( (currentLn = objReader.readLine()) != null)
        {    if(counter == 0)
            {
                if(id.equals("CLIENT"))
                {
               System.out.println("\t\t\t\t\t\t\t\tPURCHASES MADE\n\n\n");
                }else{
                    System.out.println("\n\t\t\t\t\tNUMBERED\t\t\tORDER_ID\t\t\t\tDATE-TIME\t\t\n");
                }
            }else{
                String[] listeMots = currentLn.split("\\s+");
          if(listeMots.length != 1)
          {
                if(listeMots[1].equals(customerId))
{
    check = 1;
    if(id.equals("CLIENT"))
    {
        System.out.println("\t\t\t\t\t\t\t"+listeMots[2]);
        System.out.println("\t\t\t\t\t\t\t____________________\n\n");
         OrderedCrud orderedCrud = new OrderedCrud();
         orderedCrud.productUnderOrders(listeMots[0]);
         System.out.println("\n\n");
    }else{
        System.out.println("\n\t\t\t\t\t"+counter+"\t\t\t\t\t"+listeMots[0]+"\t\t\t\t"+listeMots[2]+"\t\t");     
    }

    
}
          }
         
            }
            counter++;
           
        }

    }catch(IOException e)
    {}
if(check == 0)
{
    System.out.println("\t\t\t\t\tSorry no order found");
}
}

}