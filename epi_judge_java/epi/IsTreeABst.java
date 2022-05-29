package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class IsTreeABst {
  @EpiTest(testDataFile = "is_tree_a_bst.tsv")

  // For each node, check that its left child is less than it, and its right child is greater than it
  // Additionally, check that the maximum element in the left subtree is less than the node, and the
  // minimum element in the right subtree is greater than it.
  // Recursive strategy
  // Time complexity: O(logn^2)
  // Space complexity: O(1)
  /*public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
    // Null case
    if(tree == null)
      return true;

    // Base case
    if(tree.left == null && tree.right == null)
      return true;

    // Divide and conquer
    boolean isLeftTreeABst = isBinaryTreeBST(tree.left);
    boolean isRightTreeABst = isBinaryTreeBST(tree.right);


    Integer maxInLeftSubTree = null, minInRightSubtree = null;
    BinaryTreeNode<Integer> node;

    // Find maximum value in left subtree: O(logn)
    if(tree.left!=null){
      node = tree.left;
      maxInLeftSubTree = node.data;
      while(node.right != null){
        node = node.right;
        maxInLeftSubTree = node.data;
      }
    }

    // Find minimum value in right subtree
    if(tree.right!=null){
      node = tree.right;
      minInRightSubtree = node.data;
      while(node.left != null){
        node = node.left;
        minInRightSubtree = node.data;
      }
    }

    // Combine solutions.
    if(maxInLeftSubTree != null && minInRightSubtree !=null)
      return isLeftTreeABst && isRightTreeABst && maxInLeftSubTree <= tree.data && minInRightSubtree >= tree.data;
    else if(minInRightSubtree == null && maxInLeftSubTree != null)
      return isLeftTreeABst && isRightTreeABst && maxInLeftSubTree <= tree.data;
    else
      return isLeftTreeABst && isRightTreeABst && minInRightSubtree >= tree.data;
  }*/


  // Method 2: Using Ranges
  // Example
  //             19
  //          __/ \__
  //         7       43
  //       /  \    /   \
  //      3   11  23   47
  //     / \   \   \    \
  //    2  5   17   37   53
  //           /   /  \
  //          13  29  41
  //               \
  //               31

  // For subtree rooted at 7, the range is [-infinity, 19]
  // Rooted at 11, [7, 19]
  // Rooted at 17, [11, 17]
  // Rooted at 37, [23, 43]


  // Time complexity: O(n)
  // Space complexity: O(h) because of space implicitly allocated to the call stack.
  // The stack can get h deep with this recursive function.
  // Note that you can pass the upper and lower via arguments, or you can store that info
  // as part of each node and just pass a node as an argument.
  /*public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
    return isBinaryTreeBSTUtility(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }*/

  /*public static boolean isBinaryTreeBSTUtility(BinaryTreeNode<Integer> tree, int lower, int upper){

    if(tree == null)
      return true;

    if(tree.data >= lower && tree.data <= upper){
      return isBinaryTreeBSTUtility(tree.left, lower, tree.data) &&
      isBinaryTreeBSTUtility(tree.right, tree.data, upper);
    }

    else
      return false;
  }*/

  // Method 3: Using an inorder traversal of the tree.
  // Time complexity: O(n)
  // Space complexity: O(n)
  /*public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
    List<Integer> inorderTraversal = getInorderTraversal(tree);

    for(int i = 1; i<inorderTraversal.size(); i++){
      if(inorderTraversal.get(i) < inorderTraversal.get(i-1))
        return false;
    }
    return true;
  }*/

  /*public static List<Integer> getInorderTraversal(BinaryTreeNode<Integer> tree){

    List<Integer> inorderTraversal = new ArrayList<>();

    if(tree == null)
      return inorderTraversal;

    inorderTraversal.addAll(getInorderTraversal(tree.left));
    inorderTraversal.add(tree.data);
    inorderTraversal.addAll(getInorderTraversal(tree.right));

    return inorderTraversal;
  }*/

  // Method 4: Using a queue and the interval ranges to provide better best case performance
  // than method 3. Using the queue, we do a breadth first search so that if there is a
  // BST property violation early in the right subtree, it is caught sooner than in method 3
  // where we first have to check the entire left subtree before checking the right subtree.
  // Time complexity: O(n)
  // Space complexity: O(n)
  public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {

    if(tree == null)
      return true;

    PriorityQueue<customQueueEntry> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.node.data, o2.node.data));
    queue.add(new customQueueEntry(tree, Integer.MIN_VALUE, Integer.MAX_VALUE));

    while(!queue.isEmpty()){
      if(queue.peek().node.data <= queue.peek().upper && queue.peek().node.data >= queue.peek().lower){
        customQueueEntry parent = queue.poll();
        if(parent.node.left != null){
          queue.add(new customQueueEntry(parent.node.left, parent.lower, parent.node.data));
        }
        if(parent.node.right != null){
          queue.add(new customQueueEntry(parent.node.right, parent.node.data, parent.upper));
        }
      }
      else
        return false;
    }
    return true;
  }

  private static class customQueueEntry{
    public BinaryTreeNode<Integer> node;
    public int lower;
    public int upper;

    public customQueueEntry(BinaryTreeNode<Integer> node, int lower, int upper){
      this.node = node;
      this.lower = lower;
      this.upper = upper;
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeABst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
