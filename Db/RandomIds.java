package Db;

import java.util.*;
import java.io.*;
import java.security.SecureRandom;

public interface RandomIds{


        default public  String generateRandomString() {
              int leftLimit = 97; // letter 'a'
              int rightLimit = 122; // letter 'z'
              int targetStringLength = 3;
              Random random = new Random();
              StringBuilder buffer = new StringBuilder(targetStringLength);
              for (int i = 0; i < targetStringLength; i++) {
                  int randomLimitedInt = leftLimit + (int) 
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
                  buffer.append((char) randomLimitedInt);
              }
              String generatedString = buffer.toString();
           
              return generatedString;
      
          }
      
      default public String generateRandomInteger(){
          String dateStub = String.format("%tM%<tS%<tL", new Date());
      return dateStub;
      }

}
