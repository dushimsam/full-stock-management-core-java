package options;
import Model.*;
import Controller.*;
import View.*;
import Db.*;
import java.util.*;
import java.io.*;
import utils.*;
public class SupplierOption implements Options{

  public int deleteRecord(String id)
  {
    SupplierCrud supplier= new SupplierCrud();
      int deleted = 0;
      if(supplier.deleteData(id) == 1)
        deleted= 1;

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

    return deleted;
  }
  


  public void updateRecord(int num,String id)
  {
    boolean valid = false;
    Validation validate = new Validation();
    Selection selection = new Selection();
    SupplierCrud supCrud= new SupplierCrud();
    Supplier supplier = new Supplier();
    supplier = supCrud.readByOne(id);

    SupplierView view = new SupplierView();
    SupplierController controller = new SupplierController(supCrud.readByOne(id),view);

    switch(num)
    {
      case 1:
      System.out.println("\n\t\t\t0. GO Back");
      boolean verifyName = true;
      String data = "";
      do{
     data = this.promptExistingDetails("Name",controller.getSupplierName());
      if(!data.equals("0"))
    {

    data=data.toUpperCase();
    if(supCrud.checkExistence(data, "name") != null)
    {
 System.out.println("\t\t\tPlease Such Supplier name already exists");
    }else{
      verifyName = false;
     selection.selectChoice("save","changes");
     if(selection.getChoice() == true)
    {     
      supCrud.updateData(id,1,data);
    }
  }
    }else{
      verifyName = false;
    }
  }while(verifyName);
      break;
      case 2:
    System.out.println("\n\t\t\t0. GO Back");
    boolean verifyEmail = true;
    verifyEmailLoop:do{
   data = this.promptExistingDetails("Email",controller.getSupEmail());
     if(!data.equals("0"))
     {
      if(!supCrud.verifyEmail(data))
      {
      verifyEmail = false;
      selection.selectChoice("save","changes");
      if(selection.getChoice() == true)
     {
      supCrud.updateData(id,3,data);
      }
     }else{
      System.out.println("\t\t\tSuch Email already Exists");
     } 
       }else{
     break verifyEmailLoop;
       }
    }while(verifyEmail);  
     
     
      break;
      case 3:
      boolean validateTel = true;
      data = "0";
      do{
        do{
         System.out.println("\n\t\t\t0. GO Back");
         System.out.print("\t\t\tExisting Tel Number: " +controller.getSupTel());
         System.out.print("\t\t\tEnter The New : ");
         valid =validate.validate("int","telephone number");  
        }while(!valid);
        int telNum = validate.getValidInt();
        if(telNum == 0)
        {
         validateTel = false;
        }else{
         validate.validateTel(telNum);
          if(validate.getValidTel() == true)
          {
            validateTel = false;
            data = Integer.toString(telNum);
            selection.selectChoice("save","the changes");
            if(selection.getChoice() == true)
           {
            supCrud.updateData(id,2,data);
            }
          }  
        }
      }while(validateTel);
  
      break;
      case 4:

      System.out.println("\n\t\t\t0. GO Back");
      data = this.promptExistingDetails("Location Address",controller.getSupLocation());
      if(!data.equals("0"))
    {
     selection.selectChoice("save","changes");
     if(selection.getChoice() == true)
    {     
      supCrud.updateData(id,4,data);
    }
    }
      break;
      default:
      System.out.println("\t\t\tInvalid Choice Try AGAIN");
    }
  }



