package Model;

public class Supplier{

    private String supId;
    private String supName;
    private String location;
    private String email;
    private String tel;



    public void setSupId(String id)
    {
        this.supId = id;
    }
    public void setSupName(String name)
    {
        this.supName = name;
    }
    
    public void setEmail(String  email)
    {
         this.email=email;
    }

    public void setLocation(String location)
    {
         this.location=location;
    }
    
public void setTel(String tel)
    {
         this.tel=tel;
    }


    public String getSupId()
    {
       return this.supId;
    }
    public String getSupName()
    {
        return this.supName;
    }
    
    public String getEmail()
    {
        return  this.email;
    }

    public String getLocation()
    {
       return this.location;
    }
public String getTel()
    {
        return  this.tel;
    }

}
