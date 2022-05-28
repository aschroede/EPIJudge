package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

public class IntersectSortedArrays {
  @EpiTest(testDataFile = "intersect_sorted_arrays.tsv")

  // Brute Force Method - Don't utilize the fact that the arrays are sorted.
  // Time complexity: O(n^2)
  // Space complexity: O(k) where k is the number of distinct elements in the intersection.
/*  public static List<Integer> intersectTwoSortedArrays(List<Integer> A, List<Integer> B) {

    A.retainAll(B); // O(n^2)
    Set<Integer> intersection = new HashSet<Integer>(A);
    A.clear();
    A.addAll(intersection);
    Collections.sort(A);
    return A;

  }*/

  // Better Method using Binary Search - utilize the fact that one array is sorted.
  // Time complexity: O(klogn) where n is the number of elements in the longer list, and k
  // is the number of distinct elements in the shorter list.
  // Space complexity: O(m) where m is the number of elements in the intersection
  /*public static List<Integer> intersectTwoSortedArrays(List<Integer> A, List<Integer> B) {


    List<Integer> intersection = new ArrayList<>();

    List<Integer> shorterList;
    List<Integer> longerList;

    // Get shorter list
    if(A.size() < B.size()){
      shorterList = A;
      longerList = B;
    }
    else{
      shorterList = B;
      longerList = A;
    }

    HashSet<Integer> shorterSet = new HashSet<>(shorterList);  // O(n)

    // Iterate over shorter list
    for(Integer i : shorterSet){
      if(Collections.binarySearch(longerList, i) >= 0){
        intersection.add(i);
      }
    }

    Collections.sort(intersection);
    return intersection;
  }*/

  // Optimal method in linear time. Utilize the fact that BOTH arrays are sorted.
  // Time complexity: O(n+m)
  // Space complexity: O(k) where k is the number of unique elements in the intersection.
  /*public static List<Integer> intersectTwoSortedArrays(List<Integer> A, List<Integer> B) {
    List<Integer> result = new ArrayList<>();

    int i = 0;
    int j = 0;

    while(i < A.size() && j < B.size()){
      if(A.get(i) < B.get(j))
        ++i;
      else if(B.get(j) < A.get(i))
        ++j;
      else {
        if (i > 0 && j > 0 && !A.get(i).equals(A.get(i - 1)) && !B.get(j).equals(B.get(j - 1))) {
          result.add(A.get(i));
        } else if (i > 0 && j == 0 && !A.get(i).equals(A.get(i - 1))) {
          result.add(A.get(i));
        } else if(j > 0 && i ==0 && !B.get(j).equals(B.get(j-1))) {
          result.add(A.get(i));
        } else if(j==0 && i==0)
          result.add(A.get(i));
        ++i;
        ++j;
      }
    }

    return result;
  }*/

  // EPI Solution
  public static List<Integer> intersectTwoSortedArrays(List<Integer> A, List<Integer> B) {

    List<Integer> intersectionAB = new ArrayList<>();
    int i = 0, j = 0;
    while(i < A.size() && j < B.size()){

      // If i == 0, the second expression short-circuits on the check that i == 0 and does not try
      // to evaluate the second portion of the second expression: A.get(i-1).
      if(A.get(i).equals(B.get(j)) && (i == 0 || !A.get(i).equals((A.get(i-1))))){
        intersectionAB.add(A.get(i));
        ++j;
        ++i;
      }
      else if (A.get(i) < B.get(j)){
        ++i;
      }
      else{
        ++j;
      }
    }
    return intersectionAB;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntersectSortedArrays.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
