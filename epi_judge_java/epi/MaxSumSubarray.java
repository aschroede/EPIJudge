package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class MaxSumSubarray {
  @EpiTest(testDataFile = "max_sum_subarray.tsv")

  // Divide and conquer method
  // 1. Divide the array evenly
  // 2. Find the maximum sub-array of both divided portions
  // 3. Combine by determining if the left, right or a combination is the largest.
  // Time complexity: O(nlgn)
  // Space complexity: O(lgn) for space implicitly allocated to call stack.
//  public static int findMaximumSubarray(List<Integer> A) {
//    if(A == null || A.size() ==0)
//      return 0;
//    return findMaximumSubarrayUtility(A, 0, A.size()-1);
//  }

//

  // Way better solution
  // Time complexity: O(n)
  // Space complexity: O(1)
  public static int findMaxSubarray(List<Integer> A){
    int maxEnd = 0;
    int maxSeen = 0;

    for(Integer i : A){
      maxEnd = Math.max(i, i+maxEnd);
      maxSeen = Math.max(maxSeen, maxEnd);
    }

    return maxSeen;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MaxSumSubarray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
