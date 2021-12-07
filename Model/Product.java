package Model;

public class Product{

private String prodId;
private String prodName;
private String categId;
private int expiration;


public void setProdId(String id)
{
    this.prodId = id;
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


public String getProdId()
{
    return this.prodId;
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