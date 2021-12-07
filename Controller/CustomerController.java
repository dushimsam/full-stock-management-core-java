package Controller;

import Model.Customers;
import View.CustomerView;


public class CustomerController{

    private Customers model;
    private CustomerView view;

    public  CustomerController(Customers model,CustomerView view)
{

this.model = model;
this.view = view;

}

public void setCustLocat(String location)
{
    model.setLocation(location);
}
public void setId(String custId)
{
model.setCustId(custId);
}

public String getCustLocat()
{
   return  model.getLocation();
}

public String getId()
{
   return  model.getCustId();
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

 
public void setPassword(String password)
{
    model.setPass(password);
}


public String getPassword()
{
  return  model.getPass();
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
view.printCustomerDetails( model.getFirName(),model.getLasName(),model.getEmail(),model.getTel(),model.getLocation());
}

}