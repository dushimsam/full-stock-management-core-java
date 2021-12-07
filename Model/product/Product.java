package Model.product;

public class Product{

private String prodCode;
private String prodName;
private String categId;
private int expiration;

public void setProdCode(String id)
{
    this.prodCode = id;
}
public void setProdName(String name)
{
    this.prodName = name;
}

public void setCategId(String id)
{
     this.categId=id;
}

public void setExpiration(int months)
{
     this.expiration=months;
}


public String getProdCode()
{
    return this.prodCode;
}
public String getProdName()
{
    return   this.prodName;
}

public String  getCategId()
{
     return this.categId;
}

public int getExpiration()
{
     return this.expiration;
}


}
