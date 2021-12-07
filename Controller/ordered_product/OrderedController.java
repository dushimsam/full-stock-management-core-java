
package Controller.ordered_product;

import Model.ordered_product.OrderedProduct;
import View.ordered_product.OrderedView;

public class OrderedController{

    private OrderedProduct model;
    private OrderedView view;

public OrderedController(OrderedProduct model,OrderedView view)
{
    this.model=model;
    this.view= view;
}


    public void setProductId(String id)
    {
        model.setProdId(id);
    }
    public void setOrdId(String id)
    {
        model.setOrderId(id);
    }
    
    public void setQuantit(float quantity)
    {
         model.setQuantity(quantity);
    }
    
    
    public void setUnitPric(float price)
    {
         model.setUnitPrice(price);
    }
    
    public String getOrdId()
    {
        return model.getOrderId();
    }
    
    public String getProductId()
    {
        return model.getProdId();
    }
    
    public float getQuantit()
    {
         return  model.getQuantity();
    }
    
    public float getUnitPric()
    {
         return model.getUnitPrice();
    }
    
    public void updateView()
    {
    view.printOrderedProductDetails(model.getProdId(),model.getOrderId(),model.getQuantity(),model.getUnitPrice());
    }

}
