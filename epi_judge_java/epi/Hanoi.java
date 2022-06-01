package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hanoi {

  private static final int NUM_PEGS = 3;

  public static List<List<Integer>> computeTowerHanoi(int numRings) {
    List<Deque<Integer>> pegs = IntStream.range(0, NUM_PEGS).mapToObj(i-> new ArrayDeque<Integer>()).collect(Collectors.toList());
    List<List<Integer>> result = new ArrayList<>();

    // Initialize the first peg
    for(int i=numRings; i>0; i--){
      pegs.get(0).addFirst(i);
    }

    int from = 0, to = 1, using = 2;
    computeHanoiOperations(result, pegs, from, to, using, numRings);

    return result;
  }

  private static void computeHanoiOperations(List<List<Integer>> result, List<Deque<Integer>> pegs, int from, int to, int using, int numRings) {

    if(numRings > 0){
      // Step 1: Move all but the last ring from peg 1 to peg 3 using 2
      computeHanoiOperations(result, pegs, from, using, to, numRings-1);

      // Step 2: Move the last ring from 1 to 2, add operation to result
      pegs.get(to).addFirst(pegs.get(from).getFirst());
      result.add(List.of(from, to));

      // Step 3: Move the remaining rings from 3 to 2 using 1
      computeHanoiOperations(result, pegs, using, to, from, numRings-1);
    }
  }

  @EpiTest(testDataFile = "hanoi.tsv")
  public static void computeTowerHanoiWrapper(TimedExecutor executor,
                                              int numRings) throws Exception {
    List<Deque<Integer>> pegs = new ArrayList<>();
    for (int i = 0; i < NUM_PEGS; i++) {
      pegs.add(new LinkedList<>());
    }
    for (int i = numRings; i >= 1; --i) {
      pegs.get(0).addFirst(i);
    }

    List<List<Integer>> result =
        executor.run(() -> computeTowerHanoi(numRings));

    for (List<Integer> operation : result) {
      int fromPeg = operation.get(0);
      int toPeg = operation.get(1);
      if (!pegs.get(toPeg).isEmpty() &&
          pegs.get(fromPeg).getFirst() >= pegs.get(toPeg).getFirst()) {
        throw new TestFailure("Illegal move from " +
                              String.valueOf(pegs.get(fromPeg).getFirst()) +
                              " to " +
                              String.valueOf(pegs.get(toPeg).getFirst()));
      }
      pegs.get(toPeg).addFirst(pegs.get(fromPeg).removeFirst());
    }

    List<Deque<Integer>> expectedPegs1 = new ArrayList<>();
    for (int i = 0; i < NUM_PEGS; i++) {
      expectedPegs1.add(new LinkedList<Integer>());
    }
    for (int i = numRings; i >= 1; --i) {
      expectedPegs1.get(1).addFirst(i);
    }

    List<Deque<Integer>> expectedPegs2 = new ArrayList<>();
    for (int i = 0; i < NUM_PEGS; i++) {
      expectedPegs2.add(new LinkedList<Integer>());
    }
    for (int i = numRings; i >= 1; --i) {
      expectedPegs2.get(2).addFirst(i);
    }
    if (!pegs.equals(expectedPegs1) && !pegs.equals(expectedPegs2)) {
      throw new TestFailure("Pegs doesn't place in the right configuration");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Hanoi.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
