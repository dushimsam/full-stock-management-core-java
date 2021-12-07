
package Controller;

import Model.Supplier;
import View.SupplierView;

public class SupplierController{

    private Supplier model;
    private SupplierView view;

public SupplierController(Supplier model,SupplierView view)
{
    this.model=model;
    this.view= view;
}
  
public void setSupplierId(String id)
{
    model.setSupId(id);
}
public void setSupplierName(String name)
{
    model.setSupName(name);
}

public void setSupEmail(String  email)
{
    model.setEmail(email);
}

public void setSupLocation(String location)
{
    model.setLocation(location);
}

public void setSupTel(String tel)
{
    model.setTel(tel);
}

public String getSupplierId()
{
   return model.getSupId();
}
public String getSupplierName()
{
    return model.getSupName();
}

public String getSupTel()
{
  return model.getTel();
}

public String getSupEmail()
{
    return  model.getEmail();
}

public String getSupLocation()
{
   return model.getLocation();
}

    
    public void updateView()
    {
    view.printSupplierDetails(model.getSupId(),model.getSupName(),model.getLocation(),model.getEmail(),model.getTel());
    }

}
