
package Controller.supplied_product;

import Model.supplied_product.SuppliedProduct;
import View.supplied_product.SuppliedProductView;

public class SuppliedProductController{

    private SuppliedProduct model;
    private SuppliedProductView view;

public SuppliedProductController(SuppliedProduct model,SuppliedProductView view)
{
    this.model=model;
    this.view= view;
}

public void setProductId(String id)
{
    model.setProdId(id);
}
public void setSupp(String id)
{
    model.setSupId(id);
}



public void setQuantities(float quantity)
{
     model.setQuantity(quantity);
}


public void setUnitPric(float price)
{
     model.setUnitPrice(price);
}

public String getSuppId()
{
    return model.getSupId();
}

public String getProductId()
{
    return model.getProdId();
}

public float getQuantities()
{
     return  model.getQuantity();
}

public float getUnitPric()
{
     return model.getUnitPrice();
}

public void updateView()
{
view.printSuppliedProductDetails(model.getProdId(),model.getSupId(),model.getQuantity(),model.getUnitPrice());
}
}

