package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
public class SpiralOrdering {
  @EpiTest(testDataFile = "spiral_ordering.tsv")

  // EPI Solution 2
  // O(n^2) TC
  // O(1) SC
  public static List<Integer> matrixInSpiralOrder(List<List<Integer>> squareMatrix){

    final int[][] SHIFT = {{0,1}, {1, 0}, {0, -1}, {-1, 0}};
    int dir = 0, x = 0, y = 0;
    List<Integer> spiralOrdering = new ArrayList<>();

    for (int i = 0; i< squareMatrix.size()*squareMatrix.size(); ++i){

      // Add the latest element
      spiralOrdering.add(squareMatrix.get(x).get(y));

      // Set the latest element to 0 to show we've already processed it
      squareMatrix.get(x).set(y, 0);

      // Keep moving in the current direction
      int nextX = x + SHIFT[dir][0], nextY = y + SHIFT[dir][1];

      // If the next elements are out of bounds, or it is
      // one we have already processed then change direction
      if(nextX<0 || nextX>=squareMatrix.size() || nextY<0 || nextY>= squareMatrix.size()||
      squareMatrix.get(nextX).get(nextY) == 0){
        dir = (dir+1)%4;
        nextX = x + SHIFT[dir][0];
        nextY = y + SHIFT[dir][1];
      }

      // Otherwise, continue moving in the same direction
      x = nextX;
      y = nextY;
    }
    return spiralOrdering;
  }

  // EPI Solution 1
  // O(n^2) TC
  // O(1) SC
//  public static List<Integer> matrixInSpiralOrder(List<List<Integer>> squareMatrix){
//
//    List<Integer> spiralOrdering = new ArrayList<>();
//
//    for(int offset =0; offset < Math.ceil(0.5 * squareMatrix.size()); ++offset){
//      matrixLayerInClockwise(squareMatrix, offset, spiralOrdering);
//    }
//    return spiralOrdering;
//  }
//
//  private static void matrixLayerInClockwise(List<List<Integer>> squareMatrix, int offset, List<Integer> spiralOrdering) {
//
//    if(offset == squareMatrix.size()-1-offset){
//      // squareMatrix has odd dimension, and we are at its center
//      spiralOrdering.add(squareMatrix.get(offset).get(offset));
//    }
//
//    for(int j = offset; j<squareMatrix.size() - offset - 1; ++j){
//      spiralOrdering.add(squareMatrix.get(offset).get(j));
//    }
//
//    for(int i = offset; i<squareMatrix.size()-offset-1; ++i){
//      spiralOrdering.add(squareMatrix.get(i).get(squareMatrix.size()-offset-1));
//    }
//
//    for(int j = squareMatrix.size()-offset-1; j>offset; --j){
//      spiralOrdering.add(squareMatrix.get(squareMatrix.size()-offset-1).get(j));
//    }
//
//    for(int i = squareMatrix.size()-offset-1; i>offset; --i){
//      spiralOrdering.add(squareMatrix.get(i).get(offset));
//    }
//  }

  // My solution
  // O(n^2) TC
  // O(1) SC
  /*public static List<Integer> matrixInSpiralOrder(List<List<Integer>> squareMatrix) {
    List<Integer> result = new ArrayList<>();

    direction dir = direction.right;

    // Left, top, right, bottom
    int[] bounds = {0, 0, squareMatrix.size()-1, squareMatrix.size()-1};

    helperFunction(squareMatrix, dir, bounds, result);

    return result;
  }

  public enum direction{
    right,
    down,
    left,
    up
  }

  public static void helperFunction(List<List<Integer>> A, direction dir, int[] bounds, List<Integer> result){

    if(bounds[0] <= bounds[2] && bounds[1] <= bounds[3]){

      if(dir == direction.right){
        for(int i=bounds[0]; i<=bounds[2]; i++){
          result.add(A.get(bounds[1]).get(i));
        }
        dir = direction.down;
        bounds[1] += 1;
      }

      else if(dir == direction.down){
        for(int i=bounds[1]; i<=bounds[3]; i++){
          result.add(A.get(i).get(bounds[2]));
        }
        dir = direction.left;
        bounds[2] -= 1;
      }

      else if(dir == direction.left){
        for(int i=bounds[2]; i>=bounds[0]; i--){
          result.add(A.get(bounds[3]).get(i));
        }
        dir = direction.up;
        bounds[3] -= 1;
      }

      else if(dir == direction.up){
        for(int i=bounds[3]; i>=bounds[1]; i--){
          result.add(A.get(i).get(bounds[0]));
        }
        dir = direction.right;
        bounds[0] += 1;
      }

      helperFunction(A, dir, bounds, result);
    }
  }*/


  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SpiralOrdering.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
