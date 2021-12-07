
package Controller.order;

import Model.order.Order;
import View.order.OrderView;

public class OrderController{

    private Order model;
    private OrderView view;

public OrderController(Order model,OrderView view)
{
    this.model=model;
    this.view= view;
}


public void setOrdId(String id)
{
    model.setOrderId(id);
}
public void setCustomerId(String custId)
{
    model.setCustId(custId);
}

public void setOrdDate(String orderDate)
{
     model.setOrderDate(orderDate);
}



public String getOrdId()
{
    return model.getOrderId();
}
public String getCustomerId()
{
    return   model.getCustId();
}

public String  getOrdDate()
{
     return model.getOrderDate();
}


public void updateView()
{
    view.printOrderDetails(model.getOrderId(),model.getCustId(),model.getOrderDate());
}

}








