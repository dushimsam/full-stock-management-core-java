package options;
import java.text.*;
import Model.order.*;
import Model.ordered_product.*;
import View.ordered_product.*;
import Controller.ordered_product.*;

import Model.Customers;
import Db.CustomerCrud;
import Db.transaction.product.*;
import Controller.order.*;
import View.order.*;
import Db.*;
import Db.transaction.order.*;
import Db.transaction.ordered_product.*;
import Db.transaction.product.*;
import java.util.*;
import java.io.*;
import utils.*;

public class OrderedOption implements Options{


private String currentOrder;

public OrderedOption(String orderId)
{
    this.setCurrentOrder(orderId);
}

public void setCurrentOrder(String orderId){
    this.currentOrder = orderId;
}
public String getCurrentOrder(){
   return  this.currentOrder;
}

  public int deleteRecord(String orderDetails)
{

    String[] orderDetailsMots = orderDetails.split("_");
    String orderId = orderDetailsMots[0];
    String productId = orderDetailsMots[1];

OrderedCrud ordered =new OrderedCrud();
    int deleted = 0;
    if(ordered.deleteData(orderId,productId) == 1)
       deleted= 1;
  return deleted;
}

public void updateRecord(int num,String productId){
    boolean valid = false;
    Validation validate = new Validation();
    OrderedCrud orderedCrud =new OrderedCrud();
 OrderedProduct ordered = new OrderedProduct();

StockCrud stockCrud = new StockCrud();

    ordered = orderedCrud.readByOne(this.getCurrentOrder(),productId);
    OrderedView view = new OrderedView();
    OrderedController controller = new OrderedController(ordered, view);
    Selection selection = new Selection();
     ProductCrud product = new ProductCrud();


        switch (num) {
        case 2:
         
        String data;  
     boolean checkProd=true;
   
      do{
     System.out.println("\t\t\t0.Back");
     data = this.promptExistingDetails("Product Id",controller.getProductId());

    
       if(!data.equals("0"))
       {

              if(product.exists(data,"code")){
checkProd= false;
 if(orderedCrud.checkExistence(controller.getOrdId(),data))
          {
            if(stockCrud.quantityAvailable(data,controller.getQuantit()))
            {
 
          selection.selectChoice("save","changes");
           if(selection.getChoice())
          {
           stockCrud.updateData(controller.getProductId(),1,controller.getQuantit());
           orderedCrud.updateData(controller.getOrdId(),controller.getProductId(),1,data,"product_id");
           stockCrud.updateData(data,1,-controller.getQuantit());
          }
  
            }else{
              System.out.println("\n\t\t\tSorry "+controller.getQuantit()+" Quantities are not available for this product\n");
            }
         
          }else{
        System.out.println("\t\t\tSorry such order was made");
        }
              }else{
          System.out.println("\t\t\tSorry the product is not found");
              }
         
       }else{
           checkProd = false;
       }
          }while(checkProd);

          break;

      case 3:
    System.out.print("\t\t\tExisting Quantities :  "+ ordered.getQuantity());
   float quantity;
    boolean quantityAvailable = false;
    do{
    
      do{
             System.out.print("\n\t\t\tEnter New Quantities:...... ");
              valid =validate.validate("float","quantity");
      }while(!valid);
       quantity =validate.getValidFloat();
       float newQuantity = controller.getQuantit() - quantity;

       if(stockCrud.quantityAvailable(controller.getProductId(),newQuantity))
       {
         quantityAvailable = true;
          selection.selectChoice("save","changes");
           if(selection.getChoice())
          {
          orderedCrud.updateData(controller.getOrdId(),controller.getProductId(),2,Float.toString(quantity),"others");
       stockCrud.updateData(controller.getProductId(),1,newQuantity);
          }
       }else{
         System.out.println("\t\t\tSorry ordered Quantities are not available");
       }

          }while(!quantityAvailable);
   
      break;
        default:
          System.out.println("\t\t\tInvalid Input");
        }
}


