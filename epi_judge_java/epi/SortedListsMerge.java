package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SortedListsMerge {
  @EpiTest(testDataFile = "sorted_lists_merge.tsv")
  //@include

  // Initial attempt. Time Complexity = O(n+m). Space complexity = O(1)
  public static ListNode<Integer> mergeTwoSortedLists(ListNode<Integer> L1,
                                                      ListNode<Integer> L2) {


    ListNode<Integer> head = new ListNode<Integer>(0, null);
    ListNode<Integer> tail = head;

    while(L1 != null & L2 != null){

      if(L1.data <= L2.data){
        tail.next = L1;
        L1 = L1.next;
      }

      else {
        tail.next = L2;
        L2 = L2.next;
      }

      // Don't forget to increment the tail!
      tail = tail.next;
    }
    tail.next = L1 != null ? L1 : L2;
    return head.next;
  }

  // EPI Solution
  /*public static ListNode<Integer> mergeTwoSortedLists(ListNode<Integer> L1,
                                                      ListNode<Integer> L2) {
    ListNode<Integer> dummyHead = new ListNode<>(0, null);
    ListNode<Integer> current = dummyHead;

    while(L1 != null && L2 != null){
      if(L1.data <= L2.data){
        current.next = L1;
        L1 = L1.next;
      } else{
        current.next = L2;
        L2 = L2.next;
      }
      current = current.next;
    }

    current.next = L1 != null ? L1 : L2;
    return dummyHead.next;

  }*/

  public static void main(String[] args) {
    System.exit(
            GenericTest
                    .runFromAnnotations(args, "SortedListsMerge.java",
                            new Object() {}.getClass().getEnclosingClass())
                    .ordinal());
  }
}