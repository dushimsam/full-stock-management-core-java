package Model;

public class Users{

    private String firName;
    private String lasName;
    private String email;
    private String tel;
    private String pass;

    public void setFirName(String firName)
{
    this.firName = firName;
}
public void setLasName(String lasName)
{
    this.lasName = lasName;
}
public void setEmail(String email)
{
    this.email = email;
}

public void setTel(String tel)
{
    this.tel = tel;
}

public void setPass(String password)
{
    this.pass = password;
}

public String getEmail()
{
   return  this.email;
}

public String  getFirName(){
    return this.firName;

}
public String  getLasName(){
    return this.lasName;

}
public String getTel()
{
   return  this.tel;
}

public String getPass()
{
    return this.pass;
}

}