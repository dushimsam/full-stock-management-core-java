package options;
import java.text.*;
import Model.supplies.*;

import View.supplies.*;
import Controller.supplies.*;
import Controller.SupplierController;
import Model.Supplier;
import Db.SupplierCrud;
import Db.transaction.product.*;
import View.supplies.*;
import Db.*;
import Model.supplied_product.*;
import Controller.supplied_product.*;

import View.supplied_product.*;
import Db.transaction.supplied_product.*;
import Db.transaction.supply.*;
import java.util.*;
import java.io.*;
import utils.*;

public class SuppliesOption{


public void singleRecord(SuppliesController controller,Supplies model)
{
  SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
  dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));

  String todayDate = dateTimeInGMT.format(new Date());
  todayDate=todayDate.replaceAll(" ","_");
  String[] dateMots =todayDate.split("_+");

SupplierCrud supplier = new SupplierCrud();
SuppliesCrud  supCrud  = new SuppliesCrud();
  boolean valid = false;
  Validation validate = new Validation(); 
  Scanner scan = new Scanner(System.in);
  String supplierId;
  suppliesRecordLoop:while(1 > 0){
    System.out.println("\n\t\t\t\t\tRecords From ** Supply Id-"+controller.getSuppId()+"**"); 
    controller.updateView();  
    System.out.println("\n\t\t\t\t\tTake Action");
    
    System.out.println("\n\t\t\t\t\t1.Update");
    System.out.println("\n\t\t\t\t\t2.Delete");
    System.out.println("\n\t\t\t\t\t3.Not Now");
    System.out.print("\n\t\t\t\t\tChoose:.. ");
    String  choice4 = scan.nextLine();
    if(choice4.equals("1"))
    {
    System.out.print("\t\t\tEnter the Corresponding Number of the Record:  ");
    int num = scan.nextInt();
    switch(num)
    {
      case 1:
   
      System.out.println("\t\t\tCAN NOT INDEX CAN NOT BE UPDATED\n\t\t\t\tPRESS ENTER TO CONTINUE\n");
      scan.nextLine();
      scan.nextLine();
    break;
    case 2:
  
    System.out.print("\t\t\tExisting Supplier Id:  "+ controller.getSupplyId());
   boolean validSupId=false;
   updateSupIdLoop:do{
      System.out.print("\t\t\t0.Back");
      System.out.print("\t\t\tEnter The New Code: ");
      scan.nextLine();
      supplierId = scan.nextLine();
     if(supplierId.equals("0"))
     {
       break updateSupIdLoop;
     }
      if(supplier.checkExistence(supplierId,"id") == null)
      {
        System.out.println("\t\t\tSorry such Supplier is not registered");
      }else{
        if(supCrud.checkExistence(supplierId,dateMots[0]) == null)
        {
          validSupId=true;
          supCrud.updateData(controller.getSuppId(),supplierId);
          controller.setSupplyId(supplierId);
        }else{
          System.out.println("\t\t\tSorry Such Supply has been made Today");
        }
      }

  }while(!validSupId);

    break;
    case 3:
    System.out.println("\n\n\t\t\tDATE CAN NOT BE UPDATED\n\t\t\tENTER TO CONTINUE\n");
    scan.nextLine();
    scan.nextLine();
    break;
    default:
    System.out.println("\t\t\tInvalid Choice Try AGAIN");
    }
    }else if(choice4.equals("2"))
    {
      System.out.println("\t\t\tThis can result in losing all supplied products under this supply");
    System.out.print("\t\t\tAre You sure you want to Delete These Data (Y/N)...");
    String selection = scan.nextLine();
    if(selection.equals("yes") ||selection.equals("YES") || selection.equals("y") || selection.equals("Y"))
    {
    if(supCrud.deleteData(controller.getSuppId()) == 1){
    System.out.println("\t\t\tRecord is removed from the list");
    }else{
    System.out.println("\t\t\tTry again error occurred");
    }
    }else{}}
    else if(choice4.equals("3"))
    {
      break suppliesRecordLoop;
    }
    else{
     System.out.println("\t\t\tInvalid choice");
    }
  
  }

}


