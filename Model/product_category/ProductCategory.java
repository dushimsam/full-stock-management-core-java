package Model.product_category;

public class ProductCategory{

private String catId;
private String catName;
private String description;

public void setCatId(String catId)
{
    this.catId = catId;
}
public void setCatName(String catName)
{
    this.catName = catName;
}
public void setDescription(String description)
{
    this.description = description;
}


public String getCatId()
{
    return this.catId;
}
public String getCatName()
{
    return this.catName;
}
public String  getDescription()
{
    return this.description;
}

}
