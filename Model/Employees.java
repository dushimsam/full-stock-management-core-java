package Model;

import Model.Users;

public class Employees extends Users{


private String empId;
private float salary;
private String job;

public void setEmpId(String empId)
{
    this.empId = empId;
}
public void setSalary(float salary)
{
    this.salary = salary;
}

public void setJob(String job)
{
     this.job= job;
}


public float getSalary()
{
   return   this.salary;
}

public String getJob()
{
   return   this.job;
}
public String getEmpId()
{
   return   this.empId;
}

}