
package Controller.supplies;
import Model.supplies.Supplies;
import View.supplies.SuppliesView;

public class SuppliesController{

    private Supplies model;
    private SuppliesView view;

public SuppliesController(Supplies model,SuppliesView view)
{
    this.model=model;
    this.view= view;
}


public void setSuppId(String id)
{
    model.setSupId(id);
}
public void setSupplyId(String id)
{
    model.setSupplierId(id);
}

public void setSupDate(String date)
{
     model.setSupDate(date);
}



public String getSuppId()
{
    return model.getSupId();
}
public String getSupplyId()
{
    return   model.getSupplierId();
}

public String  getSupDate()
{
     return model.getSupDate();
}


public void updateView()
{
    view.printSupDetails(model.getSupId(),model.getSupplierId(),model.getSupDate());
}

}








