package Model.order;

public class Order{

private String orderId;
private String custId;
private String orderDate;


public void setOrderId(String id)
{
    this.orderId = id;
}
public void setCustId(String custId)
{
    this.custId = custId;
}

public void setOrderDate(String orderDate)
{
     this.orderDate=orderDate;
}



public String getOrderId()
{
    return this.orderId;
}
public String getCustId()
{
    return   this.custId;
}

public String  getOrderDate()
{
     return this.orderDate;
}


}
