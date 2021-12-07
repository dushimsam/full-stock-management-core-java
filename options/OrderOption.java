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

public class OrderOption implements Options{

  public int deleteRecord(String id)
{
    OrderCrud order = new OrderCrud();
    int deleted = 0;
    if(order.deleteData(id) == 1)
      deleted= 1;
  return deleted;
}

public void updateRecord(int num,String orderId){
    boolean valid = false;
    Validation validate = new Validation();
    OrderCrud orderCrud = new OrderCrud();
    Order order = new Order();
    order = orderCrud.readByOne(orderId);
    OrderView view = new OrderView();
    OrderController controller = new OrderController(order, view);
    Selection selection = new Selection();
CustomerCrud customer = new CustomerCrud();
        switch (num) {
        case 2:
          System.out.println("\n\t\t\t0. GO Back");
          boolean checkCustomer = true;
          do{
          String customerID = this.promptExistingDetails("Customer Id",controller.getCustomerId());
          
          if(!customerID.equals("0"))
          {
             if(customer.checkExistence(customerID))
      {
         checkCustomer = false;
        if(!customerID.equals(controller.getCustomerId()))
        {
        selection.selectChoice("save","changes");
           if(selection.getChoice() == true)
          {
          orderCrud.updateData(controller.getOrdId(),customerID);
          }
        }else{
          System.out.println("\n\t\t\tAlready the value\n");
        }
       
      }else{
          System.out.println("\t\t\tSorry such customer is not registered");
      }    
          }else{
            checkCustomer = false;
          }
          }while(checkCustomer);
          break;
     
        default:
          System.out.println("\t\t\tEnter 2 to update the customer");
        }
}



public void deleteOrder(String id,String orderId){
    Scanner scan = new Scanner(System.in);
    OrderCrud orderCrud = new OrderCrud();
    System.out.println("\t\t\tAre You sure you want to Delete " + orderId + "'s Data (Y/N)");
    String selection = scan.nextLine();
    if (selection.equals("yes") || selection.equals("YES") || selection.equals("y") || selection.equals("Y")) {
      if (orderCrud.deleteData(id) == 1) {
        System.out.println("\t\t\tOrder is removed from the list");
      }else {
        System.out.println("\t\t\tTry again error occurred");
      }
    } 
}



public void viewOne(String orderId){
    Scanner scan = new Scanner(System.in);
   OrderCrud orderCrud = new OrderCrud();
    Selection selection = new Selection();
    Order order = new Order();
    order = orderCrud.readByOne(orderId);
    boolean valid = false;
    Validation validate = new Validation();
    System.out.println("\n");
    OrderView view = new OrderView();
     OrderController controller = new OrderController(order, view);
     boolean viewRecord = true;
  
     viewRecordLoop:do{
        controller.updateView();
        System.out.println("\n\n\t\t\tTake Action on " + controller.getOrdId()+ "'s Records");
        System.out.println("\n\t\t\t1.Update");
        System.out.println("\n\t\t\t2.Delete");
        System.out.println("\n\t\t\t3.Not Now");
        System.out.print("\n\t\t\tChoose:.. ");
        String choice = scan.nextLine();
        if (choice.equals("1")) {
          boolean update = true;
          do{
            System.out.println("\t\t\t0.GO BACK");
            do{
             System.out.print("\t\t\tEnter the Corresponding index of the record to update:  ");
             valid =validate.validate("int","input");  
          }while(!valid);
          int num = validate.getValidInt();
          if(num != 0){
            updateRecord(num,controller.getOrdId());
            controller = new OrderController(orderCrud.readByOne(controller.getOrdId()),view);
          }else{
            update = false;
          }
        }while(update);

        } else if (choice.equals("2")) {
          selection.selectChoice("delete",controller.getOrdId()+"'s the records");
          if(selection.getChoice() == true) {
            int deleted = deleteRecord(controller.getOrdId());
            if(deleted == 1){
              System.out.println("\t\t\t\tBYE !!!!!!!!!!!!!!!!!!!!!!!!YOU WILL FIND ME IN THE TRASH!!!!!!\n");
              break viewRecordLoop; 
            }
            else{
              System.out.println("\t\t\t\tTry again an error occurred");
            }
          }
        }else if(choice.equals("3")){
          viewRecord=false;
         }else{
          System.out.println("\t\t\tInvalid input");
         }
        }while(viewRecord);

      System.out.println("\n\n");
    }




public void insertData(HashMap<String,String> hash_map)
{
    int inserted = 0;
Order order = new Order();

          order.setCustId(hash_map.get("customer"));
          order.setOrderDate(hash_map.get("date"));
          OrderView view = new OrderView();  
          OrderCrud orderCrud = new OrderCrud();
          order =  orderCrud.createData(order);
         OrderController controller = new OrderController(order, view);
          controller.updateView();
          System.out.println("\n\n");
}



public HashMap gatherRecords()
{
  boolean valid = false;
  Selection selection = new Selection();
Validation validate = new Validation();
HashMap<String,String> hash_map = new HashMap<String,String>();
boolean add = true;
Scanner scan = new Scanner(System.in);
OrderCrud crd = new OrderCrud();

  String custId;

addOrder:do{
  System.out.println("\t\t\tGO Back:0 ");


  System.out.println("\t\t\tEnter The Details of the New Order\n\n");

              boolean validateCustId=true;
              CustomerCrud customer = new CustomerCrud();
              do{
                  System.out.print("\n\t\t\tEnter The Customer ID:...... ");
                  custId = scan.nextLine();
  
                  if(customer.checkExistence(custId))
                  {
                    validateCustId=false;
                  }else{
                      System.out.println("\t\t\tSorry such Customer is not registered");
                  }
  
              }while(validateCustId);

      selection.selectChoice("SAVE"," The records");
      if(selection.getChoice() == true) {
         hash_map.put("customer", custId); 
       

          SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
          dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));

          String todayDate = dateTimeInGMT.format(new Date());
          todayDate=todayDate.replaceAll(" ","_"); 
    
          hash_map.put("date", todayDate); 

           insertData(hash_map);
            System.out.println("\t\t\t\tRecords are saved");

              selection.selectChoice("ADD","ANOTHER ORDER");
      
              if(selection.getChoice() == false){
                   add=false;
              }
         }else{
          add=false; 
          System.out.println("\t\t\t\tRECORDS WILL NOT BE SAVED");
        }   

}while(add);
return hash_map;
}






