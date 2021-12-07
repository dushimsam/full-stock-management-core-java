import Model.*;
import Controller.*;
import View.*;
import Db.*;
import java.util.*;
import java.io.*;
import utils.*;
import options.*;



public class Dashboards{

public void customer(String customerId){
boolean login = true;
 do{
    System.out.println("\n\n\t\t\tWELCOME IN *** RCA E-COMMERCE  ***\n");
     System.out.println("\t\t\t1.Order\n");
     System.out.println("\t\t\t2.Your Purchase History\n");
     System.out.println("\t\t\t3.Message\n");
     System.out.println("\t\t\t4.Settings\n");
     System.out.println("\t\t\t5.Logout");

     CustomerDashboardOptions dashboardOption = new CustomerDashboardOptions();

    boolean valid = false;
    Validation validate = new Validation(); 

do{
  System.out.print("\n\n\t\t\tChoose From the list ");
  System.out.print("\t\t\tChoose..... ");
  valid =validate.validate("int","input");  
}while(!valid);
int choice = validate.getValidInt();
System.out.println("\n\n");
switch(choice)
{
case 1:
dashboardOption.order(customerId);
break;
case 2:
boolean viewHistory = true;
while(viewHistory)
{
dashboardOption.purchaseHistory(customerId);
do{
  System.out.print("\n\t\t\t0.Back ");
  valid =validate.validate("int","input");  
}while(!valid);
int viewChoice = validate.getValidInt();
if(viewChoice == 0)
{
  viewHistory=false;
}
}
break;
case 5:
login = false;
break;

default:
System.out.println("\t\t\tInvalid choice");
}
  }while(login);

}

public void employee(String empId){

boolean login = true;
do{
    System.out.println("\n\n\t\t\tWELCOME IN *** RCA E-COMMERCE  ***\n");

    System.out.println("\t\t\t 1.Employee Management System\n");
     System.out.println("\t\t\t2.Customer Management System\n");
     System.out.println("\t\t\t3.Supplier Management System\n");
     System.out.println("\t\t\t4.Product Categories Management\n");
     System.out.println("\t\t\t5.Products Management System\n");
     System.out.println("\t\t\t6.Orders Management System\n");
     System.out.println("\t\t\t7.Supplies Management System\n");
     System.out.println("\t\t\t8.Stock Management System\n");
     System.out.println("\t\t\t9.Settings\n");
     System.out.println("\t\t\t10.Log-out\n");

     boolean valid = false;
     Validation validate = new Validation(); 

 do{
   System.out.print("\n\n\t\t\tChoose From the list ");
   System.out.print("\t\t\tChoose..... ");
   valid =validate.validate("int","input");  
 }while(!valid);
 int choice = validate.getValidInt();
 
 switch(choice)
 {
    case 1:
     EmployeeDashboardOptions emp = new EmployeeDashboardOptions();
     emp.employee();
     break;
    case 2:
   CustomerOption custChoice = new CustomerOption();
   custChoice.customer();
   break;
   case 3:
   SupplierOption option = new SupplierOption();
   option.supplier();
   break;
   case 4:
   CategoryOption catChoice = new CategoryOption();
   catChoice.category();
   break;
   case 5:
   ProductOption prodChoice = new ProductOption();
   prodChoice.product();
  break;
  case 6:
  OrderOption order = new OrderOption();
  order.order();
  break;
  case 7:
  SuppliesOption supply = new SuppliesOption();
  supply.supply();
  break;
  case 9:
  System.out.println("\n\t\t\t\t****SORRY I AM STILL IN CONSTRUCTION PHASE :***** \n");
  break;
  case 10:
login=false;
  break;
default:
System.out.println("\n\t\t****Invalid choice:***** ");
     }

}while(login);
}
}
