package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.OptionalInt;
import java.util.Scanner;
import java.util.stream.IntStream;

public class StringIntegerInterconversion {

  // Notes: handle -123, "-123", "+123"
  // Integer to string: input 314, output "314"; input -314 return "-314"
  // Build the result one digit at a time.
  // Initial Solution. Time complexity: O(n);
  /*public static String intToString(int x) {

    StringBuilder myString = new StringBuilder();

    boolean isNegative = false;
    long longX = x;

    if(longX == 0)
      return "0";

    if (longX<0){
      isNegative = true;
      longX *= -1;
    }

    while(longX!=0){
      //Get lowest order digit

      long lowestOrderDigit = longX % 10;

      myString.append(lowestOrderDigit);

      longX /= 10;
    }

    if(isNegative)
      myString.append('-');

    return myString.reverse().toString();
  }*/

  // Input "314",output 314
  /*public static int stringToInt(String s) {


    if(s=="0" || s=="+0")
      return 0;

    char[] chars;

    char startChar = s.charAt(0);
    if(startChar == '+' || startChar == '-')
      chars = s.substring(1).toCharArray();
    else
      chars = s.toCharArray();

    int output = 0;

    for(int i = 0; i<chars.length; i++){
      output = output*10+(chars[i]-'0');
    }

    if (startChar == '-')
      output *=-1;

    return output;
  }*/

  // The same solution but with cleaner code
  public static String intToString(int x){
    boolean isNegative = x < 0;

    StringBuilder s = new StringBuilder();
    do {
      s.append(Math.abs(x % 10));
      x /= 10;
    } while(x!=0);

    return s.append(isNegative ? '-' : "").reverse().toString();
  }

  public static int stringToInt(String s){

    return (s.charAt(0) == '-' ? -1 : 1)*
            s.substring((s.charAt(0) == '-' || s.charAt(0) == '+') ? 1 : 0)
                    .chars()
                    .reduce(0, (runningSum, c) -> runningSum*10 + (c - 48));
  }

  @EpiTest(testDataFile = "string_integer_interconversion.tsv")
  public static void wrapper(int x, String s) throws TestFailure {
    if (Integer.parseInt(intToString(x)) != x) {
      throw new TestFailure("Int to string conversion failed");
    }
    if (stringToInt(s) != x) {
      throw new TestFailure("String to int conversion failed");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StringIntegerInterconversion.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
