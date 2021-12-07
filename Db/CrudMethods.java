package Db;

import Db.RandomIds;

public interface CrudMethods extends RandomIds{

    public void readData();        
    public void updateData(String id,int index,String newData);
    public int deleteData(String id);

}

