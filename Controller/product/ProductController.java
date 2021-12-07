package Controller.product;

import Model.product.Product;
import View.product.ProductView;

public class ProductController{

    private Product model;
    private ProductView view;

public ProductController(Product model,ProductView view)
{
    this.model=model;
    this.view= view;
}



public void setProductCode(String code)
{
    model.setProdCode(code);
}
public void setProductName(String name)
{
    model.setProdName(name);
}


public void setCategoryId(String id)
{
     model.setCategId(id);
}

public void setExpir(int months)
{
     model.setExpiration(months);
}


public String getProductCode()
{
    return model.getProdCode();
}
public String getProductName()
{
    return   model.getProdName();
}

public String  getCategoryId()
{
     return model.getCategId();
}

public int getExpir()
{
     return model.getExpiration();
}

public void updateView()
{
view.printProductDetails(model.getProdCode(),model.getProdName(),model.getCategId(),model.getExpiration());
}

}
