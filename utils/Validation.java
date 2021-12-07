package utils;
import java.util.*;
public class Validation{

private int validInt;
private float validFloat;
private boolean validTel;

public  void setValidInt(int validInt)
{
 this.validInt=validInt;
}

public  void setValidTel(boolean validTel)
{
 this.validTel=validTel;
}


public boolean getValidTel()
{
  return this.validTel;
}

public  void setValidFloat(float validFloat)
{
 this.validFloat=validFloat;
}


    public  int getValidInt()
    {
    return this.validInt;
    }

    public  float getValidFloat()
    {
    return this.validFloat;
    }
    
    public  boolean validate(String status,String printed)
    {
      Scanner scan = new Scanner(System.in);
      boolean valid=false;
      float validFloat;
      int validInt;
    
      try{
    
        if(status.equals("float")){
      validFloat = Float.parseFloat(scan.nextLine());
      this.setValidFloat(validFloat);
        }else if(status.equals("int")){
          validInt=Integer.parseInt(scan.nextLine());
          this.setValidInt(validInt);
        }
      valid=true;
      }catch(NumberFormatException e)
      {
        System.out.println("\t\t\tinvalid "+printed+ " try again");
      }
      
    return valid;
    }
    

    public  void validateTel(int tel)
    {
     int count =1;
     while(tel != 0)
       {
         tel /= 10;
           ++count;
       }
     if(count != 10)
     {
       System.out.print("\n\t\t\t\tPlease only 10 digits are allowed ");
       setValidTel(false);
     }else{
      setValidTel(true);
     }   
    }

}