  public void viewOne(String productId)
  {
  

    Selection selection = new Selection();
    Scanner scan = new Scanner(System.in);
    boolean valid = false;
    Validation validate = new Validation(); 

    OrderedCrud orderedCrud= new OrderedCrud();
    OrderedProduct ordered = new OrderedProduct();
    ordered = orderedCrud.readByOne(this.getCurrentOrder(),productId);

      System.out.println("\n\n");
      OrderedView view = new OrderedView();
      OrderedController controller = new OrderedController(orderedCrud.readByOne(this.getCurrentOrder(),productId),view);
      boolean viewRecord=true;

      viewRecordLoop:do{
      controller.updateView();
    
    System.out.println("\n\n\t\t\tTake Action on "+productId+" AND " +this.getCurrentOrder()+"'s Record");
    System.out.println("\n\t\t\t1.Update");
    System.out.println("\n\t\t\t2.Delete");
    System.out.println("\n\t\t\t3.Go Back");
    System.out.print("\n\t\t\tChoose:.. ");
    String  choice4 = scan.nextLine();
    if(choice4.equals("1"))
    {

      boolean update = true;
      do{
    System.out.println("\t\t\t0.GO BACK");
    do{
      System.out.print("\t\t\tEnter the Corresponding index of the record to update:  ");
      valid =validate.validate("int","input");  
    }while(!valid);
    int num = validate.getValidInt();

    if(num != 0)
{
  updateRecord(num,productId);
  controller = new OrderedController(orderedCrud.readByOne(this.getCurrentOrder(),productId),view);
}else{
  update = false;
}
      }while(update);

    }else if(choice4.equals("2"))
    { 
  selection.selectChoice("delete",productId+" of "+this.getCurrentOrder()+"'s the records");
  if(selection.getChoice() == true) {
    int deleted = deleteRecord(productId);
    if(deleted == 1){
      System.out.println("\t\t\t\tBYE !!!!!!!!!!!!!!!!!!!!!!!!YOU WILL FIND ME IN THE TRASH!!!!!!\n");
      break viewRecordLoop; 
    }
    else{
      System.out.println("\t\t\t\tTry again an error occurred");
    }
  }

    }else if(choice4.equals("3"))
    {
        viewRecord = false;
    }
    else{
    System.out.println("\t\t\tInvalid choice");
    }
    
    System.out.println("\n\n");
}while(viewRecord);
System.out.println("\n\n");
  }


  public void insertData(HashMap<String,String> hash_map)
{
    int inserted = 0;
    OrderedProduct model = new OrderedProduct();
        
           model.setProdId(hash_map.get("product"));
           model.setOrderId(this.currentOrder);
           model.setQuantity(Float.parseFloat(hash_map.get("quantity")));

          OrderedView view = new OrderedView();

          OrderedController controller = new OrderedController(model, view);
          OrderedCrud orderedCrud = new OrderedCrud();
          orderedCrud.createData(model);
          controller.updateView();
          System.out.println("\n");
}




