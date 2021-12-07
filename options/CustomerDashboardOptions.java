package options;
import java.text.*;
import Model.order.*;
import Model.ordered_product.*;
import View.ordered_product.*;
import Controller.ordered_product.*;
import Model.product.*;

import Model.Customers;
import Db.CustomerCrud;
import Db.transaction.product.*;
import Controller.order.*;
import View.order.*;
import Db.*;
import Db.transaction.order.*;
import Db.transaction.ordered_product.*;
import Db.transaction.product.*;
import java.util.*;
import java.io.*;
import utils.*;


public class CustomerDashboardOptions{

public void payment(float totalAmount,String orderId)
{

    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);


OrderCrud order = new OrderCrud();

    System.out.println("\t\t\tTOTAL AMOUNT TO BE PAID: "+totalAmount);
    boolean checkMatch = false;
checkingMatch:do{
do{
  System.out.println("\t\t\t 0.BACK\n");
    System.out.print("\t\t\t PAY:... ");
   
        valid =validate.validate("float","input");  
 }while(!valid);
  float  pay=validate.getValidFloat();
 if(pay != 0)
  {
    if(pay != totalAmount)
    {
        System.out.println("\t\t\tYou have to match with given amount \n");
    }else{
        checkMatch=true;
    }
  }else{
    order.deleteData(orderId);
    break checkingMatch;
  }
}while(!checkMatch);
}




public void order(String custId){

    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);

boolean addingToCart=true;
float totalAmount=0;

Order orderModel = new Order();
  
SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));


String todayDate = dateTimeInGMT.format(new Date());
todayDate=todayDate.replaceAll(" ","_");
orderModel.setCustId(custId);
orderModel.setOrderDate(todayDate);

OrderCrud orderCrud = new OrderCrud();
String orderId = Integer.toString(orderCrud.createData(orderModel));
ProductCrud productCrud = new ProductCrud();
int countCarts = 1;
addToCart:do{
    StockCrud stockCrud = new StockCrud();
    stockCrud.purchase();
boolean exists = false;
int prodNbr=0;

    do{
    
    do{
      System.out.println("\n\t\t\t0.BACK");
        System.out.print("\t\t\tEnter the Number of the product:..  ");
        valid =validate.validate("int","input");  
     }while(!valid);
       prodNbr=validate.getValidInt();
      if(prodNbr < 0 || prodNbr > stockCrud.nbrOfProductsToOrder())
      {
        System.out.println("\t\t\tSuch product is  not available on the list");
      }else{
        exists = true;
      }
    }while(!exists);
      Product product = new Product();
product = stockCrud.returnOrderedProduct(prodNbr);

      if(prodNbr == 0)
      {
        if(countCarts > 1)
        {
          this.payment(totalAmount , orderId);
        }else{
          orderCrud.deleteData(orderId);
        }
        break addToCart;
      }
float quantity,unitPrice=0;
boolean checkProd=false;
boolean quantityAvailable = false;
do{
 do{
         System.out.println("\n\t\t\t0.BACK");
        System.out.print("\n\t\t\tEnter the Number of Quantities:...... ");
         valid =validate.validate("float","quantity");
 }while(!valid);
  quantity =validate.getValidFloat();
 float price = quantity * product.getPrice();
 totalAmount += price;
  if(quantity != 0)
  {
    StockCrud stock = new StockCrud();
    String stockProdId = product.getProdId();
    if(stock.updateData(stockProdId,quantity,"deduction") == false)
    {
     System.out.println("\n\n\t\t\tQuantities not Available in the Stock.\n");
    }else{
     quantityAvailable= true;
    }
  }else{
    if(countCarts > 1)
    {
      this.payment(totalAmount , orderId);
    }else{
      orderCrud.deleteData(orderId);
    }
break addToCart;
  } }while(!quantityAvailable);


System.out.println("\n\n");
OrderView view = new OrderView();
OrderController controller = new OrderController(orderModel,view);

      OrderedProduct orderedProModel = new OrderedProduct();
      OrderedCrud ordered =new OrderedCrud();

      orderedProModel.setProdId(product.getProdId());
      orderedProModel.setOrderId(orderId);
      orderedProModel.setQuantity(quantity);
      orderedProModel.setUnitPrice(unitPrice);
      ordered.createData(orderedProModel);

     OrderedProductView ordView =new OrderedProductView();
     OrderedProductController ordControll =new OrderedProductController(orderedProModel,ordView);
     System.out.println("\n\n\t\t\tProduct Number "+countCarts+" is added to cart\n\n");

     System.out.println("\t\t\tCONTINUE ADDING TO CART (Y/N)");
String ch = scan.nextLine();

if(ch.equals("n") || ch.equals("N"))
{
   this.payment(totalAmount,orderId);
   addingToCart=false; 
}else {
  countCarts++;
}
}while(addingToCart);

}


public void purchaseHistory(String customerId)
{
  CustomerCrud cust= new CustomerCrud();
  Customers customer = new Customers();
  OrderCrud ordCr = new OrderCrud();
  customer = cust.readByOne(customerId);
  if(customer.getCustId() != null){
   System.out.println("\n\n");
   ordCr.customerOrders(customer.getCustId(),"CLIENT");
  }
}

}
