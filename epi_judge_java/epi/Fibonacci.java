package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

  @EpiTest(testDataFile = "fibonacci.tsv")

  // Strategy 1: No Memoization
//  public static int fibonacci(int n) {
//    if(n<2)
//      return n;
//    return fibonacci(n-1)+fibonacci(n-2);
//
//  }

  // Strategy 2: With Memoization
  // Number of operations will be the number of numbers generated so
  // Time complexity: O(n)
  // Space complexity: O(n)
//  public static int fibonacci(int n) {
//
//    if(n<2)
//      return n;
//    fibMap.putIfAbsent(n, fibonacci(n-1)+fibonacci(n-2));
//    return fibMap.get(n);
//  }


  // Time complexity O(n)
  // Space complexity O(1)
  // Strategy 3: Iterative approach to save space
  // Fills in cache with a bottom-up strategy.
  public static int fibonacci(int n){
    if(n<2)
      return n;

    int minus1 = 1;
    int minus2 = 0;
    int f = 0;

    for(int i = 2; i<=n; i++){
      f = minus1+minus2;
      minus2 = minus1;
      minus1 = f;
    }

    return f;
  }

  public static Map<Integer, Integer> fibMap = new HashMap<>();

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Fibonacci.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