public void addSupply()
{
  boolean valid = false;
  Validation validate = new Validation(); 
  Scanner scan = new Scanner(System.in);

  String supplierId;
  System.out.println("\t\t\tEnter The Details of New Supply\n\n");
             
              
              boolean validSupId=false;
              SupplierCrud supplier = new SupplierCrud();
              do{
                  System.out.print("\n\t\t\tEnter The Supplier Code:... ");
                  supplierId = scan.nextLine();
  
                  if(supplier.checkExistence(supplierId,"id") == null)
                  {
                    System.out.println("\t\t\tSorry such Supplier is not registered");
                  }else{
                    validSupId=true;
                  }
  
              }while(!validSupId);
  
          if(supplierId != "")
            {
           
              SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
              dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
    
              String todayDate = dateTimeInGMT.format(new Date());
              todayDate=todayDate.replaceAll(" ","_");
              String[] dateMots =todayDate.split("_+");

            
                Supplies model = new Supplies();
                model.setSupplierId(supplierId);
                model.setSupDate(todayDate);
    
               SuppliesView view = new SuppliesView();
               SuppliesController controller = new SuppliesController(model,view);
               SuppliesCrud sup = new SuppliesCrud();
               ProductCrud prod = new ProductCrud();
               SuppliedCrud supplied =new SuppliedCrud();
                      boolean supplyMade = false;
               if(sup.checkExistence(supplierId,dateMots[0]) == null)
               {
                System.out.println("\n\n\t\t\t\tHere's the  New Supply");
                controller.setSuppId(Integer.toString(sup.createData(model)));
                System.out.println("\n\n");
                supplyMade=true;
               }else{
                String supCode = sup.checkExistence(supplierId,dateMots[0]);
                System.out.println("\n\n\t\t\t\tAdd Products To Supply Code "+supCode);
                controller.setSuppId(supCode);
                System.out.println("\n\n");
               }

             
              if(Integer.parseInt(model.getSupId()) !=  0)
        {
         
          controller.updateView();
        boolean stopSupplying = false;
        String prodName="";
        float quantity,unitPrice;
 
             int supplyCount=1;
        supplying:do{
        boolean checkProd=false;
        boolean prodExists = true;
        do{
        do{
          System.out.println("\t\t\t0.Back");
          System.out.print("\t\t\tEnter Supplied Product's Name... ");
          prodName=scan.nextLine();

          if(prodName.equals("0"))
{
  if(!supplyMade && supplyCount == 1)
  {
    sup.deleteData(model.getSupId());
  }  
  break supplying;
}

          if(prod.checkExistence(prodName) == 0)
            {
                System.out.println("\t\t\tSorry such product does not exist");
            }else{
                   checkProd= true;
            }
            }while(!checkProd);
            String prodId = Integer.toString(prod.checkExistence(prodName));

   if(supplied.checkExistence(model.getSupId(),prodId) == null)
{
  
  prodExists=false;
  
    do{
           System.out.print("\n\t\t\tEnter New Quantities:...... ");
            valid =validate.validate("float","quantity");
          }while(!valid);
     quantity =validate.getValidFloat();

     if(quantity == 0)
{
  if(!supplyMade)
  {
    sup.deleteData(model.getSupId());
  }  
  break supplying;
}

     StockCrud stock = new StockCrud();
     String stockProdId = Integer.toString(prod.checkExistence(prodName));
    stock.updateData(stockProdId,quantity,"addition");

do{
  System.out.println("\t\t\t0.Back");
  System.out.print("\n\t\t\tEnter the supplied at(unit price):...... ");
  valid =validate.validate("float","price");
}while(!valid);
 unitPrice =validate.getValidFloat();

 if(unitPrice == 0)
{
  if(!supplyMade)
  {
    sup.deleteData(model.getSupId());
  }  
break supplying;
}

 SuppliedProduct suppliedProModel = new SuppliedProduct();
                 suppliedProModel.setProdId(Integer.toString(prod.checkExistence(prodName)));
                 suppliedProModel.setSupId(model.getSupId());
                 suppliedProModel.setQuantity(quantity);
                 suppliedProModel.setUnitPrice(unitPrice);
                 supplied.createData(suppliedProModel);
                 System.out.print("\t\t\tProduct is added");
                  SuppliedProductView supView =new SuppliedProductView();
                  SuppliedProductController supControll =new SuppliedProductController(suppliedProModel,supView);
                
                  supControll.updateView();
              

}else{
  System.out.println("\n\n\t\t\tSuch product was supplied today,Update it's Quantities && Unit Price instead\n\n");
 }

          }while(prodExists);

        // SuppliedCrud supplied_product = new SuppliedCrud();
        if(!stopSupplying){
          System.out.println("\t\t\tCONTINUE SUPPLYING (Y/N)");
          String ch = scan.nextLine();
          if(ch.equals("n") || ch.equals("N"))
          {
            stopSupplying=true;
          }
        }
        
       supplyCount++;
        }while(!stopSupplying);
        
        }else{
          System.out.println("\n\t\t\tSorry problem ocurred in supplying Try again");
        }
              System.out.println("\n\n");
              
            }else{
                System.out.println("We Can not to store null values\n\n\n\n");
            }
}

