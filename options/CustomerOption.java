
package options;
import Model.*;
import Controller.*;
import View.*;
import Db.*;
import java.util.*;
import java.io.*;
import utils.*;
public class CustomerOption implements Options{


  public int deleteRecord(String id)
{
    CustomerCrud customer = new CustomerCrud();
    int deleted = 0;
    if(customer.deleteData(id) == 1)
      deleted= 1;
  return deleted;
}


public void updateRecord(int num,String customId){
  Selection selection = new Selection();
  boolean valid = false;
  Validation validate = new Validation();
  CustomerView view = new CustomerView();
  CustomerCrud cust= new CustomerCrud();
  Customers customer = new Customers();

  CustomerController controller = new CustomerController(cust.readByOne(customId),view);

  switch(num)
  {
    case 1:
    System.out.println("\n\t\t\t0. GO Back");
    String data = this.promptExistingDetails("FirstName",controller.getFirstName());
    if(!data.equals("0"))
    {
     selection.selectChoice("save","changes");
     if(selection.getChoice() == true)
    {     
     cust.updateData(customId,1,data);
    }
    }
    break;
    case 2:
    System.out.println("\n\t\t\t0. GO Back");
     data = this.promptExistingDetails("LastName",controller.getLastName());
    if(!data.equals("0"))
    {
     selection.selectChoice("save","changes");
     if(selection.getChoice() == true)
    {     
      cust.updateData(customId,2,data);
    }
    }
    break;
    case 3:
    System.out.println("\n\t\t\t0. GO Back");
    boolean verifyEmail = true;
    verifyEmailLoop:do{
     data = this.promptExistingDetails("Email",controller.getUserEmail());
     if(!data.equals("0"))
     {
      if(!cust.verifyEmail(data))
      {
      verifyEmail = false;
      selection.selectChoice("save","changes");
      if(selection.getChoice() == true)
     {
        cust.updateData(customId,3,data);
      }
     }else{
      System.out.println("\t\t\tSuch Email already Exists");
     } 
       }else{
     break verifyEmailLoop;
       }
    }while(verifyEmail);
 
    break;
    case 4:
     boolean validateTel = true;
     data = "0";
     do{
       do{
        System.out.println("\n\t\t\t0. GO Back");
        System.out.print("\t\t\tExisting Tel Number: " +controller.getUserTel());
        System.out.print("\t\t\tEnter The New : ");
        valid =validate.validate("int","telphone number");  
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
            cust.updateData(customId,4,data);
           }
         }  
       }
     }while(validateTel);
 
    
    break;
    case 5:
     System.out.println("\n\t\t\t0. GO Back");
     data = this.promptExistingDetails("Location Address",controller.getCustLocat());
     if(!data.equals("0"))
     {
      selection.selectChoice("save","changes");
      if(selection.getChoice() == true)
     {
      cust.updateData(customId,5,data);
     }
     }
 
