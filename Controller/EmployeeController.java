package Controller;

import Model.Employees;
import View.EmployeeView;


 public class EmployeeController{

 private Employees model;
 private EmployeeView view;

 public  EmployeeController(Employees model,EmployeeView view)
 {
     
     this.model = model;
     this.view = view;
 }

 

 public void setEmpSalary(float salary)
 {
     model.setSalary(salary);
 }
 public void setEmpJob(String job)
 {
    model.setJob(job);
 }
 
 public void setEmpId(String id)
 {
     model.setEmpId(id);
 }
 
 public void setPassword(String password)
 {
     model.setPass(password);
 }
 

 public String getPassword()
{
   return  model.getPass();
}
 
 public float getEmpSalary()
 {
    return  model.getSalary();
 }
 
 public String  getEmpJob(){
     return model.getJob();
 
 }

 public String  getId(){
     return model.getEmpId();
 
 }


 public void setFirstName(String firName)
 {
    model.setFirName(firName);
 }

 public void setLastName(String lasName)
 {
     model.setLasName(lasName);
 }

 public void setUserEmail(String email)
 {
    model.setEmail(email);
 }
 
 public void setUserTel(String tel)
 {
    model.setTel(tel);
 }


 public String getUserEmail()
 {
    return  model.getEmail();
 }
 
 public String  getFirstName(){
     return model.getFirName();
 
 }
 public String  getLastName(){
     return model.getLasName();
 
 }
 public String  getUserTel(){
     return model.getTel();
 
 }

public void updateView()
{
view.printEmployeeDetails(model.getFirName(), model.getLasName(),model.getEmail(),model.getTel(),model.getSalary(),model.getJob());
}

 }