package Model.product;

public class Stock{

private String prodId;
private float quantities;
private float unit_price;

public void setUnitPrice(float unitPrice){
    this.unit_price = unitPrice;
}
public void setProdId(String id)
{
    this.prodId = id;
}
public void setTotalQuantities(float quantities)
{
    this.quantities = quantities;
}

public String getProdId()
{
    return this.prodId;
}
public float getTotalQuantities()
{
   return  this.quantities;
}
public float getUnitPrice(){
    return unitPrice;
}
}
