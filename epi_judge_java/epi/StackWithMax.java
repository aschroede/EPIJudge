package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.*;

public class StackWithMax {

  public static class Stack {
    DoublyListNode<Integer> head = new DoublyListNode<>(0, null, null);
    DoublyListNode<Integer> tail = head;

    List<Integer> maxNumbersList = new ArrayList<Integer>();

    public boolean empty() {
      return tail == head;
    }
    public Integer max() {
      return maxNumbersList.get(maxNumbersList.size()-1);
    }
    public Integer pop() {
      int popVal = tail.data;
      tail = tail.prev;
      maxNumbersList.remove(maxNumbersList.size()-1);
      return popVal;
    }
    public void push(Integer x) {
      DoublyListNode<Integer> temp = tail;
      tail.next = new DoublyListNode<>(x, null, tail);
      tail = tail.next;
      tail.prev = temp;

      if(maxNumbersList.isEmpty())
        maxNumbersList.add(x);
      else
        maxNumbersList.add(Math.max(x, maxNumbersList.get(maxNumbersList.size()-1)));
    }
  }

  /*private static class ElementWithCachedMax{
    public Integer element;
    public Integer max;

    public ElementWithCachedMax(Integer element, Integer max){
      this.element = element;
      this.max = max;
    }
  }

  public static class Stack{

    private Deque<ElementWithCachedMax> elementWithCachedMax = new ArrayDeque<>();

    public boolean empty(){
      return elementWithCachedMax.isEmpty();
    }

    public Integer max(){
      return elementWithCachedMax.peek().max;
    }

    public Integer pop(){
      return elementWithCachedMax.removeFirst().element;
    }

    public void push(Integer x){
      elementWithCachedMax.addFirst(new ElementWithCachedMax(x, Math.max(x, empty() ? x : max())));
    }
  }*/


  @EpiUserType(ctorParams = {String.class, int.class})
  public static class StackOp {
    public String op;
    public int arg;

    public StackOp(String op, int arg) {
      this.op = op;
      this.arg = arg;
    }
  }

  @EpiTest(testDataFile = "stack_with_max.tsv")
  public static void stackTester(List<StackOp> ops) throws TestFailure {
    try {
      Stack s = new Stack();
      int result;
      for (StackOp op : ops) {
        switch (op.op) {
        case "Stack":
          s = new Stack();
          break;
        case "push":
          s.push(op.arg);
          break;
        case "pop":
          result = s.pop();
          if (result != op.arg) {
            throw new TestFailure("Pop: expected " + String.valueOf(op.arg) +
                                  ", got " + String.valueOf(result));
          }
          break;
        case "max":
          result = s.max();
          if (result != op.arg) {
            throw new TestFailure("Max: expected " + String.valueOf(op.arg) +
                                  ", got " + String.valueOf(result));
          }
          break;
        case "empty":
          result = s.empty() ? 1 : 0;
          if (result != op.arg) {
            throw new TestFailure("Empty: expected " + String.valueOf(op.arg) +
                                  ", got " + String.valueOf(s));
          }
          break;
        default:
          throw new RuntimeException("Unsupported stack operation: " + op.op);
        }
      }
    } catch (NoSuchElementException e) {
      throw new TestFailure("Unexpected NoSuchElement exception");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StackWithMax.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
