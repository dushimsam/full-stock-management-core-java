package options;

import Model.product.Product;
import Model.product_category.*;
import Controller.product_category.*;
import View.product_category.*;
import Db.transaction.product_category.*;
import java.util.*;
import java.io.*;
import utils.*;

public class CategoryOption implements Options{



  public int deleteRecord(String id)
  {
    CategoryCrud category= new CategoryCrud();
      int deleted = 0;
      if(category.deleteData(id) == 1)
        deleted= 1;
    return deleted;
  }
  


  public void updateRecord(int index,String id)
  {
    boolean valid = false;
    Validation validate = new Validation();
    Selection selection = new Selection();
    CategoryCrud categoryCrud = new CategoryCrud();
    ProductCategory categ = new ProductCategory();
    ProductCategoryView view = new ProductCategoryView();
   ProductCategoryController controller = new ProductCategoryController(categoryCrud.readByOne(id),view);


   switch(index)
   {
   case 1:
   String data = this.promptExistingDetails("Category Name",controller.getCategoryName());
   if(!data.equals("0"))
   {
   data = data.toLowerCase();
   data = data.replaceAll(" ", "_");
   selection.selectChoice("save","changes");
   if(selection.getChoice() == true)
  {
    categoryCrud.updateData(id,1,data);
   }
  }

   break;
   case 2:
   System.out.println("\n\t\t\t0. GO Back");
   data = this.promptExistingDetails("Category Description", controller.getDesc());
   if(!data.equals("0"))
   {
   data = data.replaceAll(" ", "_");
   selection.selectChoice("save","changes");
   if(selection.getChoice() == true)
  {
    categoryCrud.updateData(id,2,data);
   }
  }


   break;
   default:
   System.out.println("\t\t\tInvalid Choice Try AGAIN");
   }

  }


  public void viewOne(String id)
  {
    boolean valid = false;
    Validation validate = new Validation();
    Selection selection = new Selection();
    CategoryCrud categoryCrud = new CategoryCrud();
    ProductCategory categ = new ProductCategory();
    categ = categoryCrud.readByOne(id);
    ProductCategoryView view = new ProductCategoryView();
   ProductCategoryController controller = new ProductCategoryController(categoryCrud.readByOne(id),view);
   Scanner scan = new Scanner(System.in);


      System.out.println("\n\n");
      boolean viewRecord=true;

      viewRecordLoop:do{
      controller.updateView();
    
    System.out.println("\n\n\t\t\tTake Action on "+controller.getCategoryName()+"'s Record");
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
    int index = validate.getValidInt();

    if(index != 0)
{
  updateRecord(index,id);
  controller = new ProductCategoryController(categoryCrud.readByOne(id),view);
}else{
  update = false;
}
      }while(update);

    }else if(choice4.equals("2"))
    { 
  selection.selectChoice("delete",controller.getCategoryName()+"'s the records");
  if(selection.getChoice() == true) {
    int deleted = deleteRecord(id);
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
      ProductCategory model = new ProductCategory();
  
      model.setCatName(hash_map.get("name"));
      model.setDescription(hash_map.get("disc"));
      ProductCategoryView view = new ProductCategoryView();
      ProductCategoryController controller = new ProductCategoryController(model, view);
            CategoryCrud categCrud = new CategoryCrud();
            categCrud.createData(model);
            controller.updateView();
            System.out.println("\n");
  }

  
  public HashMap gatherRecords()
  {
    Selection selection = new Selection();
    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    HashMap<String,String> hash_map = new HashMap<String,String>();
    boolean add = true;
    String name,description;
    float salary=0;
    CategoryCrud crud = new CategoryCrud();

    addCategoryLoop:do{
      System.out.println("\t\t\tGO Back:0 ");
      
      boolean validateCategory = true;

      do{
      System.out.print("\n\t\t\tCategory Name:...... ");
      name = scan.nextLine();
      if(!name.equals("0"))
      { 
      name = name.toLowerCase();
      name = name.replaceAll(" ", "_"); 
      if(crud.exists(name,"name"))
            System.out.println("Sorry such category name already exists");
      else  
            validateCategory = false;      
        
     }else{
      break addCategoryLoop;
    }
    }while(validateCategory);


    System.out.print("\n\t\t\tDescription:...... ");
      description = scan.nextLine();
      if(description.equals("0"))
          break addCategoryLoop;
      

      description = description.replaceAll(" ", "_");


      System.out.println("\n");
      selection.selectChoice("SAVE"," The records");
         if(selection.getChoice() == true) {
            hash_map.put("name", name); 
            hash_map.put("disc",description); 
            insertData(hash_map);
        System.out.println("\t\t\t\tRecords are saved");

        selection.selectChoice("ADD","ANOTHER CATEGORY");
      
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
    CategoryCrud category = new CategoryCrud();
    directory.printDirectory("viewAll","PRODUCT CATEGORIES");
    System.out.println("\n\t\t\t\t\tTHE LIST OF REGISTERED PRODUCT CATEGORIES\n");
    System.out.println("\n\n");
    category.readData();
    System.out.println("\n\n\n");
  }

  public int checkIfExists(String id)
  {
    int exists = 0;
    CategoryCrud categoryCrud= new CategoryCrud();
    ProductCategory model = new ProductCategory();
    model = categoryCrud.readByOne(id);
    if(model.getCatId() != null)
       exists = 1;
    return exists;
  }

public void category(){
     ManageDirectories directory = new  ManageDirectories();
    boolean valid = false;
    Validation validate = new Validation(); 
    Scanner scan = new Scanner(System.in);
    boolean manageProductCategory = true;
    do{
      directory.printDirectory("home","PRODUCT CATEGORY");

      do{
        System.out.print("\t\t\tChoose..... ");
        valid =validate.validate("int","input");  
      }while(!valid);
      int choice = validate.getValidInt();
    switch(choice){
      case 1:
      
      directory.printDirectory("add","PRODUCT CATEGORY");
      this.gatherRecords();
      break;
      case 2:

      boolean view = true;
      viewLoop:do{
        directory.printDirectory("view","PRODUCT CATEGORY");
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
          directory.printDirectory("viewOne","PRODUCT CATEGORY");
          System.out.println("\t\t\t0 .GO BACK");
          System.out.print("\n\t\t\tEnter the Id of the Product Category: ");
          String id = scan.next();
          if(id.equals("0"))
             view_one = false;
          else
             if(checkIfExists(id) == 1){
              directory.printDirectory(id,"PRODUCT CATEGORY");
              viewOne(id);
             }else{
              System.out.println("\n\t\t\t\tSORRY SUCH CATEGORY WAS NOT FOUND\n\n");
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
    manageProductCategory= false;
    break;
    default: 
    System.out.println("\n\t\t****Invalid choice:***** ");
    }
    }while(manageProductCategory);
}


}
