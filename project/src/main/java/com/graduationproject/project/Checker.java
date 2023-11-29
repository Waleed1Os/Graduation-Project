package com.graduationproject.project;


public abstract class Checker {
  /**
  *@param email is the email you want to check its validity using the regex
  *@return true if it matches the regex and of length <=320 (Maximum email length)
  */
    public static boolean cheeckEmail(String email){
      return email.matches(".*@hotmail\\.(com|net)")&&email.length()<=320;  
    }
    /**
     * @param phoneNumber is the phone number you want to check its validity using the regex otherwise false
     * @return true if it matches the regex otherwise flase
     */
    public static boolean checkPhoneNumber(String phoneNumber){
        return phoneNumber.matches("\\+[0-9]{2,3}[0-9]{9}");
    }
public static boolean checkQuery(String query){
return query.matches("[\\u0600-\\u06FF\\s?]");}
}
