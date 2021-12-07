package options;

import Model.product.*;
import Controller.product.*;
import View.product.*;
import Db.transaction.product.*;
import Db.transaction.product_category.*;
import java.util.*;
import java.io.*;
import utils.*;

public class ProductOption implements Options{


  public int deleteRecord(String id)
  {
    ProductCrud prod = new ProductCrud();
      int deleted = 0;
      if(prod.deleteData(id) == 1)
        deleted= 1;
    return deleted;
  }
  

  public void updateRecord(int num,String id)
  {
    boolean valid = false;
    Validation validate = new Validation();
    Selection selection = new Selection();
    ProductCrud prodCrud = new ProductCrud();
    Product model = new Product();
    model = prodCrud.readByOne(id);
    ProductView view = new ProductView();
    ProductController controller = new ProductController(prodCrud.readByOne(id),view);
    CategoryCrud categCrud =new CategoryCrud();

    switch(num){

case 1:

String data;
boolean verifyProductName = true;
do{
      System.out.println("\n\t\t\t0.Go Back");
   data = this.promptExistingDetails("Product Name",controller.getProductName());
if(!data.equals("0"))
{
data = data.toLowerCase();
data = data.replaceAll(" ","_");

if(!prodCrud.exists(data,"name")){
   verifyProductName = false;
   selection.selectChoice("save","changes");
   if(selection.getChoice() == true){
       prodCrud.updateData(id,1,data);
   }
}else{
   System.out.println("\n\t\t\tPlease such product already exists\n");
    }
}else{
  verifyProductName = false;
}
}while(verifyProductName);

break;
case 2:

boolean validCateg = true;
  do{
    System.out.println("\n\t\t\t0.Go Back");

    data = this.promptExistingDetails("Category Id",controller.getCategoryId());

    if(!data.equals("0")){
    data = data.replaceAll(" ","_");
    if(!categCrud.exists(data,"id"))
    {
        System.out.println("\t\t\tSorry such category does not exist");
    }else{
      if(!data.equals(controller.getCategoryId()))
      {
        validCateg=false;
        selection.selectChoice("save","changes");
        if(selection.getChoice() == true){
        prodCrud.updateData(id,2,data);
        }
      }else{
        System.out.println("\t\t\tEXISTING VALUE\n");
      }
        
    }
  }else{
    validCateg = false;
  }
  }while(validCateg);

break;

case 3:

do{
      System.out.println("\n\t\t\t0.Go Back");

  System.out.print("\t\t\tExisting Expiration in Months :  "+ controller.getExpir());
    System.out.print("\t\t\tEnter The New  Expiration: ");
    valid =validate.validate("int","input");  
  }while(!valid);
  int expire=validate.getValidInt();
  if(expire != 0)
  {
    selection.selectChoice("save","changes");
    if(selection.getChoice() == true){
      prodCrud.updateData(id,3,Integer.toString(expire));
    }
  }
break;
default:
System.out.println("\t\t\tInvalid Choice Try AGAIN");
}
  }


  public void viewOne(String code)
  {
    Selection selection = new Selection();
    Scanner scan = new Scanner(System.in);
    boolean valid = false;
    Validation validate = new Validation(); 

    ProductCrud prodCrud = new ProductCrud();
    Product model = new Product();
    model = prodCrud.readByOne(code);

      System.out.println("\n\n");
      ProductView view = new ProductView();
      ProductController controller = new ProductController(model,view);
      boolean viewRecord=true;

      viewRecordLoop:do{
      controller.updateView();
    
    System.out.println("\n\n\t\t\tTake Action on "+controller.getProductName()+"'s Record");
    System.out.println("\n\t\t\t1.Update");
    System.out.println("\n\t\t\t2.Delete");
    System.out.println("\n\t\t\t3.Go Back");
    System.out.print("\n\t\t\tChoose:.. ");
    String  choice4 = scan.nextLine();
    if(choice4.equals("1"))
    {

      boolean update = true;
      do{
    System.out.println("\t\t\t0.GO BACK");
    do{
      System.out.print("\t\t\tEnter the Corresponding index of the record to update:  ");
      valid =validate.validate("int","input");  
    }while(!valid);
    int num = validate.getValidInt();

    if(num != 0)
{
  updateRecord(num,code);
  controller = new ProductController(prodCrud.readByOne(code),view);
}else{
  update = false;
}
      }while(update);

    }else if(choice4.equals("2"))
    { 
  selection.selectChoice("delete",controller.getProductName()+"'s the records");
  if(selection.getChoice() == true) {
    int deleted = deleteRecord(code);
    if(deleted == 1){
      System.out.println("\t\t\t\tBYE !!!!!!!!!!!!!!!!!!!!!!!!YOU WILL FIND ME IN THE TRASH!!!!!!\n");
      break viewRecordLoop; 
    }
    else{
      System.out.println("\t\t\t\tTry again an error occurred");
    }
  }

    }else if(choice4.equals("3"))
    {
        viewRecord = false;
    }
    else{
    System.out.println("\t\t\tInvalid choice");
    }
    
    System.out.println("\n\n");
}while(viewRecord);
System.out.println("\n\n");
  }


