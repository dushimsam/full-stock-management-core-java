package Db;

import java.io.*;
import Db.CrudMethods;
import Model.Employees;

public class EmployeeCrud implements CrudMethods{

    // private Employees model;

private File file = new File("Db/files/employees.csv");

public void setFile(File filename)
{
    this.file = filename;
}

public  File getFile()
{
    return this.file;
}


public boolean verifyEmail(String email){
    boolean found = false;
    try {
     
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        int counter = 0;
        while((currentLn = objReader.readLine()) != null){
              if(counter > 0)
              {
                String[] listeMots = currentLn.split("\\s+");
                if(listeMots.length != 1)
                {
                    if(listeMots[4].equals(email))
                            found = true;
                 }
              }

            counter++;  
        }
      }catch (IOException e) {
        System.out.println("\n\n\t\t\tCan't read File\n\n");
    }
    return found;
}

public void createData(Employees model){
    try {
        FileWriter writer = new FileWriter(file,true);
        String stringId = this.generateRandomString().toUpperCase();
        writer.write("\n"+stringId+""+this.generateRandomInteger()+"\t "+model.getPass()+"\t "+model.getFirName()+"\t"+model.getLasName()+"\t"+model.getEmail()+"\t"+model.getTel()+"\t"+model.getJob()+"\t"+model.getSalary());
        writer.close();
 
} catch (IOException e) {
    System.out.println("\n\n\t\t\tCan't read File\n\n");
}
}

public void readData(){
    try{
        BufferedReader objReader = new BufferedReader(new FileReader(file));
        String currentLn = "";
        String[] list;
        int counter =0;
        int check = 0;
while( (currentLn = objReader.readLine()) != null)
{    if(counter == 0 && check == 0)
    {   System.out.println("\n\t-------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\n\tNUMBERED |\tEMPLOYEE-ID | \tFIRST-NAME |\tLAST-NAME |\t EMAIL |\t TEL |\tJOB |\tSALARY |\n");
        System.out.println("\n\t-------------------------------------------------------------------------------------------------------------------------");
         check = 1;
    }else{
        list = currentLn.split("\\s+");
        if(list.length != 1){
        System.out.println("\n\t"+counter+"|\t"+list[0]+" | \t"+list[2]+" |\t"+list[3]+"|\t "+ list[4]+ "|\t "+list[5]+" |\t "+list[6]+" |\t "+list[7]+" |\n");
        }else{
            counter--;
        }
    }
    counter++;
   
}
objReader.close();
    }catch(IOException e)
    {
        System.out.println(e.getMessage());
    }
   
}

public Employees readByOne(String id){
    Employees employee = new Employees();
    try{
    BufferedReader objReader = new BufferedReader(new FileReader(file));
    String currentLn = "";
    int counter = 0;
    int check = 0;
while( (currentLn = objReader.readLine()) != null)
{
if(counter > 0)
{
    String[] listeMots = currentLn.split("\\s+");

if(listeMots[0].equals(id))
{
    check = 1;
     employee.setEmpId(listeMots[0]);
     employee.setPass(listeMots[1]);
    employee.setFirName(listeMots[2]);
    employee.setLasName(listeMots[3]);
    employee.setEmail(listeMots[4]);
    employee.setTel(listeMots[5]);
    employee.setJob(listeMots[6]);
    employee.setSalary(Float.parseFloat(listeMots[7]));
}

    }
    
    counter++;  
}


}
catch(IOException e)
{
    System.out.println(e.getMessage());
}
return employee;
}

public void updateData(String id,int index,String newData){
 
    try{
    BufferedReader file = new BufferedReader(new FileReader(this.file));
    StringBuffer inputBuffer = new StringBuffer();
    String line;
int counter = 0;
int check =0;
while((line = file.readLine()) != null)
{
String[] listeMots = line.split("\\s+");

if(counter > 0)
{

if(listeMots[0].equals(id))
{
String oldData = listeMots[index+1];
String replaceLn= new String(line);
replaceLn = replaceLn.replace(oldData,newData);
line = replaceLn;
check = 1;
}

}
inputBuffer.append(line);
inputBuffer.append("\n");
counter++;
}

file.close();

    // write the new string with the replaced line OVER the same file

    FileOutputStream fileOut= new FileOutputStream(this.file);
fileOut.write(inputBuffer.toString().getBytes());
fileOut.close();
if(check == 1)
{
System.out.println("\n\n\t\t\tUpdated\n");
}else{
System.out.println("\n\n\t\t\tTry again error occured\n");
}
}catch(IOException e)
{
        System.out.println(e.getMessage());
}

}
public int deleteData(String id){
    
    try{
        BufferedReader file = new BufferedReader(new FileReader(this.file));
        StringBuffer inputBuffer = new StringBuffer();
        String line;
int counter = 0;
int check =0;
int found = 0;
while((line = file.readLine()) != null)
{
    String[] listeMots = line.split("\\s+");

if(counter > 0)
{

    if(listeMots[0].equals(id))
    {
   found = 1;
   check = 1;
    }else {
        found = 0;
    }

}
if(found == 0)
{
    inputBuffer.append(line);
    inputBuffer.append("\n");
}
counter++;
}

file.close();


        FileOutputStream fileOut= new FileOutputStream(this.file);
fileOut.write(inputBuffer.toString().getBytes());
fileOut.close();
if(check == 1)
{
return 1;
}
    }catch(IOException e)
    {
            System.out.println(e.getMessage());
    }

    return 0;
}


public String authenticateEmployee(String pass,String email)
{
       String empId = null;
        try{
        BufferedReader objReader = new BufferedReader(new FileReader(this.file));
        String currentLn = "";
        int counter = 0;
     
while( (currentLn = objReader.readLine()) != null)
{
    if(counter > 0)
    {
        String[] listeMots = currentLn.split("\\s+");
if(listeMots.length != 1)
{
    if(listeMots[1].equals(pass) && listeMots[4].equals(email))
    {
        empId = listeMots[0];
    }
}
        }
        
        counter++;  
    }
  

    }
catch(IOException e)
{
        System.out.println(e.getMessage());
}
return empId;
    }


}