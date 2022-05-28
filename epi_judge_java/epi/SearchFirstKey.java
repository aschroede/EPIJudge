package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchFirstKey {
  @EpiTest(testDataFile = "search_first_key.tsv")

  // Time complexity: O((logn)^2)
  // Space complexity: O(1)
  /*public static int searchFirstOfK(List<Integer> A, int k) {
    Integer foundKey = -1;
    return searchFirstOfKUtility(A, k, 0, A.size()-1, foundKey);
  }

  public static int searchFirstOfKUtility(List<Integer> A, int k, int Lower, int Upper, Integer Index){

    String testString = "andrew";
    String sorttedString = Stream.of(testString.split("")).sorted().collect(Collectors.joining());

    while(Lower<=Upper){
      int M = Lower + (Upper-Lower)/2;

      if(A.get(M)<k)
        Lower = M+1;
      else if(A.get(M) == k){
        Index = M;
        if(Lower!=Upper)
          Index = searchFirstOfKUtility(A, k, Lower, M-1, Index);
        return Index;
      }
      else
        Upper=M-1;
    }
    return Index;
  }*/

  // Time complexity: O(logn) - each iteration reduces the candidate set by half.
  // Space complexity: O(1)
  public static int searchFirstOfK(List<Integer> A, int k) {
    int Lower = 0, Upper = A.size()-1;
    int returnVal = -1;

    while(Lower<=Upper){
      int M = Lower + (Upper-Lower)/2;

      if(A.get(M)<k)
        Lower = M+1;
      else if(A.get(M) == k){
        returnVal = M;
        Upper = M-1;
      }
      else
        Upper=M-1;
    }

    return returnVal;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchFirstKey.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