 public void insertData(HashMap<String,String> hash_map)
{
    int inserted = 0;
    Product model = new Product();

    model.setProdName(hash_map.get("name"));
    model.setCategId(hash_map.get("categoryId"));
    model.setExpiration(Integer.parseInt(hash_map.get("expiration")));

    ProductView view = new ProductView();
    ProductController controller = new ProductController(model,view);
    ProductCrud productCrud = new ProductCrud();
    productCrud.createData(model);
          controller.updateView();
          System.out.println("\n");
}



public HashMap gatherRecords()
{
  ProductCrud crd = new ProductCrud();
  Selection selection = new Selection();
  boolean valid = false;
  Validation validate = new Validation(); 
  Scanner scan = new Scanner(System.in);
  HashMap<String,String> hash_map = new HashMap<String,String>();
  boolean add = true;
  String name,categoryId;
  int expire=0;
  CategoryCrud categ=new CategoryCrud();

  addProduct:do{

    System.out.println("\t\t\tEnter The Details of the New Product\n\n");
    boolean verifyProductName = true;
    do{
    System.out.print("\n\t\t\tProduct Name:...... ");
    name = scan.nextLine();
    if(name.equals("0"))
       break addProduct;
    name = name.toLowerCase();
    name = name.replaceAll(" ","_");
    if(!crd.exists(name,"name"))
       verifyProductName = false;
    }while(verifyProductName);
   
    boolean validateCateg=true;
  
    do{
      scan.nextLine();
      System.out.print("\n\t\t\tEnter The Category Id:...... ");  
      categoryId=scan.nextLine();

      if(!categoryId.equals(0)){
      if(!categ.exists(categoryId,"id"))
           System.out.println("\t\t\tSorry such category does not exist");
      else
           validateCateg=false;
      }else{
        break addProduct;
      }
    }while(validateCateg);

    
    do{
      System.out.print("\n\t\t\tEnter The Expiration in Months:...... ");
      valid =validate.validate("int","input");
      }while(!valid);
    expire = validate.getValidInt();
    
    if(expire == 0)
       break addProduct;

    System.out.println("\n");
    selection.selectChoice("SAVE"," The records");
         if(selection.getChoice() == true) {
            hash_map.put("name", name); 
            hash_map.put("categoryId",categoryId); 
            hash_map.put("expiration", Integer.toString(expire)); 
            insertData(hash_map);
            System.out.println("\t\t\t\tRecords are saved");

            selection.selectChoice("ADD","ANOTHER PRODUCT");
      
            if(selection.getChoice() == false){
             add=false;
             }  
         }else{
          add=false; 
          System.out.println("\t\t\t\tRECORDS WILL NOT BE SAVED");
         }


  }while(add);

return hash_map;
}

public void viewAll()
{
  ManageDirectories directory = new  ManageDirectories();
  ProductCrud productCrud = new ProductCrud();
  directory.printDirectory("viewAll","PRODUCT");
  System.out.println("\n\t\t\t\t\tTHE LIST OF REGISTERED PRODUCTS\n");
  System.out.println("\n\n");
  productCrud.readData();
  System.out.println("\n\n\n");
}

public int checkIfExists(String code)
{
  int exists = 0;
  ProductCrud productCrud = new ProductCrud();
  Product product = new Product();
  product = productCrud.readByOne(code);
  if(product.getProdCode() != null)
     exists = 1;
  return exists;
}

public void product(){
  ManageDirectories directory = new  ManageDirectories();
  boolean valid = false;
  Validation validate = new Validation(); 
  Scanner scan = new Scanner(System.in);
  boolean manageProduct = true;
  do{
    directory.printDirectory("home","PRODUCT");

    do{
      System.out.print("\t\t\tChoose..... ");
      valid =validate.validate("int","input");  
    }while(!valid);
    int choice = validate.getValidInt();
  switch(choice){
    case 1:
    
    directory.printDirectory("add","PRODUCT");
    this.gatherRecords();
    break;
    case 2:

    boolean view = true;
    viewLoop:do{
      directory.printDirectory("view","PRODUCT");
    do{
      System.out.print("\t\t\tChoose..... ");
      valid =validate.validate("int","input");  
    }while(!valid);
    int choice2 = validate.getValidInt();

    switch(choice2){
      case 1:
      boolean viewList = true;
       do{
        viewAll();
        System.out.print("\t\t\t0 .GO BACK !!! ");
        String backChoice  = scan.next();
        if(backChoice.equals("0"))
            viewList = false;
        else
           System.out.println("\t\t\tInvalid Choice:::Enter Zero To go back");    

       }while(viewList);
      break;
      case 2:

      boolean view_one = true;
      do{
        directory.printDirectory("viewOne","PRODUCT");
        System.out.println("\t\t\t0 .GO BACK");
        System.out.print("\n\t\t\tEnter the Code of the product: ");
        String code = scan.nextLine();
        if(code.equals("0"))
           view_one = false;
        else
           if(checkIfExists(code) == 1){
            directory.printDirectory(code,"PRODUCT");
            viewOne(code);
           }else{
            System.out.println("\n\t\t\t\tSORRY SUCH PRODUCT WAS NOT FOUND\n\n");
           }    
      }while(view_one);
      break;
      case 3:
      view = false;
      break;
      default:
      System.out.println("\n\t\t****Invalid choice:***** ");
    }

    }while(view);
    break;
  case 3:
  manageProduct= false;
  break;
  default: 
  System.out.println("\n\t\t****Invalid choice:***** ");
  }
  }while(manageProduct);
}
}
