package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DutchNationalFlag {
  public enum Color { RED, WHITE, BLUE }

  // Solution 1: O(n)
/*  public static void dutchFlagPartition(int pivotIndex, List<Color> A) {

    int pivot = A.get(pivotIndex).ordinal();
    int i = -1;
    int j = -1;

    for (int k = 0; k<A.size(); k++){
      if(A.get(k).ordinal() < pivot){
        i++;
        Collections.swap(A, i, k);
        j++;
        if(i!=j)
          Collections.swap(A, j, k);
      }

      else if (A.get(k).ordinal() == pivot){
        ++j;
        Collections.swap(A, j, k);
      }
    }
    return;
  }*/

  // Solution 2: O(n)
  // Make four separations in an array - smaller, equal, greater, and unclassified
  // Iterate through the unclassified elements and place unclassified elements into the correct bin based on the following rules
  // An example: A = [-3, 0, -1, 1, 1, ?, ?, ?, 4, 2], Pivot is 1.
  // There are three possibilities for the first unclassified element A[5]:
  // Case 1: A[5] = -5 and is less than the pivot, in this case exchange it with the first item in the equal bucket (1) so A = [-3, 0, -1, -5, 1, 1, ?, ?, 4, 2]
  // Case 2: A[5] = 1 and is equal to the pivot, in this case do nothing and move onto the next item, A = [-3, 0, -1, 1, 1, 1, ?, ?, 4, 2]
  // case 3: A[5] = 4 and is greater than the pivot, in this case exchange it with the last unclassified element, A = [-3, 0, -1, 1, 1, ?, ?, 4, 4, 2]
  /*public static void dutchFlagPartition(int pivotIndex, List<Color> A){

    int pivot = A.get(pivotIndex).ordinal();
    int LessThan_Boundary=0;
    int EqualTo_Boundary=0;
    int GreaterThan_Boundary=A.size()-1;


      while(EqualTo_Boundary < GreaterThan_Boundary+1){
        // Case 1
        if(A.get(EqualTo_Boundary).ordinal() < pivot){
          Collections.swap(A, EqualTo_Boundary, LessThan_Boundary);
          ++LessThan_Boundary;
          ++EqualTo_Boundary;
        }

        // Case 3
        else if(A.get(EqualTo_Boundary).ordinal() > pivot){
          Collections.swap(A, EqualTo_Boundary, GreaterThan_Boundary);
          --GreaterThan_Boundary;
        }

        else if(A.get(EqualTo_Boundary).ordinal() == pivot)
          ++EqualTo_Boundary;
      }

    return;
  }*/

  // Solution 3

  public static void dutchFlagPartition(int pivotIndex, List<Color> A){
    int pivot = A.get(pivotIndex).ordinal();
    int smaller =0, equal =0, larger = A.size();

    while (equal < larger){
      if(A.get(equal).ordinal() < pivot)
        Collections.swap(A, smaller++, equal++);
      else if (A.get(equal).ordinal() == pivot)
        ++equal;
      else
        Collections.swap(A, equal, --larger);
    }

    return;
  }

  @EpiTest(testDataFile = "dutch_national_flag.tsv")
  public static void dutchFlagPartitionWrapper(TimedExecutor executor,
                                               List<Integer> A, int pivotIdx)
      throws Exception {
    List<Color> colors = new ArrayList<>();
    int[] count = new int[3];
    Color[] C = Color.values();
    for (int i = 0; i < A.size(); i++) {
      count[A.get(i)]++;
      colors.add(C[A.get(i)]);
    }

    Color pivot = colors.get(pivotIdx);
    executor.run(() -> dutchFlagPartition(pivotIdx, colors));

    int i = 0;
    while (i < colors.size() && colors.get(i).ordinal() < pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() == pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() > pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    if (i != colors.size()) {
      throw new TestFailure("Not partitioned after " + Integer.toString(i) +
                            "th element");
    } else if (count[0] != 0 || count[1] != 0 || count[2] != 0) {
      throw new TestFailure("Some elements are missing from original array");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DutchNationalFlag.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
