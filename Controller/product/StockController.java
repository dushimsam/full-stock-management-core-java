
package Controller.product;

import View.product.*;
import Model.product.*;

public class StockController{

    private Stock model;
    private StockView view;

public StockController(Stock model,StockView view)
{
    this.model=model;
    this.view= view;
}


public void setProductId(String id)
{
    model.setProdId(id);
}

public void setPrice(float price)
{
    model.setUnitPrice(price);
}

public float getPrice()
{
   return  model.getUnitPrice();
}


public void setTotalQuantity(float quantities)
{
    model.setTotalQuantities(quantities);
}

public String getProductId()
{
    return model.getProdId();
}
public float getTotalQuantity()
{
   return  model.getTotalQuantities();
}


public void updateView()
{
view.printStock(model.getProdId(),model.getTotalQuantities(),model.getUnitPrice());
}

}
