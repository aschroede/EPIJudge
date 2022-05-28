package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

public class SortedArraysMerge {
  @EpiTest(testDataFile = "sorted_arrays_merge.tsv")


  // Solution 1: Brute Force with Heap
  // Time Complexity: O(nlogn)
  // Space Complexity: O(n)
  /*public static List<Integer>
  mergeSortedArrays(List<List<Integer>> sortedArrays) {

    List<Integer> mergedList = new ArrayList<>();
    PriorityQueue<Integer> minPriorityQueue = new PriorityQueue<>();

    for (List list : sortedArrays){
      minPriorityQueue.addAll(list);
    }

    while(!minPriorityQueue.isEmpty()){
      mergedList.add(minPriorityQueue.poll());
    }
    return mergedList;
  }*/

  // Solution 2: Smarter Use of Heap
  // Time complexity: O(nlog(k))
  // Space complexity: O(k)
  /*public static List<Integer>
  mergeSortedArrays(List<List<Integer>> sortedArrays) {

    List<Integer> mergedList = new ArrayList<>();
    int numSequences = sortedArrays.size();
    PriorityQueue<keyWithSource> minPriorityQueue = new PriorityQueue<>(numSequences, (n1, n2) -> Integer.compare(n1.data, n2.data));

    // Initialize min-priority queue with the smallest values from each sequence.
    for(int i=0; i<sortedArrays.size(); i++){
      if(sortedArrays.get(i).size() != 0){
        keyWithSource newKey = new keyWithSource(sortedArrays.get(i).get(0), i, 0);
        minPriorityQueue.add(newKey);
      }
    }

    while(!minPriorityQueue.isEmpty()){

      // Get the smallest value from the queue and add to return variable;
      keyWithSource extractedKey = minPriorityQueue.poll();
      mergedList.add(extractedKey.data);

      if(extractedKey.sourceSequenceIndex+1 < sortedArrays.get(extractedKey.sourceSequence).size()){

        // Create a new key that is based on the same sequence as the one that was just extracted but with the next value in the sequence.
        keyWithSource newKey = new keyWithSource(sortedArrays.get(extractedKey.sourceSequence).get(extractedKey.sourceSequenceIndex+1),
                extractedKey.sourceSequence, extractedKey.sourceSequenceIndex+1);

        // Add new key to the queue
        minPriorityQueue.add(newKey);
      }
    }

    return mergedList;
  }

  private static class keyWithSource{
    public int data;
    public int sourceSequence;
    public int sourceSequenceIndex;

    public keyWithSource(int data, int sourceSequence, int sourceSequenceIndex){
      this.data = data;
      this.sourceSequence = sourceSequence;
      this.sourceSequenceIndex = sourceSequenceIndex;
    }
  }*/

  public static List<Integer>
  mergeSortedArrays(List<List<Integer>> sortedArrays) {

    List<Iterator<Integer>> iters = new ArrayList<>(sortedArrays.size());
    for(List<Integer> array : sortedArrays){
      iters.add((array.iterator()));
    }

    PriorityQueue<ArrayEntry> minHeap = new PriorityQueue<>(sortedArrays.size(), (o1, o2) -> Integer.compare(o1.value, o2.value));
    for(int i = 0; i<iters.size(); ++i){
      if(iters.get(i).hasNext()){
        minHeap.add((new ArrayEntry(iters.get(i).next(), i)));
      }
    }

    List<Integer> result = new ArrayList<>();
    while(!minHeap.isEmpty()){
      ArrayEntry headEntry = minHeap.poll();
      result.add(headEntry.value);
      if(iters.get(headEntry.arrayID).hasNext()){
        minHeap.add(new ArrayEntry(iters.get(headEntry.arrayID).next(), headEntry.arrayID));
      }
    }
    return  result;
  }

  private static class ArrayEntry{
    public Integer value;
    public Integer arrayID;

    public ArrayEntry(Integer value, Integer arrayID){
      this.value = value;
      this.arrayID = arrayID;
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortedArraysMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
