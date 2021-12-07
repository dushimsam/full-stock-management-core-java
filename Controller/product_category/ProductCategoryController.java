
package Controller.product_category;

import Model.product_category.ProductCategory;
import View.product_category.ProductCategoryView;

public class ProductCategoryController{

    private ProductCategory model;
    private ProductCategoryView view;

public ProductCategoryController(ProductCategory model,ProductCategoryView view)
{
    this.model=model;
    this.view= view;
}

public void setCategoryId(String catId)
{
    model.setCatId(catId);
}
public void setCategoryName(String catName)
{
    model.setCatName(catName);
}
public void setDesc(String description)
{
    model.setDescription(description);
}


public String getCategoryId()
{
    return model.getCatId();
}
public String getCategoryName()
{
    return model.getCatName();
}
public String  getDesc()
{
    return model.getDescription();
}
  

public void updateView()
{
    view.printProductCategoryDetails(model.getCatName(),model.getDescription());
}


}