    break;
    default:
    System.out.println("\t\t\tInvalid Choice Try AGAIN");
  }
}

  public void viewOne(String customId){
    Selection selection = new Selection();
    Scanner scan = new Scanner(System.in);
    boolean valid = false;
    Validation validate = new Validation(); 
    CustomerCrud cust= new CustomerCrud();
    Customers customer = new Customers();
    customer = cust.readByOne(customId);

    System.out.println("\n");
   CustomerView view = new CustomerView();
   CustomerController controller = new CustomerController(cust.readByOne(customId),view);
   boolean viewRecord = true;

   viewRecordLoop:do{ 
    controller.updateView();
 
   System.out.println("\n\n\t\t\tTake Action on "+controller.getFirstName()+"'s Record");
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
  if(num != 0){
    updateRecord(num,customId);
    controller = new CustomerController(cust.readByOne(customId),view);
  }else{
    update = false;
      }
      }while(update);
  
   }else if(choice4.equals("2"))
   {
      selection.selectChoice("delete",controller.getFirstName()+"'s the records");
      
      if(selection.getChoice() == true) {
        int deleted = deleteRecord(customId);
        if(deleted == 1){
          System.out.println("\t\t\t\tBYE !!!!!!!!!!!!!!!!!!!!!!!!YOU WILL FIND ME IN THE TRASH!!!!!!\n");
          break viewRecordLoop; 
        }
        else{
          System.out.println("\t\t\t\tTry again an error occurred");
        }
      }

   }else if(choice4.equals("3")){
    viewRecord=false;
   }else{
    System.out.println("\t\t\tInvalid input");
   }
  }while(viewRecord);
   System.out.println("\n\n");
  }
 



  public void insertData(HashMap<String,String> hash_map){
  
  int inserted = 0;
      
  Customers model = new Customers();
  model.setLocation(hash_map.get("location"));
  model.setFirName(hash_map.get("firName"));
  model.setLasName(hash_map.get("lasName"));
  model.setEmail(hash_map.get("mail"));
  model.setTel(hash_map.get("tel"));
  model.setPass(hash_map.get("passw"));

  CustomerView view = new CustomerView();
  CustomerCrud customer = new CustomerCrud();
  model = customer.createData(model);
  CustomerController controller = new CustomerController(model,view);
controller.updateView();
System.out.println("\n\n");
  }


  public HashMap gatherRecords()
  {
CustomerCrud crd = new CustomerCrud();
Selection selection = new Selection();
Validation validate = new Validation();
HashMap<String,String> hash_map = new HashMap<String,String>();
boolean add = true;
boolean valid=false;
Scanner scan = new Scanner(System.in);
String firName,lasName,mail,passw,location,tel="0";
           addCustomerLoop:do{
           System.out.println("\t\t\tGO Back:0 ");
           System.out.print("\n\t\t\tFirst Name:...... ");
           firName = scan.next();
            
            if(firName.equals("0"))
               break addCustomerLoop;


            System.out.print("\n\t\t\tLast Name:...... ");
            lasName = scan.next();

            if(lasName.equals("0"))
               break addCustomerLoop;
   
  
            boolean validateTel = true;
            do{
              do{
                System.out.print("\n\t\t\tTelephone:...... ");
                valid = validate.validate("int","telphone number");  
              }while(!valid);
              int telNum = validate.getValidInt();

              if(telNum == 0)
              {
                break addCustomerLoop;
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
                 break addCustomerLoop;
                
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
               break addCustomerLoop;
            

            System.out.print("\n\t\t\tPassword:...... ");
            passw = scan.next();
        
            if(passw.equals("0"))
               break addCustomerLoop;
          
               selection.selectChoice("SAVE"," The records");
               if(selection.getChoice() == true) {
              hash_map.put("firName", firName); 
              hash_map.put("lasName",lasName); 
              hash_map.put("mail", mail); 
              hash_map.put("passw", passw); 
              hash_map.put("location", location); 
              hash_map.put("tel", tel); 
              
                 insertData(hash_map);
                 System.out.println("\t\t\t\tRecords are saved");

              selection.selectChoice("ADD","ANOTHER CUSTOMER");
      
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
    CustomerCrud customers = new CustomerCrud();
    directory.printDirectory("viewAll","CUSTOMERS");
    System.out.println("\n\t\t\t\t\tTHE LIST OF REGISTERED CUSTOMERS\n");
    System.out.println("\n\n");

    customers.readData();
    System.out.println("\n\n\n");
  }

  public int checkIfExists(String id)
  {
    int exists = 0;
    CustomerCrud cust= new CustomerCrud();
    Customers customer = new Customers();
    customer = cust.readByOne(id);
    if(customer.getCustId() != null)
       exists = 1;
    return exists;
  }

  public void customer(){
    ManageDirectories directory = new  ManageDirectories();
    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    boolean manageCustomer = true;
    do{
      directory.printDirectory("home","CUSTOMER");

      do{
        System.out.print("\t\t\tChoose..... ");
        valid =validate.validate("int","input");  
      }while(!valid);
      int choice = validate.getValidInt();
    switch(choice){
      case 1:
      
      directory.printDirectory("add","CUSTOMER");
      this.gatherRecords();
      break;
      case 2:

      boolean view = true;
      viewLoop:do{
        directory.printDirectory("view","CUSTOMER");
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
          directory.printDirectory("viewOne","CUSTOMER");
          System.out.println("\t\t\t0 .GO BACK");
          System.out.print("\n\t\t\tEnter the Id of the Customer: ");
          String custId = scan.next();
          if(custId.equals("0"))
             view_one = false;
          else
             if(checkIfExists(custId) == 1){
              directory.printDirectory(custId,"CUSTOMER");
              viewOne(custId);
             }else{
              System.out.println("\n\t\t\t\tSORRY SUCH CUSTOMER WAS NOT FOUND\n\n");
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
    manageCustomer= false;
    break;
    default: 
    System.out.println("\n\t\t****Invalid choice:***** ");
    }
    }while(manageCustomer);
  }
}