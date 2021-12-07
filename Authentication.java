import Model.*;
import Controller.*;
import View.*;
import Db.*;
import java.util.*;
import java.io.*;
import utils.*;
import options.*;

public class Authentication{


    public String checkCustomer(String email,String password)
    {
        CustomerCrud customer = new CustomerCrud();
        String returnAuth=customer.authenticateCustomer(password,email);
        return returnAuth;
    }
    public String checkEmployee(String email,String password)
    {
        EmployeeCrud employee = new EmployeeCrud();
        String returnAuth=employee.authenticateEmployee(password,email);
        return returnAuth;
    }

}