public void dateSupplies(String supDate)
{
SuppliesCrud supCrud = new SuppliesCrud();
if(supCrud.dateSupplies(supDate).length() != 0)
{
  System.out.println("\n\t\t\t\tSUPPLY_ID\t\t\tSUPPLIER_ID\t\t\tSUPPLY-TIME");
System.out.println(supCrud.dateSupplies(supDate));
}else{
  System.out.println("\n\t\t\tSorry No supply made on this date\n");
}
System.out.println("\n\n");
}


public void supplierSupplies(String supplierId)
{
   SupplierCrud supplier= new SupplierCrud();
   Supplier sup = new Supplier();
   SuppliesCrud supCr = new SuppliesCrud();
   sup = supplier.readByOne(supplierId);
   if(sup.getSupId() != null){
    System.out.println("\n\n");
    supCr.supplierSupplies(sup.getSupId());
   }
}

public void displayChoice(){

  boolean valid = false;
  Validation validate = new Validation(); 
  Scanner scan = new Scanner(System.in);
SuppliesCrud supCrud = new SuppliesCrud(); 

  SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd");
  dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));


  boolean displayLooping = true;
 displayLoop:while(displayLooping){
  System.out.println("\t\t\t\t\tDISPLAY\n");
  System.out.println("\t\t\t1.View List\n");
  System.out.println("\t\t\t2.View One\n");
  System.out.println("\t\t\t3.Back\n");
  do{
    System.out.print("\t\t\tChoose..... ");
    valid =validate.validate("int","input");  
  }while(!valid);
  int ordChoice2=validate.getValidInt();

  if(ordChoice2 ==1)
{
  boolean listLooping=true;
  listingLoop:while(listLooping){

  System.out.println("\t\t\t\t\tDISPLAY ## VIEW LIST\n");
  System.out.println("\t\t\t1.All Supplies\n");
  System.out.println("\t\t\t2.Date\n");
  System.out.println("\t\t\t3.Back\n");
  do{
    System.out.print("\t\t\tChoose..... ");
    valid =validate.validate("int","input");  
  }while(!valid);
  int listChoice=validate.getValidInt();

switch(listChoice)

{
case 1:
System.out.println("\n\n\t\t\t\t\t\tDISPLAY ## VIEW LIST ## ALL SUPPLIES");
System.out.println("\n\t\t\t\t\t\tLIST OF ALL MADE SUPPLIES\n");
supCrud.readData();
System.out.println("\n\n");
break;
case 2:
boolean dateLooping=true;

dateLoop:while(dateLooping){
  System.out.println("\t\t\t\t\tDISPLAY ## VIEW LIST ## DATE\n");
  System.out.println("\t\t\t1.Today's Supplies\n");
  System.out.println("\t\t\t2.Search Date\n");
  System.out.println("\t\t\t3.Back\n");
  do{
    System.out.print("\t\t\tChoose..... ");
    valid =validate.validate("int","input");  
  }while(!valid);
  int supDateChoice=validate.getValidInt();

  if(supDateChoice == 1)
  {
    
    String todayDate = dateTimeInGMT.format(new Date());
    System.out.println("\n\n\t\t\t\t\t\tDISPLAY ## VIEW LIST ## DATE ## TODAY");
    System.out.println("\n\n\t\t\t\t\t\tTODAY'S SUPPLIES");
    this.dateSupplies(todayDate);

  }else if(supDateChoice == 2)
  {
    System.out.print("\t\t\tEnter the date.. ");
    String supplyDate = scan.nextLine();
    System.out.println("\n\t\t\t\tDISPLAY ## VIEW LIST ## DATE ## SEARCH DATE");
    System.out.println("\n\n\t\t\t\t\t\tSUPPLIES MADE ON "+supplyDate);
    this.dateSupplies(supplyDate);
  }else if(supDateChoice == 3)
  {
break dateLoop;
  }else{
    System.out.println("\t\t\tInvalid input");
  }

}

break;
case 3:
break listingLoop;
  default:
  System.out.println("\t\t\tInvalid choice");
}

}

}else if(ordChoice2 == 2)
{
  System.out.println("\t\t\t\tDISPLAY ## VIEW ONE");
  do{
    System.out.print("\t\t\tEnter the Supply ID(IS A NUMBER):.. ");
    valid =validate.validate("int","input");
  }while(!valid);
  int ordId = validate.getValidInt();
  Supplies model = new Supplies();

  model = supCrud.readByOne(Integer.toString(ordId));
if(model.getSupId() != null)
{
  SuppliesView view = new SuppliesView();
  SuppliesController controller = new SuppliesController(model,view);
this.singleRecord(controller,model);

}else{
System.out.println("\t\t\tSorry such supply is not found");
}

}else if(ordChoice2 == 3)
{
  displayLooping = false;
  // break displayLoop;
}else{
  System.out.println("\t\t\tInvalid Choice");
}
}

}