  public HashMap gatherRecords()
  {
    OrderedCrud crd = new OrderedCrud();
    Selection selection = new Selection();
    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    HashMap<String,String> hash_map = new HashMap<String,String>();
    boolean add = true;
    String name,mail,location,tel="0";
    float quantity;
 boolean checkProd = true;

 OrderedCrud orderedCrud = new OrderedCrud();
//  OrderedController controller = new OrderedController();
 StockCrud stockCrud = new StockCrud();
    addProductLoop:do{
  
    System.out.println("\t\t\tGO Back:0 ");
    ProductCrud prod = new ProductCrud();
     String productID;
      do{
        scan.nextLine();
        System.out.println("\t\t\t0.Back");
        System.out.print("\t\t\tEnter The Id of The Product... ");
        productID=scan.nextLine();
        if(productID.equals("0"))
           break addProductLoop;
       
    
        if(prod.exists(productID,"code"))
     {
     checkProd= false;

  boolean quantityAvailable = false;
    do{
          do{
             System.out.println("\n\t\t\t0.GO Back");
             System.out.print("\n\t\t\tEnter the Quantities:...... ");
              valid =validate.validate("float","quantity");
            }while(!valid);
        quantity =validate.getValidFloat();
           if(quantity == 0)
               break addProductLoop;

      
       if(!stockCrud.quantityAvailable(productID,quantity))
       {
        System.out.println("\n\n\t\t\tSorry such Quantities are not Available in the Stock.\n");
       }else{
        quantityAvailable= true;
        stockCrud.updateData(productID,1,-quantity);
       }
          }while(!quantityAvailable);

     hash_map.put("product", productID);
     hash_map.put("quantities",Float.toString(quantity));
     insertData(hash_map);
     System.out.println("\t\t\t\tProduct ordered");
     }else{
          System.out.println("\t\t\tSorry such product does not exist");
     }
          }while(checkProd);

        selection.selectChoice("ADD","ANOTHER PRODUCT");
        if(selection.getChoice() == false){
             add=false;
           }  
       
}while(add);
return hash_map;
  }


public void viewAll()
{
  ManageDirectories directory = new  ManageDirectories();
  OrderedCrud ordered = new OrderedCrud();
  directory.printDirectory("viewAll","ORDER PRODUCTS");
  System.out.println("\n\t\t\t\t\tTHE LIST OF ORDERED PRODUCTS UNDER "+this.getCurrentOrder()+"\n");
  System.out.println("\n\n");

  ordered.productsUnderOrder(this.getCurrentOrder());
  System.out.println("\n\n");
}


public int checkIfExists(String productID)
{
  int exists = 0;
  OrderedCrud orderedCrud= new OrderedCrud();
  OrderedProduct ordered = new OrderedProduct();
  ordered = orderedCrud.readByOne(this.getCurrentOrder(),productID);

  if(orderedCrud.checkExistence(this.getCurrentOrder(),productID))
     exists = 1;
  return exists;
}


public void orderedProduct(){

    ManageDirectories directory = new  ManageDirectories();
    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    boolean manageOrdered= true;

    do{
      directory.printDirectory("home","ORDER PRODUCTS");

      do{
        System.out.print("\t\t\tChoose..... ");
        valid =validate.validate("int","input");  
      }while(!valid);
      int choice = validate.getValidInt();
    switch(choice){
      case 1:
      
      directory.printDirectory("add","ORDER PRODUCTS");
      this.gatherRecords();
      break;
      case 2:

      boolean view = true;
      viewLoop:do{
        directory.printDirectory("view","ORDER PRODUCTS");
      do{
        System.out.print("\t\t\tChoose..... ");
        valid =validate.validate("int","input");  
      }while(!valid);
      int choice2 = validate.getValidInt();

      switch(choice2){
        case 1:
        boolean viewList = true;
         do{
          viewAll();
          System.out.print("\t\t\t0 .GO BACK !!! ");
          String backChoice  = scan.next();
          if(backChoice.equals("0"))
              viewList = false;
          else
             System.out.println("\t\t\tInvalid Choice:::Enter Zero To go back");    

         }while(viewList);
        break;
        case 2:

        boolean view_one = true;
        do{
          directory.printDirectory("viewOne","ORDER");
          System.out.println("\t\t\t0 .GO BACK");
          System.out.print("\n\t\t\tEnter the Id of the product: ");
          String productID = scan.next();
          if(productID.equals("0"))
             view_one = false;
          else
             if(checkIfExists(productID) == 1){
              directory.printDirectory(productID,"ORDER PRODUCTS");
              viewOne(productID);
             }else{
              System.out.println("\n\t\t\t\tSORRY SUCH PRODUCT IS NOT FOUND\n\n");
             }    
        }while(view_one);
        break;
        case 3:
        view = false;
        break;
        default:
        System.out.println("\n\t\t****Invalid choice:***** ");
      }

      }while(view);
      break;
    case 3:
    manageOrdered= false;
    break;
    default: 
    System.out.println("\n\t\t****Invalid choice:***** ");
    }
    }while(manageOrdered);
  }


  }


