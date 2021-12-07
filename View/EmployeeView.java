package View;
// import Controller.EmployeeController;
public class EmployeeView{

public void printEmployeeDetails(String firName,String lasName,String email,String tel,float salary,String job)
{
    System.out.println("\t\t\t1.First Name:  "+firName);
    System.out.println("\t\t\t2.Last Name:  "+lasName);
    System.out.println("\t\t\t3.Email:  "+email);
    System.out.println("\t\t\t4.Telephone Number:  "+tel);
    System.out.println("\t\t\t5.Salary:  "+salary);
    System.out.println("\t\t\t6.Job:  "+job);
}

}