public void displaySuppliedProducts()
{


  SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd");
  dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
  String todayDate = dateTimeInGMT.format(new Date());

  boolean valid = false;
  Validation validate = new Validation(); 
  Scanner scan = new Scanner(System.in);

  boolean displayLooping2 = true;
  displayLoop:while(displayLooping2){
  System.out.println("\n\n\t\t\t\t\tHOME ## SUPPLIED-PRODUCTS ## DISPLAY\n\n");  
  System.out.println("\t\t\t1.Whole-List\n");
  System.out.println("\t\t\t2.Search-By Date\n");
  System.out.println("\t\t\t3.Back\n");
  do{
  System.out.print("\t\t\tChoose: ");
  valid =validate.validate("int","input");
  }while(!valid);
  int displaySupplied = validate.getValidInt();


switch(displaySupplied)
{
case 1:
System.out.println("\n\n\t\t\t\t\tHOME ## SUPPLIED-PRODUCTS ## DISPLAY ## WHOLE-LIST\n\n");  
System.out.println("\n\t\t\t\t\t\t\tLIST OF ALL SUPPLIED-PRODUCTS");
SuppliedCrud display = new SuppliedCrud();
display.readData();
System.out.println("\n\n");
break;
case 2:
boolean searchDateLooping = true;
while(searchDateLooping){
System.out.println("\n\n\t\t\t\t\tHOME ## SUPPLIED-PRODUCTS## DISPLAY ## SEARCH-BY-DATE\n\n");  
System.out.println("\t\t\t1.Today\n");
System.out.println("\t\t\t2.Search\n");
System.out.println("\t\t\t3.Back\n");
do{
  System.out.print("\t\t\tChoose: ");
  valid =validate.validate("int","input");
}while(!valid);
int suppliedChoice = validate.getValidInt();

switch(suppliedChoice)
{
  case 1:
 
  SuppliedCrud search = new SuppliedCrud();
  SuppliesCrud supCrud = new SuppliesCrud();

if(supCrud.dateSupplies(todayDate).length() != 0)
{
  System.out.println("\n\n\t\t\t\t\tHOME ## SUPPLIED-PRODUCTS ## DISPLAY ## SEARCH-BY-DATE ## TODAY\n\n");  
  System.out.println("\n\t\t\t\t\t\t\tLIST OF PRODUCTS SUPPLIED TODAY\n");
search.suppliedProductsByDate(todayDate);
System.out.println("\n\n");
}else{
System.out.println("\n\t\t\t No supply made today\n");
}
break;
case 2:

System.out.print("\t\t\tEnter the supply date.. ");
String dateSupply = scan.nextLine();

SuppliedCrud search2 = new SuppliedCrud();
SuppliesCrud supCrud2 = new SuppliesCrud();

if(supCrud2.dateSupplies(dateSupply).length() != 0)
{
  System.out.println("\n\n\t\t\t\t\tHOME ## SUPPLIED-PRODUCTS ## DISPLAY ## SEARCH-BY-DATE ## SEARCH\n\n");  
  System.out.println("\n\t\t\t\t\t\t\tLIST OF PRODUCTS SUPPLIED ON '"+dateSupply+"'\n"); 
search2.suppliedProductsByDate(dateSupply);
System.out.println("\n\n");
}else{
System.out.println("\n\t\t\tSorry No supply made on '"+dateSupply+"'\n");
}

System.out.println("\n\n");
break;

case 3:
searchDateLooping = false;
break;
  default:
  System.out.println("\t\t\tInvalid choice");
}
}
break;
case 3:
displayLooping2=false;
 break;
  default:
  System.out.println("\t\t\tInvalid choice");
}

}
}


