package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.*;

public class SearchMaze {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Coordinate{
    int x;
    int y;

    public Coordinate(int x, int y){
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o){
      if(this == o)
        return true;

      if(o == null || getClass() != o.getClass())
        return false;

      Coordinate that = (Coordinate)o;
      if(this.x != that.x || this.y != that.y)
        return false;

      return true;
    }
  }

  public enum Color {BLACK, WHITE};

  public static List<Coordinate> searchMaze(List<List<Color>> maze,
                                            Coordinate s, Coordinate e) {
    List<Coordinate> path = new ArrayList<>();
    searchMazeHelper(maze, s, e, path);
    return path;
  }

  private static boolean searchMazeHelper(List<List<Color>> maze, Coordinate s, Coordinate e, List<Coordinate> path) {

    if(s.x < 0 || s.x >= maze.size() || s.y < 0 || s.y >= maze.get(s.x).size()
    || maze.get(s.x).get(s.y) != Color.WHITE){
      return false;
    }

    path.add(s);
    maze.get(s.x).set(s.y, Color.BLACK);
    if(s.equals(e)){
      return true;
    }

    // Search in each direction
    for(Coordinate nextMove : List.of(new Coordinate(s.x, s.y+1),
            new Coordinate(s.x, s.y-1),
            new Coordinate(s.x+1, s.y),
            new Coordinate(s.x-1, s.y))){

      if(searchMazeHelper(maze, nextMove, e, path)){
        return true;
      }
    }

    // Path not found, remove item most recently added to path
    path.remove(path.size()-1);
    return  false;
  }

  public static boolean pathElementIsFeasible(List<List<Integer>> maze,
                                              Coordinate prev, Coordinate cur) {
    if (!(0 <= cur.x && cur.x < maze.size() && 0 <= cur.y &&
          cur.y < maze.get(cur.x).size() && maze.get(cur.x).get(cur.y) == 0)) {
      return false;
    }
    return cur.x == prev.x + 1 && cur.y == prev.y ||
        cur.x == prev.x - 1 && cur.y == prev.y ||
        cur.x == prev.x && cur.y == prev.y + 1 ||
        cur.x == prev.x && cur.y == prev.y - 1;
  }

  @EpiTest(testDataFile = "search_maze.tsv")
  public static boolean searchMazeWrapper(List<List<Integer>> maze,
                                          Coordinate s, Coordinate e)
      throws TestFailure {
    List<List<Color>> colored = new ArrayList<>();
    for (List<Integer> col : maze) {
      List<Color> tmp = new ArrayList<>();
      for (Integer i : col) {
        tmp.add(i == 0 ? Color.WHITE : Color.BLACK);
      }
      colored.add(tmp);
    }
    List<Coordinate> path = searchMaze(colored, s, e);
    if (path.isEmpty()) {
      return s.equals(e);
    }

    if (!path.get(0).equals(s) || !path.get(path.size() - 1).equals(e)) {
      throw new TestFailure("Path doesn't lay between start and end points");
    }

    for (int i = 1; i < path.size(); i++) {
      if (!pathElementIsFeasible(maze, path.get(i - 1), path.get(i))) {
        throw new TestFailure("Path contains invalid segments");
      }
    }

    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchMaze.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