public void viewAll()
{
  ManageDirectories directory = new  ManageDirectories();
  OrderCrud order = new OrderCrud();
  directory.printDirectory("viewAll","ORDERS");
  System.out.println("\n\t\t\t\t\tTHE LIST OF MADE ORDERS\n");
  System.out.println("\n\n");

  order.readData();
  System.out.println("\n\n");
}



public int checkIfExists(String orderId)
{
  int exists = 0;
  OrderCrud orderCrud= new OrderCrud();
  Order order = new Order();
  order = orderCrud.readByOne(orderId);
  if(order.getOrderId() != null)
     exists = 1;
  return exists;
}



  public void order() {
    ManageDirectories directory = new  ManageDirectories();
    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    boolean manageOrder= true;
    do{
      directory.printDirectory("home","ORDER");

      do{
        System.out.print("\t\t\tChoose..... ");
        valid =validate.validate("int","input");  
      }while(!valid);
      int choice = validate.getValidInt();
    switch(choice){
      case 1:
      
      directory.printDirectory("add","ORDER");
      this.gatherRecords();
      break;
      case 2:

      boolean view = true;
      viewLoop:do{
        directory.printDirectory("view","ORDER");
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
          System.out.print("\n\t\t\tEnter the Id of the order: ");
          String id = scan.next();
          if(id.equals("0"))
             view_one = false;
          else
             if(checkIfExists(id) == 1){
              directory.printDirectory(id,"ORDER");
              viewOne(id);
             }else{
              System.out.println("\n\t\t\t\tSORRY SUCH ORDER WAS NOT FOUND\n\n");
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
    manageOrder= false;
    break;
    default: 
    System.out.println("\n\t\t****Invalid choice:***** ");
    }
    }while(manageOrder);
  }
  }