public void viewOneSuppliedProduct()
{

  boolean valid = false;
  Validation validate = new Validation(); 
  Scanner scan = new Scanner(System.in);

  SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd");
  dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
SuppliedCrud supplied = new SuppliedCrud();

  viewOneLoop:while(1 > 0){

    System.out.println("\n\n\t\t\t\t\tHOME ## SUPPLIED-PRODUCTS ## VIEW-ONE\n\n");
    System.out.println("\t\t\t\t0.BACK");  
   
    do{
      System.out.print("\t\t\tEnter the Supply ID(IS A NUMBER):.. ");
      valid =validate.validate("int","input");
    }while(!valid);
    int supplyId = validate.getValidInt();
    
    if(supplyId == 0)
    {
      break viewOneLoop;
    }
    
    do{
      System.out.print("\t\t\tEnter the Product ID(IS A NUMBER):.. ");
      valid =validate.validate("int","input");
    }while(!valid);
    int productId = validate.getValidInt();
    
    
    if(productId == 0)
    {
      break viewOneLoop;
    }
    
    SuppliedProduct product =  new SuppliedProduct();
    product = supplied.readByOne(Integer.toString(supplyId),Integer.toString(productId));
    if(product.getSupId() != null)
    {
    SuppliedProductView supView = new SuppliedProductView();
    SuppliedProductController supContr = new SuppliedProductController(product,supView);
    supplyRecordLoop:while(1 > 0){
    System.out.println("\n\n\t\t\t\t\tRecords From ** Supply Id-"+supContr.getSuppId()+" && Product Id-"+supContr.getProductId()+"**\n"); 
    supContr.updateView();  
    System.out.println("\n\n\t\t\tTake Action on");
    
    System.out.println("\n\t\t\t1.Update");
    System.out.println("\n\t\t\t2.Delete");
    System.out.println("\n\t\t\t3.Not Now");
    System.out.print("\n\t\t\tChoose:.. ");
    String  choice4 = scan.nextLine();
    if(choice4.equals("1"))
    {
    System.out.print("\t\t\tEnter the Corresponding Number of the Record:  ");
    int num = scan.nextInt();
    switch(num)
    {
      case 1:
      System.out.print("\n\t\t\tINDEX CAN NOT BE UPDATED");
      System.out.print("\n\t\t\tPRESS ENTER TO CONTINUE");
      scan.nextLine();
      scan.nextLine();
    break;
      case 2:
      System.out.println("\t\t\tExisting Product Id "+supContr.getProductId());
     boolean checkProd=false;
     ProductCrud prod = new ProductCrud();
     String prodName;
      do{
        scan.nextLine();
        System.out.println("\t\t\t0.Back");
        System.out.print("\t\t\tEnter The Name of The new Product... ");
       prodName=scan.nextLine();
       if(prodName.equals("0"))
       {
         checkProd = true;
       }
          if(prod.checkExistence(prodName) == 0)
          {
              System.out.println("\t\t\tSorry such product does not exist");
          }else{
                 checkProd= true;
           supplied.updateData(supContr.getSuppId(),supContr.getProductId(),1,Integer.toString(prod.checkExistence(prodName)),"product_id");
           supContr.setProductId(Integer.toString(prod.checkExistence(prodName))); 
        }
          }while(!checkProd);

    break;
      case 3:
    System.out.print("\t\t\tExisting Quantities :  "+ supContr.getQuantities());
   

      do{
             System.out.print("\n\t\t\tEnter New Quantities:...... ");
              valid =validate.validate("float","quantity");
            }while(!valid);
            float quantity = validate.getValidFloat();
                   StockCrud stock = new StockCrud();
       String stockProdId = supContr.getProductId();
     
       stock.updateData(stockProdId,supContr.getQuantities(),"deduction");
       stock.updateData(stockProdId,quantity,"addition");
       supContr.setQuantities(quantity);

       supplied.updateData(supContr.getSuppId(),supContr.getProductId(),2,Float.toString(quantity),"others");
      
    
      break;
      case 4:
    System.out.print("\t\t\tExisting Unit Price:  "+ supContr.getUnitPric());
    
    do{
      System.out.print("\t\t\tEnter The New: ");
      valid =validate.validate("float","price");
    }while(!valid);
    float unitPrice = validate.getValidFloat();
    supContr.setUnitPric(unitPrice);
    supplied.updateData(supContr.getSuppId(),supContr.getProductId(),3,Float.toString(unitPrice),"others");
    break;
    default:
    System.out.println("\t\t\tInvalid Choice Try AGAIN");
    }
    }else if(choice4.equals("2"))
    {
      System.out.println("\t\t\tThis can result in losing of the supply if This is Only remaining supplied Product");
    System.out.print("\t\t\tAre You sure you want to Delete These Data (Y/N)...");
    String selection = scan.nextLine();
    if(selection.equals("yes") ||selection.equals("YES") || selection.equals("y") || selection.equals("Y"))
    {
    if(supplied.deleteData(supContr.getSuppId(),supContr.getProductId()) == 1){
    System.out.println("\t\t\tRecord is removed from the list");
    StockCrud stock = new StockCrud();
       String stockProdId = supContr.getProductId();
       stock.updateData(stockProdId,supContr.getQuantities(),"deduction");
    }else{
    System.out.println("\t\t\tTry again error occurred");
    }
    }else{}
    }else if(choice4.equals("3"))
    {
      break supplyRecordLoop;
    }
    else{
     System.out.println("\t\t\tInvalid Choice");
    }
    }
    }else{
    System.out.println("\t\t\tSorry we don't find the corresponding result");
    }
    }


}

