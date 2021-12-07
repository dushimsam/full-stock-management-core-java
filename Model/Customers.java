package Model;

public class Customers extends Users{

private String custLocation;
private String custId;

public void setLocation(String location)
{
    this.custLocation = location;
}
public void setCustId(String custId)
{
    this.custId = custId;
}

public String getLocation()
{
   return  this.custLocation;
}

public String getCustId()
{
   return  this.custId;
}


}