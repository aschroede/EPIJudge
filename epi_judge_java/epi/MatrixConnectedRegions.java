package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.*;

public class MatrixConnectedRegions {

  // My DFS solution.
  // TC: O(V+E)
  // SC: O(V) implicit in the call stack??

//  public static void flipColor(int x, int y, List<List<Boolean>> image) {
//    boolean originalVal = image.get(x).get(y);
//    Coordinate start = new Coordinate(x, y);
//    dFS(image, start, originalVal);
//  }
//
//  private static void dFS(List<List<Boolean>> image, Coordinate start, boolean originalVal) {
//    if(start.x < 0 || start.x >= image.size() || start.y < 0 || start.y >= image.get(start.x).size() ||
//            image.get(start.x).get(start.y) != originalVal){
//      return;
//    }
//
//    image.get(start.x).set(start.y, !originalVal);
//
//    for(Coordinate next : Arrays.asList(new Coordinate(start.x+1, start.y),
//            new Coordinate(start.x-1, start.y),
//            new Coordinate(start.x, start.y+1),
//            new Coordinate(start.x, start.y-1))){
//      dFS(image, next, originalVal);
//    }
//  }

  // EPI DFS Solution
//  public static void flipColor(int x, int y, List<List<Boolean>> image) {
//
//    // Get color of original node
//    boolean color = image.get(x).get(y);
//
//    // Flip the color
//    image.get(x).set(y, !color);
//
//    // Visit the neighbor nodes
//    for(Coordinate next : Arrays.asList(
//            new Coordinate(x+1, y),
//            new Coordinate(x-1, y),
//            new Coordinate(x, y+1),
//            new Coordinate(x, y-1))){
//
//      if(next.x >= 0 && next.x < image.size() && next.y >= 0 && next.y < image.get(next.x).size() &&
//              image.get(next.x).get(next.y) == color){
//        flipColor(next.x, next.y, image);
//      }
//    }
//  }

  // Andrew BFS Solution
  public static void flipColor(int x, int y, List<List<Boolean>> image) {

    // Get color of original node
    boolean color = image.get(x).get(y);

    Coordinate start = new Coordinate(x, y);
    Deque<Coordinate> queue = new ArrayDeque<Coordinate>();
    queue.addLast(start);

    while(!queue.isEmpty()){
      Coordinate item = queue.removeFirst();
      image.get(item.x).set(item.y, !color);

      for(Coordinate next : Arrays.asList(
              new Coordinate(item.x+1, item.y),
              new Coordinate(item.x-1, item.y),
              new Coordinate(item.x, item.y+1),
              new Coordinate(item.x, item.y-1))){

        if(next.x >= 0 && next.x < image.size() && next.y >= 0 && next.y < image.get(next.x).size() &&
             image.get(next.x).get(next.y) == color){
          queue.addLast(next);
        }
      }
    }
  }

  public static class Coordinate{

    public int x;
    public int y;

    public Coordinate(int x, int y){
      this.x = x;
      this.y = y;
    }
  }

  @EpiTest(testDataFile = "painting.tsv")
  public static List<List<Integer>> flipColorWrapper(TimedExecutor executor,
                                                     int x, int y,
                                                     List<List<Integer>> image)
      throws Exception {
    List<List<Boolean>> B = new ArrayList<>();
    for (int i = 0; i < image.size(); i++) {
      B.add(new ArrayList<>());
      for (int j = 0; j < image.get(i).size(); j++) {
        B.get(i).add(image.get(i).get(j) == 1);
      }
    }

    executor.run(() -> flipColor(x, y, B));

    image = new ArrayList<>();
    for (int i = 0; i < B.size(); i++) {
      image.add(new ArrayList<>());
      for (int j = 0; j < B.get(i).size(); j++) {
        image.get(i).add(B.get(i).get(j) ? 1 : 0);
      }
    }

    return image;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MatrixConnectedRegions.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
