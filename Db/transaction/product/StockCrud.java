
package Db.transaction.product;
import java.io.*;
import Db.CrudMethods;
import Model.product.*;
import  Db.transaction.product.*;


public class StockCrud{


    private File file = new File("Db/transaction/files/stock.csv");
    private File file2 = new File("Db/transaction/files/product.csv");

    public void setFile(File filename)
{
    this.file = filename;
}

public  File getFile()
{
    return this.file;
}


public boolean checkExistence(String prod_id){
String exists = false;
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
    if(listeMots[0].equals(prod_id))
    {
exists = true;
    }
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


public void createData(Stock model){
    try {
            FileWriter writer = new FileWriter(file,true);
            writer.write("\n"+model.getProdId()+"\t"+model.getTotalQuantities()+"\t"+model.getUnitPrice());
            writer.close();
} catch (IOException e) {
    System.out.println("\n\n\t\t\tCan't read File\n\n");
}

}


public void readData(){
    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        int counter =0;
        int check = 0;
while( (currentLn = objReader.readLine()) != null)

{      if(counter == 0 && check == 0)
    {
        System.out.println("\n\t\tNUMBERED\t\tPRODUCT-CODE\t\t\tTOTAL-QUANTITIES\t\t\n");
        check=1;
    }else{
    String[]  lists = currentLn.split("\\s+");
        if(lists.length != 1){
            System.out.println("\n\t\t\t"+counter+"\t\t\t"+lists[0]+"\t\t\t"+lists[1]+"\t\t\t");     
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

public Stock readByOne(String prod_id){
    Stock stock = new Stock();
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
if(listeMots[0].equals(prod_id))
{
    check = 1;
    stock.setProdId(listeMots[0]);
    stock.setTotalQuantities(Float.parseFloat(listeMots[1]));
    stock.setUnitPrice(Float.parseFloat(listeMots[2]));
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
return quantity;
}

public void updateData(String prod_id,int index,float data){
    boolean check = true;
    try{
    BufferedReader file = new BufferedReader(new FileReader(this.file));
    StringBuffer inputBuffer = new StringBuffer();
    String line;
int counter = 0;
while((line = file.readLine()) != null)
{
String[] listeMots = line.split("\\s+");

if(counter > 0)
{
if(listeMots.length != 1){
if(listeMots[0].equals(prod_id))
{
String oldData = listeMots[index];
float oldD = Float.parseFloat(oldData);
float newData = oldD + data;

if(newData < 0)
  newData = 0;

String replaceLn= new String(line);
replaceLn = replaceLn.replace(Float.toString(oldData),Float.toString(newData));
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

}catch(IOException e)
{
        System.out.println(e.getMessage());
}
}

public boolean quantityAvailable(String productId,float quantities){
boolean available = false;
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
         if(listeMots[0].equals(productId))
         {
           float difference = quantities - Float.parseFloat(listeMots[1]);
           if(difference < 0)
              available = false;
           else 
              available = true;  
         }
     }
}
counter++;
}
  }catch(IOException e)
    {
            System.out.println(e.getMessage());
    }
    return available;
}

public int deleteData(String prod_id){
    
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

    if(listeMots[0].equals(prod_id))
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


public void print_rectangle(int n, int m,String productNames,String productQuantities,String productPrices) 
{ 

    String[] prodNames = productNames.split("\\s+");
    int itemLen = prodNames.length;

    String[] prodQuantities = productQuantities.split("\\s+");
     String[] prodPrices = productPrices.split("\\s+");  
    int rows,j;

    if(itemLen > 3)
    {


    }
	for (rows = 1; rows <= n; rows++) 
	{ 

        if(rows == 1 || rows == n){
            int count = 1;
            while(count <= itemLen)
            {
                System.out.print(" \t");
    for (j = 1; j <= m; j++) 
		{ 

                System.out.print("*");

                }
                System.out.print("\t\t");
                count++;
            }
    }
    else if(rows == 2){
        int count = 1;
        int items = 0;
        while(items < itemLen){
            System.out.print(" \t");
            String[] splitProdName = prodNames[items].split("~+");
            String currName = "      "+splitProdName[1];

            char[] ch = currName.toCharArray(); 

        for (j = 1; j <= m; j++) 
            { 

                if(j == 1 || j==m)
                {
                    System.out.print("*");
                }

            else  if(j <= currName.length()+1)
            {

                    int index = j-2;
                    System.out.print(ch[index]);
            
            }
                else{
                    System.out.print(" ");
                }
                   
            }
            System.out.print("\t\t");
            items++;
        }
    }
else if(rows == 3)
{
    int count = 1;
    int items = 0;

    while(items < itemLen){
       
        String currQuant = "       "+prodQuantities[items];
        char[] ch = currQuant.toCharArray(); 

        String[] splitProdName = prodNames[items].split("~+");
        String numbered =splitProdName[0];

System.out.print(numbered+".\t");
    for (j = 1; j <= m; j++) 
        { 

            if(j == 1 || j== m)
            {
                System.out.print("*");
            }

        else if(j <= currQuant.length()+1)
        {

                int index = j-2;
                System.out.print(ch[index]);

        }
            else{
                System.out.print(" ");
            }
               
        }
        System.out.print("\t\t");
        items++;
    }
   
}
else if(rows == 4)
{
    int count = 1;
    int items = 0;
    while(items < itemLen){
        System.out.print(" \t");
        String currPric = "       "+prodPrices[items];
        char[] ch = currPric.toCharArray(); 

    for (j = 1; j <= m; j++) 
        { 
           if(j == 1 || j==m)
            {
                System.out.print("*");
            }
           
        else if(j <= currPric.length()+1)
        {
          
         
                int index = j-2;
                System.out.print(ch[index]); 
            
            
        }
            else{
                System.out.print(" ");
            }
               
        }
        System.out.print("\t\t");    
            items++;
    }
   
}

else{
    int count = 1;
int items = 0;
    while(items < itemLen)
    {
        System.out.print(" \t");
     for (j = 1; j <= m; j++) 
{
    if(j == 1 || j == m)
    {
        System.out.print("*");
    }else{
        System.out.print(" ");
    }

        }
        System.out.print("\t\t");
        items++;
    }
}

System.out.print("\n");
}

}


public void purchase()
{

    Stock quantity = new Stock();
    try{
        ProductCrud productCrud = new ProductCrud();
        Product prodModel = new Product();
    BufferedReader objReader = new BufferedReader(new FileReader(file));
    String currentLn = "";
    int counter = 0;
    int check = 0;
    String productNames="";
    String productQuantities ="";
    String productPrices ="";
    int countProds = 1;
while( (currentLn = objReader.readLine()) != null)
{
if(counter > 0)
{
    String[] listeMots = currentLn.split("\\s+");

    if(listeMots.length != 1){
        if(Float.parseFloat(listeMots[1]) > 0)
        {
            prodModel= productCrud.readByOne(listeMots[0]);
            productNames +=countProds+"~"+prodModel.getProdName()+"\t";
            productQuantities +=listeMots[1]+"\t";
            productPrices +=prodModel.getPrice()+"\t";
                   countProds++;
        }
     
      }
}
counter++;
}

productNames.substring(0, productNames.length()-1);
productQuantities.substring(0, productQuantities.length()-1);
productPrices.substring(0, productPrices.length()-1);
String[] prodNames = productNames.split("\\s+");
String[] prodQuant = productQuantities.split("\\s+");
String[] prodPric = productPrices.split("\\s+");


int itemLen = prodNames.length;
int count =0;
String currProdNames ="";
String currProdQuant ="";
String currProdPric ="";
 while(count < itemLen)
 {

       currProdNames +=prodNames[count];
       currProdQuant +=prodQuant[count];
       currProdPric+=prodPric[count];

currProdNames +="\t";
currProdQuant +="\t";
currProdPric +="\t";

String[] currProdLen = currProdNames.split("\\s+");

     if(currProdLen.length == 3 || count == itemLen-1)
     {

         currProdNames.substring(0, currProdNames.length()-1);
         currProdQuant.substring(0, currProdQuant.length()-1);
         currProdPric.substring(0, currProdPric.length()-1);
         this.print_rectangle(7,30,currProdNames,currProdQuant,currProdPric);
         currProdNames="";
         currProdQuant="";
         currProdPric="";
     }

count++;
  }



}catch(IOException e)
{
    System.out.println(e.getMessage());
}
}

public Product returnOrderedProduct(int prodNbr)
{
    Product prodModel = new Product();
    Stock quantity = new Stock();
    try{
        ProductCrud productCrud = new ProductCrud();
    
    BufferedReader objReader = new BufferedReader(new FileReader(file));
    String currentLn = "";
    int counter = 0;
    int check = 0;
    String productNames="";
    String productQuantities ="";
    String productPrices ="";
    int countProds = 1;
while( (currentLn = objReader.readLine()) != null)
{
if(counter > 0)
{
    String[] listeMots = currentLn.split("\\s+");

    if(listeMots.length != 1){
        if(Float.parseFloat(listeMots[1]) > 0)
        {
            if(countProds == prodNbr)
            {
                prodModel= productCrud.readByOne(listeMots[0]);
            }
                   countProds++;
        }
     
      }
}
counter++;
}
    }catch(IOException e)
    {
        System.out.println(e.getMessage());
    }
return prodModel;
}

public int nbrOfProductsToOrder()
{
    int countProds = 0;

    Stock quantity = new Stock();
    try{
        ProductCrud productCrud = new ProductCrud();
        Product prodModel = new Product();
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
        if(Float.parseFloat(listeMots[1]) > 0)
        {
                   countProds++;
        }
     
      }
}
counter++;
}


}catch(IOException e)
{
    System.out.println(e.getMessage());
}
return countProds;

}
}