public void supplierSuppliesChoice()
{

  boolean valid = false;
  Validation validate = new Validation(); 
  Scanner scan = new Scanner(System.in);

  SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd");
  dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));


  supplierSuppliesLoop:while(1 > 0){
    System.out.println("\t\t\tHOME ## SUPPLIER-SUPPLIES");
    System.out.println("\t\t\t0.Back");
    System.out.print("\n\t\t\tEnter the Id of The Supplier: ");
   String supplierId=scan.nextLine();
    if(supplierId.equals("0"))
    {
  break supplierSuppliesLoop;
    }
    SupplierCrud supplierCrud = new SupplierCrud();
    Supplier supplierModel = new Supplier();
    supplierModel=supplierCrud.readByOne(supplierId);
    if(supplierModel.getSupId() != null)
    {
    SuppliesCrud supCrud = new SuppliesCrud();
    System.out.println("\n\n\t\t\t\t\t\t\tHOME ## SUPPLIER-SUPPLIES ");
    System.out.println("\n\t\t\t\t\t\t\tALL SUPPLIES FROM '"+supplierModel.getSupId()+"' SUPPLIER ID\n");
    supCrud.supplierSupplies(supplierModel.getSupId());
    }else{
      System.out.println("Sorry such Supplier is not registered");
    }
  }
}



