
package View.ordered_product;
public class OrderedView{

public void printOrderedProductDetails(String prodId,String orderId,float quantity,float unitPrice)
{

    System.out.println("\n\n\t\t\t\t\t1.Order Id:  "+orderId);
    System.out.println("\t\t\t\t\t2.Product Id:  "+prodId);
    System.out.println("\t\t\t\t\t3.Quantity:  "+quantity);
    System.out.println("\t\t\t\t\t4.Sold At(Unit Price):  "+unitPrice);

}

}