  public void viewOne(String id)
  {
    Selection selection = new Selection();
    Scanner scan = new Scanner(System.in);
    boolean valid = false;
    Validation validate = new Validation(); 

    SupplierCrud supCrud= new SupplierCrud();
    Supplier supplier = new Supplier();
    supplier = supCrud.readByOne(id);

      System.out.println("\n\n");
      SupplierView view = new SupplierView();
      SupplierController controller = new SupplierController(supCrud.readByOne(id),view);
      boolean viewRecord=true;

      viewRecordLoop:do{
      controller.updateView();
    
    System.out.println("\n\n\t\t\tTake Action on "+controller.getSupplierName()+"'s Record");
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
  updateRecord(num,id);
  controller = new SupplierController(supCrud.readByOne(id),view);
}else{
  update = false;
}
      }while(update);

    }else if(choice4.equals("2"))
    { 
  selection.selectChoice("delete",controller.getSupplierName()+"'s the records");
  if(selection.getChoice() == true) {
    int deleted = deleteRecord(id);
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
    Supplier model = new Supplier();

           model.setSupName(hash_map.get("name"));
           model.setEmail(hash_map.get("email"));
           model.setLocation(hash_map.get("location"));
           model.setTel(hash_map.get("tel"));

          SupplierView view = new SupplierView();

          SupplierController controller = new SupplierController(model, view);
          SupplierCrud supplierCrud = new SupplierCrud();
          supplierCrud.createData(model);
          controller.updateView();
          System.out.println("\n");
}



  public HashMap gatherRecords()
  {
    SupplierCrud crd = new SupplierCrud();
    Selection selection = new Selection();
    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    HashMap<String,String> hash_map = new HashMap<String,String>();
    boolean add = true;
    String name,mail,location,tel="0";
 
    addSupplierLoop:do{
  
    System.out.println("\t\t\tGO Back:0 ");
    boolean verifyName = true;
    do{
    System.out.print("\n\t\t\tSupplier Name:...... ");
    name = scan.next();
    if(name.equals("0"))
         break addSupplierLoop;

    name=name.toUpperCase();
    if(crd.checkExistence(name,"name") != null)
    {
      System.out.println("\t\t\tPlease such Supplier Name already exists"); 
    }else{
      verifyName = false;
    }

    }while(verifyName);

      boolean validateTel = true;
      do{
        do{
          System.out.print("\n\t\t\tTelephone:...... ");
          valid = validate.validate("int","telphone number");  
        }while(!valid);
        int telNum = validate.getValidInt();

        if(telNum == 0)
        {
          break addSupplierLoop;
        }else{
          validate.validateTel(telNum);
          if(validate.getValidTel() == true)
          {
            validateTel = false;
            tel = Integer.toString(telNum);
          }  
        }
      }while(validateTel);

     
      boolean verifyEmail = true;
      do{
        System.out.print("\n\t\t\tEmail:...... ");
        mail = scan.next();
        if(mail.equals("0"))
           break addSupplierLoop;
          
           if(!crd.verifyEmail(mail))
           {
            verifyEmail = false;
           }else{
            System.out.println("\t\t\tSuch Email already Exists");
           }

      }while(verifyEmail);


      System.out.print("\n\t\t\tLocation:...... ");
      location = scan.next();
      if(location.equals("0"))
         break addSupplierLoop;

      System.out.println("\n");
      selection.selectChoice("SAVE"," The records");
         if(selection.getChoice() == true) {
            hash_map.put("name", name); 
            hash_map.put("email",mail); 
            hash_map.put("location", location); 
            hash_map.put("tel", tel); 
            insertData(hash_map);
        System.out.println("\t\t\t\tRecords are saved");

        selection.selectChoice("ADD","ANOTHER SUPPLIER");
      
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
    SupplierCrud supplier = new SupplierCrud();
    directory.printDirectory("viewAll","SUPPLIERS");
    System.out.println("\n\t\t\t\t\tTHE LIST OF REGISTERED SUPPLIERS\n");
    System.out.println("\n\n");
    supplier.readData();
    System.out.println("\n\n\n");
  }

  public int checkIfExists(String id)
  {
    int exists = 0;
    SupplierCrud supCrud= new SupplierCrud();
    Supplier supplier = new Supplier();
    supplier = supCrud.readByOne(id);
    if(supplier.getSupId() != null)
       exists = 1;
    return exists;
  }


  public void supplier(){
    ManageDirectories directory = new  ManageDirectories();
    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    boolean manageSupplier = true;
    do{
      directory.printDirectory("home","SUPPLIER");

      do{
        System.out.print("\t\t\tChoose..... ");
        valid =validate.validate("int","input");  
      }while(!valid);
      int choice = validate.getValidInt();
    switch(choice){
      case 1:
      
      directory.printDirectory("add","SUPPLIER");
      this.gatherRecords();
      break;
      case 2:

      boolean view = true;
      viewLoop:do{
        directory.printDirectory("view","SUPPLIER");
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
          directory.printDirectory("viewOne","SUPPLIER");
          System.out.println("\t\t\t0 .GO BACK");
          System.out.print("\n\t\t\tEnter the Id of the Supplier: ");
          String supId = scan.next();
          if(supId.equals("0"))
             view_one = false;
          else
             if(checkIfExists(supId) == 1){
              directory.printDirectory(supId,"SUPPLIER");
              viewOne(supId);
             }else{
              System.out.println("\n\t\t\t\tSORRY SUCH SUPPLIER WAS NOT FOUND\n\n");
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
    manageSupplier= false;
    break;
    default: 
    System.out.println("\n\t\t****Invalid choice:***** ");
    }
    }while(manageSupplier);
  }
    }

