package Model.supplies;

public class Supplies{

private String supId;
private String supplierId;
private String supDate;


public void setSupId(String id)
{
    this.supId = id;
}
public void setSupplierId(String id)
{
    this.supplierId = id;
}

public void setSupDate(String date)
{
     this.supDate=date;
}



public String getSupId()
{
    return this.supId;
}
public String getSupplierId()
{
    return   this.supplierId;
}

public String  getSupDate()
{
     return this.supDate;
}


}
