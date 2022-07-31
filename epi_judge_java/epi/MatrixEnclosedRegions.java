package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

public class MatrixEnclosedRegions {

  // Andrew's Original Solution
  // Time complexity: O(nm)
  // Space complexity: O(nm) - because we make a copy of the board to work with
  public static void fillSurroundedRegions(List<List<Character>> board) {
    List<List<Square>> sboard = new ArrayList<>();

    // Copy the board over into the sboard
    for(int i=0; i<board.size(); ++i){
      sboard.add(new ArrayList<Square>());
      for(int j=0; j<board.get(i).size(); ++j){
        sboard.get(i).add(new Square(Color.WHITE, board.get(i).get(j), i, j));
      }
    }

    // Iterate over the board and do DFS
    for(int i=0; i<sboard.size(); ++i){
      for(int j=0; j<sboard.get(i).size(); ++j){
        if(sboard.get(i).get(j).ch == 'W' && sboard.get(i).get(j).color == Color.WHITE){
          List<Square> flipList = new ArrayList<>();
          DFS(sboard, sboard.get(i).get(j), flipList);

          boolean flip = true;

          for(Square square : flipList){
            if(square.x == 0 || square.x == sboard.size()-1 || square.y ==0 || square.y == sboard.get(square.x).size()-1){
              flip = false;
              break;
            }
          }

          if(flip){
            for(Square square : flipList){
              square.ch = 'B';
            }
          }
        }
      }
    }

    // Copy sboard back into board
    for(int i=0; i<sboard.size(); ++i){
      for(int j=0; j<sboard.get(i).size(); ++j){
        board.get(i).set(j, sboard.get(i).get(j).ch);
      }
    }
  }

  private static void DFS(List<List<Square>> sboard, Square startSquare, List<Square> flipList) {

    Deque<Square> stack = new ArrayDeque<>();
    startSquare.color = Color.GRAY;
    stack.addFirst(startSquare);
    flipList.add(startSquare);

    while(!stack.isEmpty()){
      Square square = stack.removeFirst();
      for(Coordinate next : Arrays.asList(
              new Coordinate(square.x+1, square.y),
              new Coordinate(square.x-1, square.y),
              new Coordinate(square.x, square.y+1),
              new Coordinate(square.x, square.y-1))){

        if(next.x >= 0 && next.x < sboard.size() && next.y >=0 && next.y < sboard.get(square.x).size()){
          Square nextSquare = sboard.get(next.x).get(next.y);
          if(nextSquare.color == Color.WHITE && nextSquare.ch == 'W'){
            nextSquare.color = Color.GRAY;
            flipList.add(nextSquare);
            stack.addFirst(nextSquare);
          }
        }
      }
    }
  }

  private static void printSboard(List<List<Square>> sboard) {

    System.out.println();
    for(int i=0; i<sboard.size(); ++i){
      for(int j=0; j<sboard.get(i).size(); ++j){
        System.out.print(sboard.get(i).get(j).ch + ", ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public enum Color{
    WHITE,
    GRAY,
    BLACK
  }

  public static class Square{
    Color color;
    Character ch;
    int x, y;

    Square(Color color, Character ch, int x, int y){
      this.color = color;
      this.ch = ch;
      this.x = x;
      this.y = y;
    }
  }

  public static class Coordinate{
    int x, y;

    Coordinate(int x, int y){
      this.x = x;
      this.y = y;
    }
  }

  // EPI Solution
  // Time complexity: O(nm)
  // Space complexity: O(nm)
  // Where n and m are the number of rows and columns in the board.
//  public static void fillSurroundedRegions(List<List<Character>> board) {
//
//    // Identifies the regions that are reachable via white path starting from the
//    // first or last columns
//    for(int i = 0; i< board.size(); ++i){
//      markBoundaryRegion(i, /*first column*/0, board);
//      markBoundaryRegion(i, /*last column*/board.get(i).size()-1, board);
//    }
//
//    // Identifies the regions that are reachable via white path starting from the
//    // first or last rows
//    for(int i = 0; i< board.get(0).size(); ++i){
//      markBoundaryRegion(0, i, board);
//      markBoundaryRegion(board.size()-1, i, board);
//    }
//
//    // Marks the surrounded white regions as black. All the white items that could reach an edge
//    // were labelled as 'T'. This means that the marked ones should be recolored to 'W' and any other
//    // should be labelled as black. Because the white ones that could not reach an edge are surrounded
//    // and need to be recolored.
//    for(int i = 0; i< board.size(); ++i){
//      for(int j = 0; j<board.get(i).size(); ++j){
//        board.get(i).set(j, board.get(i).get(j)=='T' ? 'W' : 'B');
//      }
//    }
//  }
//
//  private static void markBoundaryRegion(int i, int j, List<List<Character>> board){
//    Queue<Coordinate> q = new ArrayDeque<>();
//    q.add(new Coordinate(i,j));
//
//    //Uses BFS tyo traverse this region.
//    while(!q.isEmpty()){
//      Coordinate curr = q.remove();
//      if(curr.x >= 0 && curr.x < board.size() &&
//              curr.y>=0 && curr.y<board.get(curr.x).size()
//              && board.get(curr.x).get(curr.y) == 'W'){
//
//        board.get(curr.x).set(curr.y, 'T');
//
//        // Add all neighbors to queue
//        q.add(new Coordinate(curr.x-1, curr.y));
//        q.add(new Coordinate(curr.x+1, curr.y));
//        q.add(new Coordinate(curr.x, curr.y-1));
//        q.add(new Coordinate(curr.x, curr.y+1));
//      }
//    }
//  }



  @EpiTest(testDataFile = "matrix_enclosed_regions.tsv")
  public static List<List<Character>>
  fillSurroundedRegionsWrapper(List<List<Character>> board) {
    fillSurroundedRegions(board);
    return board;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MatrixEnclosedRegions.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
