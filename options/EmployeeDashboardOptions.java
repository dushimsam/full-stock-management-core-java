package options;
import Model.*;
import Controller.*;
import View.*;
import Db.*;
import java.util.*;
import java.io.*;
import utils.*;


public class EmployeeDashboardOptions implements Options{

  public int deleteRecord(String id)
{
    EmployeeCrud employee = new EmployeeCrud();
    int deleted = 0;
    if(employee.deleteData(id) == 1)
      deleted= 1;
  return deleted;
}

public void updateRecord(int num,String empId){
    boolean valid = false;
    Validation validate = new Validation();
    EmployeeCrud emp = new EmployeeCrud();
    Employees employee = new Employees();
    employee = emp.readByOne(empId);
    EmployeeView view = new EmployeeView();
    EmployeeController controller = new EmployeeController(employee, view);
    Selection selection = new Selection();

        switch (num) {
        case 1:
          System.out.println("\n\t\t\t0. GO Back");
          String data = this.promptExistingDetails("FirstName",controller.getFirstName());
          
          if(!data.equals("0"))
          {
           selection.selectChoice("save","changes");
           if(selection.getChoice() == true)
          {
          emp.updateData(empId, 1, data); 
          }
          }
          break;
        case 2:
        System.out.println("\n\t\t\t0. GO Back");
          data= promptExistingDetails("LastName",controller.getLastName());
          if(!data.equals("0"))
          {
           selection.selectChoice("save","changes");
           if(selection.getChoice() == true)
          {
          emp.updateData(empId, 2, data); 
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
          if(!emp.verifyEmail(data))
          {
          verifyEmail = false;
          selection.selectChoice("save","changes");
          if(selection.getChoice() == true)
         {   emp.updateData(empId,4,data);
          emp.updateData(empId,3,data);
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
               emp.updateData(empId,4,data);
              }
            }  
          }
        }while(validateTel);
          break;
        case 5:
          System.out.println("\n\t\t\t0. GO Back");
          System.out.print("\t\t\tExisting Salary:  " + controller.getEmpSalary());
          do {
            System.out.print("\t\t\tEnter The New: ");
            valid = validate.validate("float", "salary");
          } while (! valid );
          data = String.valueOf(validate.getValidFloat());

          if(!data.equals("0"))
          {
           selection.selectChoice("save","changes");
           if(selection.getChoice() == true)
            {
          emp.updateData(empId, 6, data); 
            }
          }
          break;

        case 6:
          System.out.println("\n\t\t\t0. GO Back");
          data = promptExistingDetails("Job",controller.getEmpJob());
          if(!data.equals("0"))
          {
           selection.selectChoice("save","changes");
           if(selection.getChoice() == true)
            {
          emp.updateData(empId, 5, data); 
            }
          }
          break;
        default:
          System.out.println("\t\t\tInvalid Choice Try AGAIN");
        }
}


public void deleteEmployee(String id,String employeeFirstName){
    Scanner scan = new Scanner(System.in);
    EmployeeCrud emp = new EmployeeCrud();
    System.out.println("\t\t\tAre You sure you want to Delete " + employeeFirstName + "'s Data (Y/N)");
    String selection = scan.nextLine();
    if (selection.equals("yes") || selection.equals("YES") || selection.equals("y") || selection.equals("Y")) {
      if (emp.deleteData(id) == 1) {
        System.out.println("\t\t\tEmployee is removed from the list");
      }else {
        System.out.println("\t\t\tTry again error occurred");
      }
    } 
}

public void viewOne(String empId){
    Scanner scan = new Scanner(System.in);
    EmployeeCrud emp = new EmployeeCrud();
    Selection selection = new Selection();
    Employees employee = new Employees();
    employee = emp.readByOne(empId);
    boolean valid = false;
    Validation validate = new Validation();
    System.out.println("\n");
    EmployeeView view = new EmployeeView();
     EmployeeController controller = new EmployeeController(employee, view);
     boolean viewRecord = true;
  
     viewRecordLoop:do{
        controller.updateView();
        System.out.println("\n\n\t\t\tTake Action on " + controller.getFirstName() + "'s Records");
        System.out.println("\n\t\t\t1.Update");
        System.out.println("\n\t\t\t2.Delete");
        System.out.println("\n\t\t\t3.Not Now");
        System.out.print("\n\t\t\tChoose:.. ");
        String choice4 = scan.nextLine();
        if (choice4.equals("1")) {
          boolean update = true;
          do{
            System.out.println("\t\t\t0.GO BACK");
            do{
             System.out.print("\t\t\tEnter the Corresponding index of the record to update:  ");
             valid =validate.validate("int","input");  
          }while(!valid);
          int num = validate.getValidInt();
          if(num != 0){
            updateRecord(num,empId);
            controller = new EmployeeController(emp.readByOne(empId),view);
          }else{
            update = false;
          }
        }while(update);

        } else if (choice4.equals("2")) {
          selection.selectChoice("delete",controller.getFirstName()+"'s the records");
          if(selection.getChoice() == true) {
            int deleted = deleteRecord(empId);
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


public void insertData(HashMap<String,String> hash_map)
{
    int inserted = 0;
    Employees model = new Employees();

          model.setSalary(Float.parseFloat(hash_map.get("salary")));
          model.setJob(hash_map.get("job"));
          model.setFirName(hash_map.get("firName"));
          model.setLasName(hash_map.get("lasName"));
          model.setEmail(hash_map.get("mail"));
          model.setTel(hash_map.get("tel"));
          model.setPass(hash_map.get("passw"));


          EmployeeView view = new EmployeeView();

          EmployeeController controller = new EmployeeController(model, view);
          EmployeeCrud emp = new EmployeeCrud();
          emp.createData(model);
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
EmployeeCrud crd = new EmployeeCrud();

String firName,lasName,mail,job,passw,tel="0";
float salary = 0;
addEmployeeLoop:do{
  System.out.println("\t\t\tGO Back:0 ");

  System.out.print("\t\t\tFirst Name:...... ");
  firName = scan.next();

  if(firName.equals("0"))
    break addEmployeeLoop;
        
  
  System.out.print("\n\t\t\tLast Name:...... ");
  scan.nextLine();
  lasName = scan.next();

  if(lasName.equals("0"))
     break addEmployeeLoop;

     boolean validateTel = true;
     do{
       do{
         System.out.print("\n\t\t\tTelephone:...... ");
         valid = validate.validate("int","telephone number");  
       }while(!valid);
       int telNum = validate.getValidInt();

       if(telNum == 0)
       {
         break addEmployeeLoop;
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
          break addEmployeeLoop;
         
          if(!crd.verifyEmail(mail))
          {
           verifyEmail = false;
          }else{
           System.out.println("\t\t\tSuch Email already Exists");
          }

     }while(verifyEmail);

  System.out.print("\n\t\t\tJob:...... ");
  job = scan.next();
  
if(job.equals("0"))
      break addEmployeeLoop;

      do {
        System.out.print("\n\t\t\tSalary:...... ");
        valid = validate.validate("float", "salary");
      } while (! valid );
      salary = validate.getValidFloat();

      if(salary == 0)
          break addEmployeeLoop;
                
         

      System.out.print("\n\t\t\tPassword:...... ");
      passw = scan.next();

      if(passw.equals("0"))
          break addEmployeeLoop;

      selection.selectChoice("SAVE"," The records");
      if(selection.getChoice() == true) {
         hash_map.put("firName", firName); 
         hash_map.put("lasName",lasName); 
         hash_map.put("mail", mail); 
         hash_map.put("passw", passw); 
         hash_map.put("salary", Float.toString(salary)); 
         hash_map.put("tel", tel); 
         hash_map.put("job", job); 

        insertData(hash_map);
            System.out.println("\t\t\t\tRecords are saved");

              selection.selectChoice("ADD","ANOTHER EMPLOYEE");
      
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
  EmployeeCrud employee = new EmployeeCrud();
  directory.printDirectory("viewAll","EMPLOYEES");
  System.out.println("\n\t\t\t\t\tTHE LIST OF REGISTERED EMPLOYEES\n");
  System.out.println("\n\n");

  employee.readData();
  System.out.println("\n\n");
}



public int checkIfExists(String id)
{
  int exists = 0;
  EmployeeCrud emp= new EmployeeCrud();
  Employees employee = new Employees();
  employee = emp.readByOne(id);
  if(employee.getEmpId() != null)
     exists = 1;
  return exists;
}




  public void employee() {
    ManageDirectories directory = new  ManageDirectories();
    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    boolean manageEmployee = true;
    do{
      directory.printDirectory("home","EMPLOYEE");

      do{
        System.out.print("\t\t\tChoose..... ");
        valid =validate.validate("int","input");  
      }while(!valid);
      int choice = validate.getValidInt();
    switch(choice){
      case 1:
      
      directory.printDirectory("add","EMPLOYEE");
      this.gatherRecords();
      break;
      case 2:

      boolean view = true;
      viewLoop:do{
        directory.printDirectory("view","EMPLOYEE");
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
          directory.printDirectory("viewOne","EMPLOYEE");
          System.out.println("\t\t\t0 .GO BACK");
          System.out.print("\n\t\t\tEnter the Id of the Employee: ");
          String id = scan.next();
          if(id.equals("0"))
             view_one = false;
          else
             if(checkIfExists(id) == 1){
              directory.printDirectory(id,"EMPLOYEE");
              viewOne(id);
             }else{
              System.out.println("\n\t\t\t\tSORRY SUCH EMPLOYEE WAS NOT FOUND\n\n");
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
    manageEmployee= false;
    break;
    default: 
    System.out.println("\n\t\t****Invalid choice:***** ");
    }
    }while(manageEmployee);
  }
  }

