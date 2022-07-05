package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
public class ThreeSum {
  @EpiTest(testDataFile = "three_sum.tsv")

  public static boolean hasThreeSum(List<Integer> A, int t) {
    Collections.sort(A);
    return A.stream().anyMatch(a -> twoSum(A, t-a));
  }

  // My initial solution
  // Time complexity: O(n^2)
  // Space complexity: O(1)
//  public static boolean hasThreeSum(List<Integer> A, int t) {
//
//    quickSort(A, 0, A.size()-1);
//
//    int newtarg;
//
//    for(int i = 0; i<A.size(); i++){
//      newtarg = t-A.get(i);
//      if(twoSum(A, newtarg)){
//        return true;
//      }
//    }
//    return false;
//  }

  private static boolean twoSum(List<Integer> A, int newtarg) {
    int i = 0, j = A.size()-1;

    while(i <= j){
      if(A.get(i)+A.get(j) == newtarg)
        return true;
      else if(A.get(i) + A.get(j) > newtarg)
        --j;
      else
        ++i;
    }
    return false;
  }
  
  private static void quickSort(List<Integer> A, int lower, int upper){
    if(lower < upper){
      int pivotIndex = partition(A, lower, upper);
      quickSort(A, lower, pivotIndex-1);
      quickSort(A, pivotIndex+1, upper);
    }
  }

  private static int partition(List<Integer> A, int start, int end){
    int pivot = A.get(end);
    int lower = start-1;

    for(int upper = start; upper<end; ++upper){
      if(A.get(upper)<pivot){
        ++lower;
        Collections.swap(A, lower, upper);
      }
    }
    Collections.swap(A, lower+1, end);
    return lower+1;
  }



  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ThreeSum.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
