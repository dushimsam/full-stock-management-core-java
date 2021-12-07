package Model.supplied_product;

public class SuppliedProduct{

private String prodId;
private String supId;
private float quantity;
private float unitPrice;

public void setProdId(String id)
{
    this.prodId = id;
}
public void setSupId(String id)
{
    this.supId = id;
}

public void setQuantity(float quantity)
{
     this.quantity=quantity;
}


public void setUnitPrice(float price)
{
     this.unitPrice=price;
}

public String getSupId()
{
    return this.supId;
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