public void supply(){
    boolean valid = false;

    SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd");
    dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));

    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    boolean supplyLoop = true;
    while(supplyLoop)
        {
       System.out.println("\n\n\t\t\t\t\tPRODUCT SUPPLY MANAGEMENT SYSTEM\n\n");
        System.out.println("\t\t\t1.Supplies\n");
        System.out.println("\t\t\t2.Supplied_Products\n");
        System.out.println("\t\t\t3.Supplier-Supplies\n");
        System.out.println("\t\t\t4.Back\n");
        int choice2;

        do{
          System.out.print("\t\t\tChoose..... ");
          valid =validate.validate("int","input");  
        }while(!valid);
        choice2=validate.getValidInt();
        switch(choice2)
        {
            case 1:
            boolean supplySectionLooping = true;
            supplySectionLoop:while(supplySectionLooping){
       System.out.println("\n\n\t\t\t\t\tHOME ## SUPPLIES \n\n");
            System.out.println("\t\t\t1.New Supply\n");
            System.out.println("\t\t\t2.Display\n");
            System.out.println("\t\t\t3.Back\n");
            do{
              System.out.print("\t\t\tChoose..... ");
              valid =validate.validate("int","input");  
            }while(!valid);
            int supChoice=validate.getValidInt();

            ///new supply
switch(supChoice)
{
  case 1:
this.addSupply();
break;
case 2:
this.displayChoice();
break;
case 3:
supplySectionLooping=false;
break;
default:
System.out.println("\t\t\tInvalid Choice");
}
            }
break;
case 2:
 boolean supplyingProductLooping = true;
 suppliedProductLoop:while(supplyingProductLooping){
  System.out.println("\n\n\t\t\t\t\tHOME ## SUPPLIED-PRODUCTS\n\n");  
  System.out.println("\t\t\t1.Display\n");
  System.out.println("\t\t\t2.View One\n");
  System.out.println("\t\t\t3.Back\n");
 do{
    System.out.print("\t\t\tChoose: ");
    valid =validate.validate("int","input");
  }while(!valid);
 int suppliedChoice = validate.getValidInt();
 switch(suppliedChoice)
  {
  case 1:
this.displaySuppliedProducts();
break;
case 2:
this.viewOneSuppliedProduct();
break;
case 3:
supplyingProductLooping=false;
break;
default:
System.out.println("\t\t\tSorry invalid choice");
}
      }
      break;
      case 3:
this.supplierSuppliesChoice();
      break;
      case 4:
      supplyLoop=false;
      break;
      default:
      System.out.println("\t\t\tInvalid Choice");
    }
  }
  }
}







