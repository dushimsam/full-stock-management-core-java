import Model.*;
import Controller.*;
import View.*;
import Db.*;
import java.util.*;
import java.io.*;
import utils.*;
import options.*;

public class app{

public static void main(String[] args)
{
 
  authorize:while(1 > 0){
    System.out.println("\n\n\n\t\t\t\t\tWELCOME ON STOCK MANAGEMENT SYSTEM\n\n");

    Scanner scan = new Scanner(System.in);
String email,password;
System.out.println("\t\t\t0.Exit");
System.out.println("\t\t------------------------------------------------------------------------------");
System.out.println("\t\t\t\t\t\t\tLOGIN\n");
System.out.println("\t\t------------------------------------------------------------------------------");
System.out.print("\t\t\tEmail:.... ");
email=scan.nextLine();

if(email.equals("0"))
    break authorize;

System.out.print("\n\t\t\tPassword:.... ");
password=scan.nextLine();

System.out.println("\t\t------------------------------------------------------------------------------");

if(password.equals("0"))
    break authorize;

Authentication auth = new Authentication();
Dashboards dashboard = new Dashboards();
String customerId = auth.checkCustomer(email,password);
String employeeId = auth.checkEmployee(email,password);
if(customerId != null)
{
  dashboard.customer(customerId);
}else if(employeeId != null)
{
  dashboard.employee(employeeId);
}
  
  }
}


}