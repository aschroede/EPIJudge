package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class PowerXY {
  @EpiTest(testDataFile = "power_x_y.tsv")
  // O(2^n) where n is the number of bits in y
//  public static double power(double x, int y) {
//
//    double result = 1;
//    boolean expNeg = y < 0 ? true : false;
//
//    for(int i = 0; i<Math.abs(y); ++i){
//      result *= x;
//    }
//
//    if(expNeg)
//      result = 1/result;
//
//    return result;
//  }

  // O(n) where n is the number of bits in y
  public static double power(double x, int y) {

    double result = 1;
    int power = y;

    if(power<0){
      power *= -1;
      x = 1/x;
    }

    while(power!=0){

      if((power&1)==1){
        result *= x;
      }

      x *= x;
      power >>= 1;
    }

    return  result;
  }


  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PowerXY.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
