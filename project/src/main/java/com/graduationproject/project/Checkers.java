package com.graduationproject.project;



import lombok.experimental.UtilityClass;



/**
 * A class that contains only static methods used for user input vlidation  
 */
@UtilityClass
public class Checkers {
public static final String PHONE_NUMBER_REGEX = "/^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$/";
public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,5}$";
public static final String INFERENCE_TO_AI_REGEX = "^[[\u0621-\u064A0-9]\\s\\d]+$";
  /**
  *@param email is the email you want to check its validity using the regex
  *<p>
  *The regex is
  *<pre>{@code
  *private static final String EMAIL_REGEX =".*@hotmail\\.(com|net)";}
  *</pre>
  *@return true if it matches the regex and of length <=320 (Maximum email length)
  */
    public static boolean checkEmail(String email){
      return email.matches(EMAIL_REGEX)&&email.length()<=320;  
    }
    /**
     * @param phoneNumber is the phone number you want to check its validity using the regex otherwise false
     * <p> 
     * The regex is 
     * <pre>{@code 
     * private static final String PHONE_NUMBER_REGEX = "/^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$/";
     *</pre>
     * @return true if it matches the regex otherwise flase
     */
    public static boolean checkPhoneNumber(String phoneNumber){
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }
    /**
     * @param query is the Arabic text a user sent and you want to check its validity using the regex otherwise false
     * <p>
     * The regex is
     * <pre>{@code
     * private static final String PHONE_NUMBER_REGEX = "/^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$/";
     * </pre>
     * @return true if it matches the regex otherwise flase
     */
    public static boolean checkQuery(String query){
      return query.matches(INFERENCE_TO_AI_REGEX);
    }
}
