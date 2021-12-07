package Model.ordered_product;

public class OrderedProduct{

private String prodId;
private String orderId;
private float quantity;
private float unitPrice;

public void setProdId(String id)
{
    this.prodId = id;
}
public void setOrderId(String id)
{
    this.orderId = id;
}

public void setQuantity(float quantity)
{
     this.quantity=quantity;
}


public void setUnitPrice(float price)
{
     this.unitPrice=price;
}

public String getOrderId()
{
    return this.orderId;
}

public String getProdId()
{
    return this.prodId;
}

public float getQuantity()
{
     return  this.quantity;
}

public float getUnitPrice()
{
     return this.unitPrice;
}


}